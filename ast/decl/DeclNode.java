package ast.decl;

import ast.ASTNode;
import ast.Type;
import semantic.Symbol;

import java.util.ArrayList;
import java.util.List;

public class DeclNode extends ASTNode
{
    public Type type;
    public List<String> idList;

    public DeclNode()
    {
        this.idList = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("DeclNode:");
        sb.append("\n\t").append(this.type.toString());

        for (String id : this.idList)
        {
            sb.append("\n\tID: ").append(id);
        }

        return sb.toString();
    }
}