package org.beetl.core.om;

import java.util.List;
import java.util.Map;

import org.beetl.core.exception.BeetlException;
import org.beetl.core.fun.MethodInvoker;
import org.beetl.core.fun.ObjectUtil;
import org.beetl.core.misc.PrimitiveArrayUtil;

/**
 * 为对象设置值的工具类
 */
public class ObjectSetterUtil {

    /**
     * 设置值
     *
     * @param o 将被修改的对象
     * @param k 键
     * @param v 值
     */
    protected static void set(Object o, Object k, Object v) {
        if (o instanceof Map) { // Map
            ((Map) o).put(k, v);
        } else if (o instanceof List) { // List
            try {
                ((List) o).set(((Number) k).intValue(), v);
            } catch (IndexOutOfBoundsException ex) {
                throw new BeetlException(BeetlException.ATTRIBUTE_INVALID, ex);
            } catch (ClassCastException ex) {
                throw new ClassCastException("目标无法强转成 java.util.List，无法设置属性:" + k);
            }
        } else if (o.getClass().isArray()) { // 数组
            try {
                if (o.getClass().getComponentType().isPrimitive()) { // 数组的组件类型为原始类型，表示当前为一维数组
                    PrimitiveArrayUtil.setObject(o, (((Number) k).intValue()), v);
                } else { // 二维以上的数组，可以直接替换引用，将key视为数组下标，用value替换该下标对应的值
                    ((Object[]) o)[(((Number) k).intValue())] = v;
                }
            } catch (ClassCastException ex) {
                throw new ClassCastException("类型为数组，无此属性:" + k);
            }
        } else {
            String keyStr = (String) k;
            Class objType = o.getClass();
            MethodInvoker invoker = ObjectUtil.getInvokder(objType, keyStr);
            if (invoker != null) {
                invoker.set(o, v);
            } else {
                throw new BeetlException(ObjectUtil.hasPrivateAttribute(objType, keyStr)
                        ? BeetlException.ATTRIBUTE_NOT_FOUND_PRIVATE
                        : BeetlException.ATTRIBUTE_NOT_FOUND, keyStr);
            }
        }
    }

}

