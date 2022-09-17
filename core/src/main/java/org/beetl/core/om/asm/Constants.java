package org.beetl.core.om.asm;

import org.intellij.lang.annotations.MagicConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface Constants {


    /**
     * 变量索引位置
     */
    @MagicConstant(intValues = {
            VarIndex.THIS,
            VarIndex.PARAM_BEAN,
            VarIndex.PARAM_ATTR,
            VarIndex.LOCAL_ATTR_STRING,
            VarIndex.LOCAL_HASH_CODE,
            VarIndex.LOCAL_INTERNAL_CLASS
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface VarIndex {
        /** 实例方法 this 变量位置 */
        int THIS = 0;
        /** 方法中第一个参数的位置 */
        int PARAM_BEAN = 1;
        /** 方法中第二个参数的位置 */
        int PARAM_ATTR = 2;
        /** 本地变量对应attrName的toString变量 */
        int LOCAL_ATTR_STRING = 3;
        /** 本地变量hashCode位置 */
        int LOCAL_HASH_CODE = 4;
        /** 本地变量innerClass类型变量的位置 */
        int LOCAL_INTERNAL_CLASS = 5;
    }

    /**
     * 字节码类型描述符
     */
    @MagicConstant(stringValues = {
            TypeDescriptor.BOOLEAN,
            TypeDescriptor.BYTE,
            TypeDescriptor.CHAR,
            TypeDescriptor.DOUBLE,
            TypeDescriptor.FLOAT,
            TypeDescriptor.DOUBLE,
            TypeDescriptor.INT,
            TypeDescriptor.LONG,
            TypeDescriptor.SHORT,
            TypeDescriptor.VOID,
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface TypeDescriptor {
        /** 字节码中的类型描述符：boolean */
        String BOOLEAN = "Z";
        /** 字节码中的类型描述符：byte */
        String BYTE = "B";
        /** 字节码中的类型描述符：char */
        String CHAR = "C";
        /** 字节码中的类型描述符：double */
        String DOUBLE = "D";
        /** 字节码中的类型描述符：float */
        String FLOAT = "F";
        /** 字节码中的类型描述符：int */
        String INT = "I";
        /** 字节码中的类型描述符：long */
        String LONG = "J";
        /** 字节码中的类型描述符：short */
        String SHORT = "S";
        /** 字节码中的类型描述符：void */
        String VOID = "V";
    }

    /**
     * 内部名
     */
    @MagicConstant(stringValues = {
            InternalName.OBJECT,
            InternalName.STRING,
            InternalName.BEETL_EXCEPTION,
            InternalName.STRING_BUILDER,
            InternalName.ATTRIBUTE_ACCESS
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface InternalName {
        /** 内部名：{@link java.lang.Object} */
        String OBJECT = "java/lang/Object";
        /** 内部名：{@link java.lang.String} */
        String STRING = "java/lang/String";
        /** 内部名：{@link java.lang.Integer} */
        String INTEGER = "java/lang/Integer";
        /** 内部名：{@link java.lang.Byte} */
        String BYTE = "java/lang/Byte";
        /** 内部名：{@link  java.lang.Boolean} */
        String BOOLEAN = "java/lang/Boolean";
        /** 内部名：{@link java.lang.Character} */
        String CHARACTER = "java.lang.Character";
        /** 内部名：{@link java.lang.Short} */
        String SHORT = "java/lang/Short";
        /** 内部名：{@link org.beetl.core.exception.BeetlException} */
        String BEETL_EXCEPTION = "org/beetl/core/exception/BeetlException";
        /** 内部名：{@link java.lang.StringBuilder} */
        String STRING_BUILDER = "java/lang/StringBuilder";
        /** 内部名：{@link org.beetl.core.om.AttributeAccess} */
        String ATTRIBUTE_ACCESS = "org/beetl/core/om/AttributeAccess";
        String DOUBLE = "java/lang/Double";
        String FLOAT = "java/lang/Float";
        String LONG = "java/lang/Long";
    }


    /**
     * 方法名
     */
    @MagicConstant(stringValues = {
            MethodName.GET,
            MethodName.IS,
            MethodName.TO_STRING,
            MethodName.VALUE,
            MethodName.VALUE_OF
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface MethodName {
        /** 方法名：get */
        String GET = "get";
        /** 方法名：get */
        String IS = "is";
        /** 方法名：value */
        String VALUE = "value";
        /** 方法名：valueOf */
        String VALUE_OF = "valueOf";

        /** 方法名：构造方法 */
        String CONSTRUCTOR = "<init>";
        /** 方法名：{@link Object#equals(Object)} */
        String EQUALS = "equals";
        /** 方法名：{@link Object#hashCode()} */
        String HASH_CODE = "hashCode";
        /** 方法名：{@link Object#toString()} */
        String TO_STRING = "toString";
        /** 方法名：{@link StringBuilder#append(Object)} */
        String APPEND = "append";
    }

    /**
     * 方法描述
     */
    @interface MethodDesc {
        /** 方法描述：{@code Integer valueOf(int)} */
        String VALUE_OF_INTEGER = "(I)Ljava/lang/Integer;";
        /** 方法描述：{@code Boolean valueOf(boolean)} */
        String VALUE_OF_BOOLEAN = "(Z)Ljava/lang/Boolean;";
        /** 方法描述：{@code Byte valueOf(byte)} */
        String VALUE_OF_BYTE = "(B)Ljava/lang/Byte;";
        /** 方法描述：{@code Character valueOf(char)} */
        String VALUE_OF_CHARACTER = "(C)Ljava/lang/Character;";
        /** 方法描述：{@code Short valueOf(short)} */
        String VALUE_OF_SHORT = "(S)Ljava/lang/Short;";
        /** 方法描述：{@code Double valueOf(double)} */
        String VALUE_OF_DOUBLE = "(D)Ljava/lang/Double;";
        /** 方法描述：{@code Float valueOf(float)} */
        String VALUE_OF_FLOAT = "(F)Ljava/lang/Float;";
        /** 方法描述：{@code Long valueOf(long)} */
        String VALUE_OF_LONG = "(J)Ljava/lang/Long;";
        /** 方法描述：{@code toString()} */
        String TO_STRING = "()Ljava/lang/String;";
        /** 方法描述：{@code StringBuilder append(String)} */
        String APPEND_STRING_BUILDER = "(Ljava/lang/String;)Ljava/lang/StringBuilder;";
        /** 方法描述：{@code StringBuilder(String)} */
        String CONSTRUCTOR_STRING_BUILDER = "(Ljava/lang/String;)V";
        /** 方法描述：{@code BeetlException(String, String)} */
        String CONSTRUCTOR_BEETL_EXCEPTION = "(Ljava/lang/String;Ljava/lang/String;)V";
        /** 方法描述：{@code void m()} */
        String EMPTY_VOID = "()V";
        /** 方法描述：{@code int m()} */
        String EMPTY_INT = "()I";
        /** 方法描述：{@link org.beetl.core.om.AttributeAccess#value(java.lang.Object, java.lang.Object)} */
        String VALUE = "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;";
        /** 方法描述：{@code boolean equals(Object)} */
        String EQUALS = "(Ljava/lang/Object;)Z";
    }

    /** 避免使用反射增加耗时 */
    String GEN_CLASS_SUFFIX = "AttributeAccess";
    /** 生成类的类名中的分隔符 */
    String GEN_SEP = "$";
}
