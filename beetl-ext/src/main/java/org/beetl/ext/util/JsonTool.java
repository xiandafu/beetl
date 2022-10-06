package org.beetl.ext.util;

/**
 * JSON 序列化的抽象
 */
public interface JsonTool {
    /**
     * 将 {@code object} 渲染成JSON字符串
     *
     * @param object 任何一个对象
     * @return JSON字符串
     */
    String render(Object object);
}
