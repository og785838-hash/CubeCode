package ast.val.expr;

public class IntNode extends ExprNode
{
    public int val;

    public IntNode(String val)
    {
        this.val = Integer.parseInt(val);
    }

    @Override
    public String toString()
    {
        return "IntNode: " + this.val;
    }
}
