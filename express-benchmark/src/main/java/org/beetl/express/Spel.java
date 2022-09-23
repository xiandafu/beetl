package org.beetl.express;

import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;

public class Spel extends BaseExpressBenchmark{

	Expression addExpression = null;
	Expression ifExpression = null;
	@Benchmark
	public Object simpleExpress() {
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("arg",getArg());
		return addExpression.getValue(context);
	}


	@Benchmark
	@Override
	public Object ifExpresss() {
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("arg",getArg());
		return ifExpression.getValue(context);
	}

	@Override
	public Object forExpresss() {
		return null;
	}

	@Setup
	public void init() throws ScriptException {
		ExpressionParser parser = new SpelExpressionParser();
		addExpression = parser.parseExpression("#arg.age+(12+#arg.pay)");
		ifExpression = parser.parseExpression("#arg.age==10?true:false");

	}

	public static void main(String[] args) throws ScriptException {
		Spel spel = new Spel();
		spel.init();
		System.out.println(spel.ifExpresss());

	}
}
