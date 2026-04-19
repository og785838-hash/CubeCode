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

public class SUBF extends Instr
{
    public SUBF(int row, TAC instr)
    {
        this.blocks = new ArrayList<>();

        // NEGATE RIGHT OPERAND

        Operand C0 = new Operand(OpType.FLOAT, OpType.Meta.CALC, "$C0");
        Operand C1 = new Operand(OpType.FLOAT, OpType.Meta.CALC, "$C1");

        List<String> negate = MULF.negate(C1, instr.op3);

        this.blocks.add(new Impulse(row, 0, negate.getFirst()));

        for (int i = 1; i < negate.size(); i++)
        {
            this.blocks.add(new Chain(row, this.blocks.size(), negate.get(i)));
        }

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size()));

        row++;
        int base = this.blocks.size();

        // COMPUTE ADDITION

        List<String> compute = ADDF.compute(instr.op2, C1);

        this.blocks.add(new Impulse(row, 0, compute.getFirst()));

        for (int i = 1; i < compute.size(); i++)
        {
            this.blocks.add(new Chain(row, this.blocks.size() - base, compute.get(i)));
        }

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size() - base));

        row++;
        base = this.blocks.size();

        // CAST TO FLOAT

        List<String> cast = ADDF.cast(instr.op1, C0);

        this.blocks.add(new Impulse(row, 0, cast.getFirst()));

        for (int i = 1; i < cast.size(); i++)
        {
            this.blocks.add(new Chain(row, this.blocks.size() - base, cast.get(i)));
        }

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size() - base));
    }
}
