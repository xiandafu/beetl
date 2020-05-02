/*
 [The "BSD license"]
 Copyright (c) 2011-2019  闲大赋 (李家智)
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
package org.beetl.core.statement.optimal;

import org.beetl.core.Context;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.om.AABuilder;
import org.beetl.core.om.AttributeAccess;
import org.beetl.core.statement.*;

/**
 * user.name
 * @author joelli
 *
 */
public class VarRefOptimal extends VarRef  {

	public VarAttribute attribute;
	public int varIndex;


	public VarRefOptimal(VarAttribute attribute, GrammarToken token,
			GrammarToken firstToken) {
		super(null,false,null,token,firstToken);
		this.attribute = attribute;

	}

	@Override
	public Object evaluate(Context ctx) {

		Object value = this.getRefValue(ctx);
		if (value == null) {
			if(this.hasSafe||ctx.safeOutput){
				return null;
			}
			BeetlException be = new BeetlException(BeetlException.NULL, "空指针");
			be.pushToken(this.token);
			throw be;
		}


		try {
			value = attribute.evaluate(ctx, value);
		} catch (BeetlException ex) {
			ex.pushToken(attribute.token);
			throw ex;

		} catch (RuntimeException ex) {
			BeetlException be = new BeetlException(BeetlException.ATTRIBUTE_INVALID, "属性访问出错", ex);
			be.pushToken(attribute.token);
			throw be;
		}


		return value;


	}



	private Object getRefValue(Context ctx) {

		Object value = ctx.vars[varIndex];
		if (value == Context.NOT_EXIST_OBJECT) {
			if (ctx.globalVar != null && ctx.globalVar.containsKey("_root")) {
				// 如果有一个根对象
				Object root = ctx.getGlobal("_root");

				if (root == null) {
					return null;
				}
				String attr = this.firstToken.text;
				AttributeAccess aa = AABuilder.buildFiledAccessor(root.getClass());
				try {
					value = aa.value(root, attr);
				} catch (RuntimeException e) {
					BeetlException ex = new BeetlException(BeetlException.ATTRIBUTE_INVALID,
							"_root " + root.getClass() + " 属性访问错误");
					ex.pushToken(this.firstToken);
					throw ex;
				}

				return value;

			} else {
				return null;
			}

		}else{
			return value;
		}



	}

	@Override
	public void setVarIndex(int index) {
		this.varIndex = index;

	}

	@Override
	public int getVarIndex() {
		return this.varIndex;
	}


}
