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
package org.beetl.core;

/**
 * ResourceLoader 负责根据GroupTemplate提供的Key,来获取Resource，这些Resource可以是文件，
 * 字符串，加密的文本，以及数据库Blob字段等
 *
 * @author xiandafu
 */
public interface ResourceLoader<T> {

    /**
     * 根据key获取Resource
     *
     * @param key 根据 key 可以查找到对应的资源
     * @return {@param key} 所对应的资源
     */
    Resource getResource(T key);

    /**
     * 检测模板是否更改，每次渲染模板前，都需要调用此方法，所以此方法不能占用太多时间，否则会影响渲染功能
     *
     * @param key 根据 key 可以查找到对应的资源
     * @return true 表示已更改，false 表示未更改
     */
    boolean isModified(Resource key);

    /**
     * 返回是否存在 {@param key} 所对应的资源
     *
     * @param key 根据 key 可以查找到对应的资源
     * @return true 表示存在，false 表示不存在
     */
    boolean exist(T key);

    /**
     * 关闭 ResourceLoader，通常是 GroupTemplate 关闭的时候也关闭对应的 ResourceLoader
     */
    void close();

    /**
     * 初始化操作
     */
    void init(GroupTemplate gt);

    /**
     * 用于 include，layout 等根据相对路径计算资源实际的位置
     *
     * @param resource 当前资源
     * @param key      目标资源所对应的键
     * @return 计算出来的目标资源的位置
     */
    T getResourceId(Resource resource, T key);

    /**
     * 获取资源加载器的信息，用于获取不到资源的时候输出提示信息
     *
     * @return 资源加载器的信息
     */
    String getInfo();

}
