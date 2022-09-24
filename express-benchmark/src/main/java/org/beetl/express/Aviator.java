package org.beetl.express;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

public class Aviator extends BaseExpressBenchmark{
	Expression addCompiledExp2;
	Expression ifCompiledExp;
	Expression forCompiledExp;
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
	@Benchmark
	public Object forExpresss() {
		Object result =
				forCompiledExp.execute(forCompiledExp.newEnv("arg", getArg()));
		return result;
	}

	@Setup
	public void init(){
		addCompiledExp2 = AviatorEvaluator.compile("arg.age+(arg.pay+12)");
		ifCompiledExp = AviatorEvaluator.compile("let a = if(arg.age==10) {true}  else {false} ;");
		forCompiledExp = AviatorEvaluator.compile("let c =0;for i in range(0,arg.age){c=c+1;} return c;");
		return ;
	}

	public  static void main(String[] args) {
		Aviator aviator = new Aviator();
		aviator.init();
		System.out.println(aviator.forExpresss());
	}
}
