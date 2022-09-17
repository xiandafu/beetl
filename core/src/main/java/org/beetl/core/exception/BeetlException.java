/*
 [The "BSD license"]
 Copyright (c) 2011-2020  闲大赋 (李家智)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
     derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.beetl.core.exception;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import org.beetl.core.Resource;
import org.beetl.core.statement.GrammarToken;
import org.intellij.lang.annotations.MagicConstant;

/**
 * Beetl异常基类，此异常包含了错误码，错误符号，所属的模板文件，以及错误详情，错误栈
 *
 * @author xiandafu
 */
public class BeetlException extends RuntimeException {

    @MagicConstant(stringValues = {
            DO_NOT_SUPPORT, VAR_ALREADY_DEFINED, AJAX_ALREADY_DEFINED, AJAX_NOT_FOUND, AJAX_PROPERTY_ERROR,
            VAR_READONLY, VAR_NOT_DEFINED, FUNCTION_NOT_FOUND, FUNCTION_INVALID, TAG_NOT_FOUND,
            VIRTUAL_NOT_FOUND, TAG_INSTANCE_ERROR, NATIVE_CALL_INVALID, NATIVE_CALL_EXCEPTION,
            NATIVE_SECUARITY_EXCEPTION, FORMAT_NOT_FOUND, DEFAULT_FORMAT_NOT_FOUND,
            ATTRIBUTE_INVALID, ATTRIBUTE_NOT_FOUND, ATTRIBUTE_NOT_FOUND_PRIVATE,
            CAST_LIST_OR_MAP_ERROR, NULL, DIV_ZERO_ERROR, ARRAY_INDEX_ERROR, ARRAY_TYPE_ERROR,
            BOOLEAN_EXPECTED_ERROR, COLLECTION_EXPECTED_ERROR, NUMBER_EXPECTED_ERROR,
            STRICK_MVC, NATIVE_NOT_ALLOWED, GET_CALL_ERROR, HAS_CALL_ILLEGAL, ERROR, TOKEN_ERROR,
            PARSER_UNKNOW_ERROR, PARSER_VIABLE_ERROR, PARSER_MISS_ERROR, PARSER_PREDICATE_ERROR,
            PARSER_HTML_TAG_ERROR, TYPE_SEARCH_ERROR, PARSER_NATIVE_ERROR, TEMPLATE_LOAD_ERROR,
            CLIENT_IO_ERROR_ERROR, EXPRESSION_NOT_COMPATIBLE, GRAMMAR_NOT_ALLOWED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BeetlErrorCode {
    }

    /** 不支持 */
    public static final String DO_NOT_SUPPORT = "DO_NOT_SUPPORT";
    /** 变量已经定义过了 */
    public static final String VAR_ALREADY_DEFINED = "VAR_ALREADY_DEFINED";
    /** AJAX 已经定义过了 */
    public static final String AJAX_ALREADY_DEFINED = "AJAX_ALREADY_DEFINED";
    /** AJAX 未找到 */
    public static final String AJAX_NOT_FOUND = "AJAX_NOT_FOUND";
    /** AJAX 属性定义错误 */
    public static final String AJAX_PROPERTY_ERROR = "AJAX_PROPERTY_ERROR";
    /** 变量只读: 通过template.set()的变量，即用于渲染模板的变量都是只读的，不能在模板中被改写 */
    public static final String VAR_READONLY = "VAR_READONLY";
    /** 模板引用未定义的变量 */
    public static final String VAR_NOT_DEFINED = "VAR_NOT_DEFINED";
    /** 函数未定义 */
    public static final String FUNCTION_NOT_FOUND = "FUNCTION_NOT_FOUND";
    /** 函数定义无效 */
    public static final String FUNCTION_INVALID = "FUNCTION_INVALID";
    /** 标签未找到 */
    public static final String TAG_NOT_FOUND = "TAG_NOT_FOUND";
    /** 虚拟属性未找到 */
    public static final String VIRTUAL_NOT_FOUND = "VIRTUAL_NOT_FOUND";
    /** 标签实例化错误 */
    public static final String TAG_INSTANCE_ERROR = "TAG_INSTANCE_ERROR";
    /** 本地(Java)调用错误，如 classnotfound, methodnotfound 等 */
    public static final String NATIVE_CALL_INVALID = "NATIVE_CALL_INVALID";
    /** 被调用的 class 抛出了异常 */
    public static final String NATIVE_CALL_EXCEPTION = "NATIVE_CALL_EXCEPTION";
    /** 不允许本地调用 */
    public static final String NATIVE_SECUARITY_EXCEPTION = "NATIVE_SECUARITY_EXCEPTION";
    /** 格式化函数未找到 */
    public static final String FORMAT_NOT_FOUND = "FORMAT_NOT_FOUND";
    /** 默认的格式化函数未找到 */
    public static final String DEFAULT_FORMAT_NOT_FOUND = "DEFAULT_FORMAT_NOT_FOUND";
    /** 引用属性失效 */
    public static final String ATTRIBUTE_INVALID = "ATTRIBUTE_INVALID";
    /** 属性未找到 */
    public static final String ATTRIBUTE_NOT_FOUND = "ATTRIBUTE_NOT_FOUND";
    /** 属性不存在，但可能是私有 */
    public static final String ATTRIBUTE_NOT_FOUND_PRIVATE = "ATTRIBUTE_NOT_FOUND_PRIVATE";
    /** 模板里使用了[]指示应该是 Map 或者 List，但实际上不是此类型 */
    public static final String CAST_LIST_OR_MAP_ERROR = "CAST_LIST_OR_MAP_ERROR";
    /** NULL 标识 */
    public static final String NULL = "NULL";
    /** 除 0 错误 */
    public static final String DIV_ZERO_ERROR = "DIV_ZERO_ERROR";
    /** 数组 index 错 */
    public static final String ARRAY_INDEX_ERROR = "ARRAY_INDEX_ERROR";
    /** 必须是数组 */
    public static final String ARRAY_TYPE_ERROR = "ARRAY_TYPE_ERROR";
    /** 期望表达式返回 bool 值，但却是其他类型 */
    public static final String BOOLEAN_EXPECTED_ERROR = "BOOLEAN_EXPECTED_ERROR";
    /** 期望是个集合，数组，Iterator，Iterable类型，但却是其他类型 */
    public static final String COLLECTION_EXPECTED_ERROR = "COLLECTION_EXPECTED_ERROR";
    /** 期望是数字类型，但却是其他类型 */
    public static final String NUMBER_EXPECTED_ERROR = "NUMBER_EXPECTED_ERROR";
    /** 严格MVC错误，模板使用严格MVC，但模板内容含有不允许的语法 */
    public static final String STRICK_MVC = "STRICK_MVC";
    /** 不允许调用本地方法 */
    public static final String NATIVE_NOT_ALLOWED = "NATIVE_NOT_ALLOWED";
    /** 对象的属性get方法出错 */
    public static final String GET_CALL_ERROR = "GET_CALL_ERROR";
    /** has函数调用错误 */
    public static final String HAS_CALL_ILLEGAL = "HAS_CALL_ILLEGAL";
    /** 语法错误 */
    public static final String ERROR = "ERROR";
    /** Token 错误 */
    public static final String TOKEN_ERROR = "TOKEN_ERROR";
    /** 解析时产生的未知错误 */
    public static final String PARSER_UNKNOW_ERROR = "PARSER_UNKNOW_ERROR";
    /** 解析时产生语法错误 */
    public static final String PARSER_VIABLE_ERROR = "PARSER_VIABLE_ERROR";
    /** 解析时缺少符号 */
    public static final String PARSER_MISS_ERROR = "PARSER_MISS_ERROR";
    /** 解析时产生的谓语错误 */
    public static final String PARSER_PREDICATE_ERROR = "PARSER_PREDICATE_ERROR";
    /** HTML TAG 解析出错 */
    public static final String PARSER_HTML_TAG_ERROR = "PARSER_HTML_TAG_ERROR";
    /** 类型识别错误 */
    public static final String TYPE_SEARCH_ERROR = "TYPE_SEARCH_ERROR";
    /** 本地调用格式错误,如a[0](1,2) */
    public static final String PARSER_NATIVE_ERROR = "PARSER_NATIVE_ERROR";
    /** 模板加载失败 */
    public static final String TEMPLATE_LOAD_ERROR = "TEMPLATE_LOAD_ERROR";
    /** Client IO */
    public static final String CLIENT_IO_ERROR_ERROR = "CLIENT_IO_ERROR_ERROR";
    /** 表达式类型不一致，无法运算,如俩个字符串相乘 */
    public static final String EXPRESSION_NOT_COMPATIBLE = "EXPRESSION_NOT_COMPATIBLE";
    /** 不允许的语法 */
    public static final String GRAMMAR_NOT_ALLOWED = "GRAMMAR_NOT_ALLOWED";

    /** 错误栈的初始容量 */
    private static final int DEFAULT_INITIAL_CAPACITY = 3;

    /** 错误的 Token */
    public GrammarToken token = null;
    /** 错误码 */
    public String detailCode;
    /** 资源id */
    public Resource resource = null;

    /** 错误资源栈 */
    public List<Resource> errorResourceStack = new ArrayList<>(DEFAULT_INITIAL_CAPACITY);
    /** 错误 Token 栈 */
    public List<GrammarToken> errorTokenStack = new ArrayList<>(DEFAULT_INITIAL_CAPACITY);

    public boolean inTagBody = false;

    /** 模板换行符 */
    public String cr = null;

    /**
     * 构造方法
     *
     * @param detailCode 错误码
     * @param msg        错误信息
     */
    public BeetlException(@BeetlErrorCode String detailCode, String msg) {
        super(msg);
        this.detailCode = detailCode;
    }

    /**
     * 构造方法
     *
     * @param detailCode 错误码
     */
    public BeetlException(@BeetlErrorCode String detailCode) {
        super();
        this.detailCode = detailCode;
    }

    /**
     * 构造方法
     *
     * @param detailCode 错误码
     * @param cause      崩溃
     */
    public BeetlException(@BeetlErrorCode String detailCode, Throwable cause) {
        super(cause);
        this.detailCode = detailCode;
    }

    /**
     * 构造方法
     *
     * @param detailCode 错误码
     * @param msg        错误信息
     * @param cause      崩溃
     */
    public BeetlException(@BeetlErrorCode String detailCode, String msg, Throwable cause) {
        super(msg, cause);
        this.detailCode = detailCode;
    }

    @Override
    public String toString() {
        return detailCode;
    }

    /**
     * 添加资源
     *
     * @param resource 资源
     */
    public BeetlException pushResource(Resource resource) {
        if (this.inTagBody) {
            /*
             如果当前渲染的是html标签，则出错资源应该使用该html标签的页面，而不是渲染改标签的页面
             参考,HTMLTagSupportWrapper2,TagStatment
             <#form >
             <#block>
             <#input name="a"/>
             </#block>
             </#form>
             input 内部渲染错误，但显示的出错页面应该是这个html页面，而不是block标签页面
             2018-9-9
             */
            this.inTagBody = false;
            return this;
        }
        if (this.resource == null) {
            this.resource = resource;
        }
        this.errorResourceStack.add(resource);
        return this;
    }

    /**
     * 添加 Token
     *
     * @param token 语法单词
     */
    public BeetlException pushToken(GrammarToken token) {
        if (this.token == null) {
            this.token = token;
        }
        this.errorTokenStack.add(token);
        return this;
    }

    /**
     * 设置Token的内容
     *
     * @param newToken 新的Token
     * @return 本身
     */
    public BeetlException setToken(GrammarToken newToken) {
        this.token = newToken;
        return this;
    }


    /**
     * 返回一个错误描述信息
     */
    public ErrorInfo toErrorInfo() {
        return new ErrorInfo(this);
    }

}
