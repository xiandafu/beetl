// Generated from BeetlParser.g4 by ANTLR 4.5.3
package org.beetl.core.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BeetlParser}.
 */
public interface BeetlParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BeetlParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(BeetlParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(BeetlParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(BeetlParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(BeetlParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlockSt(BeetlParser.BlockStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlockSt(BeetlParser.BlockStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code textOutputSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterTextOutputSt(BeetlParser.TextOutputStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code textOutputSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitTextOutputSt(BeetlParser.TextOutputStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code staticOutputSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStaticOutputSt(BeetlParser.StaticOutputStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code staticOutputSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStaticOutputSt(BeetlParser.StaticOutputStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfSt(BeetlParser.IfStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfSt(BeetlParser.IfStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterForSt(BeetlParser.ForStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitForSt(BeetlParser.ForStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileSt(BeetlParser.WhileStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileSt(BeetlParser.WhileStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code siwchSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSiwchSt(BeetlParser.SiwchStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code siwchSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSiwchSt(BeetlParser.SiwchStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selectSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSelectSt(BeetlParser.SelectStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selectSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSelectSt(BeetlParser.SelectStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trySt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterTrySt(BeetlParser.TryStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trySt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitTrySt(BeetlParser.TryStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturnSt(BeetlParser.ReturnStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturnSt(BeetlParser.ReturnStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBreakSt(BeetlParser.BreakStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBreakSt(BeetlParser.BreakStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continueSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterContinueSt(BeetlParser.ContinueStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continueSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitContinueSt(BeetlParser.ContinueStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterVarSt(BeetlParser.VarStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitVarSt(BeetlParser.VarStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code directiveSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveSt(BeetlParser.DirectiveStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code directiveSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveSt(BeetlParser.DirectiveStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignSt(BeetlParser.AssignStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignSt(BeetlParser.AssignStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionTagSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterFunctionTagSt(BeetlParser.FunctionTagStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionTagSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitFunctionTagSt(BeetlParser.FunctionTagStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statmentExpSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatmentExpSt(BeetlParser.StatmentExpStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statmentExpSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatmentExpSt(BeetlParser.StatmentExpStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ajaxSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAjaxSt(BeetlParser.AjaxStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ajaxSt}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAjaxSt(BeetlParser.AjaxStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code end}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterEnd(BeetlParser.EndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code end}
	 * labeled alternative in {@link BeetlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitEnd(BeetlParser.EndContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#directiveExp}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveExp(BeetlParser.DirectiveExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#directiveExp}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveExp(BeetlParser.DirectiveExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#directiveExpIDList}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveExpIDList(BeetlParser.DirectiveExpIDListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#directiveExpIDList}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveExpIDList(BeetlParser.DirectiveExpIDListContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#g_switchStatment}.
	 * @param ctx the parse tree
	 */
	void enterG_switchStatment(BeetlParser.G_switchStatmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#g_switchStatment}.
	 * @param ctx the parse tree
	 */
	void exitG_switchStatment(BeetlParser.G_switchStatmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#g_caseStatment}.
	 * @param ctx the parse tree
	 */
	void enterG_caseStatment(BeetlParser.G_caseStatmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#g_caseStatment}.
	 * @param ctx the parse tree
	 */
	void exitG_caseStatment(BeetlParser.G_caseStatmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#g_defaultStatment}.
	 * @param ctx the parse tree
	 */
	void enterG_defaultStatment(BeetlParser.G_defaultStatmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#g_defaultStatment}.
	 * @param ctx the parse tree
	 */
	void exitG_defaultStatment(BeetlParser.G_defaultStatmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#varDeclareList}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclareList(BeetlParser.VarDeclareListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#varDeclareList}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclareList(BeetlParser.VarDeclareListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignId}
	 * labeled alternative in {@link BeetlParser#assignMent}.
	 * @param ctx the parse tree
	 */
	void enterAssignId(BeetlParser.AssignIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignId}
	 * labeled alternative in {@link BeetlParser#assignMent}.
	 * @param ctx the parse tree
	 */
	void exitAssignId(BeetlParser.AssignIdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignGeneralInSt}
	 * labeled alternative in {@link BeetlParser#assignMent}.
	 * @param ctx the parse tree
	 */
	void enterAssignGeneralInSt(BeetlParser.AssignGeneralInStContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignGeneralInSt}
	 * labeled alternative in {@link BeetlParser#assignMent}.
	 * @param ctx the parse tree
	 */
	void exitAssignGeneralInSt(BeetlParser.AssignGeneralInStContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignTemplateVar}
	 * labeled alternative in {@link BeetlParser#assignMent}.
	 * @param ctx the parse tree
	 */
	void enterAssignTemplateVar(BeetlParser.AssignTemplateVarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignTemplateVar}
	 * labeled alternative in {@link BeetlParser#assignMent}.
	 * @param ctx the parse tree
	 */
	void exitAssignTemplateVar(BeetlParser.AssignTemplateVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#switchBlock}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlock(BeetlParser.SwitchBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#switchBlock}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlock(BeetlParser.SwitchBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroup(BeetlParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroup(BeetlParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabel(BeetlParser.SwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabel(BeetlParser.SwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#forControl}.
	 * @param ctx the parse tree
	 */
	void enterForControl(BeetlParser.ForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#forControl}.
	 * @param ctx the parse tree
	 */
	void exitForControl(BeetlParser.ForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#forInControl}.
	 * @param ctx the parse tree
	 */
	void enterForInControl(BeetlParser.ForInControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#forInControl}.
	 * @param ctx the parse tree
	 */
	void exitForInControl(BeetlParser.ForInControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#generalForControl}.
	 * @param ctx the parse tree
	 */
	void enterGeneralForControl(BeetlParser.GeneralForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#generalForControl}.
	 * @param ctx the parse tree
	 */
	void exitGeneralForControl(BeetlParser.GeneralForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(BeetlParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(BeetlParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void enterForUpdate(BeetlParser.ForUpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void exitForUpdate(BeetlParser.ForUpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(BeetlParser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(BeetlParser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(BeetlParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(BeetlParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#statementExpression}.
	 * @param ctx the parse tree
	 */
	void enterStatementExpression(BeetlParser.StatementExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#statementExpression}.
	 * @param ctx the parse tree
	 */
	void exitStatementExpression(BeetlParser.StatementExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#textStatment}.
	 * @param ctx the parse tree
	 */
	void enterTextStatment(BeetlParser.TextStatmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#textStatment}.
	 * @param ctx the parse tree
	 */
	void exitTextStatment(BeetlParser.TextStatmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#textVar}.
	 * @param ctx the parse tree
	 */
	void enterTextVar(BeetlParser.TextVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#textVar}.
	 * @param ctx the parse tree
	 */
	void exitTextVar(BeetlParser.TextVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#textformat}.
	 * @param ctx the parse tree
	 */
	void enterTextformat(BeetlParser.TextformatContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#textformat}.
	 * @param ctx the parse tree
	 */
	void exitTextformat(BeetlParser.TextformatContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#constantsTextStatment}.
	 * @param ctx the parse tree
	 */
	void enterConstantsTextStatment(BeetlParser.ConstantsTextStatmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#constantsTextStatment}.
	 * @param ctx the parse tree
	 */
	void exitConstantsTextStatment(BeetlParser.ConstantsTextStatmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void enterConstantExpression(BeetlParser.ConstantExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void exitConstantExpression(BeetlParser.ConstantExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code incDecOne}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIncDecOne(BeetlParser.IncDecOneContext ctx);
	/**
	 * Exit a parse tree produced by the {@code incDecOne}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIncDecOne(BeetlParser.IncDecOneContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addminExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddminExp(BeetlParser.AddminExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addminExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddminExp(BeetlParser.AddminExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nativeCallExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNativeCallExp(BeetlParser.NativeCallExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nativeCallExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNativeCallExp(BeetlParser.NativeCallExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExp(BeetlParser.AndExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExp(BeetlParser.AndExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCallExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExp(BeetlParser.FunctionCallExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCallExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExp(BeetlParser.FunctionCallExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignGeneralInExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignGeneralInExp(BeetlParser.AssignGeneralInExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignGeneralInExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignGeneralInExp(BeetlParser.AssignGeneralInExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExp(BeetlParser.OrExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExp(BeetlParser.OrExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExp(BeetlParser.NotExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExp(BeetlParser.NotExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code muldivmodExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMuldivmodExp(BeetlParser.MuldivmodExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code muldivmodExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMuldivmodExp(BeetlParser.MuldivmodExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compareExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompareExp(BeetlParser.CompareExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compareExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompareExp(BeetlParser.CompareExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literalExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExp(BeetlParser.LiteralExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literalExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExp(BeetlParser.LiteralExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jsonExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterJsonExp(BeetlParser.JsonExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jsonExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitJsonExp(BeetlParser.JsonExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParExp(BeetlParser.ParExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParExp(BeetlParser.ParExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNegExp(BeetlParser.NegExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNegExp(BeetlParser.NegExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code oneIncDec}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOneIncDec(BeetlParser.OneIncDecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code oneIncDec}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOneIncDec(BeetlParser.OneIncDecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ternaryExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExp(BeetlParser.TernaryExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ternaryExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExp(BeetlParser.TernaryExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varRefExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVarRefExp(BeetlParser.VarRefExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varRefExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVarRefExp(BeetlParser.VarRefExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assingSelfExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssingSelfExp(BeetlParser.AssingSelfExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assingSelfExp}
	 * labeled alternative in {@link BeetlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssingSelfExp(BeetlParser.AssingSelfExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#generalAssignExp}.
	 * @param ctx the parse tree
	 */
	void enterGeneralAssignExp(BeetlParser.GeneralAssignExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#generalAssignExp}.
	 * @param ctx the parse tree
	 */
	void exitGeneralAssignExp(BeetlParser.GeneralAssignExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#varRef}.
	 * @param ctx the parse tree
	 */
	void enterVarRef(BeetlParser.VarRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#varRef}.
	 * @param ctx the parse tree
	 */
	void exitVarRef(BeetlParser.VarRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#generalAssingSelfExp}.
	 * @param ctx the parse tree
	 */
	void enterGeneralAssingSelfExp(BeetlParser.GeneralAssingSelfExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#generalAssingSelfExp}.
	 * @param ctx the parse tree
	 */
	void exitGeneralAssingSelfExp(BeetlParser.GeneralAssingSelfExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varAttributeGeneral}
	 * labeled alternative in {@link BeetlParser#varAttribute}.
	 * @param ctx the parse tree
	 */
	void enterVarAttributeGeneral(BeetlParser.VarAttributeGeneralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varAttributeGeneral}
	 * labeled alternative in {@link BeetlParser#varAttribute}.
	 * @param ctx the parse tree
	 */
	void exitVarAttributeGeneral(BeetlParser.VarAttributeGeneralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varAttributeVirtual}
	 * labeled alternative in {@link BeetlParser#varAttribute}.
	 * @param ctx the parse tree
	 */
	void enterVarAttributeVirtual(BeetlParser.VarAttributeVirtualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varAttributeVirtual}
	 * labeled alternative in {@link BeetlParser#varAttribute}.
	 * @param ctx the parse tree
	 */
	void exitVarAttributeVirtual(BeetlParser.VarAttributeVirtualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varAttributeArrayOrMap}
	 * labeled alternative in {@link BeetlParser#varAttribute}.
	 * @param ctx the parse tree
	 */
	void enterVarAttributeArrayOrMap(BeetlParser.VarAttributeArrayOrMapContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varAttributeArrayOrMap}
	 * labeled alternative in {@link BeetlParser#varAttribute}.
	 * @param ctx the parse tree
	 */
	void exitVarAttributeArrayOrMap(BeetlParser.VarAttributeArrayOrMapContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#safe_output}.
	 * @param ctx the parse tree
	 */
	void enterSafe_output(BeetlParser.Safe_outputContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#safe_output}.
	 * @param ctx the parse tree
	 */
	void exitSafe_output(BeetlParser.Safe_outputContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#safe_allow_exp}.
	 * @param ctx the parse tree
	 */
	void enterSafe_allow_exp(BeetlParser.Safe_allow_expContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#safe_allow_exp}.
	 * @param ctx the parse tree
	 */
	void exitSafe_allow_exp(BeetlParser.Safe_allow_expContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(BeetlParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(BeetlParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#functionTagCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionTagCall(BeetlParser.FunctionTagCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#functionTagCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionTagCall(BeetlParser.FunctionTagCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#functionNs}.
	 * @param ctx the parse tree
	 */
	void enterFunctionNs(BeetlParser.FunctionNsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#functionNs}.
	 * @param ctx the parse tree
	 */
	void exitFunctionNs(BeetlParser.FunctionNsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#nativeCall}.
	 * @param ctx the parse tree
	 */
	void enterNativeCall(BeetlParser.NativeCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#nativeCall}.
	 * @param ctx the parse tree
	 */
	void exitNativeCall(BeetlParser.NativeCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#nativeMethod}.
	 * @param ctx the parse tree
	 */
	void enterNativeMethod(BeetlParser.NativeMethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#nativeMethod}.
	 * @param ctx the parse tree
	 */
	void exitNativeMethod(BeetlParser.NativeMethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#nativeArray}.
	 * @param ctx the parse tree
	 */
	void enterNativeArray(BeetlParser.NativeArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#nativeArray}.
	 * @param ctx the parse tree
	 */
	void exitNativeArray(BeetlParser.NativeArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#nativeVarRefChain}.
	 * @param ctx the parse tree
	 */
	void enterNativeVarRefChain(BeetlParser.NativeVarRefChainContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#nativeVarRefChain}.
	 * @param ctx the parse tree
	 */
	void exitNativeVarRefChain(BeetlParser.NativeVarRefChainContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#json}.
	 * @param ctx the parse tree
	 */
	void enterJson(BeetlParser.JsonContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#json}.
	 * @param ctx the parse tree
	 */
	void exitJson(BeetlParser.JsonContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#jsonKeyValue}.
	 * @param ctx the parse tree
	 */
	void enterJsonKeyValue(BeetlParser.JsonKeyValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#jsonKeyValue}.
	 * @param ctx the parse tree
	 */
	void exitJsonKeyValue(BeetlParser.JsonKeyValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(BeetlParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(BeetlParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(BeetlParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(BeetlParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(BeetlParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(BeetlParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#typeArgs}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgs(BeetlParser.TypeArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#typeArgs}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgs(BeetlParser.TypeArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#typeArg}.
	 * @param ctx the parse tree
	 */
	void enterTypeArg(BeetlParser.TypeArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#typeArg}.
	 * @param ctx the parse tree
	 */
	void exitTypeArg(BeetlParser.TypeArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link BeetlParser#classType}.
	 * @param ctx the parse tree
	 */
	void enterClassType(BeetlParser.ClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BeetlParser#classType}.
	 * @param ctx the parse tree
	 */
	void exitClassType(BeetlParser.ClassTypeContext ctx);
}