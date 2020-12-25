package org.beetl.core.om.asm;

import org.intellij.lang.annotations.MagicConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface Constants {

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
        /** 内部名：{@link org.beetl.core.exception.BeetlException} */
        String BEETL_EXCEPTION = "org/beetl/core/exception/BeetlException";
        /** 内部名：{@link java.lang.StringBuilder} */
        String STRING_BUILDER = "java/lang/StringBuilder";
        /** 内部名：{@link org.beetl.core.om.AttributeAccess} */
        String ATTRIBUTE_ACCESS = "org/beetl/core/om/AttributeAccess";
    }

    /**
     * 方法名
     */
    @MagicConstant(stringValues = {
            MethodName.GET,
            MethodName.TO_STRING,
            MethodName.VALUE,
            MethodName.VALUE_OF
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface MethodName {
        /** 方法名：get */
        String GET = "get";
        /** 方法名：toString */
        String TO_STRING = "toString";
        /** 方法名：value */
        String VALUE = "value";
        /** 方法名：valueOf */
        String VALUE_OF = "valueOf";
    }

    /** 方法描述：{@code Object(Object, Object)} */
    String METHOD_SIGNATURE = "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;";
    /** 方法描述：{@code toString()} */
    String TO_STRING_METHOD_DESCRIPTOR = "()Ljava/lang/String;";

    /** 避免使用反射增加耗时 */
    String GENETRATED_CLASS_SUFFIX = "AttributeAccess";
}
