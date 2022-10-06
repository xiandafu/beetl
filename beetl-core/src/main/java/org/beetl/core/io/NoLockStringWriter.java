/*
 [The "BSD license"]
 Copyright (c) 2011-2022  闲大赋 (李家智)
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

import java.io.IOException;
import java.io.Writer;

public class NoLockStringWriter extends Writer {

    /** 默认的缓存长度 */
    private static final int DEFAULT_BUFFER_SIZE = 64;

    // todo reuse parent writer buf??
    protected char[] buf;
    protected int pos;

    /**
     * 构造方法
     */
    public NoLockStringWriter() {
        this.buf = new char[DEFAULT_BUFFER_SIZE];
    }

	public NoLockStringWriter(int size) {
		this.buf = new char[size];
	}

    @Override
    public void write(char[] src, int off, int len) throws IOException {
        int newPos = pos + len;
        if (newPos > buf.length) {
            buf = IOUtil.copyOf(buf, Math.max(buf.length << 1, newPos));
        }
        System.arraycopy(src, off, buf, pos, len);
        pos = newPos;
    }

    @Override
    public void write(String str) throws IOException {
        if (str != null) {
            this.write(str.toCharArray());
        }
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public String toString() {
        return new String(buf, 0, pos);
    }

}
