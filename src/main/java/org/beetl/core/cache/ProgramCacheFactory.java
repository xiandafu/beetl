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
package org.beetl.core.cache;

import org.beetl.core.config.BeetlConfig;
import org.beetl.core.fun.ObjectUtil;
import org.beetl.android.util.Log;
import org.jetbrains.annotations.NotNull;

/**
 * 存储Program的缓存，默认是采用{@link DefaultLocalCache},可以通过设置 {@link Cache} 属性来设置新的缓存对象
 *
 * @author xiandafu
 */
public class ProgramCacheFactory {

    /** DEBUG flag */
    private static final boolean DEBUG = BeetlConfig.DEBUG;
    /** Log TAG */
    private static final String TAG = "ProgramCacheFactory";
    /** 缓存实现类的类名 */
    public static final String DEFAULT_CACHE_CLASS_NAME = "org.beetl.core.cache.DefaultLocalCache";

    /**
     * 默认的缓存实现
     *
     * @return 如果通过 {@link #DEFAULT_CACHE_CLASS_NAME} 获取缓存实例失败，则返回一个 {@link DefaultLocalCache} 类型的新实例
     */
    @NotNull
    public static Cache defaultCache() {
        return loadCache(DEFAULT_CACHE_CLASS_NAME);
    }

    /**
     * 默认的缓存实现
     *
     * @return 如果通过 {@link #DEFAULT_CACHE_CLASS_NAME} 获取缓存实例失败，则返回一个 {@link DefaultLocalCache} 类型的新实例
     */
    @NotNull
    public static Cache loadCache(String className) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = ProgramCacheFactory.class.getClassLoader();
        }
        try {
            return (Cache) ObjectUtil.instance(className, loader);
        } catch (Exception ex) {
            if (DEBUG) {
                Log.d(TAG, "#loadCache fail."
                        + " className=" + className + " classLoader=" + loader + " ex=\n" + ex.toString());
            }
            return new DefaultLocalCache();
        }
    }

}
