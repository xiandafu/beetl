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
    public static int MAX_NUM_LOOP = 5;
    /** 错误信息 */
    public static String MAX_NUM_LOOP_ERROR = "错误:在线引擎不允许循环次数超过  " + MAX_NUM_LOOP;

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

    static class OnlineGrammarCreator extends GrammarCreator {

        @Override
        public GeneralForStatement createFor(VarAssignSeqStatement varAssignSeq, Expression[] expInit,
                                             Expression condtion, Expression[] expUpdate, Statement forPart, Statement elseforPart,
                                             GrammarToken token) {
            return new RestrictForStatement(varAssignSeq, expInit, condtion, expUpdate, forPart, elseforPart, token);

        }

        @Override
        public WhileStatement createWhile(Expression exp, Statement whileBody, GrammarToken token) {
            return new RestrictWhileStatement(exp, whileBody, token);
        }
    }

    static class RestrictForStatement extends GeneralForStatement {
        public RestrictForStatement(VarAssignSeqStatement varAssignSeq, Expression[] expInit, Expression condtion,
                                    Expression[] expUpdate, Statement forPart, Statement elseforPart, GrammarToken token) {
            super(varAssignSeq, expInit, condtion, expUpdate, forPart, elseforPart, token);
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
            for (; i < MAX_NUM_LOOP; i++) {
                boolean bool = (Boolean) condtion.evaluate(ctx);
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

            if (i >= MAX_NUM_LOOP) {
                try {
                    ctx.byteWriter.writeString(MAX_NUM_LOOP_ERROR);
                    ctx.byteWriter.flush();

                } catch (IOException e) {
                    // ignore
                }
            }

        }

    }

    static class RestrictWhileStatement extends WhileStatement {
        public RestrictWhileStatement(Expression exp, Statement whileBody, GrammarToken token) {
            super(exp, whileBody, token);
        }

        @Override
        public void execute(Context ctx) {
            int i = 0;
            while (i < MAX_NUM_LOOP) {
                Object result = exp.evaluate(ctx);
                if (result instanceof Boolean) {
                    if ((Boolean) result) {
                        whileBody.execute(ctx);

                    } else {
                        break;
                    }

                } else {
                    BeetlException be = new BeetlException(BeetlException.BOOLEAN_EXPECTED_ERROR);
                    be.token = exp.token;
                    throw be;
                }
                i++;
            }

            if (i >= MAX_NUM_LOOP) {
                try {
                    ctx.byteWriter.writeString(MAX_NUM_LOOP_ERROR);

                } catch (IOException e) {
                    // ignore
                }
            }

        }

    }

}
