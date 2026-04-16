// Generated from src/CC.g4 by ANTLR 4.9.3
package gen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CCParser}.
 */
public interface CCListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CCParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(CCParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(CCParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(CCParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(CCParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#funcDecl}.
	 * @param ctx the parse tree
	 */
	void enterFuncDecl(CCParser.FuncDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#funcDecl}.
	 * @param ctx the parse tree
	 */
	void exitFuncDecl(CCParser.FuncDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#any_type}.
	 * @param ctx the parse tree
	 */
	void enterAny_type(CCParser.Any_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#any_type}.
	 * @param ctx the parse tree
	 */
	void exitAny_type(CCParser.Any_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(CCParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(CCParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(CCParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(CCParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(CCParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(CCParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void enterFuncCall(CCParser.FuncCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void exitFuncCall(CCParser.FuncCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(CCParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(CCParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(CCParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(CCParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(CCParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(CCParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#cmdStmt}.
	 * @param ctx the parse tree
	 */
	void enterCmdStmt(CCParser.CmdStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#cmdStmt}.
	 * @param ctx the parse tree
	 */
	void exitCmdStmt(CCParser.CmdStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#callStmt}.
	 * @param ctx the parse tree
	 */
	void enterCallStmt(CCParser.CallStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#callStmt}.
	 * @param ctx the parse tree
	 */
	void exitCallStmt(CCParser.CallStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#branchStmt}.
	 * @param ctx the parse tree
	 */
	void enterBranchStmt(CCParser.BranchStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#branchStmt}.
	 * @param ctx the parse tree
	 */
	void exitBranchStmt(CCParser.BranchStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void enterIfBlock(CCParser.IfBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void exitIfBlock(CCParser.IfBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#elifBlock}.
	 * @param ctx the parse tree
	 */
	void enterElifBlock(CCParser.ElifBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#elifBlock}.
	 * @param ctx the parse tree
	 */
	void exitElifBlock(CCParser.ElifBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#elseBlock}.
	 * @param ctx the parse tree
	 */
	void enterElseBlock(CCParser.ElseBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#elseBlock}.
	 * @param ctx the parse tree
	 */
	void exitElseBlock(CCParser.ElseBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(CCParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(CCParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCond(CCParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCond(CCParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#retStmt}.
	 * @param ctx the parse tree
	 */
	void enterRetStmt(CCParser.RetStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#retStmt}.
	 * @param ctx the parse tree
	 */
	void exitRetStmt(CCParser.RetStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#contStmt}.
	 * @param ctx the parse tree
	 */
	void enterContStmt(CCParser.ContStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#contStmt}.
	 * @param ctx the parse tree
	 */
	void exitContStmt(CCParser.ContStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#breakStmt}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(CCParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#breakStmt}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(CCParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#fStr}.
	 * @param ctx the parse tree
	 */
	void enterFStr(CCParser.FStrContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#fStr}.
	 * @param ctx the parse tree
	 */
	void exitFStr(CCParser.FStrContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#fStrVal}.
	 * @param ctx the parse tree
	 */
	void enterFStrVal(CCParser.FStrValContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#fStrVal}.
	 * @param ctx the parse tree
	 */
	void exitFStrVal(CCParser.FStrValContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(CCParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(CCParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(CCParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(CCParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link CCParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(CCParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CCParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(CCParser.FactorContext ctx);
}