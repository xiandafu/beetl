package org.beetl.core.om.asm;

/**
 * 对于public Object get(Object key)方法
 *
 * <pre>
 * MethodDescription md = new MethodDescription();
 * md.name = "get";
 * md.desc = "(Ljava/lang/Object;)Ljava/lang/Object;";
 * md.returnTypeInternalName = "java/lang/Object";
 * md.parameterInternalName = "java/lang/Object";
 * </pre>
 *
 * @author laozhaishaozuo@foxmail.com
 */
class MethodDescription {

    String name;

    /**
     * 类似(Ljava/lang/Object;)Ljava/lang/Object;
     */
    String desc;
    /**
     * 类似java/lang/Object
     */
    String parameterInternalName;
    /**
     * 类似java/lang/Object
     */
    String returnTypeInternalName;

}
