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
package org.beetl.core.runtime;

import org.beetl.core.Configuration;

import java.util.function.Function;

/**
 * 缓存的标准接口，每个方法都必须线程安全
 *
 * @author xiandafu
 */
public interface IBeetlCache {

	void init(Configuration configuration);

    /**
     * 通过 {@param key} 从缓存中获取对应的 value
     *
     * @param key 键，有可能为 null
     * @return 返回缓存中 {@param key} 所对应的 value，有可能为 null
     */
    Object get(Object key);

    /**
     * 通过 {@param key} 从缓存中获取对应的 value；
     * 如果获取的value为null，则将 {@param function#apply} 方法的返回值作为newValue，添加到缓存中，并返回
     *
     * @param key      键，有可能为 null
     * @param function 函数，在通过key获取value为null时，将执行 {@param function#apply} 方法
     * @return 如果获取的value为null，则将 {@param function#apply} 方法的返回值作为newValue，添加到缓存中，并返回
     */
    Object get(Object key, Function function);

    /**
     * 移除缓存中 {@param key} 所对应的 value
     *
     * @param key 键，有可能为null
     */
    void remove(Object key);

    /**
     * 设置缓存中的键值对，如果key已存在，则更新value
     *
     * @param key   键
     * @param value 新值
     */
    void set(Object key, Object value);

    /**
     * 清理缓存
     */
    void clearAll();


    long size();
}
