package org.beetl.core.config;

/**
 * Beetl 配置
 */
public interface IBeetlConfig {

    /**
     * 返回当前环境是否为调试环境
     *
     * @return true代表是调试环境，false表示不为调试环境
     */
    boolean isDebug();

    /**
     * 返回当前环境是否为发行环境
     *
     * @return true表示为发行环境，false表示不为发行环境
     */
    boolean isRelease();
}
