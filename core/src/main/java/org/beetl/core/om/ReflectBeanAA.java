package org.beetl.core.om;

import org.beetl.core.exception.BeetlException;
import org.beetl.core.fun.MethodInvoker;
import org.beetl.core.fun.ObjectUtil;

/**
 * 通过反射获取bean属性，注意，如果属性获取不到，检测是否是否有个get(Object xxx)方法
 *
 * @author xiandafu
 */

public class ReflectBeanAA extends AttributeAccess {

    public static final ReflectBeanAA INSTANCE = new ReflectBeanAA();

    private ReflectBeanAA() {

    }

    @Override
    public Object value(Object o, Object name) {
        Class c = o.getClass();
        String key = (String) name;
        MethodInvoker mi = ObjectUtil.getInvokder(c, key);
        if (mi == null) {
            throw new BeetlException(ObjectUtil.hasPrivateAttribute(c, key)
                    ? BeetlException.ATTRIBUTE_NOT_FOUND_PRIVATE : BeetlException.ATTRIBUTE_NOT_FOUND, key);
        }
        return mi.get(o);
    }
}
