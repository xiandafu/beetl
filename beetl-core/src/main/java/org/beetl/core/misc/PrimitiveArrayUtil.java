package org.beetl.core.misc;

/**
 * 用于原始数据类型数组操作的工具类
 */
public class PrimitiveArrayUtil {

    /**
     * 获取数组元素的个数
     *
     * @param o 数组
     * @return 数组元素的个数
     */
    public static int getSize(Object o) {
        Class<?> type = o.getClass();
        if (type == int[].class) {
            return ((int[]) o).length;
        } else if (type == long[].class) {
            return ((long[]) o).length;
        } else if (type == short[].class) {
            return ((short[]) o).length;
        } else if (type == double[].class) {
            return ((double[]) o).length;
        } else if (type == float[].class) {
            return ((float[]) o).length;
        } else if (type == char[].class) {
            return ((char[]) o).length;
        } else if (type == byte[].class) {
            return ((byte[]) o).length;
        } else if (type == boolean[].class) {
            return ((boolean[]) o).length;
        } else {
            throw new RuntimeException("不支持数组");
        }
    }

    /**
     * 根据数组下标获取数组元素
     *
     * @param o     数组
     * @param index 下标
     * @return o[index]
     */
    public static Object getObject(Object o, int index) {
        Class<?> type = o.getClass();
        if (type == int[].class) {
            return ((int[]) o)[index];
        } else if (type == long[].class) {
            return ((long[]) o)[index];
        } else if (type == short[].class) {
            return ((short[]) o)[index];
        } else if (type == double[].class) {
            return ((double[]) o)[index];
        } else if (type == float[].class) {
            return ((float[]) o)[index];
        } else if (type == char[].class) {
            return ((char[]) o)[index];
        } else if (type == byte[].class) {
            return ((byte[]) o)[index];
        } else if (type == boolean[].class) {
            return ((boolean[]) o)[index];
        } else {
            throw new RuntimeException("不支持数组 " + o);
        }
    }

    /**
     * 为数组的指定下标设置新值
     *
     * @param o     数组
     * @param index 数组的下标
     * @param value 新值
     */
    public static void setObject(Object o, int index, Object value) {
        Class<?> type = o.getClass();
        if (type == int[].class) {
            ((int[]) o)[index] = ((Number) value).intValue();
        } else if (type == long[].class) {
            ((long[]) o)[index] = ((Number) value).longValue();
        } else if (type == short[].class) {
            ((short[]) o)[index] = ((Number) value).shortValue();
        } else if (type == double[].class) {
            ((double[]) o)[index] = ((Number) value).doubleValue();
        } else if (type == float[].class) {
            ((float[]) o)[index] = ((Number) value).floatValue();
        } else if (type == char[].class) {
            if (value instanceof Number) {
                ((char[]) o)[index] = (char) ((Number) value).shortValue();
            } else {
                ((char[]) o)[index] = (Character) value;
            }

        } else if (type == byte[].class) {
            if (value instanceof Number) {
                ((byte[]) o)[index] = (byte) ((Number) value).shortValue();
            } else {
                ((byte[]) o)[index] = (Byte) value;
            }

        } else if (type == boolean[].class) {
            ((boolean[]) o)[index] = ((Boolean) value);
        } else {
            throw new RuntimeException("不支持数组 " + o);
        }
    }
}
