package ast.val.expr;

import semantic.Symbol;

public class VarRefNode extends ExprNode
{
    public String id;
    public Symbol sym;

    public VarRefNode(String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "VarRefNode: " + this.id;
    }
}
