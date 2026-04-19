package codegen.translate.arithmetic.I;

import codegen.Translate;
import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import ir.tac.OpType;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;

public abstract class CalcI extends Instr
{
    public CalcI(int row, TAC instr, String operator)
    {
        this.blocks = new ArrayList<>();

        List<String> calcI = CalcI.component(instr.op1, instr.op2, instr.op3, operator);

        this.blocks.add(new Impulse(row, 0, calcI.getFirst()));

        for (int i = 1; i < calcI.size(); i++)
        {
            this.blocks.add(new Chain(row, this.blocks.size(), calcI.get(i)));
        }

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size()));
    }

    public static List<String> component(Operand res, Operand left, Operand right, String operator)
    {
        List<String> calcI = new ArrayList<>();

        // ADD SCOREBOARD

        calcI.add("scoreboard objectives add calc dummy");

        // EXECUTE STORE

        calcI.add(CalcI.storeCommand(left, false));
        calcI.add(CalcI.storeCommand(right, true));

        // SCOREBOARD OPERATION

        calcI.add("scoreboard players operation $C0 calc " + operator + " $C1 calc");

        // SAVE

        calcI.add("execute store result storage " + res.meta + " " + res.id + ".value int 1 run scoreboard players get $C0 calc");

        // REMOVE SCOREBOARD

        calcI.add("scoreboard objectives remove calc");

        return calcI;
    }

    public static String storeCommand(Operand op, boolean calcOperand)
    {
        String calc = calcOperand ? "$C1" : "$C0";

        if (op.meta == OpType.Meta.IMM)
        {
            if (op.type == OpType.INT)
            {
                return "scoreboard players set " + calc + " calc " + op.id;
            }

            throw new RuntimeException("CODEGEN ERROR: Integer calculation only supports int types");
        }

        return "execute store result score " + calc + " calc run data get storage " + op.meta + " " + op.id + ".value";
    }
}
