package org.beetl.core.runtime.impl;

import org.beetl.core.runtime.IBeetlMemoryManager;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class DefaultBeetlMemoryManager implements IBeetlMemoryManager {

    /** 单例模式 - 不可获取实例 */
    private DefaultBeetlMemoryManager() {
    }

    /** 单例模式 - 通过内部类初始化，保证实例的线程安全 */
    private static class Holder {
        /** 单例模式 - DefaultBeetlMemoryManager 的实例 */
        private static final IBeetlMemoryManager INSTANCE = new DefaultBeetlMemoryManager();
    }

    /**
     * 获取 DefaultBeetlMemoryManager 实例
     *
     * @return 进程内唯一的实例
     */
    public static IBeetlMemoryManager get() {
        return Holder.INSTANCE;
    }

    @Override
    public Object newUnpaddedArray(Class<?> kind, int length) {
        return Array.newInstance(kind, length);
//        Unsafe unsafe = getUnsafe();
//        if (unsafe == null) {
//            return Array.newInstance(kind, length);
//        }
//        return null;
    }

    /**
     * 通过反射获取 Unsafe 类的实例
     *
     * @return Unsafe 类的实例
     */
    @Nullable
    public static Unsafe getUnsafe() {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        try {
            return (Unsafe) unsafeField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
