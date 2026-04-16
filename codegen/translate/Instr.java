package codegen.translate;

import codegen.block.Block;
import codegen.translate.arithmetic.F.ADDF;
import codegen.translate.arithmetic.F.DIVF;
import codegen.translate.arithmetic.F.MULF;
import codegen.translate.arithmetic.F.SUBF;
import codegen.translate.arithmetic.I.ADDI;
import codegen.translate.arithmetic.I.DIVI;
import codegen.translate.arithmetic.I.MULI;
import codegen.translate.arithmetic.I.SUBI;
import codegen.translate.branch.F.*;
import codegen.translate.branch.I.*;
import codegen.translate.io.*;
import codegen.translate.structure.JAL;
import codegen.translate.structure.JR;
import codegen.translate.structure.JUMP;
import codegen.translate.structure.LABEL;
import ir.tac.OpCode;
import ir.tac.TAC;

import java.util.List;
import java.util.Map;

public abstract class Instr
{
    public List<Block> blocks;

    public static Instr get(int row, TAC line, Map<String, Integer> look)
    {
        return switch (line.opCode)
        {
            case OpCode.STORE -> new STORE(row, line);
            case OpCode.ADDI -> new ADDI(row, line);
            case OpCode.SUBI -> new SUBI(row, line);
            case OpCode.MULI -> new MULI(row, line);
            case OpCode.DIVI -> new DIVI(row, line);
            case OpCode.ADDF -> new ADDF(row, line);
            case OpCode.SUBF -> new SUBF(row, line);
            case OpCode.MULF -> new MULF(row, line);
            case OpCode.DIVF -> new DIVF(row, line);
            case OpCode.FORMAT -> new FORMAT(row, line);
            case OpCode.CMD -> new CMD(row, line);
            case OpCode.PUSH -> new PUSH(row, line, look);
            case OpCode.POP -> new POP(row, line);
            case OpCode.LABEL -> new LABEL(row);
            case OpCode.JUMP -> new JUMP(row, line, look);
            case OpCode.JR -> new JR(row, look);
            case OpCode.BLTI -> new BLTI(row, line, look);
            case OpCode.BGTI -> new BGTI(row, line, look);
            case OpCode.BLEI -> new BLEI(row, line, look);
            case OpCode.BGEI -> new BGEI(row, line, look);
            case OpCode.BEQI -> new BEQI(row, line, look);
            case OpCode.BNEI -> new BNEI(row, line, look);
            case OpCode.BLTF -> new BLTF(row, line, look);
            case OpCode.BGTF -> new BGTF(row, line, look);
            case OpCode.BLEF -> new BLEF(row, line, look);
            case OpCode.BGEF -> new BGEF(row, line, look);
            case OpCode.BEQF -> new BEQF(row, line, look);
            case OpCode.BNEF -> new BNEF(row, line, look);
            case OpCode.JAL -> new JAL(row, line, look);
            default -> throw new RuntimeException("CODEGEN ERROR: Unknown opcode " + line.opCode);
        };
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (Block block : this.blocks)
        {
            sb.append(block.toString()).append("\n");
        }

        return sb.toString().trim();
    }
}
