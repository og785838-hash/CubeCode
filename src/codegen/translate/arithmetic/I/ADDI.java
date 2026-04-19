package codegen.translate.arithmetic.I;

import ir.tac.TAC;

public class ADDI extends CalcI
{
    public ADDI(int row, TAC instr)
    {
        super(row, instr, "+=");
    }
}
