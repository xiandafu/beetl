package org.beetl.core.io;


import org.beetl.core.ByteWriter;
import org.jetbrains.annotations.Range;

import java.io.IOException;

/**
 * 用于输出 int/long 类型到 Writer 的高性能工具类
 */
public class IntIOWriter {
    /** Integer 最小值的字符串表示 */
    private static final String INT_MIN_VALUE_STR = "-2147483648";
    /** Long 最小值的字符串表示 */
    private static final String LONG_MIN_VALUE_STR = "-9223372036854775808";

    /** 包含每个分位最大值的，int类型的正数数组，用该数组计算int类型正数的字符串长度 */
    private static final int[] INT_MAX_VALUE_POSITIVES = {
            9, 99, 999, 999_9, 999_99, 999_999, 999_999_9, 999_999_99, 999_999_999, Integer.MAX_VALUE
    };
    /** 包含每个分位最大值的，long类型的正数数组，用该数组计算long类型正数的字符串长度 */
    private static final long[] LONG_MAX_VALUE_POSITIVES = {
            9L, 99L, 999L, 999_9L, 999_99L,
            999_999L, 999_999_9L, 999_999_99L, 999_999_999L, 999_999_999_9L,
            999_999_999_99L, 999_999_999_999L, 999_999_999_999_9L, 999_999_999_999_99L, 999_999_999_999_999L,
            999_999_999_999_999_9L, 999_999_999_999_999_99L, 999_999_999_999_999_999L, Long.MAX_VALUE
    };

    /** 数字+字母的字符数组 */
    private static final char[] DIGIT_CHAR_ARRAY = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    /** [0, 9]的重复9次的字符数组 */
    private static final char[] DIGIT_TEN_CHAR_ARRAY = {
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
            '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
            '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
            '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
            '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
            '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
            '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
            '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
            '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
    };
    /** 9个[0, 9]的字符数组 */
    private static final char[] DIGIT_ONE_CHAR_ARRAY = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    };

    /** 预置的缓存个数 */
    private static final int DEFAULT_INT_CHAR_ARRAY_CACHE_CAPACITY = 1024;
    /** 预置的缓存 */
    private static final Object[] INT_CHAR_ARRAY_CACHE = new Object[DEFAULT_INT_CHAR_ARRAY_CACHE_CAPACITY];

    // 填充 INT_STRING_CHAR_ARRAY_CACHE
    static {
        for (int i = 0; i < DEFAULT_INT_CHAR_ARRAY_CACHE_CAPACITY; i++) {
            INT_CHAR_ARRAY_CACHE[i] = String.valueOf(i).toCharArray();
        }
    }

    /**
     * 输出 Integer 类型的 {@param integer} 到 {@param bw} 中
     *
     * @param out   混合了字节和字符的Writer
     * @param value 即将写入到 {@param bw} 中的 Integer 类型
     * @throws IOException {@link ByteWriter#writeNumberChars} 或 {@link ByteWriter#writeString}
     */
    public static void writeInt(ByteWriter out, int value) throws IOException {
        // 命中预置的缓存
        if (0 <= value && value < DEFAULT_INT_CHAR_ARRAY_CACHE_CAPACITY) {
            char[] buf = (char[]) INT_CHAR_ARRAY_CACHE[value];
            out.writeNumberChars(buf, buf.length);
            return;
        }

        // 能等于 Integer.MIN_VALUE ，说明传入时，已经是小于等于 Integer.MIN_VALUE 了，直接输出字符串，不再计算
        if (value == Integer.MIN_VALUE) {
            out.writeString(INT_MIN_VALUE_STR);
            return;
        }

        // 计算该数字的字符串形式所占的位数，如果是负数，会转成正数，计算完后，加上1位符号位
        int size = (value < 0) ? calcStringLength(-value) + 1 : calcStringLength(value);
        char[] buf = out.getLocalBuffer().getCharBuffer();
        fillChars(value, size, buf);
        out.writeNumberChars(buf, size);
    }

    /**
     * 输出 Long 类型的 {@param i} 到 {@param bw} 中
     *
     * @param out   混合了字节和字符的Writer
     * @param value 即将写入到 {@param bw} 中的 Long 类型
     * @throws IOException {@link ByteWriter#writeNumberChars} 或 {@link ByteWriter#writeString}
     */
    public static void writeLong(ByteWriter out, long value) throws IOException {
        // TODO: 类似 {@link #writeInteger} 加入缓存机制

        if (value == Long.MIN_VALUE) {
            out.writeString(LONG_MIN_VALUE_STR);
            return;
        }

        int size = (value < 0) ? calcStringLength(-value) + 1 : calcStringLength(value);
        char[] buf = out.getLocalBuffer().getCharBuffer();
        fillChars(value, size, buf);
        out.writeNumberChars(buf, size);
    }

    /**
     * 将 {@param value} 转成字符串填充到buf中
     *
     * @param value           待填充进char[]中的数值
     * @param valueStringSize {@param value} 字符串形式的字符个数
     * @param res             将 {@param value} 填充到该字符数组中
     */
    static void fillChars(int value, int valueStringSize, char[] res) {
        int q, r;
        int charPos = valueStringSize;
        char sign = 0;

        if (value < 0) {
            sign = '-';
            value = -value;
        }

        // Generate two digits per iteration
        while (value >= 65536) {
            q = value / 100;
            // really: r = i - (q * 100);
            r = value - ((q << 6) + (q << 5) + (q << 2));
            value = q;
            res[--charPos] = DIGIT_ONE_CHAR_ARRAY[r];
            res[--charPos] = DIGIT_TEN_CHAR_ARRAY[r];
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i <= 65536, i);
        do {
            q = (value * 52429) >>> (16 + 3);
            r = value - ((q << 3) + (q << 1)); // r = i-(q*10) ...
            res[--charPos] = DIGIT_CHAR_ARRAY[r];
            value = q;
        } while (value != 0);
        if (sign != 0) {
            res[--charPos] = sign;
        }
    }

    /**
     * 将 {@param value} 转成字符串填充到buf中
     *
     * @param value           待填充进char[]中的数值
     * @param valueStringSize {@param value} 字符串形式的字符个数
     * @param res             将 {@param value} 填充到该字符数组中
     */
    static void fillChars(long value, int valueStringSize, char[] res) {
        long q;
        int r;
        int charPos = valueStringSize;
        char sign = 0;

        if (value < 0) {
            sign = '-';
            value = -value;
        }

        // Get 2 digits/iteration using longs until quotient fits into an int
        while (value > Integer.MAX_VALUE) {
            q = value / 100;
            // really: r = i - (q * 100);
            r = (int) (value - ((q << 6) + (q << 5) + (q << 2)));
            value = q;
            res[--charPos] = DIGIT_ONE_CHAR_ARRAY[r];
            res[--charPos] = DIGIT_TEN_CHAR_ARRAY[r];
        }

        // Get 2 digits/iteration using ints
        int q2;
        int i2 = (int) value;
        while (i2 >= 65536) {
            q2 = i2 / 100;
            // really: r = i2 - (q * 100);
            r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
            i2 = q2;
            res[--charPos] = DIGIT_ONE_CHAR_ARRAY[r];
            res[--charPos] = DIGIT_TEN_CHAR_ARRAY[r];
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i2 <= 65536, i2);
        do {
            q2 = (i2 * 52429) >>> (16 + 3);
            r = i2 - ((q2 << 3) + (q2 << 1)); // r = i2-(q2*10) ...
            res[--charPos] = DIGIT_CHAR_ARRAY[r];
            i2 = q2;
        } while (i2 != 0);
        if (sign != 0) {
            res[--charPos] = sign;
        }
    }

    /**
     * 计算一个正数的字符串形式所占的字符个数
     *
     * @param positiveNumber 必须是一个大于0的int类型
     * @return {@param positiveNumber} 的字符串形式所占的字符个数，例如传入18，返回2
     */
    static int calcStringLength(@Range(from = 0, to = Integer.MAX_VALUE) int positiveNumber) {
        for (int i = 0; ; i++) {
            if (positiveNumber <= INT_MAX_VALUE_POSITIVES[i]) {
                return i + 1;
            }
        }
    }

    /**
     * 计算一个正数的字符串形式所占的字符个数
     *
     * @param positiveNumber 必须是一个大于0的long类型
     * @return {@param positiveNumber} 的字符串形式所占的字符个数，例如传入18，返回2
     */
    static int calcStringLength(@Range(from = 0, to = Long.MAX_VALUE) long positiveNumber) {
        for (int i = 0; ; i++) {
            if (positiveNumber <= LONG_MAX_VALUE_POSITIVES[i]) {
                return i + 1;
            }
        }
    }

}
