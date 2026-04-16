package codegen.translate.structure;

import codegen.Translate;
import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;

import java.util.ArrayList;

public class LABEL extends Instr
{
    public LABEL(int row)
    {
        this.blocks = new ArrayList<>();

        // CLEAR CURRENT POWER

        this.blocks.add(new Impulse(row, 0, "setblock ~ ~1 ~" + 0 + " air replace"));

        // POWER NEXT INSTR

        this.blocks.add(new Chain(row, 1, "setblock ~1 ~1 ~" + -1 + " redstone_block"));
    }
}
