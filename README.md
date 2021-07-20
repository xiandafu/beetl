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

                                  Beetl 3.0
```

## Beetl3 高速模板引擎

[iBeetl.com](iBeetl.com) © 2011 ~ 2021，国内流行模板引擎

[![Maven Central](https://img.shields.io/maven-central/v/com.ibeetl/beetl.svg)](https://mvnrepository.com/search?q=g:com.ibeetl%20AND%20beetl)

#### 功能 

[http://ibeetl.com/guide/#beetl](http://ibeetl.com/guide/#beetl)

- 动态页面生成
- 静态页面生成
- 代码生成
- 通过生成XML文本中间格式间接生成PDF，WORD等格式
- 短信，微信等模板内容生成
- 脚本引擎
- 规则引擎

#### 介绍

Beetl  ['biːtl]  3.0，从 [https://github.com/javamonkey/beetl2.0/](https://github.com/javamonkey/beetl2.0/) 迁移过来

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

QQ交流群：636321496,219324263

Beetl 官网：[ibeetl.com](ibeetl.com) 

Beetl 社区：[bbs.ibeetl.com](http://42.96.162.109/bbs/bbs/index/1.html)

#### 性能

<https://github.com/fizzed/template-benchmark>

![p1](/doc/resources/p1.png) 

#### 支持

任何企业和个人都可以免费使用，并能免费得到社区，论坛，QQ群和作者的免费技术支持。以下情况需要收费技术支持，详情可联系微信（lliijjzz），备注“商业技术支持”

- 任何公开申明了 `996` 工作制度的企业，将收取 `7996 元` / 年 的费用
- 想获得商业技术支持，如培训，技术咨询，定制，售后等，可根据公司规模收取 `1000 - 10000 元` 年费

#### 如何贡献

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
     * 通过 {@param key} 从缓存中获取对应的 value
     *
     * @param key 键，有可能为 null
     * @return 返回缓存中 {@param key} 所对应的 value，有可能为 null
     */
    Object get(Object key);

    /**
     * 通过 {@param key} 从缓存中获取对应的 value；
     * 如果获取的value为null，则将 {@param function#apply} 方法的返回值作为newValue，添加到缓存中，并返回
     *
     * @param key      键，有可能为 null
     * @param function 函数，在通过key获取value为null时，将执行 {@param function#apply} 方法
     * @return 如果获取的value为null，则将 {@param function#apply} 方法的返回值作为newValue，添加到缓存中，并返回
     */
    Object get(Object key, Function<Object,Object> function);

}
```

**git提交规范 - 示例**

```shell
# git commit 信息应该包含 [发行版本号] + [模块] + [提交信息]
# 例如当前 Beetl 是 3.2.0，修改的内容是"语法树结点的注释"，则 commit 信息为：
git commit -m '【Sprint3.3.0】【Beetl】语法树结点的注释'
```

