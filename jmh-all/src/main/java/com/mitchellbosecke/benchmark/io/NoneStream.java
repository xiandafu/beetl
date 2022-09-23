package com.mitchellbosecke.benchmark.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 模板测试，IO输出需要屏蔽以避免IO导致模板引擎自身测试不准，这就好比测试数据库连接池性能，不能真的去链接
 * 一个数据库，或者真的执行SQL语句一样。这样模拟使得测试结果接近性能测试
 */
public final class NoneStream extends OutputStream {

  @Override
  public final void write(int b) throws IOException {
  }

  @Override
  public final void write(byte[] source) throws IOException {
  }

  @Override
  public final void write(byte[] source, int offset, int length) throws IOException {
  }

  @Override
  public final void flush() throws IOException {
  }
}
