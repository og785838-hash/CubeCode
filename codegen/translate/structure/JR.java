package codegen.translate.structure;

import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import codegen.translate.arithmetic.I.SUBI;
import codegen.translate.io.FORMAT;
import codegen.translate.io.POP;
import ir.tac.OpType;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JR extends Instr
{
    public JR(int row, Map<String, Integer> look)
    {
        this.blocks = new ArrayList<>();

        // POP RETURN ADDRESS

        Operand C0 = new Operand(OpType.Meta.CALC, "$C0");

        this.blocks.add(new Impulse(row, 0, "data modify storage " + C0.meta + " " + C0.id + ".value set from storage link data[-1]"));
        this.blocks.add(new Chain(row, this.blocks.size(), "data remove storage link data[-1]"));

        // JUMP CALCULATION

        Operand currPos = new Operand(OpType.INT, OpType.Meta.IMM, Integer.toString(row));

        List<String> subI = SUBI.component(C0, C0, currPos);

        for (String s : subI)
        {
            this.blocks.add(new Chain(row, this.blocks.size(), s));
        }

        // CREATE COMMAND ON COMPUTED DISTANCE

        List<Operand> parts = new ArrayList<>();
        parts.add(new Operand(OpType.STR, OpType.Meta.IMM, "setblock ~"));
        parts.add(C0);
        parts.add(new Operand(OpType.STR, OpType.Meta.IMM, " ~1 ~"));
        parts.add(new Operand(OpType.STR, OpType.Meta.IMM, Integer.toString(-this.blocks.size() - 17)));
        parts.add(new Operand(OpType.STR, OpType.Meta.IMM, " redstone_block"));

        List<String> format = FORMAT.component(C0, parts);

        for (String s : format)
        {
            this.blocks.add(new Chain(row, this.blocks.size(), s));
        }

        // SET COMMAND

        this.blocks.add(new Chain(row, this.blocks.size(), "data modify block ~ ~ ~2 Command set from storage " + C0.meta + " " + C0.id + ".value"));

        // CLEAR CURRENT POWER

        this.blocks.add(new Chain(row, this.blocks.size(), "setblock ~ ~1 ~" + -this.blocks.size() + " air replace"));

        // EMPTY BLOCK

        this.blocks.add(new Chain(row, this.blocks.size(), ""));
    }
}
