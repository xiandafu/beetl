package org.beetl.core.runtime.impl;

import org.beetl.android.util.SparseArray;
import org.beetl.core.runtime.IBeetlMemoryManager;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
    private static final int OBJECT_POOL_MAX_SIZE = 100;

    private LinkedList<Map> mapPool;
    private LinkedList<List> listPool;
    private LinkedList<Set> setPool;
    private SparseArray<LinkedList<Clearable>> typeObjectPoolMap;

    private void init() {
        mapPool = new LinkedList<>();
        listPool = new LinkedList<>();
        setPool = new LinkedList<>();
        typeObjectPoolMap = new SparseArray<>();
    }

    @Override
    public Map map() {
        return mapPool.isEmpty() ? new HashMap() : mapPool.removeLast();
    }

    @Override
    public boolean recoveryMap(Map garbage) {
        if (garbage == null) {
            return false;
        }
        if (mapPool.size() > MAP_POOL_MAX_SIZE) {
            return false;
        }
        garbage.clear();
        mapPool.addLast(garbage);
        return true;
    }

    @Override
    public Set set() {
        return setPool.isEmpty() ? new HashSet() : setPool.removeLast();
    }

    @Override
    public boolean recoverySet(Set garbage) {
        if (garbage == null) {
            return false;
        }
        if (setPool.size() > SET_POOL_MAX_SIZE) {
            return false;
        }
        garbage.clear();
        setPool.addLast(garbage);
        return true;
    }

    @Override
    public List list() {
        return listPool.isEmpty() ? new ArrayList() : listPool.removeLast();
    }

    @Override
    public boolean recoveryList(List garbage) {
        if (garbage == null) {
            return false;
        }
        if (listPool.size() > LIST_POOL_MAX_SIZE) {
            return false;
        }
        garbage.clear();
        listPool.addLast(garbage);
        return true;
    }

    @Override
    public boolean recoveryObject(Class<Clearable> type, Clearable garbage) {
        if (garbage == null) {
            return false;
        }
        int key = type.hashCode();
        LinkedList<Clearable> pool = typeObjectPoolMap.get(key, new LinkedList<>());
        if (pool.size() > OBJECT_POOL_MAX_SIZE) {
            return false;
        }
        garbage.clear();
        pool.addLast(garbage);
        typeObjectPoolMap.put(key, pool);
        return true;
    }

    @Override
    public Clearable object(Class<Clearable> type) {
        int key = type.hashCode();
        LinkedList<Clearable> pool = typeObjectPoolMap.get(key, new LinkedList<>());
        if (!pool.isEmpty()) {
            Clearable result = pool.removeLast();
            typeObjectPoolMap.put(key, pool);
            return result;
        }

        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public void clearMemory() {
        for (Map map : mapPool) {
            map.clear();
        }
        for (List list : listPool) {
            list.clear();
        }
        for (Set set : setPool) {
            set.clear();
        }
        for (int i = 0; i < typeObjectPoolMap.size(); i++) {
            LinkedList<Clearable> objectPool = typeObjectPoolMap.get(i);
            for (Clearable object : objectPool) {
                object.clear();
            }
        }

        init();
    }
}
