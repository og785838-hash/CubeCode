package ast.visitor.generic;

import ast.ASTNode;
import ast.ProgramNode;
import ast.decl.DeclNode;
import ast.decl.FuncDeclNode;
import ast.decl.ParamNode;
import ast.stmt.*;
import ast.val.FStrNode;
import ast.val.ValNode;
import ast.val.expr.ExprNode;
import ast.val.expr.FloatNode;
import ast.val.expr.IntNode;
import ast.val.expr.VarRefNode;

public interface ASTVisitor
{
    default void visit(ASTNode node)
    {
        if (node instanceof ProgramNode prog)
        {
            this.visitProgramNode(prog);
        }
        else if (node instanceof FuncDeclNode func)
        {
            this.visitFuncDeclNode(func);
        }
        else if (node instanceof ParamNode param)
        {
            this.visitParamNode(param);
        }
        else if (node instanceof DeclNode decl)
        {
            this.visitDeclNode(decl);
        }
        else if (node instanceof StmtNode stmt)
        {
            this.visitStmtNode(stmt);
        }
        else if (node instanceof ExprNode expr)
        {
            this.visitExprNode(expr);
        }
        else if (node instanceof CondNode cond)
        {
            this.visitCondNode(cond);
        }
    }

    default void visitProgramNode(ProgramNode node) {}
    default void visitDeclNode(DeclNode node) {}
    default void visitFuncDeclNode(FuncDeclNode node) {}
    default void visitParamNode(ParamNode node) {}
    default void visitStmtNode(StmtNode node) {}
    default void visitBranchNode(BranchNode node) {}
    default void visitWhileNode(WhileNode node) {}
    default void visitCondBlockNode(CondBlockNode node) {}
    default void visitAssignNode(AssignNode node) {}
    default void visitCmdNode(CmdNode node) {}
    default void visitCallStmtNode(CallStmtNode node) {}
    default void visitReturnNode(ReturnNode node) {}
    default void visitCondNode(CondNode node) {}
    default void visitValNode(ValNode node) {}
    default void visitFStrNode(FStrNode node) {}
    default void visitExprNode(ExprNode node) {}
    default void visitBinExprNode(ExprNode node) {}
    default void visitFloatNode(FloatNode node) {}
    default void visitIntNode(IntNode node) {}
    default void visitVarRefNode(VarRefNode node) {}
}
