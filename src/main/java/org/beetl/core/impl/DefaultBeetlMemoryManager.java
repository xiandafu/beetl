package org.beetl.core.impl;

import org.beetl.core.runtime.IBeetlMemoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 通过维护 Collection 的引用，来避免重复创建 Collection 的实例
 * 当 Collection 的容量较大时，MemoryManager的优势开始凸显，减少了 Collection 扩容的开销
 *
 * <pre>
 *     // 以下例子演示了如何通过 DefaultBeetlMemoryManager 使代码性能提升了 45% (1058ms → 578ms)
 *     public void testRecoveryList() {
 *         String tag = "recoveryList#";
 *         int id = 1;
 *
 *         // performance: 当 Collection 的容量较大时，MemoryManager的优势开始凸显，减少了 Collection 扩容的开销
 *         final int size = 20;
 *         for (int i = 0; i < size; i++) {
 *             List list = new ArrayList();
 *             opList(list); // List 的数据操作
 *             BeetlRuntime.getMemoryManager().recoveryList(list); // 回收引用
 *         }
 *
 *         long time1 = System.nanoTime();
 *         for (int i = 0; i < size; i++) { // 耗时: 578ms
 *             List list = BeetlRuntime.getMemoryManager().takeList(); // 获取引用，没有则创建
 *             opList(list); // List 的数据操作
 *         }
 *         long time2 = System.nanoTime();
 *         for (int i = 0; i < size; i++) { // 耗时: 1058ms
 *             List list = new ArrayList(); // 常规场景创建 List 的实例
 *             opList(list); // List 的数据操作
 *         }
 *         long time3 = System.nanoTime();
 *
 *         System.out.println(tag + id++ + " 耗时: " + (time2 - time1) / 1_000_000 + "ms");
 *         System.out.println(tag + id++ + " 耗时: " + (time3 - time2) / 1_000_000 + "ms");
 *         System.out.println(BeetlRuntime.getMemoryManager());
 *     }
 *     // 一个模拟 list 操作的方法
 *     private static void opList(List list) {
 *         for (int i = 0; i < 1000_000; i++) {
 *             list.add(i);
 *         }
 *     }
 * </pre>
 */
public class DefaultBeetlMemoryManager implements IBeetlMemoryManager {

    /** 单例模式 - DefaultBeetlMemoryManager 的实例 */
    private static volatile IBeetlMemoryManager sInstance;

    /** 单例模式 - 不可获取实例 */
    private DefaultBeetlMemoryManager() {
        init();
    }

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
    public static final int sMapPoolMaxSize = 200;
    /** 缓存 {@link List} 实例的数量上限 */
    public static final int sListPoolMaxSize = 200;
    /** 缓存 {@link Set} 实例的数量上限 */
    public static final int sSetPoolMaxSize = 200;
    /** 在每个LinkedList中缓存 {@link Clearable} 实例的数量上限 */
    public static final int sObjectPoolMaxSize = 100;

    /** 保存 Map 实例的缓存池 */
    private CopyOnWriteArrayList<Map> mMapPool;
    /** 保存 List 实例的缓存池 */
    private CopyOnWriteArrayList<List> mListPool;
    /** 保存 Set 实例的缓存池 */
    private CopyOnWriteArrayList<Set> mSetPool;
    /** 维护以 [实例 Class 的 hashCode ] 为 key，[保存 Clearable 实例的缓存池] 为 value 的映射 */
    private ConcurrentHashMap<Integer, CopyOnWriteArrayList<Clearable>> mTypeObjectPoolMap;

    /**
     * 初始化操作
     */
    private void init() {
        mMapPool = new CopyOnWriteArrayList<>();
        mListPool = new CopyOnWriteArrayList<>();
        mSetPool = new CopyOnWriteArrayList<>();
        mTypeObjectPoolMap = new ConcurrentHashMap<>();
    }

    @Override
    public Map takeMap() {
        return mMapPool.isEmpty() ? new HashMap<>() : mMapPool.remove(mMapPool.size() - 1);
    }

    @Override
    public boolean recoveryMap(Map garbage) {
        if (garbage == null) {
            return false;
        }
        if (mMapPool.size() > sMapPoolMaxSize) {
            return false;
        }
        garbage.clear();
        mMapPool.add(garbage);
        return true;
    }

    @Override
    public int sizeOfMapPool() {
        return mMapPool.size();
    }

    @Override
    public Set takeSet() {
        return mSetPool.isEmpty() ? new HashSet<>() : mSetPool.remove(mSetPool.size() - 1);
    }

    @Override
    public boolean recoverySet(Set garbage) {
        if (garbage == null) {
            return false;
        }
        if (mSetPool.size() > sSetPoolMaxSize) {
            return false;
        }
        garbage.clear();
        mSetPool.add(garbage);
        return true;
    }

    @Override
    public int sizeOfSetPool() {
        return 0;
    }

    @Override
    public List takeList() {
        return mListPool.isEmpty() ? new ArrayList() : mListPool.remove(mListPool.size() - 1);
    }

    @Override
    public boolean recoveryList(List garbage) {
        if (garbage == null) {
            return false;
        }
        if (mListPool.size() > sListPoolMaxSize) {
            return false;
        }
        garbage.clear();
        mListPool.add(garbage);
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
        CopyOnWriteArrayList<Clearable> pool = mTypeObjectPoolMap.getOrDefault(key, new CopyOnWriteArrayList<>());
        if (pool.size() > sObjectPoolMaxSize) {
            return false;
        }
        garbage.clear();
        pool.add(garbage);
        mTypeObjectPoolMap.put(key, pool);
        return true;
    }

    @Override
    public Clearable takeObject(Class<Clearable> type) {
        int key = type.hashCode();
        CopyOnWriteArrayList<Clearable> pool = mTypeObjectPoolMap.getOrDefault(key, new CopyOnWriteArrayList<>());
        if (!pool.isEmpty()) {
            Clearable result = pool.remove(pool.size() - 1);
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
            CopyOnWriteArrayList<Clearable> pool = mTypeObjectPoolMap.get(i);
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
            CopyOnWriteArrayList<Clearable> objectPool = mTypeObjectPoolMap.get(i);
            while (!objectPool.isEmpty()) {
                Clearable object = objectPool.remove(objectPool.size() - 1);
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
