package codegen.translate.branch.I;

import ir.tac.TAC;

import java.util.Map;

public class BGTI extends BranchI
{
    public BGTI(int row, TAC instr, Map<String, Integer> look)
    {
        super(row, instr, ">", look);
    }
}
