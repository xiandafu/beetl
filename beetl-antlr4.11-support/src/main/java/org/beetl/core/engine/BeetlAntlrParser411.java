package org.beetl.core.engine;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.tree.ParseTree;
import org.beetl.core.parser.BeetlLexer;
import org.beetl.core.parser.BeetlParser;

import java.io.IOException;
import java.io.Reader;

public class BeetlAntlrParser411 {

    public ParseTree execute(Reader reader, DefaultErrorStrategy antlrErrorStrategy, BaseErrorListener syntaxError){
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
        return parser.prog();
    }
}
