package ast.stmt;

import java.util.ArrayList;
import java.util.List;

public class BranchNode extends StmtNode
{
    public List<CondBlockNode> branches;

    public BranchNode()
    {
        this.branches = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("BranchNode:");

        for (CondBlockNode condBlock : this.branches)
        {
            String indented = condBlock.toString().replaceAll("\n", "\n\t");
            sb.append("\n\t").append(indented);
        }

        return sb.toString();
    }
}
