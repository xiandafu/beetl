/*
 [The "BSD license"]
 Copyright (c) 2011-2020  闲大赋 (李家智)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
     derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.beetl.core.om;

import org.beetl.core.util.Pair;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.fun.MethodInvoker;
import org.beetl.core.fun.ObjectUtil;
import org.beetl.core.misc.PrimitiveArrayUtil;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 封装了一个获取对象的属性的值的方法
 *
 * @author xiandafu
 */
public abstract class AttributeAccess implements java.io.Serializable {

    public abstract Object value(Object o, Object name);

    public void setValue(Object o, Object name, Object value) {
        updateValue(o, name, value);
    }

    /**
     * 设置值
     *
     * @param o 将被修改的对象
     * @param k 键
     * @param v 值
     * @throws BeetlException 数组越界异常、属性私有、属性找不到
     * @throws ClassCastException 目标无法强转成 java.util.List 或数组的组件类型
     */
    private static void updateValue(Object o, Object k, Object v) {
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

    /**
     * 检查一个对象是否为索引
     *
     * @param attr 表示索引的引用
     * @return true 表示检查并校验通过，Integer 值为索引值；false 表示数据类型不对，此时 Integer 值为 null
     * @throws BeetlException 如果索引值小于0，会抛出该异常
     */
    @NotNull
    static Pair<Boolean, Integer> checkAndGetIndex(Object attr) {
        if (attr instanceof Integer
                || attr instanceof Long
                || attr instanceof Short
                || attr instanceof Byte
                || attr instanceof BigDecimal) {
            int index = ((Number) attr).intValue();
            if (index < 0) {
                throw new BeetlException(BeetlException.ARRAY_INDEX_ERROR, "索引必须大于或者等于");
            }
            return new Pair<>(true, index);
        }
        return new Pair<>(false, null);
    }

}