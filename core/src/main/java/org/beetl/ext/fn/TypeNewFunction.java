package org.beetl.ext.fn;

import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * 新建对象tpye.new("User") or type.new(User)
 *
 * @author xiandafu
 */
public class TypeNewFunction implements Function {

    @Override
    public Object call(Object[] paras, Context ctx) {
        String clsName = (String) paras[0];
        Class cls = ctx.gt.getClassSearch().getClassByName(clsName);
        if (cls == null) {
            throw new RuntimeException("不能加载类：" + clsName + " classloader:" + ctx.gt.getClassLoader());
        }
        try {
            return cls.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("创建类实例失败:" + cls + ",error:" + ex.getMessage(), ex);
        }
    }

}
