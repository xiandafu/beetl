package org.beetl.express;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
@Fork(1)
@Warmup(iterations = 5,time=1)
@Measurement(iterations =5 ,time=1,timeUnit= TimeUnit.SECONDS )
@Threads(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)

public abstract  class BaseExpressBenchmark {

	Arg arg = new Arg();
	List args = Arrays.asList(arg);


	/**
	 * 测试简单表达式
	 * @return
	 */
	public abstract  Object simpleExpress();

	/**
	 * 测试对脚本内部运行过程的输出
	 * @return
	 */
	public  Set reflect(){
		throw new UnsupportedOperationException();
	}

	/**
	 * 测试if语句
	 * @return
	 */
	public abstract  Object ifExpresss();

	/**
	 * 测试循环语句
	 * @return
	 */
	public abstract  Object forExpresss();

	protected  Arg getArg(){
		return arg;
	}




}
