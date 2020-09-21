package org.beetl.core.config;

/**
 * 默认的Beetl配置
 */
public class DefaultBeetlConfig implements IBeetlConfig {

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
