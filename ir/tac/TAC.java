package ir.tac;

import java.util.ArrayList;
import java.util.List;

public class TAC
{
    public OpCode opCode;
    public Operand op1;
    public Operand op2;
    public Operand op3;
    public List<Operand> args;

    public TAC()
    {
        this.args = new ArrayList<>();
    }

    public TAC(OpCode opCode, Operand op1, Operand op2, Operand op3)
    {
        this.opCode = opCode;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.args = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this.opCode.toString());

        if (this.op1 != null)
        {
            sb.append(" ").append(this.op1);
        }
        if (this.op2 != null)
        {
            sb.append(" ").append(this.op2);
        }
        if (this.op3 != null)
        {
            sb.append(" ").append(this.op3);
        }

        if (!this.args.isEmpty())
        {
            sb.append(" [");

            List<String> argsList = new ArrayList<>();

            for (Operand arg : this.args)
            {
                argsList.add(arg.toString());
            }

            String joinedArgs = String.join(" ", argsList);

            sb.append(joinedArgs).append("]");
        }

        return sb.toString();
    }
}
