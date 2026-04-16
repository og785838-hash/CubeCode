package ast.stmt;

import ast.ASTNode;
import ast.Operator;
import ast.val.expr.ExprNode;

public class CondNode extends ASTNode
{
    public Operator op;
    public ExprNode left;
    public ExprNode right;

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("CondNode:");
        sb.append("\n\t").append(this.op.toString());
        sb.append("\n\t").append(this.left.toString().replaceAll("\n", "\n\t"));
        sb.append("\n\t").append(this.right.toString().replaceAll("\n", "\n\t"));

        return sb.toString();
    }
}
