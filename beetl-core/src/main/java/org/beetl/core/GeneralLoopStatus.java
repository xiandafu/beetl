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
package org.beetl.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beetl.core.misc.PrimitiveArrayUtil;

/**
 * 用于遍历
 *
 * @author xiandafu
 */
public class GeneralLoopStatus implements ILoopStatus {

    Iterator it;
    int size = -1;
    int index = 0;
    boolean hasData = false;

    public static ILoopStatus getIteratorStatus(Object o) {
        if (o instanceof Collection) {
            return new GeneralLoopStatus((Collection) o);

        } else if (o instanceof Map) {
            return new GeneralLoopStatus((Map) o);
        } else if (o instanceof Iterable) {
            return new IteratorLoopStatus(((Iterable) o).iterator());
        } else if (o instanceof Iterator) {
            return new IteratorLoopStatus((Iterator) o);
        } else if (o.getClass().isArray()) {
            return new GeneralLoopStatus(o, o.getClass().getComponentType().isPrimitive());
        } else if (o instanceof Enumeration) {
            return new GeneralLoopStatus((Enumeration) o);
        } else {
//            throw new RuntimeException("Object:" + o.getClass() + " 不能使用在For循环里");
			return null;
        }
    }

    public GeneralLoopStatus(Enumeration e) {
        List list = Collections.list(e);
        this.it = list.iterator();
        this.size = list.size();

    }

    public GeneralLoopStatus(Collection collection) {
        this.it = collection.iterator();
        this.size = collection.size();

    }

    public GeneralLoopStatus(Map map) {
        it = map.entrySet().iterator();
        this.size = map.size();
    }

    public GeneralLoopStatus(Object[] array) {
        it = new ArrayIterator(array);
        size = array.length;

    }

    public GeneralLoopStatus(Object o, boolean isPrimitive) {
        if (isPrimitive) {
            it = new PrimitiveIterator(o);
            size = ((PrimitiveIterator) it).length;
        } else {
            Object[] array = ((Object[]) o);
            it = new ArrayIterator(array);
            size = (array).length;
        }

    }

    public GeneralLoopStatus(Iterable i) {
        this.it = i.iterator();

    }

    public final boolean hasNext() {
        return this.it.hasNext();
    }

    public final Object next() {
        index++;
        if (!hasData)
            hasData = true;
        return this.it.next();
    }

    public final int getIndex() {
        return this.index;
    }

    public final int getDataIndex() {
        return this.index - 1;
    }

    public final boolean isFirst() {
        return index == 1;
    }

    public final boolean isLast() {
        return index == size;
    }

    public final boolean isEven() {

        return index % 2 == 0;
    }

    public final boolean isOdd() {
        return index % 2 == 1;
    }

    public final boolean hasSize() {
        return size != -1;
    }

    public int getSize() {
        if (size != -1) {
            return size;
        } else {
            throw new RuntimeException("集合长度未知,请勿使用size");
        }
    }

    public final boolean hasData() {
        return hasData;
    }

    static class ArrayIterator implements Iterator {
        Object[] array = null;
        int i = 0;

        ArrayIterator(Object[] array) {
            this.array = array;
        }

        public boolean hasNext() {
            // TODO Auto-generated method stub
            return array.length > i;
        }

        public Object next() {
            return array[i++];
        }

        public void remove() {
            // TODO Auto-generated method stub

        }

    }

    static class PrimitiveIterator implements Iterator {
        Object o = null;
        int i = 0;
        int length = 0;

        PrimitiveIterator(Object o) {
            this.o = o;
            this.length = PrimitiveArrayUtil.getSize(o);
        }

        public boolean hasNext() {
            // TODO Auto-generated method stub
            return length > i;
        }

        public Object next() {
            return PrimitiveArrayUtil.getObject(o, i++);
        }

        public void remove() {
            // TODO Auto-generated method stub

        }

    }

}
