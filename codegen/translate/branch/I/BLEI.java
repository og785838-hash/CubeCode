package codegen.translate.branch.I;

import ir.tac.TAC;

import java.util.Map;

public class BLEI extends BranchI
{
    public BLEI(int row, TAC instr, Map<String, Integer> look)
    {
        super(row, instr, "<=", look);
    }
}
