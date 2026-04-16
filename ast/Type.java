package ast;

public enum Type
{
    VOID,
    INT,
    FLOAT,
    STR;

    public enum Meta { EVAL, BLOCK }

    public static Type get(String type) {
        return Type.valueOf(type.toUpperCase());
    }

    @Override
    public String toString()
    {
        return "Type: " + this.name();
    }
}
