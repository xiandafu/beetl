package org.beetl.core.util;

/**
 * 保留了各种类型的空数组的引用，以避免申请多余的内存空间
 */
public class EmptyArray {

    /** 表示一个 int 类型的空数组 */
    public static final int[] INT = new int[]{};
    /** 表示一个 Object 类型的空数组 */
    public static final Object[] OBJECT = new Object[]{};
    /** 表示一个 String 类型的空数组 */
    public static final String[] STRING = new String[]{};
    /** 表示一个 boolean 类型的空数组 */
    public static final boolean[] BOOLEAN = new boolean[]{};
    /** 表示一个 long 类型的空数组 */
    public static final long[] LONG = new long[]{};
}
