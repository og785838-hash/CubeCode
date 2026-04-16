package ir.tac;

public class Operand
{
    public OpType type;
    public OpType.Meta meta;
    public String id;

    public Operand(OpType type, OpType.Meta meta, String id)
    {
        this.type = type;
        this.meta = meta;
        this.id = id;
    }

    public Operand(OpType.Meta meta, String id)
    {
        this.meta = meta;
        this.id = id;
    }

    public Operand(OpType type, String id)
    {
        this.type = type;
        this.meta = OpType.Meta.MEM;
        this.id = id;
    }

    public Operand(String id)
    {
        this.id = id;
        this.meta = OpType.Meta.MEM;
    }

    @Override
    public String toString()
    {
        if (this.meta == OpType.Meta.IMM && this.type == OpType.STR)
        {
            return "\"" + this.id + "\"";
        }

        return this.id;
    }
}
