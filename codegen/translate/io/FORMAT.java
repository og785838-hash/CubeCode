package codegen.translate.io;

import codegen.Translate;
import codegen.block.Chain;
import codegen.block.Impulse;
import codegen.translate.Instr;
import ir.tac.OpType;
import ir.tac.Operand;
import ir.tac.TAC;

import java.util.ArrayList;
import java.util.List;

public class FORMAT extends Instr
{
    public FORMAT(int row, TAC instr)
    {
        this.blocks = new ArrayList<>();

        List<String> format = FORMAT.component(instr.op1, instr.args);

        this.blocks.add(new Impulse(row, 0, format.getFirst()));

        for (int i = 1; i < format.size(); i++)
        {
            this.blocks.add(new Chain(row, this.blocks.size(), format.get(i)));
        }

        // NEXT INSTR

        this.blocks.addAll(Translate.nextInstr(row, this.blocks.size()));
    }

    public static List<String> component(Operand res, List<Operand> input)
    {
        List<String> format = new ArrayList<>();

        // APPEND PARTS TO DATA ARRAY

        String append = "data modify storage format data append";

        for (Operand op : input)
        {
            if (op.meta == OpType.Meta.IMM)
            {
                switch (op.type)
                {
                    case OpType.INT:
                        format.add(append + " value {value:" + op.id + "}");
                        break;
                    case OpType.FLOAT:
                        format.add(append + " value {value:" + op.id + "f}");
                        break;
                    case OpType.STR:
                        format.add(append + " value {value:\\\"" + op.id + "\\\"}");
                        break;
                }
            }
            else
            {
                format.add(append + " from storage " + op.meta + " " + op.id);
            }
        }

        // WRITE TO SIGN

        String sign = "setblock ~ ~1 ~ oak_sign{front_text:{messages:[" +
                "'{\\\"storage\\\":\\\"format\\\",\\\"nbt\\\":\\\"data[].value\\\",\\\"separator\\\":\\\"\\\"}'" +
                ",'{\\\"text\\\":\\\"\\\"}'" +
                ",'{\\\"text\\\":\\\"\\\"}'" +
                ",'{\\\"text\\\":\\\"\\\"}']}} destroy";

        format.add(sign);

        // SUMMON STAND

        format.add("summon armor_stand ~ ~1 ~ {Tags:[\\\"format\\\"],Invisible:1b,Invulnerable:1b,NoGravity:1b}");

        // SET CUSTOM NAME

        format.add("data modify entity @e[tag=format,limit=1,sort=nearest] CustomName set from block ~ ~1 ~-2 front_text.messages[0]");

        // REMOVE SIGN

        format.add("setblock ~ ~1 ~-3 air");

        // ENCHANT FLATTENING

        format.add("enchant @e[tag=format,limit=1,sort=nearest] mending 1");

        // GET LAST OUTPUT

        format.add("data modify storage format result set from block ~ ~ ~-1 LastOutput");

        // TRIM

        format.add("data modify storage format result set string storage format result 108 -21");

        // SAVE

        format.add("data modify storage " + res.meta + " " + res.id + ".value set from storage format result");

        // REMOVE STAND

        format.add("kill @e[tag=format]");

        // CLEAR DATA ARRAY

        format.add("data remove storage format data");

        return format;
    }
}
