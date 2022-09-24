package org.beetl.express;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Script;
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
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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

	AtomicInteger atomicInteger = new AtomicInteger(0);
	@Override
	@Benchmark
	public Set reflect() {
		String newName = "kk"+atomicInteger.incrementAndGet();
		Script script = gt.getScript("return arg.age+("+newName+".pay+12);",loader);
		//返回arg和newName，脚本中需要外部输入的变量
		return script.program.metaData.globalIndexMap.keySet();
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
	@Benchmark
	public Object forExpresss() {
		Map map = new HashMap();
		map.put("arg",getArg());
		Writer writer =  new StringWriter();
		Map values = gt.runScript("var c =0; for(var i=0;i<arg.age;i++){ c=c+1;} ",map,writer,loader);
		return  values.get("c");
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
		Beetl beetl = new Beetl();
		beetl.init();
		System.out.println(beetl.forExpresss());
	}
}
