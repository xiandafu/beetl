
Beetl 内核是脚本引擎，Beetl模板会被解析成脚本引擎执行渲染，Beetl脚本引擎本生也可以作为
一些规则，表达式，脚本计算使用，如下是目前近几年还在维护的表达式/脚本引擎的性能测试结果

测试内容有一个简单的求值计算，和一个if语句(spel 不支持if，所以使用了三元表达式)

* Aviator https://gitee.com/mirrors/aviator
* Beetl https://gitee.com/xiandafu/beetl
* Groovy http://www.groovy-lang.org/
* JfireEL https://gitee.com/eric_ds/jfireEL
* Spel https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/expressions.html
* Nashorn https://github.com/openjdk/nashorn

测试项目
* simpleExpress ,一个简单的求值表达式 arg.age+(12+arg.pay)
* ifExpresss 一个if语句.(Spel和JfireEL 不支持，使用了三元表达式代替)
* reflect  能返回表达式的需要外部传入变量，如表达式`arg.age+(12+kk.pay)` 返回`[arg,kk]`

```
Benchmark               Mode  Cnt         Score         Error  Units

Aviator.ifExpresss     thrpt    5   4131386.819 ±  859876.392  ops/s
Aviator.simpleExpress  thrpt    5   3686300.667 ±   54327.448  ops/s
Beetl.ifExpresss       thrpt    5   4111279.921 ±  223999.136  ops/s
Beetl.simpleExpress    thrpt    5   4053010.154 ±  321478.988  ops/s
Beetl.reflect          thrpt    5   8075617.311 ±  492320.313  ops/s
Groovy.ifExpresss      thrpt    5    117707.493 ±    3160.669  ops/s
Groovy.simpleExpress   thrpt    5    118007.303 ±   15116.626  ops/s
JfireEL.ifExpresss     thrpt    5  27322877.964 ± 3306518.718  ops/s
JfireEL.simpleExpress  thrpt    5  19416536.297 ±  576597.285  ops/s
Nashorn.ifExpresss     thrpt    5      8418.353 ±    3675.774  ops/s
Nashorn.simpleExpress  thrpt    5      9125.333 ±     609.877  ops/s
Spel.ifExpresss        thrpt    5    834424.386 ±   13786.858  ops/s
Spel.simpleExpress     thrpt    5    626020.540 ±   39763.562  ops/s


```

> score越大越快。如果以上测试方式有问题，请联系我，我不是对所有的脚本引擎熟悉使用

# 测试结果

JfireEL>> Aviator=Beetl >>Spel>>Groovy>>Nashorn

* Spel和JfireEL是纯表达式引擎，Beetl,Groovy,Nashorn，Aviator则是脚本引擎
* reflect 方法是获取表达式需要的外边变量，比如这个对有些业务场景很管用，比如根据表达式可以提示用户需要输入哪些变量，`x+y/2`, 提示用户需要输入x和y ，目前自有Beetl支持






