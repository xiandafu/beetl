package org.beetl.core.om;

import org.beetl.core.om.asm.ASMBeanFactory;

import java.lang.reflect.Modifier;

/**
 * 一个字节码增强来访问属性
 *
 * @author xiandafu
 */
public class AsmAAFactory extends DefaultAAFactory {
    ASMBeanFactory asmBeanFactory = null;

    public AsmAAFactory() {
        super();
        //beetl 里主要通过JavaBean规范获取取属性,asmBeanFactory也可以直接设置通过fields来获取
        asmBeanFactory = new ASMBeanFactory(true);


    }

    @Override
    protected AttributeAccess registerClass(Class c) {
        if (Modifier.isPublic(c.getModifiers())) {
            AttributeAccess aa = asmBeanFactory.generateBean(c);
            classAttrs.put(c, aa);
            return aa;
        } else {
            //
            classAttrs.put(c, this.reflectBeanAA);
            return this.reflectBeanAA;
        }

    }
}
