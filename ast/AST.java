package ast;

import ast.decl.DeclNode;
import ast.decl.FuncDeclNode;
import ast.decl.ParamNode;
import ast.stmt.*;
import ast.val.FStrNode;
import ast.val.TextNode;
import ast.val.ValNode;
import ast.val.expr.*;
import gen.CCParser;
import gen.CCBaseVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class AST extends CCBaseVisitor<ASTNode>
{
    @Override
    public ASTNode visitProgram(CCParser.ProgramContext ctx)
    {
        ProgramNode prog = new ProgramNode();

        for (CCParser.DeclContext decl: ctx.decl())
        {
            prog.declList.add((DeclNode) this.visit(decl));
        }

        for (CCParser.FuncDeclContext funcDecl: ctx.funcDecl())
        {
            prog.funcDeclList.add((FuncDeclNode) this.visit(funcDecl));
        }

        return prog;
    }

    @Override
    public ASTNode visitDecl(CCParser.DeclContext ctx)
    {
        DeclNode decl = new DeclNode();

        decl.type = Type.get(ctx.type.getText());

        for (TerminalNode id : ctx.ID())
        {
            decl.idList.add(id.getText());
        }

        return decl;
    }

    @Override
    public ASTNode visitFuncDecl(CCParser.FuncDeclContext ctx)
    {
        FuncDeclNode funcDecl = new FuncDeclNode();

        funcDecl.ret = Type.get(ctx.any_type().getText());
        funcDecl.id = ctx.ID().getText();

        if (ctx.paramList() != null)
        {
            for (CCParser.ParamContext param : ctx.paramList().param())
            {
                funcDecl.paramList.add((ParamNode) this.visit(param));
            }
        }

        for (CCParser.DeclContext decl : ctx.body().decl())
        {
            funcDecl.declList.add((DeclNode) this.visit(decl));
        }

        for (CCParser.StmtContext stmt : ctx.body().stmt())
        {
            funcDecl.stmtList.add((StmtNode) this.visit(stmt));
        }

        return funcDecl;
    }

    @Override
    public ASTNode visitParam(CCParser.ParamContext ctx)
    {
        ParamNode param = new ParamNode();

        param.type = Type.get(ctx.type.getText());
        param.id = ctx.ID().getText();

        return param;
    }

    @Override
    public ASTNode visitStmt(CCParser.StmtContext ctx)
    {
        if (ctx.assignStmt() != null)
        {
            return this.visit(ctx.assignStmt());
        }
        else if (ctx.cmdStmt() != null)
        {
            return this.visit(ctx.cmdStmt());
        }
        else if (ctx.callStmt() != null)
        {
            return this.visit(ctx.callStmt());
        }
        else if (ctx.branchStmt() != null)
        {
            return this.visit(ctx.branchStmt());
        }
        else if (ctx.whileStmt() != null)
        {
            return this.visit(ctx.whileStmt());
        }
        else if (ctx.retStmt() != null)
        {
            return this.visit(ctx.retStmt());
        }
        else if (ctx.contStmt() != null)
        {
            return this.visit(ctx.contStmt());
        }
        else if (ctx.breakStmt() != null)
        {
            return this.visit(ctx.breakStmt());
        }

        throw new RuntimeException("AST ERROR: Unknown statement: " + ctx.getText());
    }

    @Override
    public ASTNode visitAssignStmt(CCParser.AssignStmtContext ctx)
    {
        AssignNode assignStmt = new AssignNode();

        assignStmt.id = ctx.ID().getText();

        if (ctx.expr() != null)
        {
            assignStmt.value = (ExprNode) this.visit(ctx.expr());
        }
        else if (ctx.fStr() != null)
        {
            assignStmt.value = (FStrNode) this.visit(ctx.fStr());
        }

        return assignStmt;
    }

    @Override
    public ASTNode visitCmdStmt(CCParser.CmdStmtContext ctx)
    {
        CmdNode cmdStmt = new CmdNode();
        cmdStmt.cmd = (FStrNode) this.visit(ctx.fStr());

        return cmdStmt;
    }

    @Override
    public ASTNode visitCallStmt(CCParser.CallStmtContext ctx)
    {
        return new CallStmtNode((CallExprNode) this.visitFuncCall(ctx.funcCall()));
    }

    @Override
    public ASTNode visitBranchStmt(CCParser.BranchStmtContext ctx)
    {
        BranchNode branch = new BranchNode();

        branch.branches.add((CondBlockNode) this.visit(ctx.ifBlock()));

        for (CCParser.ElifBlockContext elifBlock : ctx.elifBlock())
        {
            branch.branches.add((CondBlockNode) this.visit(elifBlock));
        }

        if (ctx.elseBlock() != null)
        {
            branch.branches.add((CondBlockNode) this.visit(ctx.elseBlock()));
        }

        return branch;
    }

    @Override
    public ASTNode visitIfBlock(CCParser.IfBlockContext ctx)
    {
        CondBlockNode ifBlock = new CondBlockNode();

        ifBlock.cond = (CondNode) this.visit(ctx.cond());

        CCParser.BodyContext body = ctx.body();

        for (CCParser.DeclContext decl : body.decl())
        {
            ifBlock.declList.add((DeclNode) this.visit(decl));
        }

        for (CCParser.StmtContext stmt : body.stmt())
        {
            ifBlock.stmtList.add((StmtNode) this.visit(stmt));
        }

        return ifBlock;
    }

    @Override
    public ASTNode visitElifBlock(CCParser.ElifBlockContext ctx)
    {
        CondBlockNode elifBlock = new CondBlockNode();

        elifBlock.cond = (CondNode) this.visit(ctx.cond());

        CCParser.BodyContext body = ctx.body();

        for (CCParser.DeclContext decl : body.decl())
        {
            elifBlock.declList.add((DeclNode) this.visit(decl));
        }

        for (CCParser.StmtContext stmt : body.stmt())
        {
            elifBlock.stmtList.add((StmtNode) this.visit(stmt));
        }

        return elifBlock;
    }

    @Override
    public ASTNode visitElseBlock(CCParser.ElseBlockContext ctx)
    {
        CondBlockNode elseBlock = new CondBlockNode();

        CCParser.BodyContext body = ctx.body();

        for (CCParser.DeclContext decl : body.decl())
        {
            elseBlock.declList.add((DeclNode) this.visit(decl));
        }

        for (CCParser.StmtContext stmt : body.stmt())
        {
            elseBlock.stmtList.add((StmtNode) this.visit(stmt));
        }

        return elseBlock;
    }

    @Override
    public ASTNode visitWhileStmt(CCParser.WhileStmtContext ctx)
    {
        WhileNode whileStmt = new WhileNode();

        whileStmt.cond = (CondNode) this.visit(ctx.cond());

        CCParser.BodyContext body = ctx.body();

        for (CCParser.DeclContext decl : body.decl())
        {
            whileStmt.declList.add((DeclNode) this.visit(decl));
        }

        for (CCParser.StmtContext stmt : body.stmt())
        {
            whileStmt.stmtList.add((StmtNode) this.visit(stmt));
        }

        return whileStmt;
    }

    @Override
    public ASTNode visitCond(CCParser.CondContext ctx)
    {
        CondNode cond = new CondNode();

        cond.op = Operator.get(ctx.op.getText());
        cond.left = (ExprNode) this.visit(ctx.expr(0));
        cond.right = (ExprNode) this.visit(ctx.expr(1));

        return cond;
    }

    @Override
    public ASTNode visitRetStmt(CCParser.RetStmtContext ctx)
    {
        ReturnNode ret = new ReturnNode();

        if (ctx.expr() != null)
        {
            ret.val = (ValNode) this.visit(ctx.expr());
        }
        else if (ctx.fStr() != null)
        {
            ret.val = (ValNode) this.visit(ctx.fStr());
        }

        return ret;
    }

    @Override
    public ASTNode visitContStmt(CCParser.ContStmtContext ctx)
    {
        return new ContNode();
    }

    @Override
    public ASTNode visitBreakStmt(CCParser.BreakStmtContext ctx)
    {
        return new BreakNode();
    }

    @Override
    public ASTNode visitFStr(CCParser.FStrContext ctx)
    {
        FStrNode fStr = new FStrNode();

        for (CCParser.FStrValContext fVal : ctx.fStrVal())
        {
            if (fVal.expr() != null)
            {
                fStr.parts.add((ValNode) this.visit(fVal.expr()));
            }
            else if (fVal.fStr() != null)
            {
                fStr.parts.add((ValNode) this.visit(fVal.fStr()));
            }
            else if (fVal.STR() != null)
            {
                fStr.parts.add(new TextNode(fVal.STR().getText()));
            }
        }

        return fStr;
    }

    @Override
    public ASTNode visitExpr(CCParser.ExprContext ctx)
    {
        ExprNode right = (ExprNode) this.visit(ctx.term());

        if (ctx.expr() != null)
        {
            BinExprNode binExpr = new BinExprNode();

            binExpr.op = Operator.get(ctx.op.getText());
            binExpr.left = (ExprNode) this.visit(ctx.expr());
            binExpr.right = right;

            return binExpr;
        }

        return right;
    }

    @Override
    public ASTNode visitTerm(CCParser.TermContext ctx)
    {
        ExprNode right = (ExprNode) this.visit(ctx.factor());

        if (ctx.term() != null)
        {
            BinExprNode binExpr = new BinExprNode();

            binExpr.op = Operator.get(ctx.op.getText());
            binExpr.left = (ExprNode) this.visit(ctx.term());
            binExpr.right = right;

            return binExpr;
        }

        return right;
    }

    @Override
    public ASTNode visitFactor(CCParser.FactorContext ctx)
    {
        if (ctx.expr() != null)
        {
            return this.visit(ctx.expr());
        }
        if (ctx.funcCall() != null)
        {
            return this.visitFuncCall(ctx.funcCall());
        }
        if (ctx.INT() != null)
        {
            return new IntNode(ctx.INT().getText());
        }
        if (ctx.FLOAT() != null)
        {
            return new FloatNode(ctx.FLOAT().getText());
        }
        if (ctx.ID() != null)
        {
            return new VarRefNode(ctx.ID().getText());
        }

        throw new RuntimeException("AST ERROR: Unknown expression.");
    }

    @Override
    public ASTNode visitFuncCall(CCParser.FuncCallContext ctx)
    {
        CallExprNode callExpr = new CallExprNode();

        callExpr.id = ctx.ID().getText();

        CCParser.ArgsContext args = ctx.args();

        for (int i = 0; i < args.getChildCount(); i++)
        {
            ParseTree child = args.getChild(i);

            if (child instanceof CCParser.FStrContext)
            {
                callExpr.args.add((ValNode) this.visit(child));
            }
            else if (child instanceof CCParser.ExprContext)
            {
                callExpr.args.add((ValNode) this.visit(child));
            }
        }

        return callExpr;
    }
}