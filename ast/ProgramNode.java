package ast;

import ast.decl.DeclNode;
import ast.decl.FuncDeclNode;
import ast.stmt.StmtNode;

import java.util.ArrayList;
import java.util.List;

public class ProgramNode extends ASTNode
{
    public List<DeclNode> declList;
    public List<FuncDeclNode> funcDeclList;

    public ProgramNode()
    {
        this.declList = new ArrayList<>();
        this.funcDeclList = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ProgramNode:");

        for (DeclNode decl : this.declList)
        {
            String indented = decl.toString().replaceAll("\n", "\n\t");
            sb.append("\n\t").append(indented);
        }

        for (FuncDeclNode funcDecl : this.funcDeclList)
        {
            String indented = funcDecl.toString().replaceAll("\n", "\n\t");
            sb.append("\n\t").append(indented);
        }

        return sb.toString();
    }
}
