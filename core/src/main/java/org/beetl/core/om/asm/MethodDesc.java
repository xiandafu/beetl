package org.beetl.core.om.asm;

/**
 * 对于public Object get(Object key)方法
 *
 * <pre>
 * MethodDesc md = new MethodDesc();
 * md.name = "get";
 * md.desc = "(Ljava/lang/Object;)Ljava/lang/Object;";
 * md.returnTypeInternalName = "java/lang/Object";
 * md.parameterInternalName = "java/lang/Object";
 * </pre>
 *
 * @author laozhaishaozuo@foxmail.com
 */
class MethodDesc {

    /** 方法名称 */
    String name;
    /** 方法描述信息，例如 {@code (Ljava/lang/Object;)Ljava/lang/Object;} */
    String desc;
    /** 方法入参的内部名，例如 {@code java/lang/Object} */
    String parameterInternalName;
    /** 返回值的内部名，例如 {@code java/lang/Object} */
    String returnTypeInternalName;

    /**
     * 构造方法
     *
     * @param name                   方法名称
     * @param desc                   方法描述信息，例如 {@code (Ljava/lang/Object;)Ljava/lang/Object;}
     * @param parameterInternalName  方法入参的内部名，例如 {@code java/lang/Object}
     * @param returnTypeInternalName 返回值的内部名，例如 {@code java/lang/Object}
     */
    MethodDesc(String name, String desc, String parameterInternalName, String returnTypeInternalName) {
        this.name = name;
        this.desc = desc;
        this.parameterInternalName = parameterInternalName;
        this.returnTypeInternalName = returnTypeInternalName;
    }
}
