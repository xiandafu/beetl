package org.beetl.core.misc;

import com.alibaba.fastjson.JSON;


/**
 * 封装了 Fastjson 的 JSON 序列化操作
 */
public class FastJsonTool implements JsonTool {

    static {
        // just test FastJSON in classpath
        String str = JSON.toJSONString(new Object());
    }

    @Override
    public String render(Object o) {
        return JSON.toJSONString(o);
    }
}
