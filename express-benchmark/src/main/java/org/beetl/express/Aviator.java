package org.beetl.express;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

public class Aviator extends BaseExpressBenchmark{
	Expression addCompiledExp;
	Expression ifCompiledExp;
	@Benchmark
	public Object simpleExpress() {
		Object result =
				addCompiledExp.execute(addCompiledExp.newEnv("a", getAddValue(), "b", getAddValue2()));
		return result;
	}
	@Benchmark
	@Override
	public Object ifExpresss() {
		Object result =
				ifCompiledExp.execute(addCompiledExp.newEnv("a", 1));
		return result;
	}

	@Setup
	public void init(){
		String expression = "a+(b+12)";
		addCompiledExp = AviatorEvaluator.compile("a+(b+12)");
		ifCompiledExp = AviatorEvaluator.compile("let a = if(a==1) {true}  else {false} ;");
		return ;
	}

	public  static void main(String[] args) {
		String expression = "let a = if(a==1) {true}  else {false} ;";
		Expression addCompiledExp = AviatorEvaluator.compile(expression);
		Object result =
				addCompiledExp.execute(addCompiledExp.newEnv("a", 1));
		System.out.println(result);
	}
}
