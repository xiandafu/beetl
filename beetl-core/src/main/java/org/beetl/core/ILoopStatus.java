package org.beetl.core;

/**
 * 遍历时所需的循环状态
 */
public interface ILoopStatus {
    /**
     * 是否还存在下一项
     *
     * @return true 表示存在，false 表示不存在
     */
    boolean hasNext();

    /**
     * 返回下一项，并将游标往下移一项
     *
     * @return 下一项
     */
    Object next();

    /**
     * 获取当前项的索引值
     *
     * @return 当前项的索引值
     */
    int getIndex();

    /**
     * 获取当前数据项的索引
     *
     * @return 当前数据项的索引值
     */
    int getDataIndex();

    /**
     * 判断当前项是否是第一项
     *
     * @return true 表示是第一项，false 表示不是第一项
     */
    boolean isFirst();

    /**
     * 判断当前项是否是最后一项
     *
     * @return true 表示是最后一项，false 表示不是最后一项
     */
    boolean isLast();

    /**
     * 判断当前数据项是否为偶数
     *
     * @return true表示为偶数，false表示不为偶数
     */
    boolean isEven();

    /**
     * 判断当前数据项是否为奇数
     *
     * @return true表示为奇数，false表示不为奇数
     */
    boolean isOdd();

    /**
     * 返回是否有数据项
     *
     * @return true表示有，false表示没有
     */
    boolean hasSize();

    /**
     * 返回所有数据项的总个数
     *
     * @return 所有数据项的总个数
     */
    int getSize();

    /**
     * 返回是否还有数据
     *
     * @return true表示有，false表示没有
     */
    boolean hasData();

}
