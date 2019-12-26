package org.beetl.core.io;

/**
 * 提供某些高速场景下的writer，无需分配char[]
 * <pre>
 *
 *     Writer writer = CachedStringWriter.buffers.get();
 *     tempalte.renderTo(writer);
 *     String result = writer.toString();
 * </pre>
 */
public class CachedStringWriter extends NoLockStringWriter {
    public String toString()
    {

        String str =  new String(buf, 0, count);
        count = 0;
        /**
         * 如果buf过长，可以考虑清空或者缩容
         * 考虑Web应用通常是直接输出到ServletIOStream
         * 所以这种很少使用，只作为性能要求很高场合要用
         * if(buf.length>1024*10) buf = new char[1024];
         */

        return str;
    }

    public static ThreadLocal<CachedStringWriter> buffers = new ThreadLocal<CachedStringWriter>(){
        public CachedStringWriter initialValue(){
            return  new CachedStringWriter();
        }
    };
}
