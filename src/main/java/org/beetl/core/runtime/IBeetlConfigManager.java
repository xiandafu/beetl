package org.beetl.core.runtime;

/**
 * Beetl 配置
 */
public interface IBeetlConfigManager {

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

    /**
     * 获取 Beetl 版本号
     *
     * @return x.x.x
     */
    String getBeetlVersion();
}
