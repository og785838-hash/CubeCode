package codegen.translate.arithmetic.I;

import ir.tac.Operand;
import ir.tac.TAC;

import java.util.List;

public class SUBI extends CalcI
{
    public SUBI(int row, TAC instr)
    {
        super(row, instr, "-=");
    }

    public static List<String> component(Operand res, Operand left, Operand right)
    {
        return CalcI.component(res, left, right, "-=");
    }
}
