package org.beetl.core.impl.cache;

import org.beetl.core.Configuration;
import org.beetl.core.runtime.IBeetlCache;

import java.util.function.Function;

/**
 * 无缓存，适用于过多的模板，比如在线体验
 */
public class NoCache implements IBeetlCache {


	@Override
	public void init(Configuration configuration) {

	}

	@Override
	public Object get(Object key) {
		return null;
	}

	@Override
	public Object get(Object key, Function function) {
		return function.apply(key);
	}

	@Override
	public void remove(Object key) {

	}

	@Override
	public void set(Object key, Object value) {

	}

	@Override
	public void clearAll() {

	}

	@Override
	public long size() {
		return 0;
	}
}
