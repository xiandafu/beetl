package org.beetl.core.cache;

import org.beetl.core.annotation.NonThreadSafety;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * TODO3，需要性能压测
 * 模板渲染和计算中需要的临时存储空间，缓存以提高性能
 */
@NonThreadSafety
public class ContextLocalBuffers {

    /** ContextBuffer 队列 */
    private final ArrayBlockingQueue<ContextBuffer> queue;
    /** 字节数组与字符数组的最大容量 */
    private final int bufferMax;

    /**
     * 构造方法
     *
     * @param num       队列的大小
     * @param bufferMax 字节数组与字符数组的最大容量
     */
    public ContextLocalBuffers(int num, int bufferMax) {
        this.bufferMax = bufferMax;
        queue = new ArrayBlockingQueue<>(num);
        for (int i = 0; i < num; i++) {
            ContextBuffer buffer = new ContextBuffer(bufferMax, true);
            queue.add(buffer);
        }
    }

    public ContextBuffer getContextLocalBuffer() {
        ContextBuffer buffer = queue.poll();
        return buffer == null ? new ContextBuffer(bufferMax, false) : buffer;
    }

    public void putContextLocalBuffer(ContextBuffer buffer) {
        if (!buffer.inner) {
            // 放弃，这是临时生成的
            return;
        }
        queue.add(buffer);
    }

}
