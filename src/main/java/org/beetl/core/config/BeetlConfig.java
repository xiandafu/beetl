package org.beetl.core.config;

/**
 * Beetl 配置类
 *
 * @author z8g
 * @since 2020-09-21
 */
public class BeetlConfig {
    /** DEBUG flag */
    public static final boolean DEBUG = BeetlRuntime.getConfigRuntime().isDebug();
    /** RELEASE flag */
    public static final boolean RELEASE = BeetlRuntime.getConfigRuntime().isRelease();

}
