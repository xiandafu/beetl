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
package org.beetl.core.resource;

import org.beetl.core.util.Log;

/**
 * 检查key以给定的字符串开头的匹配器
 *
 * @author 李飞 (lifei@wellbole.com)
 */
public class StartsWithMatcher implements Matcher {

    /** Log TAG */
    private static final String TAG = "org.beetl.core.lab.StartsWithMatcher";

    /** 前缀 */
    private String prefix;
    /** 前缀长度 */
    private int prefixLen = 0;
    /** 返回的newKey是否保留前缀，默认不保留 */
    private boolean withPrefix = false;

    public StartsWithMatcher() {
    }

    public StartsWithMatcher(String prefix) {
        this.prefix = prefix;
        this.prefixLen = prefix.length();
    }

    public StartsWithMatcher withPrefix() {
        this.withPrefix = true;
        return this;
    }

    public StartsWithMatcher withoutPrefix() {
        this.withPrefix = false;
        return this;
    }

    @Override
    public String match(String key) {
        return !key.startsWith(prefix)
                ? null
                : (withPrefix ? key : key.substring(prefixLen));
    }

    public final boolean isWithPrefix() {
        return withPrefix;
    }

    public final void setWithPrefix(boolean withPrefix) {
        this.withPrefix = withPrefix;
    }

    public final String getPrefix() {
        return prefix;
    }

    public final void setPrefix(String prefix) {
        this.prefix = prefix;
        this.prefixLen = prefix.length();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
        result = prime * result + prefixLen;
        result = prime * result + (withPrefix ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StartsWithMatcher)) {
            return false;
        }
        StartsWithMatcher other = (StartsWithMatcher) obj;
        if (prefix == null) {
            if (other.prefix != null) {
                return false;
            }
        } else if (!prefix.equals(other.prefix)) {
            return false;
        }
        return prefixLen == other.prefixLen && withPrefix == other.withPrefix;
    }

    @Override
    public String toString() {
        return "StartsWithMatcher " + prefix;
    }

    public static void main(String[] args) {
        StartsWithMatcher swm = new StartsWithMatcher("/com/wellbole").withoutPrefix();
        Log.i(TAG, swm.match("/com/wellbole/app/pg/index.html"));

        swm = new StartsWithMatcher("/com/wellbole").withPrefix();
        Log.i(TAG, swm.match("/com/wellbole/app/pg/index.html"));
    }
}
