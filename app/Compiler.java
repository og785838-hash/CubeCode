package app;

import ast.AST;
import ast.ProgramNode;
import codegen.Gen;
import ir.IR;
import semantic.SymbolTableBuilder;
import util.IO;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import gen.CCLexer;
import gen.CCParser;

import java.io.IOException;

public class Compiler
{
    public String run(String code) throws IOException
    {
        CharStream input = CharStreams.fromString(code);

        CCLexer lexer = new CCLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CCParser parser = new CCParser(tokens);

        ParseTree tree = parser.program();
        ProgramNode prog = (ProgramNode) new AST().visit(tree);
        // IO.write(prog.toString(), base + "code.ast");

        SymbolTableBuilder stb = new SymbolTableBuilder(prog);
        stb.resolve();
        // IO.write(stb.toString(),base + "code.sym");

        IR ir = new IR(prog);
        //IO.write(ir.toString(), base + "code.ir");

        Gen gen = new Gen(ir.code);

        return gen.toString();
    }
}