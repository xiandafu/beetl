package org.beetl.core.runtime;

import org.beetl.core.impl.cache.DefaultBeetlCacheFactory;
import org.beetl.core.impl.DefaultBeetlConfigManager;
import org.beetl.core.impl.DefaultBeetlMemoryManager;
import org.beetl.core.impl.cache.LRUBeetlCache;

/**
 * Beetl运行时，用于获取各种运行时
 */
public class BeetlRuntime {

    /**
     * 获取Beetl的运行时配置信息
     *
     * @return Beetl的运行时配置信息
     */
    public static IBeetlConfigManager getConfigManager() {
        return DefaultBeetlConfigManager.get();
    }

    /**
     * 获取Beetl的运行时内存管理器
     *
     * @return Beetl的运行时内存管理器
     */
    public static IBeetlMemoryManager getMemoryManager() {
        return DefaultBeetlMemoryManager.get();
    }

    /**
     * 获取缓存实例
     *
     * @return 返回一个默认的实现
     */
    public static IBeetlCache getCache() {
        return DefaultBeetlCacheFactory.defaultCache();
    }

    /**
     * 获取缓存实例
     *
     * @param className 全限定类名
     * @return 根据类名加载缓存类，返回其实例
	 * @see org.beetl.core.impl.cache.NoCache
	 * @see org.beetl.core.impl.cache.LRUBeetlCache
	 * @see org.beetl.core.impl.cache.CaffeineCache
     */
    public static IBeetlCache getCache(String className) {
        return DefaultBeetlCacheFactory.loadCache(className);
    }

}
