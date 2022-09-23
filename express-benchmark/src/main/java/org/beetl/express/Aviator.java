package org.beetl.express;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

public class Aviator extends BaseExpressBenchmark{
	Expression addCompiledExp2;
	Expression ifCompiledExp;
	@Benchmark
	public Object simpleExpress() {
		Object result =
				addCompiledExp2.execute(addCompiledExp2.newEnv("arg", getArg()));
		return result;
	}


	@Benchmark
	@Override
	public Object ifExpresss() {
		Object result =
				ifCompiledExp.execute(ifCompiledExp.newEnv("arg", getArg()));
		return result;
	}

	@Override
	public Object forExpresss() {
		return null;
	}

	@Setup
	public void init(){
		addCompiledExp2 = AviatorEvaluator.compile("arg.age+(arg.pay+12)");
		ifCompiledExp = AviatorEvaluator.compile("let a = if(arg.age==10) {true}  else {false} ;");
		return ;
	}

	public  static void main(String[] args) {
		String expression = "arg.age+(arg.pay+12) ";
		Expression addCompiledExp = AviatorEvaluator.compile(expression);
		Object result =
				addCompiledExp.execute(addCompiledExp.newEnv("arg", new Arg()));
		System.out.println(result);
	}
}
