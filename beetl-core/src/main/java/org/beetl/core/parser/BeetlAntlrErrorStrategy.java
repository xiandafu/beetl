/*
 [The "BSD license"]
 Copyright (c) 2011-2020  闲大赋 (李家智)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
     derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.beetl.core.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.BeetlParserException;
import org.beetl.core.statement.GrammarToken;

/**
 * antlr 语法解析错的处理策略。对所有语法错误，都抛出以阻止继续解析
 *
 * @author xiandafu
 */
public class BeetlAntlrErrorStrategy extends DefaultErrorStrategy {

    static HashSet<String> keys = new HashSet<>();
    static Map<String, String> expects = new HashMap<>();

    static {
        expects.put("LEFT_PAR", "(");
        expects.put("RIGHT_PAR", ")");

    }

    static {
        keys.add("select");
        keys.add("if");
        keys.add("for");
        keys.add("elsefor");
        keys.add("while");
        keys.add("switch");
        keys.add("return");
        keys.add("break");
        keys.add("var");
        keys.add("continue");
        keys.add("directive");
        keys.add("in");
        keys.add("case");
        keys.add("default");
        keys.add("try");
        keys.add("catch");

    }

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        //不会执行到此处，因为在report部分就抛出异常了
        super.recover(recognizer, e);
    }

    @Override
    public void reportError(Parser recognizer, RecognitionException e) {
        // if we've already reported an error and have not matched a token
        // yet successfully, don't report any errors.
        if (inErrorRecoveryMode(recognizer)) {
            //			System.err.print("[SPURIOUS] ");
            return; // don't report spurious errors
        }
        beginErrorCondition(recognizer);
        if (e instanceof NoViableAltException) {
            reportNoViableAlternative(recognizer, (NoViableAltException) e);
        } else if (e instanceof InputMismatchException) {
            reportInputMismatch(recognizer, (InputMismatchException) e);
        } else if (e instanceof FailedPredicateException) {
            reportFailedPredicate(recognizer, (FailedPredicateException) e);
        } else {
            throw new BeetlException(BeetlException.PARSER_UNKNOW_ERROR, e.getClass().getName(), e)
                    .pushToken(this.getGrammarToken(e.getOffendingToken()));
        }

    }

    protected void reportNoViableAlternative(Parser recognizer, NoViableAltException e) {
        TokenStream tokens = recognizer.getInputStream();
        String input = tokens == null ? "<未知输入>"
                : e.getStartToken().getType() == Token.EOF ? "<文件尾>"
                : tokens.getText(e.getStartToken(), e.getOffendingToken());
        String msg = keys.contains(e.getOffendingToken().getText())
                ? "不允许" + e.getOffendingToken().getText() + "关键出现在这里" + ":" + escapeWSAndQuote(input)
                : escapeWSAndQuote(input);
        throw new BeetlParserException(BeetlException.PARSER_VIABLE_ERROR, msg, e)
                .pushToken(this.getGrammarToken(e.getOffendingToken()));
    }

    protected void reportInputMismatch(Parser recognizer, InputMismatchException e) {
        Token t1 = recognizer.getInputStream().LT(-1);
        String msg = "缺少输入在 " + getTokenErrorDisplay(t1) + " 后面， 期望 "
                + e.getExpectedTokens().toString(recognizer.getVocabulary());
        throw new BeetlParserException(BeetlException.PARSER_MISS_ERROR, msg, e)
                .pushToken(this.getGrammarToken(t1));

    }

    protected void reportFailedPredicate(Parser recognizer, FailedPredicateException e) {
        String ruleName = recognizer.getRuleNames()[recognizer.getContext().getRuleIndex()];
        throw new BeetlParserException(BeetlException.PARSER_PREDICATE_ERROR, ruleName, e)
                .pushToken(this.getGrammarToken(e.getOffendingToken()));
    }

    /** Make sure we don't attempt to recover from problems in subrules. */
    @Override
    public void sync(Parser recognizer) {
    }

    /**
     * Make sure we don't attempt to recover inline; if the parser
     * successfully recovers, it won't throw an exception.
     */
    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        // SINGLE TOKEN DELETION
        Token matchedSymbol = singleTokenDeletion(recognizer);
        if (matchedSymbol != null) {
            // we have deleted the extra token.
            // now, move past ttype token as if all were ok
            recognizer.consume();
            return matchedSymbol;
        }

        // SINGLE TOKEN INSERTION
        if (singleTokenInsertion(recognizer)) {
            return getMissingSymbol(recognizer);
        }

//		BeetlException exception = new BeetlParserException(BeetlException.PARSER_MISS_ERROR);
//		exception.pushToken(this.getGrammarToken(recognizer.getCurrentToken()));
//		throw exception;
        throw new InputMismatchException(recognizer);
    }

    protected void reportMissingToken(Parser recognizer) {
        if (inErrorRecoveryMode(recognizer)) {
            return;
        }

        beginErrorCondition(recognizer);

        Token t = recognizer.getTokenStream().LT(-1);
        IntervalSet expecting = getExpectedTokens(recognizer);
        String expect = expecting.toString(recognizer.getVocabulary());
        if (expects.containsKey(expect)) {
            expect = expects.get(expect);
        }
        if (expect.equals("'~>'")) {
            expect = "'模板的占位结束符号'";
        }

        String tokenStr = getTokenErrorDisplay(t);
        String msg = null;
        if (expect.equals("'}'") && tokenStr.equals("'>>'")) {
            msg = "未找到 '{' 匹配的结束符号 '}'";
        } else {
            //常规情况
            msg = "缺少输入 " + expect + " 在 " + tokenStr + " 后面";
        }
        throw new BeetlParserException(BeetlException.PARSER_MISS_ERROR, msg).pushToken(this.getGrammarToken(t));
    }

    protected void reportUnwantedToken(Parser recognizer) {
        if (inErrorRecoveryMode(recognizer)) {
            return;
        }

        beginErrorCondition(recognizer);

        Token t = recognizer.getCurrentToken();
        String tokenName = getTokenErrorDisplay(t);
        IntervalSet expecting = getExpectedTokens(recognizer);
        String key = getErrorKey(expecting.toString(recognizer.getVocabulary()));
        String msg = "多余输入 " + tokenName + " 期望 " + key;
        throw new BeetlParserException(BeetlException.PARSER_MISS_ERROR, msg).pushToken(this.getGrammarToken(t));
    }

    protected String getErrorKey(String key) {
        if (key.equals("'~>'")) {
            return "占位结束符号";
        }
        return key;
    }

    protected GrammarToken getGrammarToken(Token token) {
        return GrammarToken.createToken(token.getText(), token.getLine());
    }

}
