package org.beetl.core.runtime;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IBeetlMemoryManager {

    /**
     * 创建数组实例
     *
     * @param type   类型
     * @param minLen 长度
     * @return {@param type} 类型的数组实例
     */
    Object newUnpaddedArray(Class<?> type, int minLen);

    /**
     * 获取一个 {@link Map} 的实例
     *
     * @return Map 实例，如果池中实例不够，则会创建一个
     */
    Map map();

    /**
     * 将一个 {@link Map} 实例放入池中
     *
     * @param garbage 应该被回收的实例
     * @return true 表示放入成功，false 表示放入失败
     */
    boolean recoveryMap(Map garbage);

    /**
     * 获取一个 {@link Set} 的实例
     *
     * @return Set 实例，如果池中实例不够，则会创建一个
     */
    Set set();

    /**
     * 将一个 {@link Set} 实例放入池中
     *
     * @param garbage 应该被回收的实例
     * @return true 表示放入成功，false 表示放入失败
     */
    boolean recoverySet(Set garbage);

    /**
     * 获取一个 {@link List} 的实例
     *
     * @return List 实例，如果池中实例不够，则会创建一个
     */
    List list();

    /**
     * 将一个 {@link List} 实例放入池中
     *
     * @param garbage 应该被回收的实例
     * @return true 表示放入成功，false 表示放入失败
     */
    boolean recoveryList(List garbage);

    interface Clearable {
        void clear();
    }

    boolean recoveryObject(Class<Clearable> bigObjectType, Clearable bigObject);

    Clearable object(Class<Clearable> bigObjectType);

    void clearMemory();

}
