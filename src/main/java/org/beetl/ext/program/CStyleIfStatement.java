package org.beetl.ext.program;

import org.beetl.core.Context;
import org.beetl.core.statement.Expression;
import org.beetl.core.statement.GrammarToken;
import org.beetl.core.statement.IfStatement;
import org.beetl.core.statement.Statement;

/**
 * 不考虑对象是否是为boolean类型，类似c
 * <pre>
 *     var a =1;
 *     if(a){}
 * </pre>
 * @see org.beetl.core.engine.GrammarCreator
 */
public class CStyleIfStatement extends IfStatement {
	public CStyleIfStatement(Expression condition, Statement ifStatement, Statement elseStatement, GrammarToken token) {
		super(condition, ifStatement, elseStatement, token);
	}
	@Override
	public void execute(Context ctx) {
		Object value = condition.evaluate(ctx);
		if(isSuccess(value)){
			ifStatement.execute(ctx);
		}else{
			if (elseStatement != null) {
				elseStatement.execute(ctx);
			}
		}
	}

	protected boolean isSuccess(Object value){
		if(value==null){
			return false;
		}else if(value instanceof  Boolean){
			return (Boolean)value;
		}else{
			return true;
		}

	}

}
