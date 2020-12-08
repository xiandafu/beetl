package org.beetl.core.statement;

import org.beetl.core.Context;

/**
 * var c
 *
 * @author xiandafu
 */
public class VarDefineNode extends Expression implements IVarIndex {

    int varIndex = 0;

    public VarDefineNode(GrammarToken token) {
        super(token);
    }

    @Override
    public Object evaluate(Context ctx) {
        return null;
    }

    @Override
    public void setVarIndex(int index) {
        this.varIndex = index;

    }

    @Override
    public int getVarIndex() {
        return varIndex;
    }

}
