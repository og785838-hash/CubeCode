package app;

import ast.AST;
import ast.ProgramNode;
import codegen.Gen;
import ir.IR;
import semantic.SymbolTableBuilder;
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
        // create a lexer and parser for the code
        CharStream input = CharStreams.fromString(code);
        CCLexer lexer = new CCLexer(input);

        // create a token stream and parse the code
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CCParser parser = new CCParser(tokens);

        // visit the parse tree and build an AST
        ParseTree tree = parser.program();
        ProgramNode prog = (ProgramNode) new AST().visit(tree);

        // build a symbol table and resolve identifiers
        SymbolTableBuilder stb = new SymbolTableBuilder(prog);
        stb.resolve();

        // generate IR code and then convert it to Minecraft commands
        IR ir = new IR(prog);
        Gen gen = new Gen(ir.code);

        return gen.toString();
    }
}