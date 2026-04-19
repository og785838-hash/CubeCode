package ast;

public enum Operator
{
    ADD, SUB, MUL, DIV,
    LT, GT, LE, GE, EQ, NEQ;

    public static Operator get(String op)
    {
        return switch (op)
        {
            case "+" -> Operator.ADD;
            case "-" -> Operator.SUB;
            case "*" -> Operator.MUL;
            case "/" -> Operator.DIV;
            case "<" -> Operator.LT;
            case ">" -> Operator.GT;
            case "<=" -> Operator.LE;
            case ">=" -> Operator.GE;
            case "==" -> Operator.EQ;
            case "!=" -> Operator.NEQ;
            default -> null;
        };
    }

    @Override
    public String toString()
    {
        return "Operator: " + this.name();
    }
}
