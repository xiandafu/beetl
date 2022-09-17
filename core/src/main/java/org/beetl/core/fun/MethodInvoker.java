package org.beetl.core.fun;

import java.lang.reflect.Method;

/**
 * 传入对象，获取对象对应的属性值 {@link PojoMethodInvoker}
 *
 * @author xiandafu
 */
public interface MethodInvoker {
    /**
     * @param o 传入的Pojo对象
     * @return 返回值
     */
    Object get(Object o);

    /**
     * @return 改调用预期的返回类型
     */
    Class<?> getReturnType();

    Method getMethod();

    void set(Object ins, Object value);
}
