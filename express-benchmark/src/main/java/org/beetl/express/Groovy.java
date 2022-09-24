package org.beetl.express;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Groovy extends BaseExpressBenchmark{
	GroovyScriptEngineImpl engine= null;
	String  addJs2="arg.age+(arg.pay+12);";
	String ifJs = "if(arg.age==10)  true ; else  false;";
//	String forJs = "int c =0 ; for(int i=0;i<arg.age;i++){ c=c+1;}  return c>0;";

	CompiledScript addJsScript2 = null;
	CompiledScript ifScript = null;
	CompiledScript forScript = null;
	@Benchmark
	public Object simpleExpress() {
		try {
			Arg arg = getArg();
			Bindings bindings = engine.createBindings();
			bindings.put("arg",arg);
			Object  o=  (Object) addJsScript2.eval(bindings);
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
			bindings.put("arg",getArg());
			Object  o=  (Object) ifScript.eval(bindings);
			return o;

		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Benchmark
	public Object forExpresss() {
//		try {
//			Bindings bindings = engine.createBindings();
//			bindings.put("arg",getArg());
//			Object  o=  (Object) forScript.eval(bindings);
//			return o;
//
//		} catch (ScriptException e) {
//			throw new RuntimeException(e);
//		}
		//不知道怎么写
		throw new UnsupportedOperationException();
	}

	@Setup
	public void init() throws ScriptException {
		engine = new GroovyScriptEngineImpl();
		this.addJsScript2 = engine.compile(addJs2);
		this.ifScript = engine.compile(ifJs);
//		this.forScript = engine.compile(forJs);
	}

	public static void main(String[] args) throws ScriptException {
		Groovy groovy = new Groovy();
		groovy.init();
		System.out.println(groovy.forExpresss());

	}
}
