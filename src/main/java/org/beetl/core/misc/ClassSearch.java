/*
 [The "BSD license"]
 Copyright (c) 2011-2019  闲大赋 (李家智)
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
package org.beetl.core.misc;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.beetl.android.util.Log;
import org.beetl.core.GroupTemplate;
import org.beetl.core.config.BeetlConfig;

/**
 * 在classloader下仅仅根据类名加载类，会尝试用系统内置的以及配置好的包名作为类的包名
 *
 * @author xiandafu
 */
public class ClassSearch {

    /** DEBUG flag */
    private static final boolean DEBUG = BeetlConfig.DEBUG;
    /** DEBUG TAG */
    private static final String TAG = "ClassSearch";

    /** 类名与类型的缓存 */
    final Map<String, Class> nameClassCache = new ConcurrentHashMap<>();
    /** 去重后的包名列表 */
    final Set<String> pkgList;
    /** 用于获取类加载器 */
    final GroupTemplate gt;

    /**
     * 构造方法
     */
    public ClassSearch(Set<String> pkgList, GroupTemplate gt) {
        this.pkgList = pkgList;
        this.gt = gt;
    }

    /**
     * 根据类名得到指定类
     * - 如果类名是带包名，则直接用当前classloader加载
     * - 如果仅是类名
     *  - 先尝试从缓存中取
     *  - 再尝试用内置的或者配置的包名作为包名尝试加载
     *
     * @return 返回 {@param name} 的类型，如果不成功会返回 null
     */
    public Class getClassByName(String name) {
        ClassLoader classLoader = gt.getClassLoader();

        // 如果类名是带包名，则直接用当前classloader加载
        if (name.contains(".")) {
            try {
                return Class.forName(name, true, classLoader);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }

        // 如果仅是类名，先尝试从缓存中取
        Class cls = nameClassCache.get(name);
        if (cls != null) {
            return cls;
        }

        // 再尝试用内置的或者配置的包名作为包名尝试加载
        for (String pkg : pkgList) {
            try {
                String clsName = pkg.concat(name);
                cls = Class.forName(clsName, true, classLoader);
                nameClassCache.put(name, cls); // 缓存起来
                return cls;
            } catch (Exception ex) {
                if (DEBUG) {
                    Log.d(TAG, ex.toString());
                }
            }
        }

        // 如果不成功会返回 null
        return null;
    }

}
