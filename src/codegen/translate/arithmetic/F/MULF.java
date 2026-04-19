package codegen.translate.arithmetic.F;

import codegen.Translate;
import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import ir.tac.OpType;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;

public class MULF extends Instr
{
    public MULF(int row, TAC instr)
    {
        this.blocks = new ArrayList<>();

        // STORE OPERANDS INTO CALC

        this.blocks.add(new Impulse(row, 0, CalcF.storeCommand(instr.op2, false)));
        this.blocks.add(new Chain(row, this.blocks.size(), CalcF.storeCommand(instr.op3, true)));

        // MATRIX 0 & 1

        String matrix = "data modify storage calc";
        String init = "set value [0f,0f,0f,1f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f]";

        String M0 = matrix + " $M0 " + init;
        String M1 = matrix + " $M1 " + init;

        this.blocks.add(new Chain(row, this.blocks.size(), M0));
        this.blocks.add(new Chain(row, this.blocks.size(), M1));

        // SET STORED OPERANDS

        String setM0 = "data modify storage calc $M0[3] set from storage calc $C0.value";
        String setM1 = "data modify storage calc $M1[-1] set from storage calc $C1.value";

        this.blocks.add(new Chain(row, this.blocks.size(), setM0));
        this.blocks.add(new Chain(row, this.blocks.size(), setM1));

        // SUMMON COMPUTER

        this.blocks.add(new Chain(row, this.blocks.size(), "summon block_display ~ ~1 ~ {Tags:[\\\"calc\\\"]}"));

        // SET RECIPROCAL

        String reciprocal = "data modify entity @e[tag=calc,limit=1,sort=nearest]" +
                " transformation set from storage calc $M1";

        String set = "data modify storage calc $M0[-1] set from entity" +
                " @e[tag=calc,limit=1,sort=nearest] transformation.translation[0]";

        this.blocks.add(new Chain(row, this.blocks.size(), reciprocal));
        this.blocks.add(new Chain(row, this.blocks.size(), set));

        // COMPUTE

        String compute = "data modify entity @e[tag=calc,limit=1,sort=nearest]" +
                " transformation set from storage calc $M0";

        this.blocks.add(new Chain(row, this.blocks.size(), compute));

        // RETRIEVE

        String retrieve = "data modify storage mem " + instr.op1.id +
                ".value set from entity @e[tag=calc,limit=1,sort=nearest]" +
                " transformation.translation[0]";

        this.blocks.add(new Chain(row, this.blocks.size(), retrieve));

        // REMOVE COMPUTER

        this.blocks.add(new Chain(row, this.blocks.size(), "kill @e[tag=calc]"));

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size()));
    }

    public static List<String> negate(Operand res, Operand op)
    {
        return switch(op.type)
        {
            case OpType.INT -> MULF.negateI(res, op);
            case OpType.FLOAT -> MULF.negateF(res, op);
            default -> null;
        };
    }

    private static List<String> negateI(Operand to, Operand from)
    {
        List<String> mulf = new ArrayList<>();

        // STORE NEGATED OPERAND

        mulf.add("execute store result storage " + to.meta + " " + to.id + ".value float 1 run data get storage " + from.meta + " " + from.id + ".value -1");

        return mulf;
    }

    private static List<String> negateF(Operand res, Operand op)
    {
        List<String> mulf = new ArrayList<>();

        // STORE OPERAND INTO CALC

        mulf.add(CalcF.storeCommand(op, false));

        // MATRIX 0

        String matrix = "data modify storage calc";
        String init = "set value [0f,0f,0f,1f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,-1f]";

        mulf.add(matrix + " $M0 " + init);

        // SET STORED OPERANDS

        mulf.add("data modify storage calc $M0[3] set from storage " + op.meta + " " + op.id + ".value");

        // SUMMON COMPUTER

        mulf.add("summon block_display ~ ~1 ~ {Tags:[\\\"calc\\\"]}");

        // COMPUTE

        String compute = "data modify entity @e[tag=calc,limit=1,sort=nearest]" +
                " transformation set from storage calc $M0";

        mulf.add(compute);

        // RETRIEVE

        String retrieve = "data modify storage " + res.meta + " " + res.id +
                ".value set from entity @e[tag=calc,limit=1,sort=nearest]" +
                " transformation.translation[0]";

        mulf.add(retrieve);

        // REMOVE COMPUTER

        mulf.add("kill @e[tag=calc]");

        return mulf;
    }

    public static List<String> scale(Operand op, int scale)
    {
        List<String> mulf = new ArrayList<>();
        scale = -scale;

        // MATRIX 0

        String matrix = "data modify storage calc";
        String init = "set value [0f,0f,0f,1f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1e" + scale + "f]";

        mulf.add(matrix + " $M0 " + init);

        // SET STORED OPERANDS

        mulf.add("data modify storage calc $M0[3] set from storage " + op.meta + " " + op.id + ".value");

        // SUMMON COMPUTER

        mulf.add("summon block_display ~ ~1 ~ {Tags:[\\\"calc\\\"]}");

        // COMPUTE

        String compute = "data modify entity @e[tag=calc,limit=1,sort=nearest]" +
                " transformation set from storage calc $M0";

        mulf.add(compute);

        // RETRIEVE

        String retrieve = "data modify storage " + op.meta + " " + op.id + ".value" +
                " set from entity @e[tag=calc,limit=1,sort=nearest]" +
                " transformation.translation[0]";

        mulf.add(retrieve);

        // REMOVE COMPUTER

        mulf.add("kill @e[tag=calc]");

        return mulf;
    }
}
