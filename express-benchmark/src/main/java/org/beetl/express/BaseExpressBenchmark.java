package org.beetl.express;

import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;
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
	public abstract  Object simpleExpress();
//	public abstract  Object callFunction();
//	public abstract  Object complexCondition();

	public abstract  Object ifExpresss();

	protected  int getAddValue(){
		return 12;
	}

	protected  int getAddValue2(){
		return 89;
	}


}
