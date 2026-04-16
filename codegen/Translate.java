package codegen;

import codegen.block.Block;
import codegen.block.Chain;

import java.util.ArrayList;
import java.util.List;

public class Translate
{
    public static List<Block> nextInstr(int i, int j)
    {
        List<Block> blocks = new ArrayList<>();

        // CLEAR CURRENT POWER

        blocks.add(new Chain(i, j, "setblock ~ ~1 ~" + -j + " air replace"));

        // POWER NEXT INSTR

        blocks.add(new Chain(i, j + 1, "setblock ~1 ~1 ~" + -(j + 1) + " redstone_block"));

        return blocks;
    }
}
