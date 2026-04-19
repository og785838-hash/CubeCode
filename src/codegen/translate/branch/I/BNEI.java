package codegen.translate.branch.I;

import ir.tac.TAC;

import java.util.Map;

public class BNEI extends BranchI
{
    public BNEI(int row, TAC instr, Map<String, Integer> look)
    {
        super(row, instr, "=", look, true);
    }
}
