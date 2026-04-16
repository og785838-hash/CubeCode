package ast.stmt;

import ast.val.ValNode;
import semantic.Symbol;

public class AssignNode extends StmtNode
{
    public String id;
    public ValNode value;
    public Symbol sym;

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("AssignNode:");
        sb.append("\n\tID: ").append(this.id);

        String indented = this.value.toString().replaceAll("\n", "\n\t");
        sb.append("\n\t").append(indented);

        return sb.toString();
    }
}
