package org.beetl.core.tag;

/**
 * 标签函数工厂类
 *
 * @author xiandafu
 */
public interface TagFactory {
    /**
     * 创建一个标签
     *
     * @return 一个标签实例, 不一定是新实例
     */
    Tag createTag();
}
