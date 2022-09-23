package org.beetl.express;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.io.NoLockStringWriter;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Beetl extends BaseExpressBenchmark{

	GroupTemplate gt ;
	StringTemplateResourceLoader loader = new StringTemplateResourceLoader();

	@Benchmark
	public Object simpleExpress() {
		Arg arg = getArg();
		Map map = new HashMap();
		map.put("arg",arg);
		Writer writer =  new NoLockStringWriter(0);
		Map values = gt.runScript("return arg.age+(arg.pay+12);",map,writer,loader);
		return  values.get("return");
	}


	@Benchmark
	@Override
	public Object ifExpresss() {
		Map map = new HashMap();
		map.put("arg",getArg());
		Writer writer =  new StringWriter();
		Map values = gt.runScript("if(arg.age==10) return true ; else return  false;",map,writer,loader);
		return  values.get("return");
	}

	@Override
//	@Benchmark
	public Object forExpresss() {
		Map map = new HashMap();
		map.put("arg",getArg());
		Writer writer =  new StringWriter();
		Map values = gt.runScript("if(arg.age==10) return true ; else return  false;",map,writer,loader);
		return  values.get("return");
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
		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("/");
		Configuration cfg = Configuration.defaultConfiguration();
		cfg.setStatementStart("@");
		cfg.setStatementEnd(null);
		gt = new GroupTemplate(resourceLoader, cfg);

	}

	public  static void main(String[] args) throws IOException {
		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("/");
		Configuration cfg = Configuration.defaultConfiguration();
		cfg.setStatementStart("@");
		cfg.setStatementEnd(null);
		GroupTemplate gt ;
		StringTemplateResourceLoader loader = new StringTemplateResourceLoader();
		gt = new GroupTemplate(resourceLoader, cfg);
		Map map = new HashMap();
		map.put("a",1);
		Writer writer =  new StringWriter();
		Map values = gt.runScript("if(a==1) return true ; else return  false;",map,writer,loader);
		System.out.println(values);
	}
}
