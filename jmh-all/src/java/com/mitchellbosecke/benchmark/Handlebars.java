
package com.mitchellbosecke.benchmark;
import java.io.IOException;
import java.io.StringWriter;

import com.mitchellbosecke.benchmark.io.CharStream;
import com.mitchellbosecke.benchmark.io.NoneStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import com.github.jknack.handlebars.Handlebars.SafeString;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.mitchellbosecke.benchmark.model.Stock;

public class Handlebars extends BaseBenchmark {

    private Object context;

    private Template template;

    @Setup
    public void setup() throws IOException {
        template = new com.github.jknack.handlebars.Handlebars(new ClassPathTemplateLoader("/", ".html"))
                .registerHelper("minus", new Helper<Stock>() {
                    @Override
                    public CharSequence apply(final Stock stock, final Options options)
                            throws IOException {
                        return stock.getChange() < 0 ? new SafeString("class=\"minus\"") : null;
                    }
                }).compile("templates/stocks.hbs");
        this.context = getContext();
    }

    @Benchmark
    public String benchmark() throws IOException {

		StringWriter writer = new StringWriter();
		template.apply(context,new CharStream("UTF-8"));
		return writer.toString();

    }

	@Override
	public String test() throws Exception {
		return template.apply(context);
	}

}