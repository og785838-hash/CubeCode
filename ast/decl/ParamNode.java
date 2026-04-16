package ast.decl;

import ast.ASTNode;
import ast.Type;
import semantic.Symbol;

public class ParamNode extends ASTNode
{
    public Type type;
    public String id;
    public Symbol sym;

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("ParamNode:");
        sb.append("\n\t").append(this.type.toString());
        sb.append("\n\tID: ").append(this.id);

        return sb.toString();
    }
}
