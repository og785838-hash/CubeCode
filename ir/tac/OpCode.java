package ir.tac;

public enum OpCode
{
    STORE,
    ADDI, SUBI, MULI, DIVI,
    ADDF(2), SUBF(3), MULF, DIVF,
    FORMAT, CMD,
    PUSH, POP,
    LABEL, JUMP, JAL, JR,
    BLTI, BGTI, BLEI, BGEI, BEQI, BNEI,
    BLTF(6), BGTF(6), BLEF(6), BGEF(6), BEQF(6), BNEF(6);

    public final int width;

    OpCode(int width)
    {
        this.width = width;
    }

    OpCode()
    {
        this.width = 1;
    }
}
