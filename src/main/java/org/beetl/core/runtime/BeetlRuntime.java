package org.beetl.core.runtime;

import org.beetl.core.runtime.impl.DefaultBeetlConfigManager;
import org.beetl.core.runtime.impl.DefaultBeetlMemoryManager;

/**
 * Beetl运行时，用于获取各种运行时
 */
public class BeetlRuntime {

    /**
     * 获取Beetl的运行时配置信息
     *
     * @return Beetl的运行时配置信息
     */
    public static IBeetlConfigManager getConfigManager() {
        return DefaultBeetlConfigManager.get();
    }

    public static IBeetlMemoryManager getMemoryManager() {
        return DefaultBeetlMemoryManager.get();
    }

}
