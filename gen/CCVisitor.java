// Generated from src/CC.g4 by ANTLR 4.9.3
package gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CCParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CCVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CCParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(CCParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(CCParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#funcDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDecl(CCParser.FuncDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#any_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAny_type(CCParser.Any_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(CCParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(CCParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(CCParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#funcCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(CCParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(CCParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(CCParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#assignStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmt(CCParser.AssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#cmdStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdStmt(CCParser.CmdStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#callStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStmt(CCParser.CallStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#branchStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranchStmt(CCParser.BranchStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#ifBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfBlock(CCParser.IfBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#elifBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElifBlock(CCParser.ElifBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#elseBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseBlock(CCParser.ElseBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(CCParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond(CCParser.CondContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#retStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetStmt(CCParser.RetStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#contStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContStmt(CCParser.ContStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#breakStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(CCParser.BreakStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#fStr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFStr(CCParser.FStrContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#fStrVal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFStrVal(CCParser.FStrValContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(CCParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(CCParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link CCParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(CCParser.FactorContext ctx);
}