---
typora-root-url: ../beetl3.0
---

```text
 ______                 _________  _____     
|_   _ \               |  _   _  ||_   _|    
  | |_) |  .---.  .---.|_/ | | \_|  | |      
  |  __'. / /__\\/ /__\\   | |      | |   _  
 _| |__) || \__.,| \__.,  _| |_    _| |__/ | 
|_______/  '.__.' '.__.' |_____|  |________| 

```

## Beetl3 高速模板引擎

[iBeetl.com](iBeetl.com) © 2011 ~ 2022，国内流行模板引擎

[![Maven Central](https://img.shields.io/maven-central/v/com.ibeetl/beetl.svg)](https://mvnrepository.com/search?q=g:com.ibeetl%20AND%20beetl)

### 功能 



- 动态页面生成
- 静态页面生成
- 代码生成
- 通过生成XML文本中间格式间接生成PDF，WORD等格式
- 短信，微信等模板内容生成
- 脚本引擎 
- 规则引擎

### 介绍

Beetl  ['biːtl]  3.0，从 [https://github.com/javamonkey/beetl2.0/](https://github.com/javamonkey/beetl2.0/) 迁移过来

QQ交流群：636321496(满),219324263(满),252010126

官网：[ibeetl.com](ibeetl.com) 

### 性能 每秒处理量，score越大越好

* 模板性能（参考template-beanchmark)

Beetl>Enjoy>Rocker>>Freemarker>>Thymeleaf==Velociy

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

* 表达式/规则性能 (参考express-benchmark) 

JfireEL>> Aviator=Beetl=Jexl3 >>Spel>>Mvel=Groovy>>Nashorn

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
### 基本功能

官网文档: [https://www.kancloud.cn/xiandafu/beetl3_guide](https://www.kancloud.cn/xiandafu/beetl3_guide)

```
Beetl 3 中文文档
第一部分 基础用法
1.1 安装
1.2 快速开始
1.3 模板基础配置
1.4 模板加载器
1.5 定界符与占位符
1.6 注释
1.7 变量定义
1.8 属性
1.9 数学表达式
1.10 循环语句
1.11 条件语句
1.12 异常捕获
1.13 虚拟属性
1.14 函数调用
1.15 安全输出(重要)
1.16 输出格式化
1.17 标签
1.18 调用Java方法与属性
1.19 严格MVC控制
1.20 指令
1.21 错误处理
1.22 Beetl小工具
1.23 Escape
第二部分 高级用法
2.1 配置GroupTemplate
2.2 自定义方法
2.3 自定义格式化函数
2.4 自定义标签
2.5 自定义虚拟属性
2.6 使用额外的资源加载器
2.7 自定义资源加载器
2.8 使用CompositeResourceLoader
2.9 自定义错误处理器
2.10 自定义安全管理器
2.11 注册全局共享变量
2.12 自定义布局
2.13 性能优化
2.14 定制输出
2.15 定制模板引擎
2.16 直接运行Beetl脚本
2.17 模板校验
第三部分 Web 集成
3.1 Web提供的全局变量
3.2 集成技术开发指南
3.3 Servlet集成
3.4 SpringMVC集成
3.5 Spring Boot集成
3.6 Jodd集成
3.7 JFinal4 集成方案
3.8 Nutz集成
3.9 Struts2集成
3.10 整合ajax的局部渲染技术
3.11 在页面输出错误提示信息
```

### 特色功能

* 语法js的子集，非常易学
* 自定义模板占位符和定界符
* 能扩展模板引擎，实现高级功能
* 能修改语法树，实现高级功能
* 字符出和字节输出支持，高性能web表现
* 高性能解释执行，提供特殊语法，如for{} elsefor{},安全输出语法，省略的三元表达式等
* 支持html/xml Tag 语法
* 内核是脚本引擎，除了模板外，也可以用于表达式和规则。

### 支持

任何企业和个人都可以免费使用，并能免费得到社区，论坛，QQ群和作者的免费技术支持。以下情况需要收费技术支持，详情可联系微信（lliijjzz），备注“商业技术支持”

- 任何公开申明了 `996` 工作制度的企业，将收取 `7996 元` / 年 的费用
- 想获得商业技术支持，如培训，技术咨询，定制，售后等，可根据公司规模收取 `1000 - 10000 元` 年费

### 如何贡献

在您共享代码时，请遵循以下基本代码规范：
- 每行代码不超过120个[等宽字符](https://baike.baidu.com/item/%E7%AD%89%E5%AE%BD%E5%AD%97%E4%BD%93/8434037?fr=aladdin)
- 每个类(Class)、字段(Field)、方法(Method)都必须添加[javadoc注释](https://baike.baidu.com/item/javadoc/4640765?fr=aladdin)(@Override方法可以不加)
- 不能省略if、while后面的大括号
- 不能使用制表符，通过4个空格代替

**注释规范 - 示例**

```java
/*
版权声明部分
*/

package org.beetl.core.cache;

import java.util.function.Function;

/**
 * 缓存的标准接口，每个方法都必须线程安全
 *
 * @author xiandafu
 * @since 2020-09-21
 */
public interface Cache {

    /** Log TAG */
    String TAG = "Cache";

    /**
     * 通过 {@code key} 从缓存中获取对应的 value
     *
     * @param key 键，有可能为 null
     * @return 返回缓存中 {@code key} 所对应的 value，有可能为 null
     */
    Object get(Object key);

    /**
     * 通过 {@code key} 从缓存中获取对应的 value；
     * 如果获取的value为null，则将 {@code function#apply} 方法的返回值作为newValue，添加到缓存中，并返回
     *
     * @param key      键，有可能为 null
     * @param function 函数，在通过key获取value为null时，将执行 {@code function#apply} 方法
     * @return 如果获取的value为null，则将 {@code function#apply} 方法的返回值作为newValue，添加到缓存中，并返回
     */
    Object get(Object key, Function<Object,Object> function);

}
```

**git提交规范 - 示例**

```shell
# git commit 信息应该包含 [发行版本号] + [模块] + [提交信息]
# 例如当前 org.beetl.express.Beetl 是 3.2.0，修改的内容是"语法树结点的注释"，则 commit 信息为：
git commit -m '【Sprint3.3.0】【org.beetl.express.Beetl】语法树结点的注释'
```



Beetl 的成长离不开以下人员的帮助（排名不分先后）：

- [作死模式](javascript:;)
- [一粟蜉蝣](javascript:;)
- [逝水fox](javascript:;)
- [kraken](javascript:;)
- [西安玛雅牛](javascript:;)
- [级?!](javascript:;)
- [orangetys](javascript:;)
- [Oo不懂oO](javascript:;)
- [原上一颗草](javascript:;)
- [龙图腾飞](javascript:;)
- [nutz](javascript:;)
- [天方地圆](javascript:;)
- [九月](javascript:;)
- [Daemons](javascript:;)
- [Gavin.King](javascript:;)
- [Sue](javascript:;)
- [Zhoupan](javascript:;)
- [woate](javascript:;)
- [fitz](javascript:;)
- [darren](http://darren.ink/)
- [zqq](javascript:;)
- [ 醉引花眠](javascript:;)

