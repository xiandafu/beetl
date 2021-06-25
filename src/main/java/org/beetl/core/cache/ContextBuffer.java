package org.beetl.core.cache;

import org.beetl.core.annotation.NonThreadSafety;
import org.jetbrains.annotations.NotNull;

/**
 * 一个缓存的字节和字符数组，用于减少beetl渲染各个过程中渲染字符数组
 *
 * @author xiandafu
 */
@NonThreadSafety
public class ContextBuffer {

    /** 空字符数组 */
    private static final char[] EMPTY_CHAR_ARRAY = new char[0];
    /** 空字节数组 */
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    /** 扩容倍数 */
    private static final float RESIZE_FACTOR = 1.2F;
    /** 初始容量 */
    private static final int DEFAULT_SIZE = 256;

    /** 最大空间 */
    int maxSize;
    /** 是否是个临时buffer，如果是，不能保存在ContextLocalBuffers */
    boolean inner = true;

    /** 字符数组缓存 */
    private char[] charBuffer = new char[DEFAULT_SIZE];
    /** 字节数组缓存 */
    private byte[] byteBuffer = new byte[DEFAULT_SIZE];

    /**
     * 构造方法
     *
     * @param maxSize 最大容量
     * @param inner   是否为临时 buffer
     */
    public ContextBuffer(int maxSize, boolean inner) {
        this(maxSize);
        this.inner = inner;
    }

    /**
     * 构造方法
     *
     * @param maxSize 最大容量
     */
    public ContextBuffer(int maxSize) {
        if (maxSize < DEFAULT_SIZE) {
            // TODO: DEBUG 环境才抛出异常，非DEBUG环境，设置为默认值
            throw new IllegalArgumentException("buffer期望设置需要大于 " + DEFAULT_SIZE);
        }
        this.maxSize = maxSize;
    }

    /**
     * 获取字符数组缓存
     *
     * @return 上下文中缓存的字符数组
     */
    @NotNull
    public char[] getCharBuffer() {
        return this.charBuffer;
    }

    /**
     * 获取字节数组缓存
     *
     * @return 上下文中缓存的字节数组
     */
    @NotNull
    public byte[] getByteBuffer() {
        return this.byteBuffer;
    }

    /**
     * 得到一个期望长度的buffer,调用者应该检测是否返回null，表示
     */
    @NotNull
    public char[] getCharBuffer(int expected) {
        if (this.charBuffer.length >= expected) {
            return charBuffer;
        } else if (expected < maxSize) {
            this.charBuffer = new char[(int) (expected * RESIZE_FACTOR)]; // 预先设置多一点
            return charBuffer;
        }
        return EMPTY_CHAR_ARRAY;
    }

    /**
     * 得到期望字节数组大小
     */
    @NotNull
    public byte[] getByteBuffer(int expected) {
        if (this.byteBuffer.length >= expected) {
            return byteBuffer;
        } else if (expected < maxSize) {
            // 预先设置多一点
            byteBuffer = new byte[(int) (expected * RESIZE_FACTOR)];
            return byteBuffer;
        }
        return EMPTY_BYTE_ARRAY;
    }

}
