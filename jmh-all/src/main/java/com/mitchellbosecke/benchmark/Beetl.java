package com.mitchellbosecke.benchmark;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Map;

import com.jfinal.template.io.WriterBuffer;
import com.mitchellbosecke.benchmark.io.CharStream;
import com.mitchellbosecke.benchmark.io.NoneStream;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.io.CachedStringWriter;
import org.beetl.core.io.NoLockStringWriter;
import org.beetl.core.io.SoftReferenceWriter;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

public class Beetl extends BaseBenchmark {

	private Map<String, Object> context;
	GroupTemplate gt = null;

	@Setup
	public void setup() throws IOException {
		ClasspathResourceLoader resourceLoader = new MyClasspathResourceLoader("/");
		Configuration cfg = Configuration.defaultConfiguration();
		cfg.setStatementStart("@");
		cfg.setStatementEnd(null);
		context = this.getContext();
		gt = new GroupTemplate(resourceLoader, cfg);


	}

	@Benchmark
	@Override
	public String benchmark() throws IOException {
		Template template = gt.getTemplate("/templates/stocks.beetl.html");
		template.binding(context);
		try(SoftReferenceWriter sw = SoftReferenceWriter.local()){
			template.renderTo(sw);
			return sw.toString();
		}

	}



	@Override
	public String test() throws Exception {
//		Template template = gt.getTemplate("/templates/stocks.beetl.html");
//		template.binding(getContext());
//		String str = template.render();
//		return str;

		Template template = gt.getTemplate("/templates/stocks.beetl.html");
		template.binding(context);
		try(SoftReferenceWriter sw = SoftReferenceWriter.local()){
			template.renderTo(sw);
			return sw.toString();
		}
	}

	static class MyClasspathResourceLoader extends ClasspathResourceLoader {

		public MyClasspathResourceLoader(String root) {
			super(root);
		}

		@Override
		public void init(GroupTemplate gt) {
			Map<String, String> resourceMap = gt.getConf().getResourceMap();

			if (this.charset == null) {
				this.charset = resourceMap.get("charset");

			}

			this.setAutoCheck(false);

		}
	}
}
