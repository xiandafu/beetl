package org.beetl.core.runtime;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IBeetlMemoryManager {

    /**
     * 获取一个 {@link Map} 的实例
     *
     * @return Map 实例，如果池中实例不够，则会创建一个
     */
    Map takeMap();

    /**
     * 将一个 {@link Map} 实例放入池中
     *
     * @param garbage 应该被回收的实例
     * @return true 表示放入成功，false 表示放入失败
     */
    boolean recoveryMap(Map garbage);

    /**
     * 返回 Map 池中保存的 Map 实例的个数
     *
     * @return Map 池中实例的个数
     */
    int sizeOfMapPool();

    /**
     * 获取一个 {@link Set} 的实例
     *
     * @return Set 实例，如果池中实例不够，则会创建一个
     */
    Set takeSet();

    /**
     * 将一个 {@link Set} 实例放入池中
     *
     * @param garbage 应该被回收的实例
     * @return true 表示放入成功，false 表示放入失败
     */
    boolean recoverySet(Set garbage);

    /**
     * 返回 Set 池中保存的 Set 实例的个数
     *
     * @return Set 池中实例的个数
     */
    int sizeOfSetPool();

    /**
     * 获取一个 {@link List} 的实例
     *
     * @return List 实例，如果池中实例不够，则会创建一个
     */
    List takeList();

    /**
     * 将一个 {@link List} 实例放入池中
     *
     * @param garbage 应该被回收的实例
     * @return true 表示放入成功，false 表示放入失败
     */
    boolean recoveryList(List garbage);

    /**
     * 返回 List 池中保存的 List 实例的个数
     *
     * @return List 池中实例的个数
     */
    int sizeOfListPool();

    /**
     * 标识一个类具备可清除的特性 {@link #recoveryObject} {@link #takeObject}
     */
    interface Clearable {
        /**
         * 实现该接口的类自行维护清理逻辑
         */
        void clear();
    }

    /**
     * 将一个 {@link Clearable} 实例放入池中
     *
     * @param type    类型
     * @param garbage 应该被回收的实例
     * @return true 表示放入成功，false 表示放入失败
     */
    boolean recoveryObject(Class<Clearable> type, Clearable garbage);

    /**
     * 获取一个 {@link Clearable} 的实例
     *
     * @return Clearable 实例，如果池中实例不够，则会创建一个
     */
    Clearable takeObject(Class<Clearable> type);

    /**
     * 返回 Object 池中类型的个数
     *
     * @return 返回 type 的个数
     */
    int sizeOfObjectType();

    /**
     * 返回 Object 池中实例的个数
     *
     * @return Obejct 池中所有类型的实例数量总和
     */
    int sizeOfObjectPool();

    /**
     * 清理 ListPool SetPool MapPool 和 ObjectPool
     * 对于 ObjectPool，会依次调用每个实例的 {@link Clearable#clear} 方法
     */
    void clearMemory();

}
