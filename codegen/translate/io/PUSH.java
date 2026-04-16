package codegen.translate.io;

import codegen.Translate;
import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import ir.tac.OpType;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PUSH extends Instr
{
    public PUSH(int row, TAC instr, Map<String, Integer> look)
    {
        this.blocks = new ArrayList<>();

        List<String> push = PUSH.component(instr, look);

        this.blocks.add(new Impulse(row, 0, push.getFirst()));

        for (int i = 1; i < push.size(); i++)
        {
            this.blocks.add(new Chain(row, i, push.get(i)));
        }

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size()));
    }

    public static List<String> component(TAC instr, Map<String, Integer> look)
    {
        List<String> push = new ArrayList<>();

        // PUSH EACH ITEM TO STACK

        String stack = "data modify storage stack data append ";
        String storage = "from storage ";
        String value = "value {value:";

        for (Operand op : instr.args.reversed())
        {
            if (op.meta != OpType.Meta.IMM)
            {
                push.add(stack + storage + op.meta + " " + op.id);
            }
            else if (op.type == OpType.INT)
            {
                push.add(stack + value + op.id + "}");
            }
            else if (op.type == OpType.FLOAT)
            {
                push.add(stack + value + op.id + "f}");
            }
            else if (op.type == OpType.STR)
            {
                push.add(stack + value + "\\\"" + op.id + "\\\"}");
            }
        }

        return push;
    }
}
