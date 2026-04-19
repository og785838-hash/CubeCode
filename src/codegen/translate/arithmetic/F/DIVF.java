package codegen.translate.arithmetic.F;

import codegen.Translate;
import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import ir.tac.TAC;
import java.util.ArrayList;

public class DIVF extends Instr
{
    public DIVF(int row, TAC instr)
    {
        this.blocks = new ArrayList<>();

        // STORE OPERANDS INTO CALC

        this.blocks.add(new Impulse(row, 0, CalcF.storeCommand(instr.op2, false)));
        this.blocks.add(new Chain(row, this.blocks.size(), CalcF.storeCommand(instr.op3, true)));

        // MATRIX 0

        String matrix = "data modify storage calc";
        String init = "set value [0f,0f,0f,1f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f]";
        String M0 = matrix + " $M0 " + init;

        this.blocks.add(new Chain(row, this.blocks.size(), M0));

        // SET STORED OPERANDS

        String dividend = "data modify storage calc $M0[3] set from storage calc $C0.value";
        String divisor = "data modify storage calc $M0[-1] set from storage calc $C1.value";

        this.blocks.add(new Chain(row, this.blocks.size(), dividend));
        this.blocks.add(new Chain(row, this.blocks.size(), divisor));

        // SUMMON COMPUTER

        this.blocks.add(new Chain(row, this.blocks.size(), "summon block_display ~ ~1 ~ {Tags:[\\\"calc\\\"]}"));

        // COMPUTE

        String compute = "data modify entity @e[tag=calc,limit=1,sort=nearest]" +
                " transformation set from storage calc $M0";

        this.blocks.add(new Chain(row, this.blocks.size(), compute));

        // RETRIEVE

        String retrieve = "data modify storage mem " + instr.op1.id + ".value set from entity" +
                " @e[tag=calc,limit=1,sort=nearest] transformation.translation[0]";

        this.blocks.add(new Chain(row, this.blocks.size(), retrieve));

        // REMOVE COMPUTER

        this.blocks.add(new Chain(row, this.blocks.size(), "kill @e[tag=calc]"));

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size()));
    }
}
