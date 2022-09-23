package com.mitchellbosecke.benchmark;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

import com.mitchellbosecke.benchmark.model.Stock;

@Fork(1)
@Warmup(iterations = 5,time=1)
@Measurement(iterations =5 ,time=1,timeUnit=TimeUnit.SECONDS )
@Threads(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public abstract  class BaseBenchmark {

	/**
	 * 在TYPE_STRING基础上，新增模拟俩种测试场景，提供字符流，提供字节流，实际情况应该是字节流才是最终运行结果
	 * 目前只有Beetl和Rocker提供
	 * 在验证加入的时候，Param可设置为1先验证，如果不支持3，抛出异常
	 */

    protected Map<String, Object> getContext() {
        Map<String, Object> context = new HashMap<>();
        context.put("items", Stock.dummyItems());
        return context;
    }
	abstract String benchmark() throws Exception;

    public  String test() throws Exception{
		return benchmark();
	}

}
