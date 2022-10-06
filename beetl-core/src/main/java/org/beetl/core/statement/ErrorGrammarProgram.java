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
package org.beetl.core.statement;

import org.beetl.core.Context;
import org.beetl.core.Resource;
import org.beetl.core.exception.BeetlException;

import java.util.ArrayList;

/**
 * 语法错的时候处理错误,避免同一个模板重复执行错误
 *
 * @author xiandafu
 */

public class ErrorGrammarProgram extends Program {
	protected BeetlException exception = null;

    public ErrorGrammarProgram(Resource res, String cr) {
        super();
        this.res = res;
        this.metaData = new ProgramMetaData() {
            public void initContext(Context ctx) {
                // do nothing;
            }

            protected void putGlobaToArray(Context ctx) {
                // do nothing;
            }

            public AjaxStatement getAjax(String anchor) {
                // 创建一个临时的ajax片段语句
                return new AjaxStatement(null, GrammarToken.createToken(anchor, 0), true) {
                    public void execute(Context ctx) {
                        ErrorGrammarProgram.this.execute(ctx);

                    }
                };
            }
        };
        this.metaData.lineSeparator = cr;
    }

    public void execute(Context ctx) {
		BeetlException copy =   clone(this.exception);
		throw copy;
    }

    protected  BeetlException clone(BeetlException source){
		BeetlException target = new BeetlException(source.detailCode,source.getMessage(),source.getCause());
		target.setToken(source.token);
		target.resource = source.resource;
		target.inTagBody = source.inTagBody;
		target.cr = source.cr;
		target.errorTokenStack = new ArrayList(source.errorTokenStack);
		target.errorResourceStack = new ArrayList(source.errorResourceStack);

		return target;

	}
    public BeetlException getException() {
        return clone(exception);
    }

    public void setException(BeetlException exception) {
        this.exception = exception;
        this.exception.cr = this.metaData.lineSeparator;
    }

}
