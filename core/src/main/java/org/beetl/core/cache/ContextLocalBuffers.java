package org.beetl.core.cache;

import org.beetl.core.annotation.NonThreadSafety;
import org.jetbrains.annotations.NotNull;

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

    /**
     * 获取一个 ContextBuffer 实例
     *
     * @return 从队列中返回一个实例，如果没有则创建一个
     */
    @NotNull
    public ContextBuffer getContextLocalBuffer() {
        ContextBuffer buffer = queue.poll();
        return buffer == null ? new ContextBuffer(bufferMax, false) : buffer;
    }

    /**
     * 将一个 ContextBuffer 实例回收到队列中
     *
     * @param buffer 一个 ContextBuffer 实例
     */
    public void putContextLocalBuffer(@NotNull ContextBuffer buffer) {
        if (!buffer.inner) {
            // 放弃，这是临时生成的
            return;
        }
        queue.add(buffer);
    }

}
