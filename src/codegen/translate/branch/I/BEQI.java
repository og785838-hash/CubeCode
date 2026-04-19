package codegen.translate.branch.I;

import ir.tac.TAC;

import java.util.Map;

public class BEQI extends BranchI
{
    public BEQI(int row, TAC instr, Map<String, Integer> look)
    {
        super(row, instr, "=", look);
    }
}
