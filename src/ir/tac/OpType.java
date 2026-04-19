package ir.tac;

import ast.Type;

public enum OpType
{
    INT, FLOAT, STR;

    public static OpType get(Type type)
    {
        return switch(type)
        {
            case Type.VOID -> null;
            case Type.INT -> OpType.INT;
            case Type.FLOAT -> OpType.FLOAT;
            case Type.STR -> OpType.STR;
        };
    }

    public enum Meta
    {
        MEM, CALC, IMM;

        @Override
        public String toString()
        {
            return this.name().toLowerCase();
        }
    }
}
