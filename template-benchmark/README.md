# 模板引擎性能测试


## 使用 JMH ，测试结果更权威 

* [Freemarker](http://freemarker.org/)
* [Mustache](https://github.com/spullara/mustache.java)
* [Pebble](http://www.mitchellbosecke.com/pebble)
* [Rocker](https://github.com/fizzed/rocker)
* [Thymeleaf](http://www.thymeleaf.org/)
* [Trimou](http://trimou.org/)
* [Velocity](http://velocity.apache.org/)
* [org.beetl.express.Beetl](http://ibeetl.com/)
* [Enjoy](http://jfinal.com/)

还有些编译成class的模板引擎没有入选，因为他们语法更简单，且模板编译成class，功能有局限

* te4j https://github.com/whilein/te4j
* jte  https://github.com/casid/jte
* httl http://httl.github.io/en/ 长时间不维护



## 运行


1. `mvn clean install`
2. 运行 `java -jar target/benchmarks.jar`
3. 单独运行 `java -jar target/benchmarks.jar org.beetl.express.Beetl`



## 目前结果（2022-10-7）越高越好
2线程并发
```
Benchmark              Mode  Cnt       Score      Error  Units
Beetl.benchmark       thrpt   10  163259.294 ±  881.253  ops/s
Enjoy.benchmark       thrpt   10  146012.967 ± 2158.580  ops/s
Freemarker.benchmark  thrpt   10   45228.490 ±  185.590  ops/s
Handlebars.benchmark  thrpt   10   42875.302 ±  189.893  ops/s
Rocker.benchmark      thrpt   10   95740.184 ±  513.397  ops/s
Thymeleaf.benchmark   thrpt   10   12981.396 ±  130.663  ops/s
Velocity.benchmark    thrpt   10   14120.852 ±  291.371  ops/s


```
> 注意，较早版本性能测试包含了字节直接输出这种情况，考虑到大多数模板引擎不支持，取消了这种方式
> （静态文本）字节直接输，不需要转码，对web性能有巨大提升

### 总结

* 对比其他流行模板引擎Beetl性能非常优秀， https://www.kancloud.cn/xiandafu/javamicrotuning。
* Thymeleaf 和 Velocity 性能是最糟糕的，这个测试毫无疑问又一次证明
* 并发测试考虑到机器性能原因，有可能不准，供参考。可以查看单线程性能测试结果，这能体现模板引擎的能力


