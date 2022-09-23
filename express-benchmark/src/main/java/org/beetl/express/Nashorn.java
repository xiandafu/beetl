package org.beetl.express;

import org.openjdk.jmh.annotations.Benchmark;

import javax.script.*;

public class Nashorn extends BaseExpressBenchmark{
	ScriptEngine engine= new ScriptEngineManager().getEngineByName("Nashorn");
	String  addJs=" a+(b+12);";
	String ifJs = "if(a==1)  true ; else  false;";
	@Benchmark
	public Object simpleExpress() {
		try {
			Bindings bindings = engine.createBindings();
			bindings.put("a",12);
			bindings.put("b",23);
			Object  o=  (Object) engine.eval(addJs,bindings);
			return o;

		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
	}
	@Benchmark
	@Override
	public Object ifExpresss() {
		try {
			Bindings bindings = engine.createBindings();
			bindings.put("a",1);
			Object  o=  (Object) engine.eval(ifJs,bindings);
			return o;

		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws ScriptException {
		ScriptEngine engine= new ScriptEngineManager().getEngineByName("Nashorn");
		String ifJs = "if(a==1)  true ; else  false;";
		Bindings bindings = engine.createBindings();
		bindings.put("a",1);
		Object  o=  (Object) engine.eval(ifJs,bindings);
		System.out.println(o);
	}
}
