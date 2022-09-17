package org.beetl.core.om.asm;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

/**
 * asm生成的类描述
 *
 * @author laozhaishaozuo@foxmail.com
 */
class ClassDesc {

    Class<?> target = null;

    List<PropertyDescriptor> propertyDescriptors;

    /**
     * get(Object)或get(String)或null
     */
    MethodDesc generalGetMethodDesc;

    Map<Integer, List<FieldDesc>> fieldDescMap;

    boolean hasField = true;
}
