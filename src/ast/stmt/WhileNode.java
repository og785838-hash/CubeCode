package ast.stmt;

import ast.decl.DeclNode;

public class WhileNode extends CondBlockNode
{
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("WhileNode:");

        String indented = this.cond.toString().replaceAll("\n", "\n\t");
        sb.append("\n\t").append(indented);

        for (DeclNode decl : this.declList)
        {
            indented = decl.toString().replaceAll("\n", "\n\t");
            sb.append("\n\t").append(indented);
        }

        for (StmtNode stmt : this.stmtList)
        {
            indented = stmt.toString().replaceAll("\n", "\n\t");
            sb.append("\n\t").append(indented);
        }

        return sb.toString();
    }
}
