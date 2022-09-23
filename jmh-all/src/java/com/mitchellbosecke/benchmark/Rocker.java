package com.mitchellbosecke.benchmark;

import com.fizzed.rocker.ContentType;
import com.fizzed.rocker.RockerOutput;
import com.fizzed.rocker.RockerOutputFactory;
import com.mitchellbosecke.benchmark.io.CharStream;
import com.mitchellbosecke.benchmark.io.NoneStream;
import com.mitchellbosecke.benchmark.model.Stock;
import freemarker.template.TemplateException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Benchmark for Rocker template engine by Fizzed.
 * 
 * https://github.com/fizzed/rocker
 * 
 * @author joelauer
 */
public class Rocker extends BaseBenchmark {

    private List<Stock> items;

    @Setup
    public void setup() throws IOException {
        // no config needed, replicate stocks from context
        this.items = Stock.dummyItems();
    }

    @Benchmark
	public String benchmark() throws TemplateException, IOException {

		return templates.stocks
				.template(this.items)
				.render().toString();

    }

	@Override
	public String test() throws Exception {
		return templates.stocks
				.template(this.items)
				.render().toString();
	}


	class EmptyCharRockerOutputFactory implements RockerOutputFactory{

		@Override
		public RockerOutput create(ContentType contentType, String charsetName) {
			return new EmptyCharRockerOutput(contentType,charsetName);
		}
	}

	class EmptyByteRockerOutputFactory implements RockerOutputFactory{

		@Override
		public RockerOutput create(ContentType contentType, String charsetName) {
			return new EmptyByteRockerOutput(contentType,charsetName);
		}
	}

	class EmptyCharRockerOutput implements  RockerOutput{

		ContentType contentType;
		Charset charset = null;
		CharStream stream = null;
		public EmptyCharRockerOutput(ContentType contentType,String charsetName) {
			this.contentType = contentType;
			this.charset = Charset.forName(charsetName);
			stream = new CharStream(charsetName);
		}

		@Override
		public ContentType getContentType() {
			return contentType;
		}

		@Override
		public Charset getCharset() {
			return charset;
		}

		@Override
		public RockerOutput w(String string) throws IOException {
			stream.write(string);
			return this;
		}

		@Override
		public RockerOutput w(byte[] bytes) throws IOException {
			String s = new String(bytes, charset);
			stream.write(s);
			return this;
		}

		@Override
		public int getByteLength() {
			return 0;
		}

		@Override
		public String toString(){
			return null;
		}
	}



	class EmptyByteRockerOutput implements  RockerOutput{

		ContentType contentType;
		Charset charset = null;
		NoneStream stream = null;
		public EmptyByteRockerOutput(ContentType contentType,String charsetName) {
			this.contentType = contentType;
			this.charset = Charset.forName(charsetName);
			stream = new NoneStream();
		}

		@Override
		public ContentType getContentType() {
			return contentType;
		}

		@Override
		public Charset getCharset() {
			return charset;
		}

		@Override
		public RockerOutput w(String string) throws IOException {
			stream.write(string.getBytes(charset));
			return this;
		}

		@Override
		public RockerOutput w(byte[] bytes) throws IOException {

			stream.write(bytes);
			return this;
		}

		@Override
		public int getByteLength() {
			return 0;
		}

		@Override
		public String toString(){
			return null;
		}
	}

}
