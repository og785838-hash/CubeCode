package codegen.translate.io;

import codegen.Translate;
import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;

public class POP extends Instr
{
    public POP(int row, TAC instr)
    {
        this.blocks = new ArrayList<>();

        List<String> pop = POP.component(instr.args);

        this.blocks.add(new Impulse(row, 0, pop.getFirst()));

        for (int i = 1; i < pop.size(); i++)
        {
            this.blocks.add(new Chain(row, this.blocks.size(), pop.get(i)));
        }

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size()));
    }

    public static List<String> component(List<Operand> ops)
    {
        List<String> pop = new ArrayList<>();

        // POP TO EACH VAR FROM STACK

        String modify = "data modify storage ";
        String from = " set from storage stack data[-1]";
        String remove = "data remove storage stack data[-1]";

        for (Operand op : ops.reversed())
        {
            pop.add(modify + op.meta + " " + op.id + from);
            pop.add(remove);
        }

        return pop;
    }
}
