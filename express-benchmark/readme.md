
Beetl 内核是脚本引擎，Beetl模板会被解析成脚本引擎执行渲染，Beetl脚本引擎本生也可以作为
一些规则，表达式，脚本计算使用，如下是目前近几年还在维护的表达式/脚本引擎的性能测试结果

测试内容有一个简单的求值计算，和一个if语句(spel 不支持if，所以使用了三元表达式)

* Aviator https://gitee.com/mirrors/aviator
* Beetl https://gitee.com/xiandafu/beetl
* Groovy http://www.groovy-lang.org/
* JfireEL https://gitee.com/eric_ds/jfireEL
* Spel https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/expressions.html
* Nashorn https://github.com/openjdk/nashorn
* Jexl3 https://commons.apache.org/proper/commons-jexl/reference/examples.html
* Mvel http://mvel.documentnode.com/#language-guide-for-2.0


测试项目
* simpleExpress ,一个简单的求值表达式 arg.age+(12+arg.pay)
* ifExpresss 一个if语句.(Spel和JfireEL 不支持，使用了三元表达式代替)
* reflect  能返回表达式的需要外部传入变量，如表达式`arg.age+(12+kk.pay)` 返回`[arg,kk]`
* forExpresss 循环测试。

如果脚本语言没有如上测试项目，则是不支持

```
Benchmark               Mode  Cnt         Score        Error  Units
Aviator.forExpresss    thrpt    5    501413.321 ±   4657.336  ops/s
Aviator.ifExpresss     thrpt    5   4699456.542 ± 266831.101  ops/s
Aviator.simpleExpress  thrpt    5   3868701.018 ±  38439.986  ops/s
Beetl.forExpresss      thrpt    5   1685875.017 ±  28454.020  ops/s
Beetl.ifExpresss       thrpt    5   4461489.443 ±  78687.317  ops/s
Beetl.reflect          thrpt    5     62972.088 ±  85785.390  ops/s
Beetl.simpleExpress    thrpt    5   4328852.130 ± 144586.699  ops/s
Groovy.ifExpresss      thrpt    5    119493.364 ±   1472.301  ops/s
Groovy.simpleExpress   thrpt    5    121724.720 ±   1533.726  ops/s
Jexl3.forExpresss      thrpt    5    789815.632 ±  42390.393  ops/s
Jexl3.ifExpresss       thrpt    5   4500714.752 ± 138214.400  ops/s
Jexl3.simpleExpress    thrpt    5   3901843.173 ± 125472.114  ops/s
JfireEL.ifExpresss     thrpt    5  28337464.920 ± 291859.385  ops/s
JfireEL.simpleExpress  thrpt    5  18824292.084 ± 103241.504  ops/s
Mvel.forExpresss       thrpt    5     11954.857 ±     84.105  ops/s
Mvel.ifExpresss        thrpt    5    230373.242 ±   1827.288  ops/s
Mvel.simpleExpress     thrpt    5    316083.646 ±   1320.717  ops/s
Nashorn.ifExpresss     thrpt    5     10010.541 ±    752.057  ops/s
Nashorn.simpleExpress  thrpt    5      8993.022 ±    518.940  ops/s
Spel.ifExpresss        thrpt    5    850338.540 ±  41826.542  ops/s
Spel.simpleExpress     thrpt    5    636251.839 ±   2553.017  ops/s


```

> score越大越快。如果以上测试方式有问题，请联系我，我不是对所有的脚本引擎熟悉使用

# 测试结果

JfireEL>> Aviator=Beetl >>Spel>>Groovy>>Nashorn

* Spel和JfireEL是纯表达式引擎，Beetl,Groovy,Nashorn，Aviator则是脚本引擎
* reflect 方法是获取表达式需要的外边变量，比如这个对有些业务场景很管用，比如根据表达式可以提示用户需要输入哪些变量，`x+y/2`, 提示用户需要输入x和y ，目前自有Beetl支持






