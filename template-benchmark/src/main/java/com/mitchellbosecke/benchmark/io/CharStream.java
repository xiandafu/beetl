package com.mitchellbosecke.benchmark.io;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;


public final class CharStream extends Writer  {
  private int size;
  private Writer writer;

  public CharStream(String encoding)  {
    try {
      this.writer = new OutputStreamWriter(new NoneStream(), encoding);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void write(int c) throws IOException {
    this.writer.write(c);
    this.size++;
  }

  @Override
  public void write(char[] source) throws IOException {
    this.writer.write(source);
    this.size += source.length;
  }

  @Override
  public void write(char[] source, int offset, int length) throws IOException {
    this.writer.write(source, offset, length);
    this.size += length;
  }

  @Override
  public void write(String source) throws IOException {
    this.writer.write(source);
    this.size += source.length();
  }

  @Override
  public void write(String source, int offset, int length) throws IOException {
    this.writer.write(source, offset, length);
    this.size += length;
  }

  @Override
  public Writer append(CharSequence source) throws IOException {
    this.writer.append(source);
    this.size += source.length();
    return this;
  }

  @Override
  public Writer append(CharSequence source, int start, int end) throws IOException {
    this.writer.append(source, start, end);
    this.size += end - start;
    return this;
  }

  @Override
  public Writer append(char c) throws IOException {
    this.writer.append(c);
    this.size++;
    return this;
  }

  @Override
  public void flush() throws IOException {
    this.writer.flush();
  }

  @Override
  public void close() throws IOException {
    this.writer.close();
  }

  public final long size() {
    return this.size;
  }
}
