package ast.stmt;

import ast.val.expr.CallExprNode;

public class CallStmtNode extends StmtNode
{
    public CallExprNode call;

    public CallStmtNode() {}

    public CallStmtNode(CallExprNode func)
    {
        this.call = func;
    }

    @Override
    public String toString()
    {
        return this.call.toString();
    }
}
