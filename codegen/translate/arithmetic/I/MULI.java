package codegen.translate.arithmetic.I;

import ir.tac.TAC;

public class MULI extends CalcI
{
    public MULI(int row, TAC instr)
    {
        super(row, instr, "*=");
    }
}
