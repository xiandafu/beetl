package org.beetl.core.engine;

import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.antlr.v4.runtime.*;
import org.beetl.core.AntlrProgramBuilder;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.TemplateEngine;
import org.beetl.core.fun.ObjectUtil;
import org.beetl.core.parser.BeetlAntlrErrorStrategy;
import org.beetl.core.parser.BeetlParser;
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

    static Method antlrParserMethod ;
    static  Object parserBuilder;
    static {
		ininAntlrRuntime();
	}



	/**
	 * 得到一个适合当前的antlr版本的parser
	 * @return
	 */
	static void ininAntlrRuntime()  {

		ClassLoader classLoader = DefaultTemplateEngine.class.getClassLoader();
		Field field = null;
		String version;
		try {
			field = RuntimeMetaData.class.getField("VERSION");
			version = (String)field.get(null);
		} catch (NoSuchFieldException|IllegalAccessException e) {
			throw new IllegalArgumentException("不支持的antlr版本",e);
		}

		try{
			if(version.startsWith("4.10")||version.startsWith("4.11")||version.startsWith("4.12")){
				parserBuilder  = ObjectUtil.tryInstance("org.beetl.core.engine.BeetlAntlrParser411",classLoader);
			}else if(version.startsWith("4.9")||version.startsWith("4.8")||version.startsWith("4.7")){
				parserBuilder  = ObjectUtil.tryInstance("org.beetl.core.engine.BeetlAntlrParser49",classLoader);
			}else if(version.startsWith("4.5")||version.startsWith("4.6")) {
				parserBuilder  = ObjectUtil.tryInstance("org.beetl.core.engine.BeetlAntlrParser45",classLoader);
			}
			if(parserBuilder==null){
				throw new IllegalArgumentException("不支持的antlr版本:"+version+"，联系xiandafu@126.con 定制，或者参考源码antlr4.5-support");
			}
			antlrParserMethod = parserBuilder.getClass().getMethod("execute",new Class[]{Reader.class, DefaultErrorStrategy.class, BaseErrorListener.class});

		}catch(NoSuchMethodException | SecurityException ex){
			throw new IllegalStateException(ex);
		}

	}

    @Override
    public Program createProgram(Resource resource, Reader reader, Map<Integer, String> textMap, String cr,
                                 GroupTemplate gt) {
        BeetlParser parser = null;
        try {
        	//see getRuntimeMethod
             parser = (BeetlParser)antlrParserMethod.invoke(parserBuilder,new Object[]{reader,antlrErrorStrategy,syntaxError});
        } catch (InvocationTargetException e) {
           throw new IllegalStateException(e.getTargetException());
        }catch(Exception ex){
            throw new IllegalStateException(ex);
        }

        BeetlParser.ProgContext tree = parser.prog(); // 参见 BeetlParser.g4 中的 prog
        // 创建脚本运行程序
        Program program = new Program();
        program.res = resource;
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
