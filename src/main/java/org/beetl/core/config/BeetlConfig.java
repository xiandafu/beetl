package org.beetl.core.config;

import org.beetl.core.runtime.BeetlRuntime;

/**
 * Beetl 配置类
 *
 * @author z8g
 * @since 2020-09-21
 */
public class BeetlConfig {

    /** DEBUG flag */
    public static final boolean DEBUG = BeetlRuntime.getConfigManager().isDebug();
    /** RELEASE flag */
    public static final boolean RELEASE = BeetlRuntime.getConfigManager().isRelease();
    /** Beetl 版本号 */
    public static final String BEETL_VERSION = BeetlRuntime.getConfigManager().getBeetlVersion();

}
