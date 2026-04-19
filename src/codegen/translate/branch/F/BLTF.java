package codegen.translate.branch.F;

import codegen.block.Block;
import codegen.block.Chain;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BLTF extends BranchF
{
    public BLTF(int row, TAC instr, Map<String, Integer> look)
    {
        super(row, instr, look);
    }

    public static List<Block> comp1(int row, int col, int dist)
    {
        List<Block> blocks = new ArrayList<>();

        String C0 = "execute if score $C0 calc ";
        String C1 = " $C1 calc run setblock ~";

        blocks.add(new Chain(row, col, C0 + ">" + C1 + "3 ~1 ~" + -col + " redstone_block"));

        col++;

        blocks.add(new Chain(row, col, C0 + "<" + C1 + dist + " ~1 ~" + -col + " redstone_block"));

        col++;

        blocks.add(new Chain(row, col, C0 + "=" + C1 + "1 ~1 ~" + -col + " redstone_block"));

        return blocks;
    }

    public static List<Block> comp2(int row, int col, int dist)
    {
        List<Block> blocks = new ArrayList<>();

        String C0 = "execute if score $C0 calc ";
        String C1 = " $C1 calc run setblock ~";

        blocks.add(new Chain(row, col, C0 + ">=" + C1 + "1 ~1 ~" + -col + " redstone_block"));

        col++;

        blocks.add(new Chain(row, col, C0 + "<" + C1 + dist + " ~1 ~" + -col + " redstone_block"));

        return blocks;
    }
}
