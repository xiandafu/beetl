
Beetl 内核是脚本引擎，Beetl模板会被解析成脚本引擎执行渲染，Beetl脚本引擎本生也可以作为
一些规则，表达式，脚本计算使用，如下是目前近几年还在维护的表达式/脚本引擎的性能测试结果

测试内容有一个简单的求值计算，和一个if语句(spel 不支持if，所以使用了三元表达式)


```
Benchmark               Mode  Cnt        Score       Error  Units

Aviator.ifExpresss     thrpt    5  4874817.011 ± 56872.655  ops/s
Aviator.simpleExpress  thrpt    5  3929907.791 ± 71560.624  ops/s
Beetl.ifExpresss       thrpt    5  4773559.740 ± 39440.129  ops/s
Beetl.simpleExpress    thrpt    5  4577262.922 ± 71660.094  ops/s
Groovy.ifExpresss      thrpt    5   147872.132 ±  8210.101  ops/s
Groovy.simpleExpress   thrpt    5   140936.790 ± 10458.662  ops/s
Nashorn.ifExpresss     thrpt    5    10414.081 ±  1203.854  ops/s
Nashorn.simpleExpress  thrpt    5     9972.630 ±  1621.798  ops/s
Spel.ifExpresss        thrpt    5   847461.241 ± 18546.745  ops/s
Spel.simpleExpress     thrpt    5   668638.399 ± 14497.212  ops/s


```

> score越大越快

