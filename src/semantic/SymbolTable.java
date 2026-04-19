package semantic;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable
{
    public String title;
    public Map<String, Symbol> symbols;

    public SymbolTable(String title)
    {
        this.title = title;
        this.symbols = new HashMap<>();
    }

    public void add(Symbol symbol)
    {
        if (this.symbols.putIfAbsent(symbol.id, symbol) != null)
        {
            throw new RuntimeException("SYMBOL RESOLUTION ERROR: Identical variables within the same scope are disallowed.");
        }

        symbol.uniqueID = symbol.id + "_" + this.title;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Symbol Table: ").append(this.title);

        for (Symbol s : this.symbols.values())
        {
            sb.append("\n\t").append(s);
        }

        return sb.toString();
    }
}
