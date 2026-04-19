package ast.val.expr;

import ast.val.ValNode;
import semantic.Symbol;

import java.util.ArrayList;
import java.util.List;

public class CallExprNode extends ExprNode
{
    public String id;
    public List<ValNode> args;
    public Symbol sym;

    public CallExprNode()
    {
        this.args = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("FuncCallNode:");
        sb.append("\n\tID: ").append(this.id);

        for (ValNode arg : this.args)
        {
            sb.append("\n\t").append(arg.toString().replaceAll("\n", "\n\t"));
        }

        return sb.toString();
    }
}
