package com.mitchellbosecke.benchmark;

import com.jfinal.kit.Kv;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.mitchellbosecke.benchmark.io.CharStream;
import com.mitchellbosecke.benchmark.io.NoneStream;
import com.mitchellbosecke.benchmark.model.Stock;
import freemarker.template.TemplateException;
import org.beetl.core.io.CachedStringWriter;
import org.beetl.core.io.NoLockStringWriter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class Enjoy extends BaseBenchmark {

	private Template template;
	private Map<?, ?> data;

	@Setup
	public void setup() throws IOException {
		// 配置快速模式，使用动态编译去除 java.lang.reflect.Method.invoke()，性能提升 13%
		Engine.setFastMode(true);

		// 配置从 class path 与 jar 包中读取模板文件
		Engine engine = Engine.use().setToClassPathSourceFactory();

		// 获取要被渲染的模板，Template 对象是线程安全的，可以多线程下并发使用
		template = engine.getTemplate("/templates/stocks.jfinal.html");

		// 获取渲染模板时用到的数据，类型为 JDK 的 Map 或其子类，本示例使用 Map 的子类 Kv
		data = Kv.by("items", Stock.dummyItems());
	}

	@Benchmark
	public String benchmark() throws TemplateException, IOException {
		return template.renderToString(data);
	}
}

