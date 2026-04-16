package codegen.translate.io;

import codegen.Translate;
import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import ir.tac.TAC;

import java.util.ArrayList;

public class CMD extends Instr
{
    public CMD(int row, TAC instr)
    {
        this.blocks = new ArrayList<>();

        // LOAD IN COMMAND

        this.blocks.add(new Impulse(row, 0, "data modify block ~ ~ ~1 Command set from storage mem " + instr.op1.id + ".value"));

        // EMPTY BLOCK

        this.blocks.add(new Chain(row, this.blocks.size(), ""));

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size()));
    }
}
