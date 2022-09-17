package org.beetl.core.engine;

import java.io.Reader;
import java.util.Map;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.om.AABuilder;
import org.beetl.core.om.AsmAAFactory;
import org.beetl.core.statement.*;
import org.beetl.core.statement.optimal.BlockStatementOptimal;
import org.beetl.core.statement.optimal.VarRefOptimal;

/**
 * 快速运行时引擎
 * 基于默认模板引擎，POJO 的属性采用 ASM 来访问
 */
public class FastRuntimeEngine extends DefaultTemplateEngine {

    /**
     * 构造方法
     */
    public FastRuntimeEngine() {
        super();
        // TODO: 改成非静态，跟引擎相关属性
        AABuilder.defalutAAFactory = new AsmAAFactory();
    }

    @Override
    public Program createProgram(Resource rs, Reader reader, Map<Integer, String> textMap, String cr,
                                 GroupTemplate gt) {
        return super.createProgram(rs, reader, textMap, cr, gt);
    }

    @Override
    protected GrammarCreator getGrammarCreator(GroupTemplate groupTemplate) {
        GrammarCreator result = new FastGrammarCreator();
        super.setStrictDisableGrammars(result, groupTemplate);
        return result;
    }

    /**
     * 自定义的语法创建者
     */
    private static class FastGrammarCreator extends GrammarCreator {
        @Override
        public VarRef createVarRef(VarAttribute[] attributes, boolean hasSafe, Expression safe, GrammarToken token,
                                   GrammarToken firstToken) {
            disableSyntaxCheck(GrammarCreator.VarRefOptimal);
            return (attributes.length == 1 && !hasSafe)
                    ? new VarRefOptimal(attributes[0], token, firstToken)
                    : new VarRef(attributes, hasSafe, safe, firstToken);
        }

        @Override
        public BlockStatement createBlock(Statement[] nodes, GrammarToken token) {
            return (nodes.length == 1) ? new BlockStatementOptimal(nodes, token) : new BlockStatement(nodes, token);
        }
    }

}
