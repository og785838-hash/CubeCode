package ast.val;

import java.util.ArrayList;
import java.util.List;

public class FStrNode extends ValNode
{
    public List<ValNode> parts;

    public FStrNode()
    {
        this.parts = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("FStrNode:");

        for (ValNode val : this.parts)
        {
            sb.append("\n\t").append(val.toString().replace("\n", "\n\t"));
        }

        return sb.toString();
    }
}
