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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.core.Resource;
import org.beetl.core.statement.GrammarToken;
import org.jetbrains.annotations.NotNull;

/**
 * 将BeetlException表达成ErrorInfo
 *
 * @author xiandafu
 */
public class ErrorInfo {

    /** {@link GrammarToken#text} */
    public String errorTokenText = "";
    /** {@link GrammarToken#line} */
    public int errorTokenLine = 0;
    /** {@link BeetlException#getMessage()} */
    public String msg;
    /** {@link BeetlException#getCause()} */
    public Throwable cause;
    /** {@link BeetlException#errorResourceStack} */
    public List<Resource> resourceCallStack = null;
    /** {@link BeetlException#errorTokenStack} */
    public List<GrammarToken> tokenCallStack = null;

    /** 错误码 */
    @BeetlException.BeetlErrorCode
    public String errorCode = null;
    /** 错误类型 */
    public String type = "";

    /** errCode 与 type 的映射 */
    public static Map<String, String> errorLocalMap = new HashMap<>();

    static {
        errorLocalMap.put(BeetlException.ERROR, "错误");
        errorLocalMap.put(BeetlException.DO_NOT_SUPPORT, "不支持的语法");
        errorLocalMap.put(BeetlException.VAR_ALREADY_DEFINED, concat("变量已经定义", BeetlException.VAR_ALREADY_DEFINED));
        errorLocalMap.put(BeetlException.VAR_READONLY, concat("全局变量为只读变量", BeetlException.VAR_READONLY));
        errorLocalMap.put(BeetlException.VAR_NOT_DEFINED, concat("变量未定义", BeetlException.VAR_NOT_DEFINED));
        errorLocalMap.put(BeetlException.FUNCTION_NOT_FOUND, concat("该方法未注册", BeetlException.FUNCTION_NOT_FOUND));
        errorLocalMap.put(BeetlException.FUNCTION_INVALID, concat("方法未实现Function接口", BeetlException.FUNCTION_INVALID));
        errorLocalMap.put(BeetlException.TAG_NOT_FOUND, concat("该标签未注册", BeetlException.TAG_NOT_FOUND));
        errorLocalMap.put(BeetlException.VIRTUAL_NOT_FOUND, concat("未找到虚拟属性", BeetlException.VIRTUAL_NOT_FOUND));
        errorLocalMap.put(BeetlException.TAG_INSTANCE_ERROR, concat("创建Tag错", BeetlException.TAG_INSTANCE_ERROR));
        errorLocalMap.put(BeetlException.NATIVE_CALL_INVALID, concat("本地调用不合法，找不到类或者方法", BeetlException.NATIVE_CALL_INVALID));
        errorLocalMap.put(BeetlException.NATIVE_CALL_EXCEPTION, concat("调用方法抛出了异常", BeetlException.NATIVE_CALL_EXCEPTION));
        errorLocalMap.put(BeetlException.NATIVE_SECUARITY_EXCEPTION, concat("安全管理器不允许的本地调用", BeetlException.NATIVE_SECUARITY_EXCEPTION));
        errorLocalMap.put(BeetlException.FORMAT_NOT_FOUND, concat("未注册的格式化函数", BeetlException.FORMAT_NOT_FOUND));
        errorLocalMap.put(BeetlException.DEFAULT_FORMAT_NOT_FOUND, concat("类型未有对应的默认的格式化函数", BeetlException.DEFAULT_FORMAT_NOT_FOUND));
        errorLocalMap.put(BeetlException.ATTRIBUTE_INVALID, concat("属性获取异常", BeetlException.ATTRIBUTE_INVALID));
        errorLocalMap.put(BeetlException.ATTRIBUTE_NOT_FOUND, concat("未找到属性", BeetlException.ATTRIBUTE_NOT_FOUND));
        errorLocalMap.put(BeetlException.CAST_LIST_OR_MAP_ERROR, concat("模板里使用了[]指示应该是Map或者List，但实际上不是此类型", BeetlException.CAST_LIST_OR_MAP_ERROR));
        errorLocalMap.put(BeetlException.ATTRIBUTE_NOT_FOUND_PRIVATE, concat("未找到属性，可能是私有属性", BeetlException.ATTRIBUTE_NOT_FOUND));
        errorLocalMap.put(BeetlException.NULL, concat("表达式值为空", BeetlException.NULL));
        errorLocalMap.put(BeetlException.DIV_ZERO_ERROR, concat("除0错误", BeetlException.DIV_ZERO_ERROR));
        errorLocalMap.put(BeetlException.ARRAY_INDEX_ERROR, concat("数组index错", BeetlException.ARRAY_INDEX_ERROR));
        errorLocalMap.put(BeetlException.ARRAY_TYPE_ERROR, concat("本地调用[] 期望类型是数组", BeetlException.ARRAY_TYPE_ERROR));
        errorLocalMap.put(BeetlException.BOOLEAN_EXPECTED_ERROR, concat("期望为Boolean类型", BeetlException.BOOLEAN_EXPECTED_ERROR));
        errorLocalMap.put(BeetlException.COLLECTION_EXPECTED_ERROR, concat("期望遍历目标是是集合，数组，Iterator，Iterable", BeetlException.COLLECTION_EXPECTED_ERROR));
        errorLocalMap.put(BeetlException.NUMBER_EXPECTED_ERROR, concat("期望是数字类型", BeetlException.NUMBER_EXPECTED_ERROR));
        errorLocalMap.put(BeetlException.STRICK_MVC, concat("严格MVC，不允许的语法", BeetlException.STRICK_MVC));
        errorLocalMap.put(BeetlException.NATIVE_NOT_ALLOWED, concat("本地调用不允许", BeetlException.NATIVE_NOT_ALLOWED));
        errorLocalMap.put(BeetlException.GET_CALL_ERROR, concat("属性获取错误", BeetlException.GET_CALL_ERROR));
        errorLocalMap.put(BeetlException.TOKEN_ERROR, concat("词法错误", BeetlException.TOKEN_ERROR));
        errorLocalMap.put(BeetlException.PARSER_HTML_TAG_ERROR, concat("解析HTML标签出错", BeetlException.PARSER_HTML_TAG_ERROR));
        errorLocalMap.put(BeetlException.PARSER_UNKNOW_ERROR, concat("语法错误", BeetlException.PARSER_UNKNOW_ERROR));
        errorLocalMap.put(BeetlException.PARSER_VIABLE_ERROR, concat("语法错误，无法继续解析模板", BeetlException.PARSER_VIABLE_ERROR));
        errorLocalMap.put(BeetlException.PARSER_MISS_ERROR, concat("缺少符号", BeetlException.PARSER_MISS_ERROR));
        errorLocalMap.put(BeetlException.PARSER_PREDICATE_ERROR, concat("语法错误", BeetlException.PARSER_PREDICATE_ERROR));
        errorLocalMap.put(BeetlException.TYPE_SEARCH_ERROR, concat("未找到指定类型，请配置类搜索路径或者增加类包名", BeetlException.TYPE_SEARCH_ERROR));
        errorLocalMap.put(BeetlException.PARSER_NATIVE_ERROR, concat("本地调用格式出错，不允许此格式", BeetlException.PARSER_NATIVE_ERROR));
        errorLocalMap.put(BeetlException.TEMPLATE_LOAD_ERROR, concat("错找不到指定模板或者加载错", BeetlException.TEMPLATE_LOAD_ERROR));
        errorLocalMap.put(BeetlException.CLIENT_IO_ERROR_ERROR, concat("客户端IO出错", BeetlException.CLIENT_IO_ERROR_ERROR));
        errorLocalMap.put(BeetlException.EXPRESSION_NOT_COMPATIBLE, concat("表达式类型不一致，无法运算", BeetlException.EXPRESSION_NOT_COMPATIBLE));
        errorLocalMap.put(BeetlException.AJAX_ALREADY_DEFINED, concat("Ajax 锚点已经定义", BeetlException.AJAX_ALREADY_DEFINED));
        errorLocalMap.put(BeetlException.AJAX_NOT_FOUND, concat("Ajax 锚点未定义", BeetlException.AJAX_NOT_FOUND));
        errorLocalMap.put(BeetlException.AJAX_PROPERTY_ERROR, concat("Ajax 属性设置错误", BeetlException.AJAX_PROPERTY_ERROR));
    }

    /**
     * 将入参拼接成字符串
     *
     * @param info 错误信息
     * @param code 错误码
     * @return {@link #type}
     */
    @NotNull
    private static String concat(String info, @BeetlException.BeetlErrorCode String code) {
        return info + "(" + code + ")";
    }

    /**
     * 构造方法
     *
     * @param ex Beetl异常
     */
    public ErrorInfo(BeetlException ex) {
        cause = ex.getCause();
        errorCode = ex.detailCode;
        if (errorCode.startsWith("TEMPLATE")) {
            this.msg = ex.getMessage();
            this.type = errorLocalMap.get(errorCode);
            return;
        } else if (errorCode.startsWith("PARSER")) {
            if (ex.token != null) {
                this.errorTokenText = ex.token.text;
            } else {
                this.errorTokenText = ex.getMessage();
            }
        } else {
            if (ex.token != null) {
                this.errorTokenText = ex.token.text;
            } else {
                ex.printStackTrace();
            }
        }

        this.msg = ex.getMessage();
        this.errorTokenLine = ex.token.line;
        this.type = errorLocalMap.get(errorCode);
        if (type == null) {
            type = errorCode;
        }
        this.resourceCallStack = ex.errorResourceStack;
        this.tokenCallStack = ex.errorTokenStack;

    }

    /**
     * 调用栈的大小是否大于1
     *
     * @return true 表示调用栈的大小大于1
     */
    public boolean hasCallStack() {
        return resourceCallStack.size() > 1;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "errorTokenText='" + errorTokenText + '\'' +
                ", errorTokenLine=" + errorTokenLine +
                ", msg='" + msg + '\'' +
                ", cause=" + cause +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
