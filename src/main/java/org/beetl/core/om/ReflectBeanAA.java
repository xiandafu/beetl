package org.beetl.core.om;

import org.beetl.core.exception.BeetlException;
import org.beetl.core.fun.MethodInvoker;
import org.beetl.core.fun.ObjectUtil;

/**
 * 通过反射获取bean属性，注意，如果属性获取不到，检测是否是否有个get(Object xxx)方法
 * @author xiandafu
 */


public class ReflectBeanAA extends AttributeAccess {
	

	public static ReflectBeanAA instance = new ReflectBeanAA();

	private ReflectBeanAA(){

	}
    @Override
    public Object value(Object o, Object name) {
        MethodInvoker mi = ObjectUtil.getInvokder(o.getClass(),(String)name);
		if(mi==null){
			BeetlException ex = new BeetlException(BeetlException.ATTRIBUTE_NOT_FOUND, (String) name);
			throw ex;
		}
      	return mi.get(o);
    }
}
