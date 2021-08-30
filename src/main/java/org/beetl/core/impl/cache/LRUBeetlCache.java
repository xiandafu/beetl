package org.beetl.core.impl.cache;

import org.beetl.core.Configuration;
import org.beetl.core.runtime.IBeetlCache;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 缓存保留最常使用的模板
 */
public class LRUBeetlCache implements IBeetlCache {

	Map lruCache ;

	public LRUBeetlCache(){

	}


	@Override
	public void init(Configuration configuration) {
		int size = configuration.getIntProperty("cache.size",256);
		lruCache = Collections.synchronizedMap(new LRUCache(size));
	}

	@Override
	public Object get(Object key) {
		return lruCache.get(key);
	}

	@Override
	public Object get(Object key, Function function) {
		return lruCache.computeIfAbsent(key,function);
	}

	@Override
	public void remove(Object key) {
		lruCache.remove(key);
	}

	@Override
	public void set(Object key, Object value) {
		lruCache.put(key,value);
	}

	@Override
	public void clearAll() {
		lruCache.clear();
	}

	@Override
	public long size() {
		return lruCache.size();
	}

	static  class LRUCache<K, V> extends LinkedHashMap<K, V> {
		private int cacheSize;

 		 public LRUCache(int cacheSize) {
			super(16, 0.75F, true);
			this.cacheSize = cacheSize;
		}

		protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
			return size() >cacheSize;
		}
	}
}
