package org.beetl.express;

import org.apache.commons.jexl3.*;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Script;
import org.beetl.core.io.NoLockStringWriter;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Jexl3 extends BaseExpressBenchmark {

	JexlExpression  simpleJexlExpress;
	JexlScript  ifJexlScript;
	JexlScript  forJexlScript;

	@Benchmark
	public Object simpleExpress() {
		JexlContext jc = new MapContext();
		jc.set("arg",getArg());
		return simpleJexlExpress.evaluate(jc);
	}




	@Benchmark
	@Override
	public Object ifExpresss() {
		JexlContext jc = new MapContext();
		jc.set("arg",getArg());
		return ifJexlScript.execute(jc);
	}

	@Override
	@Benchmark
	public Object forExpresss() {
		JexlContext jc = new MapContext();
		jc.set("arg",getArg());
		return forJexlScript.execute(jc);
	}

	//	@Benchmark
	//	public Object callFunction() {
	//		return null;
	//	}
	//	@Benchmark
	//	public Object complexCondition() {
	//		return null;
	//	}
	@Setup
	public void init() throws IOException {
		JexlEngine jexl = new JexlBuilder().cache(128).create();

		//创建一个表达式
		String simpleScript  ="arg.age+(12+arg.pay)";
		String ifScript  ="if(3>1){ c= true ; }else {c= false;}";
		String forScript  ="x=1;while(x<arg.age){x=x+1;} return x;";
		simpleJexlExpress = jexl.createExpression(simpleScript);
		ifJexlScript = jexl.createScript(ifScript);
		forJexlScript = jexl.createScript(forScript);



	}

	public  static void main(String[] args) throws IOException {
		Jexl3 jexl3 = new Jexl3();
		jexl3.init();
		System.out.println(jexl3.forExpresss());
	}
}
