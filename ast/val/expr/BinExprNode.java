package ast.val.expr;

import ast.Operator;

public class BinExprNode extends ExprNode
{
    public Operator op;
    public ExprNode left;
    public ExprNode right;

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("BinExprNode:");
        sb.append("\n\t").append(this.op.toString());

        String indented;

        indented = this.left.toString().replaceAll("\n", "\n\t");
        sb.append("\n\t").append(indented);

        indented = this.right.toString().replaceAll("\n", "\n\t");
        sb.append("\n\t").append(indented);

        return sb.toString();
    }
}
