package org.beetl.core.om;

import org.beetl.core.om.asm.AsmBeanFactory;

import java.lang.reflect.Modifier;

/**
 * 一个字节码增强来访问属性
 *
 * @author xiandafu
 */
public class AsmAAFactory extends DefaultAAFactory {
    /** 创建 ASM JavaBean 工厂实例时，是否使用属性描述符 */
    private static final boolean USE_PROPERTY_DESCRIPTOR = true;

    /** ASM JavaBean 工厂 */
    private final AsmBeanFactory asmBeanFactory;

    public AsmAAFactory() {
        super();
        // beetl 里主要通过JavaBean规范获取取属性,asmBeanFactory也可以直接设置通过fields来获取
        asmBeanFactory = new AsmBeanFactory(USE_PROPERTY_DESCRIPTOR);
    }

    @Override
    protected AttributeAccess registerClass(Class c) {
        if (Modifier.isPublic(c.getModifiers())) {
            AttributeAccess aa = asmBeanFactory.generateBean(c);
            classAttrs.put(c, aa);
            return aa;
        } else {
            classAttrs.put(c, this.reflectBeanAA);
            return this.reflectBeanAA;
        }

    }
}
