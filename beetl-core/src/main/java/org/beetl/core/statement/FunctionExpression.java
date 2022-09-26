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
package org.beetl.core.statement;

import java.util.Map;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.fun.FileFunctionWrapper;

/**
 * call();
 *
 * @author xiandafu
 */
public class FunctionExpression extends Expression {
	protected String name;
    public Expression[] exps;
    public VarAttribute[] vas;
    public Expression safeExp;
	protected boolean hasSafe = false;

    public FunctionExpression(String name, Expression[] exps, VarAttribute[] vas, boolean hasSafe, Expression safeExp,
                              GrammarToken token) {
        super(token);
        this.name = name;
        this.exps = exps;
        this.vas = vas;
        this.safeExp = safeExp;
        this.hasSafe = hasSafe;
    }

    public Object evaluate(Context ctx) {
        Function fn = ctx.gt.getFunction(name);
        if (fn == null) {
            // 检查html实现
            Resource resource = getResource(ctx.gt, name);

            if (resource != null && resource.getResourceLoader().exist(resource.getId())) {
                fn = new FileFunctionWrapper(resource.getId().toString());
            } else {
                throw new BeetlException(BeetlException.FUNCTION_NOT_FOUND).pushToken(token);
            }
        }

        Object[] paras = new Object[exps.length];
        for (int i = 0; i < paras.length; i++) {
            paras[i] = exps[i].evaluate(ctx);
        }
        Object value = null;
        try {
            value = fn.call(paras, ctx);
        } catch (BeetlException ex) {
            throw ex.pushToken(token);
        } catch (RuntimeException ex) {
            throw new BeetlException(BeetlException.NATIVE_CALL_EXCEPTION, "调用方法出错 " + name, ex)
                    .pushToken(this.token);
        }

        if (vas != null) {
            for (VarAttribute attr : vas) {

                try {
                    value = attr.evaluate(ctx, value);
                } catch (BeetlException ex) {
                    throw ex.pushToken(attr.token);
                } catch (RuntimeException ex) {
                    throw new BeetlException(BeetlException.ATTRIBUTE_INVALID, "属性访问出错", ex).pushToken(attr.token);
                }

                if (value == null) {
                    if (hasSafe) {
                        return safeExp == null ? null : safeExp.evaluate(ctx);
                    } else {
                        throw new BeetlException(BeetlException.ERROR, "空指针 ").pushToken(attr.token);
                    }
                }

            }
        }

        if (value == null && hasSafe) {
            return safeExp == null ? null : safeExp.evaluate(ctx);
        } else {
            return value;
        }

    }

    protected Resource getResource(GroupTemplate gt, String name) {
        Map<String, String> resourceMap = gt.getConf().getResourceMap();
        String functionSuffix = resourceMap.get("functionSuffix");
        String functionRoot = resourceMap.get("functionRoot");
        String path = name.replace(".", "/");
        return gt.getResourceLoader().getResource(functionRoot + "/" + path + "." + functionSuffix);
    }

}
