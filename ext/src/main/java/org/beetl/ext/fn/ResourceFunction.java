package org.beetl.ext.fn;

import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * 获取当前模板信息
 * var a = meta.resource();
 *
 * @author xiandafu
 */
public class ResourceFunction implements Function {

    @Override
    public Object call(Object[] paras, Context ctx) {

        return ctx.getResourceId();

    }

}
