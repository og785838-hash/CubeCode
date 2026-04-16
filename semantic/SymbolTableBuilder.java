package semantic;

import ast.ProgramNode;
import ast.Type;
import ast.decl.DeclNode;
import ast.decl.FuncDeclNode;
import ast.decl.ParamNode;
import ast.stmt.*;
import ast.val.FStrNode;
import ast.val.TextNode;
import ast.val.ValNode;
import ast.val.expr.BinExprNode;
import ast.val.expr.ExprNode;
import ast.val.expr.CallExprNode;
import ast.val.expr.VarRefNode;
import ast.visitor.generic.ASTVisitor;

import javax.sql.ConnectionPoolDataSource;
import java.util.HashSet;
import java.util.Stack;

public class SymbolTableBuilder implements ASTVisitor
{
    private final TableMap tables;
    private final Stack<Integer> scope;
    private int numScopes;
    private int numBlocks;

    public SymbolTableBuilder(ProgramNode prog)
    {
        this.tables = new TableMap();
        this.scope = new Stack<>();
        this.visit(prog);
    }

    // VISITOR IMPLEMENTATION

    @Override
    public void visitProgramNode(ProgramNode node)
    {
        SymbolTable table = new SymbolTable("GLOBAL");

        this.scope.push(this.numScopes);
        this.addTable(table);
        this.numScopes++;

        // DECLARATIONS

        for (DeclNode d : node.declList)
        {
            this.visit(d);
        }

        // STATEMENTS

        for (FuncDeclNode s : node.funcDeclList)
        {
            this.visit(s);
        }

        this.scope.pop();
    }

    @Override
    public void visitDeclNode(DeclNode node)
    {
        for (String id : node.idList)
        {
            Symbol symbol = new Symbol();

            symbol.meta = Type.Meta.EVAL;
            symbol.type = node.type;
            symbol.id = id;

            this.addSymbol(symbol);
        }
    }

    @Override
    public void visitFuncDeclNode(FuncDeclNode node)
    {
        // ADD REFERENCE SYMBOL

        Symbol funcRef = new Symbol();

        funcRef.meta = Type.Meta.BLOCK;
        funcRef.type = node.ret;
        funcRef.id = node.id;

        this.addSymbol(funcRef);

        // NOW CREATE TABLE

        SymbolTable table = new SymbolTable(node.id);

        this.scope.push(this.numScopes);
        this.addTable(table);
        this.numScopes++;

        for (ParamNode p : node.paramList)
        {
            this.visit(p);

            funcRef.references.add(this.getCurrSymbol(p.id));
        }

        for (DeclNode d : node.declList)
        {
            this.visit(d);
        }

         for (StmtNode s : node.stmtList)
         {
             this.visit(s);
         }

        this.scope.pop();
    }

    @Override
    public void visitParamNode(ParamNode node)
    {
        Symbol symbol = new Symbol();

        symbol.meta = Type.Meta.EVAL;
        symbol.type = node.type;
        symbol.id = node.id;

        this.addSymbol(symbol);

        node.sym = symbol;
    }

    @Override
    public void visitStmtNode(StmtNode node)
    {
        if (node instanceof BranchNode branch)
        {
            this.visitBranchNode(branch);
        }
        else if (node instanceof WhileNode whileNode)
        {
            this.visitWhileNode(whileNode);
        }
        else if (node instanceof AssignNode assign)
        {
            this.visitAssignNode(assign);
        }
        else if (node instanceof CmdNode cmd)
        {
            this.visitCmdNode(cmd);
        }
        else if (node instanceof ReturnNode ret)
        {
            this.visitReturnNode(ret);
        }
        else if (node instanceof CallStmtNode call)
        {
            this.visitCallStmtNode(call);
        }
    }

    @Override
    public void visitBranchNode(BranchNode node)
    {
        for (CondBlockNode condBlock : node.branches)
        {
            this.visitCondBlockNode(condBlock);
        }
    }

    @Override
    public void visitWhileNode(WhileNode node)
    {
        this.visitCondBlockNode(node);
    }

    @Override
    public void visitCondBlockNode(CondBlockNode node)
    {
        // ADD REFERENCE SYMBOL
        Symbol blockRef = new Symbol();

        this.numBlocks++;

        blockRef.meta = Type.Meta.BLOCK;
        blockRef.type = Type.VOID;
        blockRef.id = "BLOCK_" + this.numBlocks;

        this.addSymbol(blockRef);

        // NOW CREATE TABLE
        SymbolTable table = new SymbolTable(blockRef.id);

        this.scope.push(this.numScopes);
        this.addTable(table);
        this.numScopes++;

        this.visit(node.cond);

        for (DeclNode d : node.declList)
        {
            this.visit(d);
        }

        for (StmtNode s : node.stmtList)
        {
            this.visit(s);
        }

        this.scope.pop();
    }

    @Override
    public void visitCondNode(CondNode node)
    {
        this.visit(node.left);
        this.visit(node.right);
    }

    @Override
    public void visitAssignNode(AssignNode node)
    {
        node.sym = this.getCurrSymbol(node.id);

        this.visit(node.value);
    }

    @Override
    public void visitCmdNode(CmdNode node)
    {
        this.visitFStrNode(node.cmd);
    }

    @Override
    public void visitReturnNode(ReturnNode node)
    {
        this.visit(node.val);
    }

    @Override
    public void visitCallStmtNode(CallStmtNode node)
    {
        this.visit(node.call);
    }

    @Override
    public void visitValNode(ValNode node)
    {
        if (node instanceof ExprNode expr)
        {
            this.visitExprNode(expr);
        }
        else if (node instanceof FStrNode fStr)
        {
            this.visitFStrNode(fStr);
        }
    }

    @Override
    public void visitExprNode(ExprNode node)
    {
        if (node instanceof BinExprNode bin)
        {
            this.visitExprNode(bin.left);
            this.visitExprNode(bin.right);
        }
        else if (node instanceof CallExprNode func)
        {
            func.sym = this.getCurrSymbol(func.id);

            for (ValNode arg : func.args)
            {
                this.visitValNode(arg);
            }
        }
        else if (node instanceof VarRefNode var)
        {
            var.sym = this.getCurrSymbol(var.id);
        }
    }

    @Override
    public void visitFStrNode(FStrNode node)
    {
        for (ValNode val : node.parts)
        {
            this.visitValNode(val);
        }
    }

    // RESOLUTION

    public void resolve()
    {
        this.resolve(new HashSet<>(), this.getTable("GLOBAL"));
    }

    private void resolve(HashSet<Symbol> seen, SymbolTable table)
    {
        seen = new HashSet<>(seen); // Copy seen set to branch - DFS

        for (Symbol s : table.symbols.values())
        {
            // COLLECT SYMBOLS IN CURRENT SCOPE (TOP-DOWN ORDER)
            if (!seen.add(s))
            {
                throw new RuntimeException("SYMBOL RESOLUTION ERROR: Identical variables within the same scope are disallowed.");
            }

            // BRANCH TO FUNC + BLOCK REFERENCES
            if (s.meta == Type.Meta.BLOCK)
            {
                this.resolve(seen, this.getTable(s.id));
            }
        }
    }

    // AUXILIARY

    private void addTable(SymbolTable table)
    {
        this.tables.put(this.scope.peek(), table);
    }

    public void addSymbol(Symbol symbol)
    {
        this.tables.get(this.scope.peek()).add(symbol);
    }

    public SymbolTable getTable(int scopeIndex)
    {
        return this.tables.get(scopeIndex);
    }

    public SymbolTable getTable(String title)
    {
        return this.tables.get(title);
    }

    private Symbol getCurrSymbol(String id)
    {
        Stack<Integer> scope = new Stack<>();
        scope.addAll(this.scope);

        while (!scope.isEmpty())
        {
            int scopeIndex = scope.pop();
            SymbolTable tb = this.getTable(scopeIndex);
            Symbol sym = tb.symbols.get(id);
            if (sym != null)
            {
                return sym;
            }
        }

        throw new RuntimeException("SYMBOL TABLE ERROR: Unknown symbol referenced: " + id);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (SymbolTable tb : this.tables)
        {
            if (!sb.isEmpty())
            {
                sb.append("\n\n");
            }

            sb.append(tb.toString());
        }

        return sb.toString();
    }
}
