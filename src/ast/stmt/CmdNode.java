package ast.stmt;

import ast.val.FStrNode;

public class CmdNode extends StmtNode
{
    public FStrNode cmd;

    @Override
    public String toString()
    {
        return "CmdStmtNode: " + "\n\t" + this.cmd.toString().replaceAll("\n", "\n\t");
    }
}
