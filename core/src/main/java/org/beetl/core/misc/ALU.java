/*
 [The "BSD license"]
 Copyright (c) 2011-2020  闲大赋 (李家智)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
     derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.beetl.core.misc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;

import org.beetl.core.exception.BeetlException;
import org.beetl.core.statement.ASTNode;
import org.beetl.core.statement.GrammarToken;
import org.intellij.lang.annotations.MagicConstant;

/**
 * 用于算数表达式，对于加法，允许null值，但其他则不允许，将抛出异常
 * 参考了webit
 *
 * @author xiandafu
 */
public class ALU {

    public static final int NULL = 0;
    public static final int OBJECT = 1;
    public static final int STRING = 2;
    public static final int DOUBLE = 3;
    public static final int FLOAT = 4;
    public static final int LONG = 5;
    public static final int INTEGER = 6;
    public static final int SHORT = 7;
    public static final int CHAR = 8;
    public static final int BIG_DECIMAL = 9;

    @MagicConstant(intValues = {NULL, OBJECT, STRING, DOUBLE, FLOAT, LONG, INTEGER, SHORT, CHAR, BIG_DECIMAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AluType {
    }

    public static int scale = 12;
    public static int round = BigDecimal.ROUND_HALF_DOWN;

    /**
     * 获取 ALU 的类型
     *
     * @param left  左值
     * @param right 右值
     * @return {@link AluType} 类型
     */
    @AluType
    public static int getAluType(final Object left, final Object right) {
        if (left == null || right == null) {
            return NULL;
        }

        final Class<?> cls1 = left.getClass();
        final Class<?> cls2 = right.getClass();

        if (cls1 == Character.class && cls2 == Character.class) {
            return CHAR;
        } else if (left instanceof Number && right instanceof Number) {
            if (cls1 == BigDecimal.class || cls2 == BigDecimal.class) {
                return BIG_DECIMAL;
            } else if (cls1 == Double.class || cls2 == Double.class) {
                return DOUBLE;
            } else if (cls1 == Float.class || cls2 == Float.class) {
                return FLOAT;
            } else if (cls1 == Long.class || cls2 == Long.class) {
                return LONG;
            } else if (cls1 == Integer.class || cls2 == Integer.class) {
                return INTEGER;
            } else if (cls1 == Short.class || cls2 == Short.class) {
                return SHORT;
            }
        }
        return STRING;
    }

    /**
     * 获取 ALU 的类型
     *
     * @param cls1 左值的类型
     * @param cls2 右值的类型
     * @return {@link AluType} 类型
     */
    @AluType
    public static int getAluType(Class<?> cls1, Class<?> cls2) {
        if (cls1 == String.class || cls2 == String.class) {
            return STRING;
        }
        if (cls1 == Character.class && cls2 == Character.class) {
            return CHAR;
        }
        if (Number.class.isAssignableFrom(cls1) && Number.class.isAssignableFrom(cls2)) {
            if (cls1 == BigDecimal.class || cls2 == BigDecimal.class) {
                return BIG_DECIMAL;
            } else if (cls1 == Double.class || cls2 == Double.class) {
                return DOUBLE;
            } else if (cls1 == Float.class || cls2 == Float.class) {
                return FLOAT;
            } else if (cls1 == Long.class || cls2 == Long.class) {
                return LONG;
            } else if (cls1 == Integer.class || cls2 == Integer.class) {
                return INTEGER;
            } else if (cls1 == Short.class || cls2 == Short.class) {
                return SHORT;
            }
        }
        return OBJECT;
    }

    /**
     * 根据类型,先计算 {@link AluType}，再返回对应的 {@link Class}
     *
     * @param cls1 左值的类型
     * @param cls2 右值的类型
     * @return Java 类型，默认为 {@link Object}
     * @see #getAluType(Object, Object)
     */
    public static Class<?> getBaseTypeClass(Class<?> cls1, Class<?> cls2) {
        int type = getAluType(cls1, cls2);
        switch (type) {
            case INTEGER:
                return Integer.class;
            case STRING:
                return String.class;
            case LONG:
                return Long.class;
            case DOUBLE:
                return Double.class;
            case FLOAT:
                return Float.class;
            case SHORT:
                return Short.class;
            case BIG_DECIMAL:
                return BigDecimal.class;
            default:
                return Object.class;
        }
    }

    /**
     * 获取 Number 实例的 ALU 类型
     *
     * @param num {@link Number} 类型的实例
     * @return {@link AluType} 类型
     */
    public static int getNumberType(final Number num) {
        if (num == null) {
            return NULL;
        }
        final Class<?> cls = num.getClass();
        if (cls == Integer.class) {
            return INTEGER;
        } else if (cls == Long.class) {
            return LONG;
        } else if (cls == Short.class) {
            return SHORT;
        } else if (cls == Float.class) {
            return FLOAT;
        } else if (cls == Double.class) {
            return DOUBLE;
        } else if (cls == BigDecimal.class) {
            return BIG_DECIMAL;
        }
        return NULL;
    }

    /**
     * 获取 Object 实例的 ALU 类型
     *
     * @param obj {@link Object} 类型的实例
     * @return {@link AluType} 类型
     */
    public static int getAluType(final Object obj) {
        if (obj == null) {
            return NULL;
        }
        final Class<?> cls = obj.getClass();
        if (cls == String.class) {
            return STRING;
        } else if (cls == Integer.class) {
            return INTEGER;
        } else if (cls == Long.class) {
            return LONG;
        } else if (cls == Short.class) {
            return SHORT;
        } else if (cls == Float.class) {
            return FLOAT;
        } else if (cls == Double.class) {
            return DOUBLE;
        } else if (cls == Character.class) {
            return CHAR;
        } else if (cls == BigDecimal.class) {
            return BIG_DECIMAL;
        }
        return OBJECT;
    }

    /**
     * 加1操作
     *
     * @param obj  值
     * @param node AST结点
     * @return 加1后的值
     */
    public static Object plusOne(final Object obj, ASTNode node) {
        if (obj != null) {
            if (obj instanceof Number) {
                final Number num = (Number) obj;
                switch (getNumberType(num)) {
                    case INTEGER:
                        return Integer.valueOf(num.intValue() + 1);
                    case LONG:
                        return Long.valueOf(num.longValue() + 1L);
                    case DOUBLE:
                        return Double.valueOf(num.doubleValue() + 1D);
                    case FLOAT:
                        return Float.valueOf(num.floatValue() + 1F);
                    case SHORT:
                        return Short.valueOf((short) (num.intValue() + 1));
                    case BIG_DECIMAL:
                        BigDecimal bd = (BigDecimal) obj;
                        return bd.add(BigDecimal.ONE);
                }
            } else {
                throw numberExpectedException(obj, node);
            }
        }
        throw valueIsNullException(obj, node);
    }

    /**
     * 减1操作
     *
     * @param obj  值
     * @param node AST结点
     * @return 减1后的值
     */
    public static Object minusOne(final Object obj, ASTNode node) {
        if (obj != null) {
            if (obj instanceof Number) {
                final Number num = (Number) obj;
                switch (getNumberType(num)) {
                    case INTEGER:
                        return Integer.valueOf(num.intValue() - 1);
                    case LONG:
                        return Long.valueOf(num.longValue() - 1l);
                    case DOUBLE:
                        return Double.valueOf(num.doubleValue() - 1d);
                    case FLOAT:
                        return Float.valueOf(num.floatValue() - 1f);
                    case SHORT:
                        return Short.valueOf((short) (num.intValue() - 1));
                    case BIG_DECIMAL:
                        BigDecimal bd = (BigDecimal) obj;
                        return bd.min(BigDecimal.ONE);
                }
            } else {
                throw numberExpectedException(obj, node);
            }
        }
        throw valueIsNullException(obj, node);
    }

    /**
     * 相加操作: {@param o1} + {@param o2}
     *
     * @param o1    左值
     * @param o2    右值
     * @param node1 AST结点
     * @param node2 AST结点
     * @return 相加的结果
     */
    public static Object plus(final Object o1, final Object o2, final ASTNode node1, final ASTNode node2) {
        if (o1 != null && o2 != null) {
            switch (getAluType(o1, o2)) {
                case STRING:
                    return String.valueOf(o1).concat(String.valueOf(o2));
                case INTEGER:
                    return Integer.valueOf(((Number) o1).intValue() + ((Number) o2).intValue());
                case LONG:
                    return Long.valueOf(((Number) o1).longValue() + ((Number) o2).longValue());
                case DOUBLE:
                    return Double.valueOf(((Number) o1).doubleValue() + ((Number) o2).doubleValue());
                case FLOAT:
                    return Float.valueOf(((Number) o1).floatValue() + ((Number) o2).floatValue());
                case SHORT:
                    return Short.valueOf((short) (((Number) o1).intValue() + ((Number) o2).intValue()));
                case CHAR:
                    return Character.valueOf((char) (((Number) o1).intValue() + ((Number) o2).intValue()));
                case BIG_DECIMAL:
                    BigDecimal b1 = getBigDecimal(o1);
                    BigDecimal b2 = getBigDecimal(o2);
                    return b1.add(b2);
                default:
                    throw UnsupportedTypeException(o1, o2, node1, node2, "+");
            }
        } else if (o1 != null) {
            if (getAluType(o1) == STRING) {
                return o1;
            }
            throw valueIsNullException(o1, o2, node1, node2);
        } else if (o2 != null) {
            if (getAluType(o2) == STRING) {
                return o2;
            }
            throw valueIsNullException(o1, o2, node1, node2);
        } else {
            return null;
        }
    }

    /**
     * 相减操作: {@param o1} - {@param o2}
     *
     * @param o1    左值
     * @param o2    右值
     * @param node1 AST结点
     * @param node2 AST结点
     * @return {@param o1} 减 {@param o2} 的结果
     */
    public static Object minus(final Object o1, final Object o2, final ASTNode node1, final ASTNode node2) {
        if (o1 != null && o2 != null) {
            switch (getAluType(o1, o2)) {
                // case STRING:
                // return String.valueOf(o1) + String.valueOf(o2);
                case INTEGER:
                    return Integer.valueOf(((Number) o1).intValue() - ((Number) o2).intValue());
                case LONG:
                    return Long.valueOf(((Number) o1).longValue() - ((Number) o2).longValue());
                case DOUBLE:
                    return Double.valueOf(((Number) o1).doubleValue() - ((Number) o2).doubleValue());
                case FLOAT:
                    return Float.valueOf(((Number) o1).floatValue() - ((Number) o2).floatValue());
                case SHORT:
                    return Short.valueOf((short) (((Number) o1).intValue() - ((Number) o2).intValue()));
                case CHAR:
                    return Character.valueOf((char) (((Number) o1).intValue() - ((Number) o2).intValue()));
                case BIG_DECIMAL:
                    BigDecimal b1 = getBigDecimal(o1);
                    BigDecimal b2 = getBigDecimal(o2);
                    return b1.subtract(b2);
                default:
                    throw UnsupportedTypeException(o1, o2, node1, node2, "-");
            }
        } else {
            throw valueIsNullException(o1, o2, node1, node2);
        }
    }

    /**
     * 取反操作: -{@param o1}
     *
     * @param o1   值
     * @param node AST 结点
     * @return 对 {@param o1} 取反后的值
     */
    public static Object negative(final Object o1, ASTNode node) {
        if (o1 != null) {
            switch (getAluType(o1)) {
                // case STRING:
                // return String.valueOf(o1) + String.valueOf(o2);
                case INTEGER:
                    return -((Number) o1).intValue();
                case LONG:
                    return -((Number) o1).longValue();
                case DOUBLE:
                    return -((Number) o1).doubleValue();
                case FLOAT:
                    return -((Number) o1).floatValue();
                case SHORT:
                    return -((Number) o1).shortValue();
                case BIG_DECIMAL:
                    return ((BigDecimal) o1).negate();
                default:
                    throw new RuntimeException("value not a number");
            }
        } else {
            throw valueIsNullException(o1, node);
        }
    }

    /**
     * 相乘操作: {@param o1} * {@param o2}
     *
     * @param o1    左值
     * @param o2    右值
     * @param node1 AST结点
     * @param node2 AST结点
     * @return {@param o1} 乘以 {@param o2} 的结果
     */
    public static Object mult(final Object o1, final Object o2, final ASTNode node1, final ASTNode node2) {
        if (o1 != null && o2 != null) {
            switch (getAluType(o1, o2)) {
                // case STRING:
                // return String.valueOf(o1) + String.valueOf(o2);
                case INTEGER:
                    return Integer.valueOf(((Number) o1).intValue() * ((Number) o2).intValue());
                case LONG:
                    return Long.valueOf(((Number) o1).longValue() * ((Number) o2).longValue());
                case DOUBLE:
                    return Double.valueOf(((Number) o1).doubleValue() * ((Number) o2).doubleValue());
                case FLOAT:
                    return Float.valueOf(((Number) o1).floatValue() * ((Number) o2).floatValue());
                case SHORT:
                    return Short.valueOf((short) (((Number) o1).intValue() * ((Number) o2).intValue()));
                case BIG_DECIMAL:
                    BigDecimal b1 = getBigDecimal(o1);
                    BigDecimal b2 = getBigDecimal(o2);
                    return b1.multiply(b2);
                default:
                    throw UnsupportedTypeException(o1, o2, node1, node2, "*");
            }
        } else {
            throw valueIsNullException(o1, o2, node1, node2);
        }
    }

    /**
     * 相除操作: {@param o1} / {@param o2}
     *
     * @param o1    左值
     * @param o2    右值
     * @param node1 AST结点
     * @param node2 AST结点
     * @return {@param o1} 乘以 {@param o2} 的结果
     */
    public static Object div(final Object o1, final Object o2, final ASTNode node1, final ASTNode node2) {
        if (o1 != null && o2 != null) {
            switch (getAluType(o1, o2)) {
                case INTEGER:
                case LONG:
                case DOUBLE:
                case FLOAT:
                case SHORT:
                    double c = ((Number) o2).doubleValue();
                    if (c == 0) {
                        throw new BeetlException(BeetlException.DIV_ZERO_ERROR).pushToken(node2.token);
                    }
                    double a = ((Number) o1).doubleValue() / ((Number) o2).doubleValue();
                    return trim(a, (Number) o1, (Number) o2);
                case BIG_DECIMAL:
                    BigDecimal b1 = getBigDecimal(o1),
                            b2 = getBigDecimal(o2);
                    BigDecimal b = b1.divide(b2, scale, round);
                    return b.stripTrailingZeros();
//					return b1.divide(b2);
                default:
                    throw UnsupportedTypeException(o1, o2, node1, node2, "/");
            }
        } else {
            throw valueIsNullException(o1, o2, node1, node2);
        }
    }

    private static Number trim(double d, Number a1, Number a2) {

        Number n1 = null;
        Number n2 = null;

        if (a1 instanceof BigDecimal || a2 instanceof BigDecimal) {
            return d;
        } else if (n1 instanceof Double || n2 instanceof Double) {
            return d;
        } else if (n1 instanceof Float || n2 instanceof Float) {
            return (float) d;
        } else if (n1 instanceof Long || n2 instanceof Long) {
            if (d != (long) d) {
                return d;
            } else {
                return (long) d;
            }

        } else if (n1 instanceof Integer || n2 instanceof Integer) {
            if (d != (int) d) {
                return d;
            } else {
                return (int) d;
            }
        } else {
            if (d != (short) d) {
                return d;
            } else {
                return (short) d;
            }
        }
    }

    // %

    public static Object mod(final Object o1, final Object o2, final ASTNode node1, final ASTNode node2) {
        if (o1 != null && o2 != null) {
            switch (getAluType(o1, o2)) {
                // case STRING:
                // return String.valueOf(o1) + String.valueOf(o2);
                case INTEGER:
                    return Integer.valueOf(((Number) o1).intValue() % ((Number) o2).intValue());
                case LONG:
                    return Long.valueOf(((Number) o1).longValue() % ((Number) o2).longValue());
                case DOUBLE:
                    return Double.valueOf(((Number) o1).doubleValue() % ((Number) o2).doubleValue());
                case FLOAT:
                    return Float.valueOf(((Number) o1).floatValue() % ((Number) o2).floatValue());
                case SHORT:
                    return Short.valueOf((short) (((Number) o1).intValue() % ((Number) o2).intValue()));
                case BIG_DECIMAL:
                    BigDecimal b1 = getBigDecimal(o1),
                            b2 = getBigDecimal(o2);
                    return b1.divide(b2);
                default:
                    throw UnsupportedTypeException(o1, o2, node1, node2, "%");
            }
        } else {
            throw valueIsNullException(o1, o2, node1, node2);
        }
    }

    public static boolean equals(final Object o1, final Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (o1 instanceof Number && o2 instanceof Number) {
            switch (getAluType(o1, o2)) {
                // case STRING:
                // case CHAR:

                case SHORT:
                case INTEGER:
                    return ((Number) o1).intValue() == ((Number) o2).intValue();
                case LONG:
                    return ((Number) o1).longValue() == ((Number) o2).longValue();
                case DOUBLE:
                    return ((Number) o1).doubleValue() == ((Number) o2).doubleValue();
                case FLOAT:
                    return ((Number) o1).floatValue() == ((Number) o2).floatValue();

                case BIG_DECIMAL:
                    BigDecimal b1 = getBigDecimal(o1),
                            b2 = getBigDecimal(o2);
                    return b1.compareTo(b2) == 0;
            }
        } else if (o1 instanceof Enum) {
            return (o1.toString()).equals(o2);
        } else if (o2 instanceof Enum) {
            return (o2.toString()).equals(o1);
        }

        return false;
    }

    // !=

    public static boolean notEquals(final Object o1, final Object o2) {
        return !equals(o1, o2);
    }

    // >
    public static boolean greater(final Object o1, final Object o2, final ASTNode node1, final ASTNode node2) {
        if (o1 != null && o2 != null) {
            switch (getAluType(o1, o2)) {
                // case STRING:
                case CHAR:
                    return (o1 instanceof Number ? ((Number) o1).intValue() : (int) ((Character) o1).charValue()) > (o2 instanceof Number ? ((Number) o2)
                            .intValue() : (int) ((Character) o2).charValue());

                case SHORT:
                case INTEGER:
                    return ((Number) o1).intValue() > ((Number) o2).intValue();
                case LONG:
                    return ((Number) o1).longValue() > ((Number) o2).longValue();
                case DOUBLE:
                    return ((Number) o1).doubleValue() > ((Number) o2).doubleValue();
                case FLOAT:
                    return ((Number) o1).floatValue() > ((Number) o2).floatValue();
                case BIG_DECIMAL:
                    BigDecimal b1 = getBigDecimal(o1),
                            b2 = getBigDecimal(o2);
                    return b1.compareTo(b2) > 0;
                default:
                    int result = compareObject(o1, o2, node1, node2, ">");
                    if (result == -2) {
                        throw UnsupportedTypeException(o1, o2, node1, node2, ">");
                    } else {
                        return result > 0;
                    }
            }
        } else {
            throw valueIsNullException(o1, o2, node1, node2);
        }
    }

    // >=

    public static boolean greaterEquals(final Object o1, final Object o2, final ASTNode node1, final ASTNode node2) {
        if (o1 != null && o2 != null) {
            switch (getAluType(o1, o2)) {
                // case STRING:
                case CHAR:
                    return (o1 instanceof Number ? ((Number) o1).intValue() : (int) ((Character) o1).charValue()) >= (o2 instanceof Number ? ((Number) o2)
                            .intValue() : (int) ((Character) o2).charValue());

                case SHORT:
                case INTEGER:
                    return ((Number) o1).intValue() >= ((Number) o2).intValue();
                case LONG:
                    return ((Number) o1).longValue() >= ((Number) o2).longValue();
                case DOUBLE:
                    return ((Number) o1).doubleValue() >= ((Number) o2).doubleValue();
                case FLOAT:
                    return ((Number) o1).floatValue() >= ((Number) o2).floatValue();
                case BIG_DECIMAL:
                    BigDecimal b1 = getBigDecimal(o1),
                            b2 = getBigDecimal(o2);
                    return b1.compareTo(b2) >= 0;
                default:
                    int result = compareObject(o1, o2, node1, node2, ">=");
                    if (result == -2) {
                        throw UnsupportedTypeException(o1, o2, node1, node2, ">=");
                    } else {
                        return result >= 0;
                    }
            }
        } else {
            throw valueIsNullException(o1, o2, node1, node2);
        }
    }

    // <

    public static boolean less(final Object o1, final Object o2, final ASTNode node1, final ASTNode node2) {
        if (o1 != null && o2 != null) {
            switch (getAluType(o1, o2)) {
                // case STRING:
                case CHAR:
                    return (o1 instanceof Number ? ((Number) o1).intValue() : (int) ((Character) o1).charValue()) < (o2 instanceof Number ? ((Number) o2)
                            .intValue() : (int) ((Character) o2).charValue());

                case SHORT:
                case INTEGER:
                    return ((Number) o1).intValue() < ((Number) o2).intValue();
                case LONG:
                    return ((Number) o1).longValue() < ((Number) o2).longValue();
                case DOUBLE:
                    return ((Number) o1).doubleValue() < ((Number) o2).doubleValue();
                case FLOAT:
                    return ((Number) o1).floatValue() < ((Number) o2).floatValue();
                case BIG_DECIMAL:
                    BigDecimal b1 = getBigDecimal(o1),
                            b2 = getBigDecimal(o2);
                    return b1.compareTo(b2) < 0;
                default:
                    int result = compareObject(o1, o2, node1, node2, "<");
                    if (result == -2) {
                        throw UnsupportedTypeException(o1, o2, node1, node2, "<");
                    } else {
                        return result < 0;
                    }
            }
        } else {
            throw valueIsNullException(o1, o2, node1, node2);
        }
    }

    // <=

    public static boolean lessEquals(final Object o1, final Object o2, final ASTNode node1, final ASTNode node2) {
        if (o1 != null && o2 != null) {
            switch (getAluType(o1, o2)) {
                // case STRING:
                case CHAR:
                    return (o1 instanceof Number ? ((Number) o1).intValue() : (int) ((Character) o1).charValue()) <= (o2 instanceof Number ? ((Number) o2)
                            .intValue() : (int) ((Character) o2).charValue());

                case SHORT:
                case INTEGER:
                    return ((Number) o1).intValue() <= ((Number) o2).intValue();
                case LONG:
                    return ((Number) o1).longValue() <= ((Number) o2).longValue();
                case DOUBLE:
                    return ((Number) o1).doubleValue() <= ((Number) o2).doubleValue();
                case FLOAT:
                    return ((Number) o1).floatValue() <= ((Number) o2).floatValue();
                case BIG_DECIMAL:
                    BigDecimal b1 = getBigDecimal(o1),
                            b2 = getBigDecimal(o2);
                    return b1.compareTo(b2) <= 0;
                default:
                    int result = compareObject(o1, o2, node1, node2, "<=");
                    if (result == -2) {
                        throw UnsupportedTypeException(o1, o2, node1, node2, "<=");
                    } else {
                        return result <= 0;
                    }
            }
        } else {
            throw valueIsNullException(o1, o2, node1, node2);
        }
    }

    // *******************

    private static int compareObject(Object a, Object b, final ASTNode node1, final ASTNode node2, String type) {
        if (a instanceof Comparable && b instanceof Comparable) {
            Comparable ac = (Comparable) a;
            try {
                int result = ac.compareTo(b);
                if (result > 0) {
                    return 1;
                } else if (result < 0) {
                    return -1;
                }
                return result;
            } catch (RuntimeException e) {
                throw new BeetlException(BeetlException.EXPRESSION_NOT_COMPATIBLE, e)
                        .pushToken(GrammarToken.createToken(node1.token.text + " " + node2.token.text,
                                node1.token.line));
            }
        } else {
            return -2;
        }
    }

    private static RuntimeException UnsupportedTypeException(final Object o1, final Object o2, final ASTNode node1,
                                                             final ASTNode node2, String type) {
        String tagName = node1.token.text + type + node2.token.text;
        throw new BeetlException(BeetlException.EXPRESSION_NOT_COMPATIBLE, o1.getClass() + type + o2.getClass())
                .pushToken(GrammarToken.createToken(tagName, node1.token.line));
    }

    private static RuntimeException numberExpectedException(final Object o1, ASTNode node1) {
        throw new BeetlException(BeetlException.NUMBER_EXPECTED_ERROR).pushToken(node1.token);
    }

    private static RuntimeException valueIsNullException(final Object o1, ASTNode node1) {
        throw new BeetlException(BeetlException.NULL).pushToken(node1.token);
    }

    private static BeetlException valueIsNullException(final Object o1, final Object o2,
                                                       final ASTNode node1, final ASTNode node2) {
        throw o1 == null
                ? new BeetlException(BeetlException.NULL).pushToken(node1.token)
                : new BeetlException(BeetlException.NULL).pushToken(node2.token);
    }

    private static BigDecimal getBigDecimal(Object o) {
        return o instanceof BigDecimal ? (BigDecimal) o : new BigDecimal(String.valueOf(o));
    }

    /**
     *
     */
    public static Boolean isTrue(final Object o, ASTNode node) {
        if (o == null) {
            throw new BeetlException(BeetlException.NULL).pushToken(node.token);
        }

        if (Boolean.TRUE == o) {
            return Boolean.TRUE;
        } else if (Boolean.FALSE == o) {
            return Boolean.FALSE;
        } else if (o instanceof Boolean) {
            return ((Boolean) o);
        } else {
            throw new BeetlException(BeetlException.BOOLEAN_EXPECTED_ERROR, o.getClass().toString())
                    .pushToken(node.token);
        }
    }
}
