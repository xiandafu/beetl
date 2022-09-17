package org.beetl.core.om.asm;

/**
 * 字段描述信息
 */
class FieldDesc {

    /** 字段名称 */
    String name;
    /** 字段描述符 */
    String desc;
    /** getXXX */
    String readMethodName;
    /** 类似()I,(I)Ljava/lang/Integer; {@link org.beetl.ow2.asm.Type} */
    String readMethodDesc;

    /**
     * 构造方法
     *
     * @param name           字段名称
     * @param desc           描述
     * @param readMethodName getXXX 方法名称
     * @param readMethodDesc getXXX 方法描述
     */
    public FieldDesc(String name, String desc, String readMethodName, String readMethodDesc) {
        super();
        this.name = name;
        this.desc = desc;
        this.readMethodName = readMethodName;
        this.readMethodDesc = readMethodDesc;
    }

}
