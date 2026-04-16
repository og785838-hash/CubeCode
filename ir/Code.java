package ir;

import ir.tac.OpCode;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;

public class Code
{
    public List<TAC> lines;
    public Operand result;

    public Code()
    {
        this.lines = new ArrayList<>();
    }

    public void add(Code code)
    {
        this.lines.addAll(code.lines);
        this.result = code.result;
    }

    public void add(TAC tac)
    {
        this.lines.add(tac);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (TAC line : this.lines)
        {
            if (line.opCode != OpCode.LABEL)
            {
                sb.append("\t").append(line);
            }
            else
            {
                sb.append(line);
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
