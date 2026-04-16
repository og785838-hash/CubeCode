package semantic;

import ast.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Symbol
{
    public Type.Meta meta;
    public Type type;
    public String id;

    public String uniqueID;
    public List<Symbol> references;

    public Symbol()
    {
        this.references = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Symbol s)) return false;
        return ((s.meta == this.meta) && (Objects.equals(s.id, this.id)));
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.meta, this.id);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this.meta.toString());

        int rLen = 5 - this.meta.toString().length();

        sb.append(" ".repeat(Math.max(0, rLen))).append(" | ");

        rLen = 11 - this.type.toString().length();

        sb.append(this.type);

        sb.append(" ".repeat(Math.max(0, rLen))).append(" | ");

        sb.append("ID: ").append(this.id);

        return sb.toString();
    }
}
