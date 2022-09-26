package org.beetl.core.engine;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.beetl.core.Context;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.statement.Expression;
import org.beetl.core.statement.GeneralForStatement;
import org.beetl.core.statement.GrammarToken;
import org.beetl.core.statement.IGoto;
import org.beetl.core.statement.Program;
import org.beetl.core.statement.Statement;
import org.beetl.core.statement.VarAssignSeqStatement;
import org.beetl.core.statement.WhileStatement;

/**
 * 在线运行的模板引擎，为了防止用户胡乱输入，对循环，安全调用做了限制
 *
 * @author xiandafu
 */
public class OnlineTemplateEngine extends DefaultTemplateEngine {

    /** 最大循环次数 */
    public static int sMaxNumLoop = 5;
    /** 错误信息 */
    public static String sMaxNumLoopError = "错误:在线引擎不允许循环次数超过  " + sMaxNumLoop;

    @Override
    public Program createProgram(Resource rs, Reader reader, Map<Integer, String> textMap, String cr,
                                 GroupTemplate gt) {
        return super.createProgram(rs, reader, textMap, cr, gt);
    }

    @Override
    protected GrammarCreator getGrammarCreator(GroupTemplate groupTemplate) {
        GrammarCreator result = new OnlineGrammarCreator();
        super.setStrictDisableGrammars(result, groupTemplate);
        return result;
    }

    /**
     * 线上引擎的语法创建者，通过重写 {@code for} 和 {@code while} 语句块来限制循环次数等
     */
    private static class OnlineGrammarCreator extends GrammarCreator {

        @Override
        public GeneralForStatement createFor(VarAssignSeqStatement varAssignSeq, Expression[] expInit,
                                             Expression condition, Expression[] expUpdate, Statement forPart,
                                             Statement elseforPart, GrammarToken token) {
            return new RestrictForStatement(varAssignSeq, expInit, condition, expUpdate, forPart, elseforPart, token);
        }

        @Override
        public WhileStatement createWhile(Expression exp, Statement whileBody, GrammarToken token) {
            return new RestrictWhileStatement(exp, whileBody, token);
        }
    }

    /**
     * 受限制的循环，用于重写循环块，主要是对最大循环次数作了限制
     */
    private static class RestrictForStatement extends GeneralForStatement {

        /**
         * 构造方法
         *
         * @param varAssignSeq 变量委派序列
         * @param expInit      初始化的表达式
         * @param condition    条件
         * @param expUpdate    更新后的表达式
         * @param forPart      for部分
         * @param elseforPart  elsefor部分
         * @param token        语法单词
         */
        public RestrictForStatement(VarAssignSeqStatement varAssignSeq, Expression[] expInit,
                                    Expression condition, Expression[] expUpdate, Statement forPart,
                                    Statement elseforPart, GrammarToken token) {
            super(varAssignSeq, expInit, condition, expUpdate, forPart, elseforPart, token);
        }

        @Override
        public void execute(Context ctx) {
            if (expInit != null) {
                for (Expression exp : expInit) {
                    exp.evaluate(ctx);
                }
            }
            if (varAssignSeq != null) {
                varAssignSeq.execute(ctx);
            }

            int i = 0;
            for (; i < sMaxNumLoop; i++) {
                boolean bool = (Boolean) condition.evaluate(ctx);
                if (bool) {
                    forPart.execute(ctx);
                    switch (ctx.gotoFlag) {
                        case IGoto.NORMAL:
                            break;
                        case IGoto.CONTINUE:
                            ctx.gotoFlag = IGoto.NORMAL;
                            continue;
                        case IGoto.RETURN:
                            return;
                        case IGoto.BREAK:
                            ctx.gotoFlag = IGoto.NORMAL;
                            return;
                    }
                } else {
                    break;
                }

                if (this.expUpdate != null) {
                    for (Expression exp : expUpdate) {
                        exp.evaluate(ctx);
                    }
                }

            }

            if (i >= sMaxNumLoop) {
                try {
                    ctx.byteWriter.writeString(sMaxNumLoopError);
                    ctx.byteWriter.flush();
                } catch (IOException ignored) {
                }
            }

        }

    }

    private static class RestrictWhileStatement extends WhileStatement {
        public RestrictWhileStatement(Expression exp, Statement whileBody, GrammarToken token) {
            super(exp, whileBody, token);
        }

        @Override
        public void execute(Context ctx) {
            int i = 0;
            while (i < sMaxNumLoop) {
                Object result = exp.evaluate(ctx);
                if (result instanceof Boolean) {
                    if ((Boolean) result) {
                        whileBody.execute(ctx);
                    } else {
                        break;
                    }
                } else {
                    throw new BeetlException(BeetlException.BOOLEAN_EXPECTED_ERROR).setToken(exp.token);
                }
                i++;
            }

            if (i >= sMaxNumLoop) {
                try {
                    ctx.byteWriter.writeString(sMaxNumLoopError);
                } catch (IOException ignored) {
                }
            }
        }
    }

}
