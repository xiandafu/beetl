package org.beetl.core.engine;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beetl.core.exception.BeetlException;
import org.beetl.core.statement.AjaxStatement;
import org.beetl.core.statement.AndExpression;
import org.beetl.core.statement.ArthExpression;
import org.beetl.core.statement.BlockStatement;
import org.beetl.core.statement.BreakStatement;
import org.beetl.core.statement.CompareExpression;
import org.beetl.core.statement.ContentBodyExpression;
import org.beetl.core.statement.ContinueStatement;
import org.beetl.core.statement.Expression;
import org.beetl.core.statement.ForStatement;
import org.beetl.core.statement.FormatExpression;
import org.beetl.core.statement.FunctionExpression;
import org.beetl.core.statement.GeneralForStatement;
import org.beetl.core.statement.GrammarToken;
import org.beetl.core.statement.IfStatement;
import org.beetl.core.statement.IncDecExpression;
import org.beetl.core.statement.JsonArrayExpression;
import org.beetl.core.statement.JsonMapExpression;
import org.beetl.core.statement.Literal;
import org.beetl.core.statement.NativeCallExpression;
import org.beetl.core.statement.NegExpression;
import org.beetl.core.statement.NotBooleanExpression;
import org.beetl.core.statement.OrExpression;
import org.beetl.core.statement.PlaceholderST;
import org.beetl.core.statement.ReturnStatement;
import org.beetl.core.statement.SelectStatement;
import org.beetl.core.statement.Statement;
import org.beetl.core.statement.StatementExpression;
import org.beetl.core.statement.StaticTextASTNode;
import org.beetl.core.statement.StaticTextByteASTNode;
import org.beetl.core.statement.SwitchStatement;
import org.beetl.core.statement.TagStatement;
import org.beetl.core.statement.TagVarBindingStatement;
import org.beetl.core.statement.TernaryExpression;
import org.beetl.core.statement.TryCatchStatement;
import org.beetl.core.statement.VarAssignStatement;
import org.beetl.core.statement.VarAssignSeqStatement;
import org.beetl.core.statement.VarAttribute;
import org.beetl.core.statement.VarDefineNode;
import org.beetl.core.statement.VarRef;
import org.beetl.core.statement.VarRefAssignExpress;
import org.beetl.core.statement.VarRefAssignStatement;
import org.beetl.core.statement.WhileStatement;
import org.beetl.core.statement.nat.ClassNode;
import org.beetl.core.statement.nat.InstanceNode;
import org.beetl.core.statement.nat.NativeNode;

/**
 * 语法自定义控制，可以集成这个类实现特定扩展
 *
 * 1） 负责提供语法黑名单，则模板不允许出现特定语法，如java直接调用，或者while循环
 * 2） 负责提供功一系列create方法构造语法树，可以重载这些create方法构造自己的语法树,比如，一个允许最多循环10次的while
 *
 * @author xiandafu
 */
public class GrammarCreator implements IGrammarConstants {

    /** 禁用语法的集合 */
    protected Set<String> disableGrammarSet = new HashSet<>();

    /**
     * 参考BeetlLexer.g4
     */
    public void addDisableGrammar(String disableGrammar) {
        disableGrammarSet.add(disableGrammar);
    }

    public VarAssignSeqStatement createVarAssignSeq(VarAssignStatement[] assings) {
        disableSyntaxCheck(VarAssignSeq);
        return new VarAssignSeqStatement(assings, null);
    }

    public VarAssignStatement createVarAssign(Expression exp, GrammarToken token) {
        disableSyntaxCheck(VarAssign);
        return new VarAssignStatement(exp, token);
    }

    public VarRefAssignStatement createVarRefAssign(Expression exp, VarRef varRef) {
        disableSyntaxCheck(VarRefAssign);
        return new VarRefAssignStatement(exp, varRef);
    }

    public BlockStatement createBlock(Statement[] nodes, GrammarToken token) {
        return new BlockStatement(nodes, token);
    }

    public PlaceholderST createTextOutputSt(Expression exp, FormatExpression format) {
        disableSyntaxCheck(TextOutputSt);
        return new PlaceholderST(exp, format, null);
    }

    public PlaceholderST createTextOutputSt2(Expression exp, FormatExpression format) {
        disableSyntaxCheck(TextOutputSt2);
        return new PlaceholderST(exp, format, null);
    }

    public ReturnStatement createReturn(Expression exp) {
        disableSyntaxCheck(Return);
        return new ReturnStatement(exp, null);
    }

    public BreakStatement createBreak(GrammarToken token) {
        disableSyntaxCheck(Break);
        return new BreakStatement(token);
    }

    public ContinueStatement createContinue(GrammarToken token) {
        disableSyntaxCheck(Continue);
        return new ContinueStatement(token);
    }

    public ForStatement createForIn(VarDefineNode forVar, Expression exp, boolean hasSafe, Statement forPart,
                                    Statement elseForPart) {
        disableSyntaxCheck(ForIn);
        return new ForStatement(forVar, exp, hasSafe, forPart, elseForPart, forVar.token);
    }

    public GeneralForStatement createFor(VarAssignSeqStatement varAssignSeq, Expression[] expInit, Expression condtion,
                                         Expression[] expUpdate, Statement forPart, Statement elseforPart, GrammarToken token) {
        disableSyntaxCheck(For);
        return new GeneralForStatement(varAssignSeq, expInit, condtion, expUpdate, forPart, elseforPart, token);
    }

    public WhileStatement createWhile(Expression exp, Statement whileBody, GrammarToken token) {
        disableSyntaxCheck(While);
        return new WhileStatement(exp, whileBody, token);
    }

    public StaticTextASTNode createStaticText(int textIndex, GrammarToken token) {
        return new StaticTextASTNode(textIndex, token);
    }

    public StaticTextByteASTNode createStaticByteText(int textIndex, GrammarToken token) {
        return new StaticTextByteASTNode(textIndex, token);
    }

    public IfStatement createIf(Expression condtion, Statement ifStatement, Statement elseStatement,
                                GrammarToken token) {
        disableSyntaxCheck(If);
        return new IfStatement(condtion, ifStatement, elseStatement, token);
    }

    public StatementExpression createStatementExpression(Expression expression) {
        return new StatementExpression(expression, null);
    }

    public TryCatchStatement createTry(BlockStatement tryPart, BlockStatement catchPart, VarDefineNode error,
                                       GrammarToken token) {
        disableSyntaxCheck(Try);
        return new TryCatchStatement(tryPart, catchPart, error, token);
    }

    public TagStatement createTag(String tagName, Expression[] expList, Statement block, GrammarToken token) {
        disableSyntaxCheck(Tag);
        return new TagStatement(tagName, expList, block, token);
    }

    public TagVarBindingStatement createVarTag(String tagName, Expression[] expList, Statement block,
                                               VarDefineNode[] varDefine, GrammarToken token) {
        disableSyntaxCheck(VarTag);
        return new TagVarBindingStatement(tagName, expList, block, varDefine, token);
    }

    public SwitchStatement createSwitch(Expression value, LinkedHashMap<Expression, BlockStatement> map,
                                        BlockStatement defaultBlock, GrammarToken token) {
        disableSyntaxCheck(Switch);
        return new SwitchStatement(value, map, defaultBlock, token);
    }

    public SelectStatement createSelect(Expression value, Expression[] conditions, BlockStatement[] blocks,
                                        BlockStatement defaultBlock, GrammarToken token) {
        disableSyntaxCheck(Select);
        return new SelectStatement(value, conditions, blocks, defaultBlock, token);
    }

    public AjaxStatement createAjax(BlockStatement block, GrammarToken token, boolean defaultRender) {
        disableSyntaxCheck(Ajax);
        return new AjaxStatement(block, token, defaultRender);
    }

    /* Express */

    public FunctionExpression createFunction(String name, Expression[] exps, VarAttribute[] vas, boolean hasSafe,
                                             Expression safeExp, GrammarToken token) {
        disableSyntaxCheck(Function);
        return new FunctionExpression(name, exps, vas, hasSafe, safeExp, token);
    }

    public Literal createLiteral(Object value, GrammarToken token) {
        return new Literal(value, token);
    }

    public CompareExpression createCompare(Expression a, Expression b, short mode, GrammarToken token) {
        disableSyntaxCheck(Compare);
        return new CompareExpression(a, b, mode, token);
    }

    public TernaryExpression createTernary(Expression condtion, Expression a, Expression b, GrammarToken token) {
        disableSyntaxCheck(Ternary);
        return new TernaryExpression(condtion, a, b, token);
    }

    public ArthExpression createArth(Expression a, Expression b, short mode, GrammarToken token) {
        disableSyntaxCheck(Arth);
        return new ArthExpression(a, b, mode, token);
    }

    public JsonArrayExpression createJasonArray(List<Expression> list, GrammarToken token) {
        disableSyntaxCheck(JasonArray);
        return new JsonArrayExpression(list, token);
    }

    public JsonMapExpression createJsonMap(Map<String, Expression> map, GrammarToken token) {
        disableSyntaxCheck(JsonMap);
        return new JsonMapExpression(map, token);
    }

    public FunctionExpression createFunctionExp(String name, Expression[] exps, VarAttribute[] vas, boolean hasSafe,
                                                Expression safeExp, GrammarToken token) {
        disableSyntaxCheck(FunctionExp);
        return new FunctionExpression(name, exps, vas, hasSafe, safeExp, token);
    }

    public NativeCallExpression createClassNativeCall(ClassNode clsNode, NativeNode[] chain, GrammarToken token) {
        disableSyntaxCheck(ClassNativeCall);
        return new NativeCallExpression(clsNode, chain, token);
    }

    public NativeCallExpression createInstanceNativeCall(InstanceNode insNode, NativeNode[] chain, GrammarToken token) {
        disableSyntaxCheck(InstanceNativeCall);
        return new NativeCallExpression(insNode, chain, token);
    }

    public AndExpression createAnd(Expression exp1, Expression exp2, GrammarToken token) {
        disableSyntaxCheck(And);
        return new AndExpression(exp1, exp2, token);
    }

    public OrExpression createOr(Expression exp1, Expression exp2, GrammarToken token) {
        disableSyntaxCheck(Or);
        return new OrExpression(exp1, exp2, token);
    }

    public NotBooleanExpression createNot(Expression exp, GrammarToken token) {
        disableSyntaxCheck(Not);
        return new NotBooleanExpression(exp, token);
    }

    public NegExpression createNeg(Expression exp, GrammarToken token) {
        disableSyntaxCheck(Neg);
        return new NegExpression(exp, token);
    }

    public IncDecExpression createIncDec(boolean isInc, boolean returnOrginal, GrammarToken token) {
        disableSyntaxCheck(IncDec);
        return new IncDecExpression(isInc, returnOrginal, token);
    }

    public VarRef createVarRef(VarAttribute[] attributes, boolean hasSafe, Expression safe, GrammarToken token,
                               GrammarToken firstToken) {
        disableSyntaxCheck(VarRefOptimal);
        return new VarRef(attributes, hasSafe, safe, firstToken);
    }

    public VarRefAssignExpress createVarRefAssignExp(Expression exp, VarRef varRef) {
        disableSyntaxCheck(VarRefAssignExp);
        return new VarRefAssignExpress(exp, varRef);
    }

    public ContentBodyExpression createTemplateContent(BlockStatement block, GrammarToken token) {
        disableSyntaxCheck(TemplateContent);
        return new ContentBodyExpression(block, token);
    }

    public FormatExpression createFormat(String name, String pattern, GrammarToken token) {
        disableSyntaxCheck(Format);
        return new FormatExpression(name, pattern, token);
    }

    protected void disableSyntaxCheck(String ast) {
        // 禁用的语法列表
        if (this.disableGrammarSet.contains(ast)) {
            throw new BeetlException(BeetlException.GRAMMAR_NOT_ALLOWED, "语法 " + ast + "不允许");
        }
    }

}
