package codegen.translate.arithmetic.F;

import codegen.Translate;
import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import codegen.translate.io.FORMAT;
import ir.tac.OpType;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;

public class ADDF extends Instr
{
    public ADDF(int row, TAC instr)
    {
        this.blocks = new ArrayList<>();

        // COMPUTE

        List<String> compute = ADDF.compute(instr.op2, instr.op3);

        this.blocks.add(new Impulse(row, 0, compute.getFirst()));

        for (int i = 1; i < compute.size(); i++)
        {
            this.blocks.add(new Chain(row, this.blocks.size(), compute.get(i)));
        }

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size()));

        row++;
        int base = this.blocks.size();

        // CAST

        Operand C0 = new Operand(OpType.FLOAT, OpType.Meta.CALC, "$C0");

        List<String> cast = ADDF.cast(C0, instr.op1);

        this.blocks.add(new Impulse(row, 0, cast.getFirst()));

        for (int i = 1; i < cast.size(); i++)
        {
            this.blocks.add(new Chain(row, this.blocks.size() - base, cast.get(i)));
        }

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size() - base));
    }

    public static List<String> compute(Operand left, Operand right)
    {
        List<String> compute = new ArrayList<>();

        // STRIP DATA TYPE

        compute.add(ADDF.storeCommand(left, false));
        compute.add(ADDF.storeCommand(right, true));

        // FORMAT

        List<Operand> parts = new ArrayList<>();
        parts.add(new Operand(OpType.STR, OpType.Meta.IMM, "execute positioned ~ "));
        Operand C0 = new Operand(OpType.Meta.CALC, "$C0");
        parts.add(C0);
        parts.add(new Operand(OpType.STR, OpType.Meta.IMM, " ~ run tp @e[tag=calc,limit=1,sort=nearest] ~ ~"));
        parts.add(new Operand(OpType.Meta.CALC, "$C1"));
        parts.add(new Operand(OpType.STR, OpType.Meta.IMM, " ~"));

        compute.addAll(FORMAT.component(C0, parts));

        // SUMMON COMPUTER

        compute.add("summon block_display ~ ~1 ~ {Tags:[\\\"calc\\\"]}");

        // SET COMMAND

        compute.add("data modify block ~ ~ ~1 Command set from storage calc $C0.value");

        // EMPTY BLOCK

        compute.add("");

        // RETRIEVE

        compute.add("data modify storage calc $C0.value set from entity @e[tag=calc,limit=1,sort=nearest] Pos[1]");

        // REMOVE COMPUTER

        compute.add("kill @e[tag=calc]");

        return compute;
    }

    public static List<String> cast(Operand to, Operand from)
    {
        List<String> cast = new ArrayList<>();

        // STRIP DATA TYPE

        cast.add(ADDF.storeCommand(from, false));

        // FORMAT

        List<Operand> parts = new ArrayList<>();
        parts.add(new Operand(OpType.STR, OpType.Meta.IMM, "data modify storage " + to.meta + " " + to.id + " set value {value:"));
        parts.add(from);
        parts.add(new Operand(OpType.STR, OpType.Meta.IMM, "f}"));
        cast.addAll(FORMAT.component(from, parts));

        // SET COMMAND

        cast.add("data modify block ~ ~ ~1 Command set from storage " + from.meta + " " + from.id + ".value");

        // EMPTY BLOCK

        cast.add("");

        return cast;
    }

    private static String storeCommand(Operand op, boolean calcOperand)
    {
        String calc = calcOperand ? "$C1" : "$C0";

        String store = "data modify storage calc " + calc + ".value set";

        return switch(op.type)
        {
            case OpType.INT -> store + " from storage " + op.meta + " " + op.id + ".value";
            case OpType.FLOAT -> store + " string storage " + op.meta + " " + op.id + ".value 0 -1";
            default -> null;
        };
    }
}
