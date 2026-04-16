package codegen.translate.branch.I;

import codegen.Translate;
import codegen.block.Block;
import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import codegen.translate.structure.JUMP;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BranchI extends Instr
{
    public BranchI(int row, TAC instr, String op, Map<String, Integer> look, boolean negate)
    {
        this.blocks = new ArrayList<>();

        // ADD SCOREBOARD

        this.blocks.add(new Impulse(row, 0, "scoreboard objectives add calc dummy"));

        // EXECUTE STORE

        String execScore = "execute store result score ";
        String execData = " calc run data get storage mem ";
        String execValue = ".value";

        this.blocks.add(new Chain(row, this.blocks.size(), execScore + "$C0" + execData + instr.op1.id + execValue));
        this.blocks.add(new Chain(row, this.blocks.size(), execScore + "$C1" + execData + instr.op2.id + execValue));

        // NEXT INSTR

        String cond = "score $C0 calc " + op + " $C1 calc run ";
        String condFalse = "execute unless ";
        String condTrue = "execute if ";

        if (negate)
        {
            String temp = condFalse;
            condFalse = condTrue;
            condTrue = temp;
        }

        List<Block> next = Translate.nextInstr(row, this.blocks.size());
        next.get(1).content = condFalse + cond + next.get(1).content;

        this.blocks.addAll(next);

        // BRANCH

        String branch = JUMP.component(row, this.blocks.size() - 1, instr.op3, look).get(1);
        this.blocks.add(new Chain(row, this.blocks.size(), condTrue + cond + branch));

        // REMOVE SCOREBOARD

        this.blocks.add(new Chain(row, this.blocks.size(), "scoreboard objectives remove calc"));
    }

    public BranchI(int row, TAC instr, String op, Map<String, Integer> look)
    {
        this(row, instr, op, look, false);
    }
}
