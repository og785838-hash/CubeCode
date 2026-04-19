package ir;

import ast.Operator;
import ast.ProgramNode;
import ast.decl.DeclNode;
import ast.decl.FuncDeclNode;
import ast.decl.ParamNode;
import ast.stmt.*;
import ast.val.FStrNode;
import ast.val.TextNode;
import ast.val.ValNode;
import ast.val.expr.*;
import ast.visitor.param.ASTVisitor;
import ir.tac.OpCode;
import ir.tac.OpType;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.*;

public class IR implements ASTVisitor<Code>
{
    public Code code;
    private int nTemp;
    private int nLabel;

    private final Operand RET  = new Operand("$RET");

    private final Stack<Operand> breakStack;
    private final Stack<Operand> contStack;

    public IR(ProgramNode prog)
    {
        this.nTemp = 0;
        this.nLabel = 0;
        this.breakStack = new Stack<>();
        this.contStack = new Stack<>();
        this.code = this.visit(prog);
    }

    // VISITOR IMPLEMENTATION

    @Override
    public Code visitProgramNode(ProgramNode node)
    {
        Code prog = new Code();

        // GLOBAL DECLARATIONS

        Code global = new Code();

        for (DeclNode decl : node.declList)
        {
            global.add(this.visit(decl));
        }

        // FUNCTION DECLARATIONS

        Code allFunc = new Code();

        Map<String, Code> funcMap = new HashMap<>();

        for (FuncDeclNode funcDecl : node.funcDeclList)
        {
            funcMap.put(funcDecl.id, this.visit(funcDecl));
        }

        Code main = funcMap.get("main");

        if (main != null)
        {
            // PROGRAM END

            TAC end = new TAC();
            end.opCode = OpCode.LABEL;
            end.op1 = new Operand("$END");

            // JAL TO MAIN

            TAC jMain = new TAC();
            jMain.opCode = OpCode.JAL;
            jMain.op1 = new Operand("main");

            allFunc.add(jMain);

            // JUMP TO END

            TAC jEnd = new TAC();
            jEnd.opCode = OpCode.JUMP;
            jEnd.op1 = end.op1;

            allFunc.add(jEnd);

            // MAIN FUNCTION

            allFunc.add(main);
            funcMap.remove("main");

            // OTHER FUNCTIONS

            for (Code func : funcMap.values())
            {
                allFunc.add(func);
            }

            allFunc.add(end);

            prog.add(global);
            prog.add(allFunc);

            return prog;
        }
        else
        {
            throw new RuntimeException("IR GENERATION ERROR: Main function does not exist.");
        }
    }

    // FUNCTION DECLARATIONS

    @Override
    public Code visitFuncDeclNode(FuncDeclNode node)
    {
        Code func = new Code();

        // LABEL

        TAC label = new TAC();
        label.opCode = OpCode.LABEL;
        label.op1 = new Operand(node.id);

        func.add(label);

        // POP ARGS

        if (!node.paramList.isEmpty())
        {
            TAC pop = new TAC();
            pop.opCode = OpCode.POP;

            for (ParamNode param : node.paramList.reversed())
            {
                String paramID = param.sym.uniqueID;

                pop.args.add(new Operand(OpType.get(param.type), paramID));
            }

            func.add(pop);
        }

        // DECLARATIONS

        for (DeclNode decl : node.declList)
        {
            func.add(this.visit(decl));
        }

        // STATEMENTS

        for (StmtNode stmt : node.stmtList)
        {
            func.add(this.visit(stmt));
        }

        return func;
    }

    // DECLARATIONS

    @Override
    public Code visitDeclNode(DeclNode node)
    {
        return new Code();
    }

    // STATEMENTS

    @Override
    public Code visitStmtNode(StmtNode node)
    {
        if (node instanceof BranchNode branch)
        {
            return this.visitBranchNode(branch);
        }
        else if (node instanceof WhileNode whl)
        {
            return this.visitWhileNode(whl);
        }
        else if (node instanceof AssignNode assign)
        {
            return this.visitAssignNode(assign);
        }
        else if (node instanceof CmdNode cmd)
        {
            return this.visitCmdNode(cmd);
        }
        else if (node instanceof CallStmtNode call)
        {
            return this.visitCallStmtNode(call);
        }
        else if (node instanceof ReturnNode ret)
        {
            return this.visitReturnNode(ret);
        }
        else if (node instanceof ContNode cont)
        {
            return this.visitContNode(cont);
        }
        else if (node instanceof BreakNode br)
        {
            return this.visitBreakNode(br);
        }

        throw new RuntimeException("IR GENERATION ERROR: Unknown StmtNode.");
    }

    // BLOCK STATEMENTS

    @Override
    public Code visitBranchNode(BranchNode node)
    {
        Code branch = new Code();

        // EXIT BLOCK

        TAC exit = new TAC();
        exit.opCode = OpCode.LABEL;
        exit.op1 = this.newLabel();

        // CONDITIONAL BLOCKS

        Operand labelOp = this.newLabel();

        for (CondBlockNode block : node.branches)
        {
            Code code = new Code();

            // LABEL

            TAC label = new TAC();
            label.opCode = OpCode.LABEL;
            label.op1 = labelOp;

            code.add(label);

            // CONDITIONAL

            if (block.cond != null)
            {
                Code cond = this.visitCondNode(block.cond);

                labelOp = this.newLabel();
                cond.lines.getLast().op3 = labelOp;

                code.add(cond);
            }

            // DECLARATIONS

            for (DeclNode decl : block.declList)
            {
                code.add(this.visit(decl));
            }

            // STATEMENTS

            for (StmtNode stmt : block.stmtList)
            {
                code.add(this.visit(stmt));
            }

            // EXIT JUMP

            TAC jExit = new TAC();
            jExit.opCode = OpCode.JUMP;
            jExit.op1 = exit.op1;

            code.add(jExit);

            branch.add(code);
        }

        branch.add(exit);

        return branch;
    }

    @Override
    public Code visitWhileNode(WhileNode node)
    {
        Code whl = new Code();

        // LABEL

        TAC label = new TAC();
        label.opCode = OpCode.LABEL;
        label.op1 = this.newLabel();

        whl.add(label);

        // LOOP JUMP

        TAC jLoop = new TAC();
        jLoop.opCode = OpCode.JUMP;
        jLoop.op1 = label.op1;

        // OUT BLOCK

        TAC out = new TAC();
        out.opCode = OpCode.LABEL;
        out.op1 = this.newLabel();

        // STACKS

        this.breakStack.add(out.op1);
        this.contStack.add(jLoop.op1);

        // CONDITIONAL

        if (node.cond != null)
        {
            Code cond = this.visitCondNode(node.cond);
            cond.lines.getLast().op3 = out.op1;

            whl.add(cond);
        }

        // DECLARATIONS

        for (DeclNode decl : node.declList)
        {
            whl.add(this.visit(decl));
        }

        // STATEMENTS

        for (StmtNode stmt : node.stmtList)
        {
            whl.add(this.visit(stmt));
        }

        whl.add(jLoop);
        whl.add(out);

        this.breakStack.pop();
        this.contStack.pop();

        return whl;
    }

    @Override
    public Code visitCondNode(CondNode node)
    {
        Code cond = new Code();

        Code left = this.visit(node.left);
        Code right = this.visit(node.right);

        OpCode op;

        if (left.result.type == OpType.FLOAT || right.result.type == OpType.FLOAT)
        {
            op = switch (node.op)
            {
                case Operator.LT -> OpCode.BGEF;
                case Operator.GT -> OpCode.BLEF;
                case Operator.LE -> OpCode.BGTF;
                case Operator.GE -> OpCode.BLTF;
                case Operator.EQ -> OpCode.BNEF;
                case Operator.NEQ -> OpCode.BEQF;
                default -> throw new RuntimeException("IR GENERATION ERROR: Unsupported conditional operator: " + node.op);
            };
        }
        else
        {
            op = switch (node.op)
            {
                case Operator.LT -> OpCode.BGEI;
                case Operator.GT -> OpCode.BLEI;
                case Operator.LE -> OpCode.BGTI;
                case Operator.GE -> OpCode.BLTI;
                case Operator.EQ -> OpCode.BNEI;
                case Operator.NEQ -> OpCode.BEQI;
                default -> throw new RuntimeException("IR GENERATION ERROR: Unsupported conditional operator: " + node.op);
            };
        }

        TAC comp = new TAC(op, left.result, right.result, null);

        cond.add(left);
        cond.add(right);
        cond.add(comp);

        return cond;
    }

    // EVAL STATEMENTS

    @Override
    public Code visitAssignNode(AssignNode node)
    {
        Code code = new Code();

        Code val = this.visit(node.value);

        Operand res = new Operand(val.result.type, node.sym.uniqueID);
        Operand out = val.result;

        TAC tac = new TAC(OpCode.STORE, res, out,null);

        code.add(val);
        code.add(tac);

        return code;
    }

    @Override
    public Code visitCmdNode(CmdNode node)
    {
        Code code = new Code();

        Code fStr = this.visitFStrNode(node.cmd);

        TAC tac = new TAC(OpCode.CMD, fStr.result, null, null);

        code.add(fStr);
        code.add(tac);

        return code;
    }

    @Override
    public Code visitBreakNode(BreakNode node)
    {
        Code code = new Code();

        TAC jOut = new TAC();
        jOut.opCode = OpCode.JUMP;
        jOut.op1 = this.breakStack.pop();

        this.contStack.pop();

        code.add(jOut);

        return code;
    }

    @Override
    public Code visitContNode(ContNode node)
    {
        Code code = new Code();

        TAC jLoop = new TAC();
        jLoop.opCode = OpCode.JUMP;
        jLoop.op1 = this.contStack.pop();

        this.breakStack.pop();

        code.add(jLoop);

        return code;
    }

    // FUNCTION CALLS + RETURNS

    @Override
    public Code visitCallStmtNode(CallStmtNode node)
    {
        return this.visit(node.call);
    }

    @Override
    public Code visitCallExprNode(CallExprNode node)
    {
        Code call = new Code();

        // PUSH ARGUMENTS

        TAC push = new TAC();
        push.opCode = OpCode.PUSH;

        for (int i = 0; i < node.args.size(); i++)
        {
            Code arg = this.visit(node.args.get(i));

            push.args.add(arg.result);

            call.add(arg);
        }

        call.add(push);

        // FUNCTION JAL

        TAC fJal = new TAC();
        fJal.opCode = OpCode.JAL;
        fJal.op1 = new Operand(node.id);

        call.add(fJal);

        // RETRIEVE RETURNED VALUE

        Operand res = this.newTemp(this.RET.type);

        TAC ret = new TAC();
        ret.opCode = OpCode.STORE;
        ret.op1 = res;
        ret.op2 = this.RET;

        call.add(ret);
        call.result = ret.op1;

        return call;
    }

    @Override
    public Code visitReturnNode(ReturnNode node)
    {
        Code ret = new Code();

        // SAVE EXPRESSION

        if (node.val != null)
        {
            Code val = this.visit(node.val);

            this.RET.type = val.result.type;

            TAC store = new TAC();
            store.opCode = OpCode.STORE;
            store.op1 = this.RET;
            store.op2 = val.result;

            ret.add(val);
            ret.add(store);
        }

        // JUMP TO RETURN ADDRESS

        TAC jr = new TAC();
        jr.opCode = OpCode.JR;
        ret.add(jr);

        return ret;
    }

    // VALUE NODES

    @Override
    public Code visitValNode(ValNode node)
    {
        if (node instanceof ExprNode expr)
        {
            return this.visitExprNode(expr);
        }
        else if (node instanceof FStrNode fStr)
        {
            return this.visitFStrNode(fStr);
        }
        else if (node instanceof TextNode text)
        {
            return this.visitTextNode(text);
        }

        throw new RuntimeException("IR GENERATION ERROR: Unknown expression node.");
    }

    // F-STRINGS

    @Override
    public Code visitFStrNode(FStrNode node)
    {
        Code code = new Code();

        TAC fm = new TAC();
        fm.opCode = OpCode.FORMAT;
        fm.op1 = this.newTemp(OpType.STR);

        for (ValNode val : node.parts)
        {
            if (val instanceof ExprNode expr)
            {
                Code eCode = this.visit(expr);
                fm.args.add(eCode.result);
                code.add(eCode);
            }
            else if (val instanceof FStrNode fStr)
            {
                Code fCode = this.visit(fStr);
                fm.args.add(fCode.result);
                code.add(fCode);
            }
            else if (val instanceof TextNode text)
            {
                Code tCode = this.visit(text);
                fm.args.add(tCode.result);
                code.add(tCode);
            }
        }

        code.add(fm);
        code.result = fm.op1;

        return code;
    }

    @Override
    public Code visitTextNode(TextNode node)
    {
        Code text = new Code();

        Operand literal = new Operand(OpType.STR, OpType.Meta.IMM, node.text);

        TAC t = new TAC();
        t.opCode = OpCode.STORE;
        t.op1 = this.newTemp(OpType.STR);
        t.op2 = literal;

        text.add(t);
        text.result = t.op1;

        return text;
    }

    // EXPRESSIONS

    @Override
    public Code visitExprNode(ExprNode node)
    {
        if (node instanceof BinExprNode bin)
        {
            return this.visitBinExprNode(bin);
        }
        else if (node instanceof IntNode val)
        {
            return this.visitIntNode(val);
        }
        else if (node instanceof FloatNode val)
        {
            return this.visitFloatNode(val);
        }
        else if (node instanceof VarRefNode ref)
        {
            return this.visitVarRefNode(ref);
        }
        else if (node instanceof CallExprNode call)
        {
            return this.visitCallExprNode(call);
        }

        throw new RuntimeException("IR GENERATION ERROR: Unknown expression node.");
    }

    @Override
    public Code visitBinExprNode(BinExprNode node)
    {
        Code code = new Code();

        Code left = this.visitExprNode(node.left);
        Code right = this.visitExprNode(node.right);
        Operand res;

        if (left.result.type == OpType.FLOAT || right.result.type == OpType.FLOAT)
        {
            res = this.newTemp(OpType.FLOAT);
        }
        else
        {
            res = this.newTemp(OpType.INT);
        }

        OpCode op;

        if (res.type == OpType.FLOAT)
        {
            op = switch (node.op) {
                case Operator.ADD -> OpCode.ADDF;
                case Operator.SUB -> OpCode.SUBF;
                case Operator.MUL -> OpCode.MULF;
                case Operator.DIV -> OpCode.DIVF;
                default -> throw new RuntimeException("IR GENERATION ERROR: Unrecognized arithmetic operator.");
            };
        }
        else
        {
            op = switch (node.op)
            {
                case Operator.ADD -> OpCode.ADDI;
                case Operator.SUB -> OpCode.SUBI;
                case Operator.MUL -> OpCode.MULI;
                case Operator.DIV -> OpCode.DIVI;
                default -> throw new RuntimeException("IR GENERATION ERROR: Unrecognized arithmetic operator.");
            };
        }

        TAC tac = new TAC(op, res, left.result, right.result);

        code.add(left);
        code.add(right);
        code.add(tac);
        code.result = res;

        return code;
    }

    // BASE EXPRESSION FACTORS

    @Override
    public Code visitIntNode(IntNode node)
    {
        Code code = new Code();

        Operand op = new Operand(OpType.INT, OpType.Meta.IMM, Integer.toString(node.val));
        Operand res = this.newTemp(op.type);

        TAC tac = new TAC(OpCode.STORE, res, op, null);

        code.add(tac);
        code.result = res;

        return code;
    }

    @Override
    public Code visitFloatNode(FloatNode node)
    {
        Code code = new Code();

        Operand op = new Operand(OpType.FLOAT, OpType.Meta.IMM, Float.toString(node.val));
        Operand res = this.newTemp(op.type);

        TAC tac = new TAC(OpCode.STORE, res, op, null);

        code.add(tac);
        code.result = res;

        return code;
    }

    @Override
    public Code visitVarRefNode(VarRefNode node)
    {
        Code code = new Code();

        code.result = new Operand(OpType.get(node.sym.type), node.sym.uniqueID);

        return code;
    }

    // AUXILIARY HELPER METHODS

    private Operand newTemp(OpType type)
    {
        return new Operand(type, "$T" + this.nTemp++);
    }

    private Operand newLabel()
    {
        return new Operand("$L" + this.nLabel++);
    }

    @Override
    public String toString()
    {
        return this.code.toString();
    }
}
