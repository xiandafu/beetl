package org.beetl.ext.simulate;

/**
 * 模拟测试中的json工具，Beetl 不自带 JSON 工具，建议使用 {@code jackson} 或 {@code fastjson}
 *
 * @author xiandafu
 */
public interface JsonUtil {
    /**
     * 将 object 格式化为 JSON 字符串
     *
     * @param object 任何一个对象
     * @return JSON字符串
     * @throws Exception 可能抛出异常
     */
    String toJson(Object object) throws Exception;

    /**
     * 将JSON字符串以及对应的类型转换为对象
     *
     * @param jsonString     任意一个JSON字符串
     * @param jsonObjectType JSON字符串的类型
     * @return 将JSON字符串转成的对象
     */
    Object toObject(String jsonString, Class jsonObjectType) throws Exception;
}
