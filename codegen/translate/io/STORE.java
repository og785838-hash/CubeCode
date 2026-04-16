package codegen.translate.io;

import codegen.Translate;
import codegen.block.Impulse;
import codegen.translate.Instr;
import ir.tac.OpType;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;

public class STORE extends Instr
{
    public STORE(int row, TAC instr)
    {
        this.blocks = new ArrayList<>();

        // STORE

        String store = STORE.component(instr.op1, instr.op2).getFirst();

        this.blocks.add(new Impulse(row, 0, store));

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size()));
    }

    public static List<String> component(Operand op1, Operand op2)
    {
        List<String> store = new ArrayList<>();

        StringBuilder sb = new StringBuilder().append("data modify storage mem ").append(op1).append(" set");

        if (op2.meta != OpType.Meta.IMM)
        {
            sb.append(" from storage mem ").append(op2);
        }
        else if (op2.type == OpType.INT)
        {
            sb.append(" value {value:").append(op2).append("}");
        }
        else if (op2.type == OpType.FLOAT)
        {
            sb.append(" value {value:").append(op2).append("f}");
        }
        else if (op2.type == OpType.STR)
        {
            String quote = "\\\"" + op2.id + "\\\"";
            sb.append(" value {value:").append(quote).append("}");
        }

        store.add(sb.toString());

        return store;
    }
}
