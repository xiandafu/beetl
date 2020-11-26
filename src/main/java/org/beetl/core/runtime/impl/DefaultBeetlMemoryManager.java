package org.beetl.core.runtime.impl;

import org.beetl.android.util.LruCache;
import org.beetl.core.runtime.IBeetlMemoryManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sun.misc.Unsafe;

/**
 * 通过维护 Collection 的引用，来避免重复创建 Collection 的实例
 * 当 Collection 的容量较大时，MemoryManager的优势开始凸显，减少了 Collection 扩容的开销
 */
public class DefaultBeetlMemoryManager implements IBeetlMemoryManager {

    /** 单例模式 - 不可获取实例 */
    private DefaultBeetlMemoryManager() {
        init();
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

    private static final int MAP_POOL_MAX_SIZE = 200;
    private static final int LIST_POOL_MAX_SIZE = 200;
    private static final int SET_POOL_MAX_SIZE = 200;
    private List<Map> mapPool;
    private List<List> listPool;
    private List<Set> setPool;

    private void init() {
        mapPool = new ArrayList<>(MAP_POOL_MAX_SIZE);
        listPool = new ArrayList<>(LIST_POOL_MAX_SIZE);
        setPool = new ArrayList<>(SET_POOL_MAX_SIZE);
    }

    @Override
    public Map map() {
        return mapPool.size() == 0 ? new HashMap() : mapPool.remove(0);
    }

    @Override
    public boolean recoveryMap(Map garbage) {
        if (garbage == null) {
            return false;
        }
        garbage.clear();
        mapPool.add(garbage);
        return true;
    }

    @Override
    public Set set() {
        return setPool.size() == 0 ? new HashSet() : setPool.remove(0);
    }

    @Override
    public boolean recoverySet(Set garbage) {
        if (garbage == null) {
            return false;
        }
        garbage.clear();
        setPool.add(garbage);
        return true;
    }

    @Override
    public List list() {
        return listPool.size() == 0 ? new ArrayList() : listPool.remove(0);
    }

    @Override
    public boolean recoveryList(List garbage) {
        if (garbage == null) {
            return false;
        }
        garbage.clear();
        listPool.add(garbage);
        return true;
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

    /** 通过反射获取的 Unsafe 类的实例 */
    private static Unsafe unsafe;

    /**
     * 通过反射获取 Unsafe 类的实例
     *
     * @return Unsafe 类的实例
     */
    @Nullable
    public static Unsafe getUnsafe() {
        if (unsafe == null) {
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            try {
                unsafe = (Unsafe) unsafeField.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return unsafe;
    }
}
