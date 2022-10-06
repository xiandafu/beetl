/*
 [The "BSD license"]
 Copyright (c) 2011-2022  闲大赋 (李家智)
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
package org.beetl.core;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;
import org.beetl.core.io.ByteWriter_Char;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.statement.ErrorGrammarProgram;
import org.beetl.core.statement.Program;

/**
 * 模板类
 *
 * @author xiandafu
 */
public class Script<T>  extends Template{

    protected boolean success = false;
    protected Map result = null;
    protected ErrorInfo errorInfo = null;
    protected BeetlException ex = null;
    private boolean done = false;

    protected Script(GroupTemplate gt, Program program, Resource resource,Configuration cf) {
    	super(gt,program,resource,cf);
    	this.ctx.template = this;
    }

    /**
     * 获取模板输出的文本，结果是一个String
     */
    public void execute() throws BeetlException {
        if (done) {
            return;
        }
        StringWriter sw = new StringWriter();
        try {
            renderTo(sw);
            success = true;
        } catch (BeetlException ex) {
            success = false;
            this.ex = ex;
            this.errorInfo = new ErrorInfo(ex);
        }
        done = true;

    }

    public boolean isSuccess() {
        return success;
    }

    public Map getResult() {
        if (!success) {
            throw ex;
        }
        if (result == null) {
            result = parseResult();
        }
        return result;
    }

    public Object getVar(String varName) {
        return getResult().get(varName);
    }

    public T getReturnValue() {
        Map ret = getResult();
        return (T) ret.get("return");
    }

    public ErrorInfo getErrorInfo() {
        if (isSuccess()) {
            throw new IllegalStateException("脚本运行成功");
        }
        return this.errorInfo;
    }

    protected Map parseResult() {
        Map<String, Integer> idnexMap = program.metaData.getTemplateRootScopeIndexMap();
        Object[] values = ctx.vars;
        Map<String, Object> result = new HashMap<String, Object>();
        for (Entry<String, Integer> entry : idnexMap.entrySet()) {
            String name = entry.getKey();
            int index = entry.getValue();
            Object value = values[index];
            result.put(name, value);
        }
        if (values == null) {
            return null;
        }
        Object ret = ctx.vars[ctx.vars.length - 1];
        if (ret != Context.NOT_EXIST_OBJECT) {
            result.put("return", ret);
        }

        return result;
    }

    /**
     * 获取模板输出的文本,输出到Writer里
     */
    public void renderTo(Writer writer) throws BeetlException {
        ByteWriter_Char byteWriter = new ByteWriter_Char(writer, cf.charset, ctx);
        this.renderTo(byteWriter);

    }

    public void renderTo(ByteWriter byteWriter) {

        try {
            ctx.byteWriter = byteWriter;
            ctx.byteOutputMode = cf.directByteOutput;
            ctx.gt = this.gt;

            program.metaData.initContext(ctx);
            program.execute(ctx);
            this.success = true;

        } catch (BeetlException e) {
            if (!(program instanceof ErrorGrammarProgram)) {
                e.pushResource(this.resource);
            }
            Writer w = BeetlUtil.getWriterByByteWriter(ctx.byteWriter);

            e.cr = this.program.metaData.lineSeparator;
            throw e;

        }

    }

    /**
     * 为模板绑定一个变量，infer是true
     *
     * @param varName 变量名称，必须符合jaavascript 命名规范
     * @param o       模板变量
     */
    public void binding(String varName, Object o) {
        ctx.set(varName, o);
    }

    /**
     * 为脚本绑定多个变量，map的key，value对应了变量名称和变量值。key必须符合javascript命名规范
     */
    public void binding(Map map) {
        Map<String, Object> values = map;
        if (values == null) return;
        for (Entry<String, Object> entry : values.entrySet()) {
            this.binding(entry.getKey(), entry.getValue());
        }

    }

    public void fastBinding(Map map) {
        ctx.globalVar = map;
    }

    public Context getCtx() {
        return this.ctx;
    }

    /**
     * 语法校验，如果返回ErrorInfo，则表示语法有错，返回null，语法无错误
     */
    public BeetlException validate() {
        if (!(program instanceof ErrorGrammarProgram)) {
            return null;
        }
        ErrorGrammarProgram error = (ErrorGrammarProgram) program;
        return error.getException();
    }

}
