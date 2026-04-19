package codegen.translate.structure;

import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JUMP extends Instr
{
    public JUMP(int row, TAC instr, Map<String, Integer> look)
    {
        this.blocks = new ArrayList<>();

        List<String> inner = JUMP.component(row, 0, instr.op1, look);

        // CLEAR CURRENT POWER

        this.blocks.add(new Impulse(row, 0, inner.getFirst()));

        // POWER LABEL INSTR

        this.blocks.add(new Chain(row, this.blocks.size(), inner.get(1)));
    }

    public static List<String> component(int row, int col, Operand label, Map<String, Integer> look)
    {
        List<String> inner = new ArrayList<>();

        // CLEAR CURRENT POWER

        inner.add("setblock ~ ~1 ~ air replace");

        // POWER LABEL INSTR

        int dist = look.get(label.id) - row;

        inner.add("setblock ~" + dist + " ~1 ~" + -(col + 1) + " redstone_block");

        return inner;
    }
}
