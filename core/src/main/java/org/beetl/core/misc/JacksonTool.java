package org.beetl.core.misc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 封装了 Jackson 的 JSON 序列化操作
 */
public class JacksonTool implements JsonTool {

    /** Jackson 对 JSON 序列化时所需的实例 */
    static ObjectMapper mapper = new ObjectMapper();

    @Override
    public String render(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
