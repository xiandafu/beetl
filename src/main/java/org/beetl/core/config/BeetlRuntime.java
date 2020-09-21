package org.beetl.core.config;

/**
 * 用于获取各种运行时
 */
public class BeetlRuntime {

    /**
     * 获取Beetl的运行时配置信息
     *
     * @return Beetl的运行时配置信息
     */
    public static IBeetlConfig getConfigRuntime() {
        return new DefaultBeetlConfig();
    }

//    static IBeetlConfig getAppRuntime() {
//    }
//    static IBeetlConfig getAntlrRuntime() {
//    }
}
