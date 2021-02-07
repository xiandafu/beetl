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
package org.beetl.core.impl.cache;

import org.beetl.core.annotation.ThreadSafety;
import org.beetl.core.runtime.IBeetlCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 本地的一个实现，采用ConcurrentHashMap
 *
 * @author xiandafu
 */
@ThreadSafety
public class DefaultBeetlCache implements IBeetlCache {

    /** 线程安全的缓存 */
    private final Map<Object, Object> threadSafeCache = new ConcurrentHashMap<>();

    @Override
    public Object get(Object key) {
        return threadSafeCache.get(key);
    }

    @Override
    public Object get(Object key, Function function) {
        return threadSafeCache.computeIfAbsent(key, function);
    }

    @Override
    public void remove(Object key) {
        threadSafeCache.remove(key);
    }

    @Override
    public void set(Object key, Object value) {
        threadSafeCache.put(key, value);
    }

    @Override
    public void clearAll() {
        threadSafeCache.clear();
    }

}
