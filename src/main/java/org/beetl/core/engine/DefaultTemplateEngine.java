package org.beetl.core.engine;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.beetl.core.AntlrProgramBuilder;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.TemplateEngine;
import org.beetl.core.parser.BeetlAntlrErrorStrategy;
import org.beetl.core.parser.BeetlLexer;
import org.beetl.core.parser.BeetlParser;
import org.beetl.core.parser.BeetlParser.ProgContext;
import org.beetl.core.parser.SyntaxErrorListener;
import org.beetl.core.statement.AjaxStatement;
import org.beetl.core.statement.Program;
import org.beetl.core.statement.ProgramMetaData;

/**
 * 默认的视图引擎
 */
public class DefaultTemplateEngine implements TemplateEngine, IGrammarConstants {

    /** ANTLR 错误策略 */
    protected BeetlAntlrErrorStrategy antlrErrorStrategy = new BeetlAntlrErrorStrategy();
    /** 语法错误监听器 */
    protected SyntaxErrorListener syntaxError = new SyntaxErrorListener();
    /** 严格MVC下不允许的语法，跟逻辑相关 */
    protected static final String[] STRICT_DISABLE_GRAMMARS = {
            Arth, ClassNativeCall, Compare, Function, IncDec, InstanceNativeCall, VarAssign,
            VarRefAssign, VarRefAssignExp,
    };

    @Override
    public Program createProgram(Resource resource, Reader reader, Map<Integer, String> textMap, String cr,
                                 GroupTemplate gt) {

        BeetlLexer lexer;
        try {
            lexer = new BeetlLexer(CharStreams.fromReader(reader));
        } catch (IOException ex) {
            throw new IllegalStateException(ex); // 不可能发生
        }

        // 设置 ANTLR 的错误监听器
        lexer.removeErrorListeners();
        lexer.addErrorListener(syntaxError);

        // 生成 prog 语法树
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BeetlParser parser = new BeetlParser(tokens);
        parser.setErrorHandler(antlrErrorStrategy);
        ProgContext tree = parser.prog(); // 参见 BeetlParser.g4 中的 prog

        // 创建脚本运行程序
        Program program = new Program();
        program.res = resource;
        program.gt = gt;
        program.metaData = getAntlrBuilder(gt).build(tree,resource);
        program.metaData.lineSeparator = cr;
        program.metaData.staticTextArray = new Object[textMap.size()];

        // 模板静态数据
        int index = 0;
        Configuration conf = gt.getConf();
        boolean directByteOutput = conf.isDirectByteOutput();
        String charset = conf.getCharset();
        try {
            for (String v : textMap.values()) {
                program.metaData.staticTextArray[index++] = directByteOutput ? v.getBytes(charset) : v.toCharArray();
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // AJAX 元数据
        if (program.metaData.ajaxs != null) {
            for (AjaxStatement ajax : program.metaData.ajaxs.values()) {
                ProgramMetaData metaData = ajax.getLocalProgramMetaData();
                metaData.staticTextArray = program.metaData.staticTextArray;
                metaData.lineSeparator = cr;
            }
        }

        return program;
    }

    /**
     * 子类可以加工{@param program}，修改或者添加包括静态文本或者Statement语句
     *
     * @param program 脚本运行程序
     */
    protected void checkProgram(Program program) {
    }

    /**
     * 获取一个beetl模板的解析器，子类可以扩展
     *
     * @param groupTemplate 组模板
     * @return 一个新的 ANTLR 程序构建器的实例
     */
    protected AntlrProgramBuilder getAntlrBuilder(GroupTemplate groupTemplate) {
        GrammarCreator grammarCreator = this.getGrammarCreator(groupTemplate);
        return new AntlrProgramBuilder(groupTemplate, grammarCreator);
    }

    /**
     * 语法节点生成器，子类可以扩展射生成自己个性化节点解析，比如带有xss的输出的${}，限制循环个数的for语法
     *
     * @param groupTemplate 组模板
     * @return 一个新的语法创建者的实例
     */
    protected GrammarCreator getGrammarCreator(GroupTemplate groupTemplate) {
        GrammarCreator result = new GrammarCreator();
        setStrictDisableGrammars(result, groupTemplate);
        return result;
    }

    /**
     * 严格MVC下，添加禁用语法的集合
     *
     * @param grammarCreator 语法创建者
     * @param groupTemplate  组模板
     */
    protected void setStrictDisableGrammars(GrammarCreator grammarCreator, GroupTemplate groupTemplate) {
        if (groupTemplate.getConf().isStrict()) {
            for (String disableGrammar : STRICT_DISABLE_GRAMMARS) {
                grammarCreator.addDisableGrammar(disableGrammar);
            }
        }
    }
}
