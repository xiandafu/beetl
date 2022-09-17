package org.beetl.core.fun;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.beetl.core.exception.BeetlException;

/**
 * 传入对象，获取对象对应的属性值,或者调用{@link #set(Object, Object)} 设置对象值
 *
 * @author xiandafu
 */
public class PojoMethodInvoker implements MethodInvoker {

    PropertyDescriptor pd = null;
    Method readMethod = null;

    /**
     * @param pd 目标调用方法，应该是一个无参数的get方法
     */
    public PojoMethodInvoker(PropertyDescriptor pd) {
        this.pd = pd;
        this.readMethod = pd.getReadMethod();
        this.readMethod.setAccessible(true);

    }

    @Override
    public Object get(Object obj) {
        try {

            return readMethod.invoke(obj, null);
        } catch (IllegalArgumentException e) {
            throw new BeetlException(BeetlException.ATTRIBUTE_INVALID, "错误参数", e);

        } catch (IllegalAccessException e) {
            throw new BeetlException(BeetlException.ATTRIBUTE_INVALID, "无法访问", e);

        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof BeetlException) {
                throw (BeetlException) target;
            }
            throw new BeetlException(BeetlException.ATTRIBUTE_INVALID, "属性访问异常", e.getTargetException());
        }
    }

    @Override
    public Class<?> getReturnType() {
        return pd.getPropertyType();
    }

    @Override
    public Method getMethod() {
        return pd.getReadMethod();
    }

    @Override
    public void set(Object ins, Object value) {

        try {
			Method method = pd.getWriteMethod();
			if(method==null){
				throw new BeetlException(BeetlException.ATTRIBUTE_NOT_FOUND, "找不到相应的set方法，确保方法符合JavaBean规范 " + pd);
			}
			method.invoke(ins, value);
        } catch (IllegalAccessException e) {
            throw new BeetlException(BeetlException.ATTRIBUTE_INVALID, "无法访问 " + pd, e);
        } catch (IllegalArgumentException e) {
            throw new BeetlException(BeetlException.ATTRIBUTE_INVALID, "错误参数 " + pd, e);
        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof BeetlException) {
                throw (BeetlException) target;
            }
            throw new BeetlException(BeetlException.ATTRIBUTE_INVALID, "属性访问异常 " + pd, e.getTargetException());
        }
    }

}
