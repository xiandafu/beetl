package org.beetl.express;

import com.jfirer.jfireel.expression.Expression;
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

public class JfireEL extends BaseExpressBenchmark{

	Expression addLexer = null;
	Expression ifLexer = null;

	@Benchmark
	public Object simpleExpress() {

		 Map map = new HashMap();
		 map.put("arg",getArg());
		return addLexer.calculate(map);
	}


	@Benchmark
	@Override
	public Object ifExpresss() {
		Map map = new HashMap();
		map.put("arg",getArg());
		return ifLexer.calculate(map);
	}

	@Override
//	@Benchmark
	public Object forExpresss() {
		return null;
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
		addLexer = Expression.parse("arg.age+(arg.pay+12)");
		ifLexer = Expression.parse("arg.age==10?true:false");

	}

	public  static void main(String[] args) throws IOException {
		JfireEL jfireEL = new JfireEL();
		jfireEL.init();
		System.out.println(jfireEL.ifExpresss());
	}
}
