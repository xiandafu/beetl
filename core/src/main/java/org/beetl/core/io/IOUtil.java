package org.beetl.core.io;

public class IOUtil {

    /**
     * 拷贝一份内容
     *
     * @param original  原始字节数组
     * @param newLength 待拷贝的长度
     * @return 新的数组实例
     */
    public static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    /**
     * 拷贝一份内容
     *
     * @param original  原始字符数组
     * @param newLength 待拷贝的长度
     * @return 新的数组实例
     */
    public static char[] copyOf(char[] original, int newLength) {
        char[] copy = new char[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }
}
