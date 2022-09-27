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



## 目前结果（2022-9-27）越高越好
```
Beetl.benchmark       thrpt    5  141912.506 ± 19090.130  ops/s
Enjoy.benchmark       thrpt    5  129350.516 ±  8206.576  ops/s
Freemarker.benchmark  thrpt    5   48062.783 ±  9054.282  ops/s
Handlebars.benchmark  thrpt    5   48505.286 ±  6078.944  ops/s
Rocker.benchmark      thrpt    5  102699.041 ± 11827.123  ops/s
Thymeleaf.benchmark   thrpt    5   14495.261 ±  1460.815  ops/s
Velocity.benchmark    thrpt    5   12071.498 ±  4226.219  ops/s

```
> 注意，较早版本性能测试包含了字节直接输出这种情况，考虑到大多数模板引擎不支持，取消了这种方式
> （静态文本）字节直接输，不需要转码，对web性能有巨大提升

### 总结

* 对比其他流行模板引擎Beetl性能非常优秀， https://www.kancloud.cn/xiandafu/javamicrotuning。
* Thymeleaf 和 Velocity 性能是最糟糕的，这个测试毫无疑问又一次证明
* 并发测试考虑到机器性能原因，有可能不准，供参考。可以查看单线程性能测试结果，这能体现模板引擎的能力


