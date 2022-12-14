/*
 [The "BSD license"]
 Copyright (c) 2011-2013  闲大赋 (李家智)
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

/**
 * html解析异常
 *
 * @author xiandafu
 */
public class HTMLTagParserException extends BeetlException {

    /** 行号 */
    public int line = 0;

    /**
     * 构造方法
     *
     * @param message 错误信息
     */
    public HTMLTagParserException(String message) {
        super(BeetlException.PARSER_HTML_TAG_ERROR, message);
    }

    /**
     * 获取HTML标签的错误信息
     *
     * @return 错误信息
     */
    public String getHtmlTagErrorMsg() {
        return super.getMessage();
    }

    /**
     * 设置行号
     *
     * @param newLine 新的行号
     * @return 自身
     */
    public HTMLTagParserException setLine(int newLine) {
        this.line = newLine;
        return this;
    }
}
