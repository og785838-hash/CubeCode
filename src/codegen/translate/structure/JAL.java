package codegen.translate.structure;

import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JAL extends Instr
{
    public JAL(int row, TAC instr, Map<String, Integer> look)
    {
        this.blocks = new ArrayList<>();

        List<String> jal = JAL.component(row, 0, instr.op1, look);

        this.blocks.add(new Impulse(row, 0, jal.getFirst()));

        for (int i = 1; i < jal.size(); i++)
        {
            this.blocks.add(new Chain(row, this.blocks.size(), jal.get(i)));
        }
    }

    public static List<String> component(int row, int col, Operand label, Map<String, Integer> look)
    {
        List<String> jal = new ArrayList<>();

        // CLEAR CURRENT POWER

        jal.add("setblock ~ ~1 ~ air replace");

        col++;

        // LINK

        jal.add("data modify storage link data append value " + (row + 1));

        col++;

        // POWER LABEL INSTR

        int dist = look.get(label.id) - row;

        jal.add("setblock ~" + dist + " ~1 ~" + -col + " redstone_block");

        return jal;
    }
}
