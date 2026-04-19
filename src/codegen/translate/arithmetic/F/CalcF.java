package codegen.translate.arithmetic.F;

import ir.tac.OpType;
import ir.tac.Operand;

public class CalcF
{
    public static String storeCommand(Operand op, boolean calcOperand)
    {
        String calc = calcOperand ? "$C1" : "$C0";

        return switch(op.type)
        {
            case OpType.INT -> "execute store result storage calc " + calc + ".value float 1 run data get storage mem " + op.id + ".value 1";
            case OpType.FLOAT -> "data modify storage calc " + calc + " set from storage mem " + op.id;
            default -> null;
        };
    }
}
