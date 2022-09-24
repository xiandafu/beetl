package org.beetl.express;

import org.mvel2.MVEL;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class Mvel extends BaseExpressBenchmark{

	String  simpleScript = "arg.age+(12+arg.pay)";
	String ifScript = "if(arg.age==10){return true} else { return false}";
	String forScript = "x=0;for(int i=0;i<arg.age;i++){x=x+1;} return x;";
	@Benchmark
	public Object simpleExpress() {
		Arg arg = getArg();
		Map map = new HashMap();
		map.put("arg",arg);
		Object ret = MVEL.eval(simpleScript,map);
		return ret;
	}


	@Benchmark
	@Override
	public Object ifExpresss() {
		Arg arg = getArg();
		Map map = new HashMap();
		map.put("arg",arg);
		Object ret = MVEL.eval(ifScript,map);
		return ret;
	}

	@Override
	@Benchmark
	public Object forExpresss() {
		Arg arg = getArg();
		Map map = new HashMap();
		map.put("arg",arg);
		Object ret = MVEL.eval(forScript,map);
		return ret;
	}

	@Setup
	public void init() throws ScriptException {
		MVEL.compileExpression(simpleScript);
		MVEL.compileExpression(ifScript);
		MVEL.compileExpression(forScript);

	}

	public static void main(String[] args) throws ScriptException {
		Mvel spel = new Mvel();
		spel.init();
		System.out.println(spel.forExpresss());

	}
}
