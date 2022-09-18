package org.beetl.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) // 使用模式为运行时间，默认是Mode.Throughput，表示吞吐量
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS) // 预热
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS) // 运行
@Threads(1) // 同时执行的线程数
@Fork(1) // 为每个方法启动一个进程
@OutputTimeUnit(TimeUnit.NANOSECONDS) // 统计结果的时间单元
@State(Scope.Benchmark) // 对象的生命周期
public class BaseJmhTest {

    public static void main(String[] args) {
        BaseJmhTest jmhTest = new BaseJmhTest();
        jmhTest.run();
    }

    protected void run() {
        Options opt = new OptionsBuilder()
                .include(this.getClass().getSimpleName())
                .forks(1)
                .build();
        try {
            new Runner(opt).run();
        } catch (RunnerException e) {
            throw new RuntimeException(this.getClass().getSimpleName() + "#" + e);
        }
    }
}
