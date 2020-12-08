package org.beetl.core.runtime.impl;

import org.beetl.android.util.SparseArray;
import org.beetl.core.runtime.IBeetlMemoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 通过维护 Collection 的引用，来避免重复创建 Collection 的实例
 * 当 Collection 的容量较大时，MemoryManager的优势开始凸显，减少了 Collection 扩容的开销
 */
public class DefaultBeetlMemoryManager implements IBeetlMemoryManager {

    /** 单例模式 - 不可获取实例 */
    private DefaultBeetlMemoryManager() {
        init();
    }

    /** 单例模式 - DefaultBeetlMemoryManager 的实例 */
    private static IBeetlMemoryManager sInstance;

    /**
     * 获取 DefaultBeetlMemoryManager 实例
     *
     * @return 进程内唯一的实例
     */
    public static IBeetlMemoryManager get() {
        if (sInstance == null) {
            synchronized (DefaultBeetlMemoryManager.class) {
                if (sInstance == null) {
                    sInstance = new DefaultBeetlMemoryManager();
                }
            }
        }
        return sInstance;
    }

    /** 缓存 {@link Map} 实例的数量上限 */
    private static final int MAP_POOL_MAX_SIZE = 200;
    /** 缓存 {@link List} 实例的数量上限 */
    private static final int LIST_POOL_MAX_SIZE = 200;
    /** 缓存 {@link Set} 实例的数量上限 */
    private static final int SET_POOL_MAX_SIZE = 200;
    /** 在每个LinkedList中缓存 {@link Clearable} 实例的数量上限 */
    private static final int OBJECT_POOL_MAX_SIZE = 100;

    /** 保存 Map 实例的缓存池 */
    private LinkedList<Map> mMapPool;
    /** 保存 List 实例的缓存池 */
    private LinkedList<List> mListPool;
    /** 保存 Set 实例的缓存池 */
    private LinkedList<Set> mSetPool;
    /** 维护以 [实例 Class 的 hashCode ] 为 key，[保存 Clearable 实例的缓存池] 为 value 的映射 */
    private SparseArray<LinkedList<Clearable>> mTypeObjectPoolMap;

    /**
     * 初始化操作
     */
    private void init() {
        mMapPool = new LinkedList<>();
        mListPool = new LinkedList<>();
        mSetPool = new LinkedList<>();
        mTypeObjectPoolMap = new SparseArray<>();
    }

    @Override
    public Map takeMap() {
        return mMapPool.isEmpty() ? new HashMap<>() : mMapPool.removeLast();
    }

    @Override
    public boolean recoveryMap(Map garbage) {
        if (garbage == null) {
            return false;
        }
        if (mMapPool.size() > MAP_POOL_MAX_SIZE) {
            return false;
        }
        garbage.clear();
        mMapPool.addLast(garbage);
        return true;
    }

    @Override
    public int sizeOfMapPool() {
        return mMapPool.size();
    }

    @Override
    public Set takeSet() {
        return mSetPool.isEmpty() ? new HashSet<>() : mSetPool.removeLast();
    }

    @Override
    public boolean recoverySet(Set garbage) {
        if (garbage == null) {
            return false;
        }
        if (mSetPool.size() > SET_POOL_MAX_SIZE) {
            return false;
        }
        garbage.clear();
        mSetPool.addLast(garbage);
        return true;
    }

    @Override
    public int sizeOfSetPool() {
        return 0;
    }

    @Override
    public List takeList() {
        return mListPool.isEmpty() ? new ArrayList() : mListPool.removeLast();
    }

    @Override
    public boolean recoveryList(List garbage) {
        if (garbage == null) {
            return false;
        }
        if (mListPool.size() > LIST_POOL_MAX_SIZE) {
            return false;
        }
        garbage.clear();
        mListPool.addLast(garbage);
        return true;
    }

    @Override
    public int sizeOfListPool() {
        return mListPool.size();
    }

    @Override
    public boolean recoveryObject(Class<Clearable> type, Clearable garbage) {
        if (garbage == null) {
            return false;
        }
        int key = type.hashCode();
        LinkedList<Clearable> pool = mTypeObjectPoolMap.get(key, new LinkedList<>());
        if (pool.size() > OBJECT_POOL_MAX_SIZE) {
            return false;
        }
        garbage.clear();
        pool.addLast(garbage);
        mTypeObjectPoolMap.put(key, pool);
        return true;
    }

    @Override
    public Clearable takeObject(Class<Clearable> type) {
        int key = type.hashCode();
        LinkedList<Clearable> pool = mTypeObjectPoolMap.get(key, new LinkedList<>());
        if (!pool.isEmpty()) {
            Clearable result = pool.removeLast();
            mTypeObjectPoolMap.put(key, pool);
            return result;
        }

        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public int sizeOfObjectType() {
        return mTypeObjectPoolMap.size();
    }

    @Override
    public int sizeOfObjectPool() {
        int length = 0;
        for (int i = 0; i < mTypeObjectPoolMap.size(); i++) {
            LinkedList<Clearable> pool = mTypeObjectPoolMap.get(i);
            length += pool.size();
        }
        return length;
    }

    @Override
    public void clearMemory() {
        for (Map map : mMapPool) {
            map.clear();
        }
        for (List list : mListPool) {
            list.clear();
        }
        for (Set set : mSetPool) {
            set.clear();
        }
        for (int i = 0; i < mTypeObjectPoolMap.size(); i++) {
            LinkedList<Clearable> objectPool = mTypeObjectPoolMap.get(i);
            while (!objectPool.isEmpty()) {
                Clearable object = objectPool.pollLast();
                object.clear();
            }
        }
        init();
    }

    @Override
    public String toString() {
        return "DefaultBeetlMemoryManager{" +
                "mapPool=" + mMapPool +
                ", listPool=" + mListPool +
                ", setPool=" + mSetPool +
                ", typeObjectPoolMap=" + mTypeObjectPoolMap +
                '}';
    }
}
