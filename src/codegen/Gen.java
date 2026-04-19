package codegen;

import codegen.block.Impulse;
import codegen.translate.Instr;
import ir.Code;
import ir.tac.OpCode;
import ir.tac.TAC;

import java.util.*;

public class Gen
{
    public List<Instr> instrList;
    public Map<String, Integer> look;

    public Gen(Code code)
    {
        this.instrList = new ArrayList<>();
        this.look = new HashMap<>();

        this.address(code);
        this.run(code);
    }

    private void address(Code code)
    {
        int row = 0;

        for (TAC instr : code.lines)
        {
            if (instr.opCode == OpCode.LABEL)
            {
                this.look.put(instr.op1.id, row);
            }

            row += instr.opCode.width;
        }
    }

    private void run(Code code)
    {
        int row = 0;

        for (TAC line : code.lines)
        {
            this.instrList.add(Instr.get(row, line, this.look));
            row += line.opCode.width;
        }
    }

    private List<String> startUp()
    {
        this.instrList.getFirst().blocks.addFirst(new Impulse(0, -1, "say Program Launch"));

        List<String> start = new ArrayList<>();

        start.add("gamerule commandBlockOutput false");
        start.add("setblock ~ ~1 ~-1 stone_button[face=floor]");

        return start;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (String init : this.startUp())
        {
            sb.append(init).append("\n");
        }

        for (Instr instr : this.instrList)
        {
            sb.append(instr.toString()).append("\n");
        }

        return sb.toString().trim();
    }
}
