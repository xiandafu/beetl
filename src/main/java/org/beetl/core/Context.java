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

import java.util.HashMap;
import java.util.Map;

import org.beetl.core.tag.Tag;

/**
 * 模板渲染的Context
 *
 * @author xiandafu
 */
public class Context {

    protected Context(GroupTemplate gt) {
        this.gt = gt;
        this.localBuffer = gt.buffers.getContextLocalBuffer();
    }

    protected Context(GroupTemplate gt, ContextBuffer buffer) {
        this.gt = gt;
        this.localBuffer = buffer;

    }

    public void completed() {

    }

    public static Object NOT_EXIST_OBJECT = new Object();

    /**
     * 采用的输出流
     */
    public ByteWriter byteWriter;

    /**
     * 当前模板
     */
    public Template template;

    /**
     * 模板组
     */
    public GroupTemplate gt = null;
    /** 全局变量 */
    public Map<String, Object> globalVar = new HashMap<String, Object>();
    ;

    /**
     * 输出模式
     */
    public boolean byteOutputMode;

    /**
     * 当前会话相关变量全局变量和临时变量都放在数组里，全局变量放在前面，Beetl并没有使用Map来存放变量名和值，世为了提高性能
     */
    public Object[] vars;

    /**
     * 这些变量来自于ProgrameMeta，模板的静态文本，TODO，能否改成SoftRefernce，避免占用较大内存？
     * 或者采用其他机制
     */
    public Object[] staticTextArray;

    /**
     * 临时变量开始计数的位置
     */
    public int tempVarStartIndex;

    /**
     * 0 正常语句，继续执行，1 continue，2 break，3 return；
     */
    public short gotoFlag;

    public boolean isInit;

    /**
     * 当前所有变量是否都是安全输出
     */
    public boolean safeOutput;

    /**
     * 一个字符和字节的buffer
     */
    public ContextBuffer localBuffer = null;

    /**
     * 判断全局变量是否存在
     */
    protected boolean exist(int i) {
        if (i >= this.tempVarStartIndex) {
            return true;
        } else {
            Object object = vars[i];
            return object != NOT_EXIST_OBJECT;
        }
    }

    /**
     * 设置全局变量
     */
    public void set(String key, Object value) {
        globalVar.put(key, value);
    }

    /**
     * 得到全局变量
     */
    public Object getGlobal(String key) {
        return globalVar.get(key);
    }

    public Object getResourceId() {
        return this.template.program.res.getId();
    }

    /**
     * 当前处于哪个Resource
     */
    public Resource getResource() {
        return this.template.program.res;
    }

    public void setCurrentTag(Tag tag) {
        Map map = (Map) getGlobal("$page");
        if (map == null) {
            map = new HashMap();
            this.set("$page", map);

        }
        map.put("$parentTag", tag);
    }

    public Tag getCurrentTag() {
        Map map = (Map) getGlobal("$page");
        if (map == null) {
            return null;
        }
        return (Tag) map.get("$parentTag");
    }

    public void destory() {
        this.gt.buffers.putContextLocalBuffer(this.localBuffer);
    }
}
