package org.beetl.core.misc;

/**
 * 用于加载 JSON 序列化工具的工厂类
 * 预置 {@link JacksonTool} 和 {@link FastJsonTool}
 */
public class JsonFactory {

    /** JSON 序列化工具的实例 */
    static JsonTool tool = null;

    static {
        try {
            tool = new JacksonTool();
        } catch (Throwable ex) {
            // 类不加载
        }
        if (tool == null) {
            try {
                tool = new FastJsonTool();
            } catch (Throwable ex) {
                //类不加载
            }
        }

        if (tool == null) {
            throw new IllegalStateException("沒有对应的jackson或者fastjson库");
        }

    }

    public static JsonTool get() {
        return tool;
    }
}
