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



	public abstract  Object simpleExpress();
	public  Set reflect(){
		throw new UnsupportedOperationException();
	}

//	public abstract  Object callFunction();
//	public abstract  Object complexCondition();

	public abstract  Object ifExpresss();
	public abstract  Object forExpresss();
	protected  Arg getArg(){
		return arg;
	}

	protected  List<Arg> argList(){
		return args;
	}


}
