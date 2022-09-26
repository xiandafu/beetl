package org.beetl.core.statement;

import org.beetl.core.Context;
import org.beetl.core.misc.ALU;

/**
 * exp1 or exp2
 *
 * @author xiandafu
 */
public class OrExpression extends Expression {

    public Expression exp1, exp2;

    public OrExpression(Expression exp1, Expression exp2, GrammarToken token) {
        super(token);
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    public Object evaluate(Context ctx) {
        return ALU.isTrue(exp1.evaluate(ctx), exp1) ? Boolean.TRUE : ALU.isTrue(exp2.evaluate(ctx), exp2);
    }

}
