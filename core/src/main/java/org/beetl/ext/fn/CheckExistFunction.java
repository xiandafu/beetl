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
package org.beetl.ext.fn;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.GroupTemplate;
import org.beetl.core.engine.GrammarCreator;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.statement.Expression;
import org.beetl.core.statement.Literal;
import org.beetl.core.statement.VarRef;

/**
 * 判断是否存在全局变量，是否存在全局变量userList,sessions
 * ${has(userList,sessions)}
 * 参数可以一个到多个
 *
 * @author xiandafu
 */
public class CheckExistFunction implements Function ,Function.ChangeInput {

    @Override
    public Boolean call(Object[] paras, Context ctx) {

        if (ctx.globalVar == null) {
            return false;
        }
        String key;
        for (Object o : paras) {
            key = (String) o;
            if (!ctx.globalVar.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

	/**
	 * 在语法解析阶段，把${has(userList,sessions)} 转化为 ${has("userList","sessions")}
	 * @param creator
	 * @param exps
	 * @param gt
	 * @return
	 */
	@Override
	public Expression[] update(GrammarCreator creator, Expression[] exps, GroupTemplate gt) {
		if (exps.length == 0) {
			return exps;
		}
		for (int i = 0; i < exps.length; i++) {
			Expression one = exps[i];
			if (one instanceof VarRef) {
				VarRef ref = (VarRef) one;
				if (ref.attributes.length != 0) {
					BeetlException ex = new BeetlException(BeetlException.HAS_CALL_ILLEGAL,
							"has函数用于判断全局变量是否存在，不能判断其属性是否有值，可以使用安全输出符号或者isEmpty函数");
					ex.pushToken(ref.token);
					throw ex;
				}
				String name = ref.token.text;
				Literal newExp = creator.createLiteral(name, ref.token);
				// 将变量名引用转化为字符串
				exps[i] = newExp;

			}
			else if(one instanceof  Literal){
				Literal literal = (Literal)one;
				if(literal.obj!=null&&literal.obj instanceof  String){
					continue;
				}
				BeetlException ex = new BeetlException(BeetlException.HAS_CALL_ILLEGAL, "has函数用于判断全局变量是否存在,请传入一个全局变量名");
				ex.pushToken(exps[i].token);
				throw ex;
			}
			else {
				BeetlException ex = new BeetlException(BeetlException.HAS_CALL_ILLEGAL, "has函数用于判断全局变量是否存在,请传入一个全局变量名");
				ex.pushToken(exps[i].token);
				throw ex;
			}
		}
		return exps;

	}
}
