package ast.stmt;

import ast.decl.DeclNode;

import java.util.ArrayList;
import java.util.List;

public class CondBlockNode extends StmtNode
{
    public CondNode cond;
    public List<DeclNode> declList;
    public List<StmtNode> stmtList;

    public CondBlockNode()
    {
        this.declList = new ArrayList<>();
        this.stmtList = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CondBlockNode:");


        String indented;

        if (this.cond != null)
        {
            indented = this.cond.toString().replaceAll("\n", "\n\t");
            sb.append("\n\t").append(indented);
        }

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
