package ast.stmt;

import ast.val.ValNode;

public class ReturnNode extends StmtNode
{
    public ValNode val;

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ReturnNode:");

        if (this.val != null)
        {
            sb.append("\n\t").append(this.val.toString().replace("\n", "\n\t"));
        }

        return sb.toString();
    }
}
