package org.beetl.core.runtime.impl;

import org.beetl.core.runtime.IBeetlConfigManager;

/**
 * 默认的Beetl配置
 */
public class DefaultBeetlConfigManager implements IBeetlConfigManager {

    /** 单例模式 - 不可获取实例 */
    private DefaultBeetlConfigManager() {
    }

    /** 单例模式 - 通过内部类初始化，保证实例的线程安全 */
    private static class Holder {
        /** 单例模式 - DefaultBeetlConfigManager 的实例 */
        private static final IBeetlConfigManager INSTANCE = new DefaultBeetlConfigManager();
    }

    /**
     * 获取 DefaultBeetlConfigManager 实例
     *
     * @return 进程内唯一的实例
     */
    public static IBeetlConfigManager get() {
        return Holder.INSTANCE;
    }

    @Override
    public boolean isDebug() {
        // TODO: 从配置文件中动态获取
        return true;
    }

    @Override
    public boolean isRelease() {
        return !isDebug();
    }
}
