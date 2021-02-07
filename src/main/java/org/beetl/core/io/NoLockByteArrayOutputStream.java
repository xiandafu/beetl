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
package org.beetl.core.io;

import java.io.OutputStream;

public class NoLockByteArrayOutputStream extends OutputStream {

    /** 默认的缓存大小 */
    private static final int DEFAULT_BUFFER_SIZE = 32;
    /** 缓存 */
    protected byte[] buf;
    /** 缓存中的位置 */
    protected int pos;

    /**
     * 构造方法
     */
    public NoLockByteArrayOutputStream() {
        this(DEFAULT_BUFFER_SIZE);
    }

    /**
     * 构造方法
     *
     * @param size 缓存大小
     */
    public NoLockByteArrayOutputStream(int size) {
        buf = new byte[size];
    }

    /**
     * 向流中写入一个字节
     *
     * @param src 字节来源
     */
    public void write(int src) {
        int newPos = pos + 1;
        if (newPos > buf.length) { // 扩容
            buf = IOUtil.copyOf(buf, Math.max(buf.length << 1, newPos));
        }
        buf[pos] = (byte) src;
        pos = newPos;
    }

    /**
     * 向流中写入一个字节数组
     *
     * @param src 字节数组
     * @param off 在 {@param src} 中的起始下标
     * @param len 在 {@param src} 中的长度
     */
    public void write(byte[] src, int off, int len) {
        int newPos = pos + len;
        if (newPos > buf.length) {
            buf = IOUtil.copyOf(buf, Math.max(buf.length << 1, newPos));
        }
        System.arraycopy(src, off, buf, pos, len);
        pos = newPos;
    }

    /**
     * 重置
     */
    public void reset() {
        pos = 0;
    }

    /**
     * 转成字节数组
     *
     * @return 新的字节数组实例
     */
    public byte[] toByteArray() {
        return IOUtil.copyOf(this.buf, pos);
    }

    /**
     * 获取已写入的大小
     *
     * @return 已写入的大小
     */
    public int size() {
        return pos;
    }


}
