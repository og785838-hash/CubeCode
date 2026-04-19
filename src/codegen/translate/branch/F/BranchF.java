package codegen.translate.branch.F;

import codegen.Translate;
import codegen.block.Block;
import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import codegen.translate.arithmetic.F.MULF;
import codegen.translate.arithmetic.F.SUBF;

import ir.tac.OpCode;
import ir.tac.OpType;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BranchF extends Instr
{
    public BranchF(int row, TAC instr, Map<String, Integer> look)
    {
        this.blocks = new ArrayList<>();

        Operand C0 = new Operand(OpType.FLOAT, OpType.Meta.CALC, "$C0");

        // GET FLOAT DIFFERENCE

        TAC subInstr = new TAC();
        subInstr.opCode = OpCode.SUBF;
        subInstr.op1 = C0;
        subInstr.op2 = instr.op1;
        subInstr.op3 = instr.op2;

        this.blocks.addAll((new SUBF(row, subInstr)).blocks);

        // SCOREBOARD SETUP

        row += OpCode.SUBF.width;
        int base = this.blocks.size();

        this.blocks.addAll(BranchF.scoreSet(row, C0));

        // COMPARISON 1

        int dist = look.get(instr.op3.id) - row;

        this.blocks.addAll(BranchF.comp1(row, this.blocks.size() - base, dist, instr));

        // REMOVE SCOREBOARD

        this.blocks.add(new Chain(row, this.blocks.size() - base, "scoreboard objectives remove calc"));

        row++;
        base = this.blocks.size();

        // SCALE BY 6 DIGITS

        List<String> scale = MULF.scale(C0, 6);

        this.blocks.add(new Impulse(row, 0, scale.getFirst()));

        for (int i = 1; i < scale.size(); i++)
        {
            this.blocks.add(new Chain(row, this.blocks.size() - base, scale.get(i)));
        }

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size() - base));

        row++;
        base = this.blocks.size();

        // SCOREBOARD SETUP

        this.blocks.addAll(BranchF.scoreSet(row, C0));

        // COMPARISON 2

        dist = look.get(instr.op3.id) - row;

        this.blocks.addAll(BranchF.comp2(row, this.blocks.size() - base, dist, instr));

        // REMOVE SCOREBOARD

        this.blocks.add(new Chain(row, this.blocks.size() - base, "scoreboard objectives remove calc"));
    }

    private static List<Block> scoreSet(int row, Operand op)
    {
        List<Block> scoreSet = new ArrayList<>();

        // CREATE SCOREBOARD

        scoreSet.add(new Impulse(row, 0, "scoreboard objectives add calc dummy"));

        // EXECUTE STORE

        scoreSet.add(new Chain(row, scoreSet.size(), "execute store result score $C0 calc run data get storage " + op.meta + " " + op.id + ".value"));

        // SET ZERO CONSTANT

        scoreSet.add(new Chain(row, scoreSet.size(), "scoreboard players set $C1 calc 0"));

        // CLEAR POWER

        scoreSet.add(new Chain(row, scoreSet.size(), "setblock ~ ~1 ~-3 air replace"));

        return scoreSet;
    }

    public static List<Block> comp1(int row, int col, int dist, TAC instr)
    {
        return switch(instr.opCode)
        {
            case OpCode.BLTF -> BLTF.comp1(row, col, dist);
            case OpCode.BGTF -> BGTF.comp1(row, col, dist);
            case OpCode.BLEF -> BLEF.comp1(row, col, dist);
            case OpCode.BGEF -> BGEF.comp1(row, col, dist);
            case OpCode.BEQF -> BEQF.comp1(row, col);
            case OpCode.BNEF -> BNEF.comp1(row, col, dist);
            default -> throw new RuntimeException("CODEGEN ERROR: Unknown float branch opcode " + instr.opCode);
        };
    }

    public static List<Block> comp2(int row, int col, int dist, TAC instr)
    {
        return switch(instr.opCode)
        {
            case OpCode.BLTF -> BLTF.comp2(row, col, dist);
            case OpCode.BGTF -> BGTF.comp2(row, col, dist);
            case OpCode.BLEF -> BLEF.comp2(row, col, dist);
            case OpCode.BGEF -> BGEF.comp2(row, col, dist);
            case OpCode.BEQF -> BEQF.comp2(row, col, dist);
            case OpCode.BNEF -> BNEF.comp2(row, col, dist);
            default -> throw new RuntimeException("CODEGEN ERROR: Unknown float branch opcode " + instr.opCode);
        };
    }
}
