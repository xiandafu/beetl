package org.beetl.core.impl.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.beetl.core.Configuration;
import org.beetl.core.runtime.IBeetlCache;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 使用Caffeine
 */
public class CaffeineCache implements IBeetlCache {

	Cache<Object, Object> cache ;

	public CaffeineCache(){

	}

	@Override
	public void init(Configuration configuration) {
		int size = configuration.getIntProperty("cache.size",256);
		int duration = configuration.getIntProperty("cache.duration",30);
		cache =  Caffeine.newBuilder()
				.expireAfterAccess(duration, TimeUnit.MINUTES)
				.maximumSize(size)
				.build();
	}

	@Override
	public Object get(Object key) {
		return cache.getIfPresent(key);
	}

	@Override
	public Object get(Object key, Function function) {
		return cache.get(key,function);
	}

	@Override
	public void remove(Object key) {
		cache.invalidate(key);
	}

	@Override
	public void set(Object key, Object value) {
		cache.put(key,value);
	}

	@Override
	public void clearAll() {
		cache.invalidateAll();
	}

	/**
	 * 此方法不应该经常被调用，因为会触发cleanUP操作，返回一个精确值。
	 * @return
	 */
	@Override
	public long size() {
		cache.cleanUp();
		return cache.estimatedSize();
	}


}
