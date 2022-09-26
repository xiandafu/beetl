package org.beetl.ext.fn;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.ext.util.JsonFactory;

/**
 * 使用jackson或者fastjson 序列化对对象
 */
public class Json implements Function {

    @Override
    public String call(Object[] paras, Context ctx) {
        Object object = paras[0];
        return object == null ? "" : JsonFactory.get().render(object);
    }

}
