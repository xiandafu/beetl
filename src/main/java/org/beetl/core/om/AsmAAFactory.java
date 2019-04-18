package org.beetl.core.om;

import org.beetl.core.om.asm.ASMBeanFactory;

import java.lang.reflect.Modifier;

public class AsmAAFactory  extends DefaultAAFactory{
    public AsmAAFactory(){  
        super();

    }

    @Override
    protected  AttributeAccess registerClass(Class c) {
        if(Modifier.isPublic(c.getModifiers())){
            AttributeAccess aa = ASMBeanFactory.generateBean(c);
            classAttrs.put(c,aa);
            return aa;
        }else{
            classAttrs.put(c,this.reflectBeanAA);
            return this.reflectBeanAA;
        }

    }
}
