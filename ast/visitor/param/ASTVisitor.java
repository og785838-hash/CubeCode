package ast.visitor.param;

import ast.ASTNode;
import ast.ProgramNode;
import ast.decl.DeclNode;
import ast.decl.FuncDeclNode;
import ast.decl.ParamNode;
import ast.stmt.*;
import ast.val.FStrNode;
import ast.val.TextNode;
import ast.val.ValNode;
import ast.val.expr.*;

public interface ASTVisitor<T>
{
    default T visit(ASTNode node)
    {
        if (node instanceof ProgramNode prog)
        {
            return this.visitProgramNode(prog);
        }
        else if (node instanceof DeclNode decl)
        {
            return this.visitDeclNode(decl);
        }
        else if (node instanceof FuncDeclNode func)
        {
            return this.visitFuncDeclNode(func);
        }
        else if (node instanceof ParamNode param)
        {
            return this.visitParamNode(param);
        }
        else if (node instanceof StmtNode stmt)
        {
            return this.visitStmtNode(stmt);
        }
        else if (node instanceof ValNode val)
        {
            return this.visitValNode(val);
        }

        throw new RuntimeException("AST VISITOR ERROR: Unknown AST node.");
    }

    default T visitProgramNode(ProgramNode node) { return null; }
    default T visitDeclNode(DeclNode node) { return null; }
    default T visitFuncDeclNode(FuncDeclNode node) { return null; }
    default T visitParamNode(ParamNode node) { return null; }
    default T visitStmtNode(StmtNode node) { return null; }
    default T visitBranchNode(BranchNode node) { return null; }
    default T visitWhileNode(WhileNode node) { return null; }
    default T visitCondNode(CondNode node) { return null; }
    default T visitCondBlockNode(CondBlockNode node) { return null; }
    default T visitAssignNode(AssignNode node) { return null; }
    default T visitCmdNode(CmdNode node) { return null; }
    default T visitCallStmtNode(CallStmtNode node) { return null; }
    default T visitReturnNode(ReturnNode node) { return null; }
    default T visitContNode(ContNode node) { return null; }
    default T visitBreakNode(BreakNode node) { return null; }
    default T visitValNode(ValNode node) { return null; }
    default T visitFStrNode(FStrNode node) { return null; }
    default T visitTextNode(TextNode node) { return null; }
    default T visitExprNode(ExprNode node) { return null; }
    default T visitBinExprNode(BinExprNode node) { return null; }
    default T visitCallExprNode(CallExprNode node) { return null; }
    default T visitFloatNode(FloatNode node) { return null; }
    default T visitIntNode(IntNode node) { return null; }
    default T visitVarRefNode(VarRefNode node) { return null; }
}
