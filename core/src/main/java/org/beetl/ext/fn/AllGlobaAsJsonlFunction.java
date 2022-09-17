package org.beetl.ext.fn;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.misc.JsonFactory;

public class AllGlobaAsJsonlFunction implements Function {
    @Override
    public Object call(Object[] paras, Context ctx) {
        return JsonFactory.get().render(ctx.globalVar);
    }
}
