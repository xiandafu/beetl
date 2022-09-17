package org.beetl.core.event;

/**
 * 事件监听器
 */
public interface EventListener {

    /**
     * 回调 {@code event} 中设置的动作
     *
     * @param event 事件
     * @return 返回事件执行的结果
     */
    Object onEvent(Event event);
}
