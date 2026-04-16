package codegen.translate.arithmetic.I;

import ir.tac.TAC;

public class DIVI extends CalcI
{
    public DIVI(int row, TAC instr)
    {
        super(row, instr, "/=");
    }
}
