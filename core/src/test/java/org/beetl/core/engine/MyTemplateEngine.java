package org.beetl.core.engine;

import org.beetl.core.Context;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.statement.GrammarToken;
import org.beetl.core.statement.Statement;

import java.io.IOException;

public class MyTemplateEngine extends  DefaultTemplateEngine{

	protected GrammarCreator getGrammarCreator(GroupTemplate groupTemplate) {
		GrammarCreator result = new MyGrammarCreator();
		return result;
	}

	static class MyGrammarCreator extends  GrammarCreator{

		public  Statement last(Resource resource){
			GrammarToken grammarToken = GrammarToken.createToken("lastToken",-1);
			SimpleStatement simpleStatement = new SimpleStatement(grammarToken);
			return simpleStatement;
		}
	}
	static class SimpleStatement extends  Statement{

		/**
		 * 构造方法
		 *
		 * @param token 语法单词
		 */
		public SimpleStatement(GrammarToken token) {
			super(token);
		}

		@Override
		public void execute(Context ctx) {
			try {
				ctx.byteWriter.writeString("!end");
			} catch (IOException e) {
				//ignore
				e.printStackTrace();
			}
		}
	}
}
