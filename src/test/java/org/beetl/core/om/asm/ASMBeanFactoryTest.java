package org.beetl.core.om.asm;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.beetl.core.BasicTestCase;
import org.objectweb.asm.tree.FieldNode;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;


public class ASMBeanFactoryTest extends BasicTestCase {

	static User user = new User();
	static {
		user.setName("shaozuo");
		user.setAddress("北京");
		user.setNumbers(15);
		user.setBirthDate(new Date());
		user.setAge((short) 12);
		user.setDistance(44L);
		user.setFlag((byte) 1);
		user.setIsManager(false);
		user.setHeight(1.73F);
		user.setGender('M');
		user.setAaAa(12);
		user.setAaBB(13);
	}

	@Test
	public void testAttrByAsm() throws Exception {

		ClassDescription classDescription = BeanEnhanceUtils.getClassDescription(User.class);
		ASMBeanFactory asmBeanFactory = new ASMBeanFactory();
		asmBeanFactory.setUsePropertyDescriptor(false);
		for (List<FieldNode> nodes : classDescription.fieldMap.values()) {
			for (FieldNode node : nodes) {
				System.out.println(node.name + ":" + asmBeanFactory.value(user, node.name));
				AssertJUnit.assertEquals(getValue(user, node.name), asmBeanFactory.value(user, node.name));
			}
		}
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "填写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "填"));
	}

	private static Object getValue(User user, String attrName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = user.getClass().getDeclaredField(attrName);
		field.setAccessible(true);
		return field.get(user);
	}

	@Test
	public void testByProp() throws Exception {
		ASMBeanFactory asmBeanFactory = new ASMBeanFactory();
		ClassDescription classDescription = BeanEnhanceUtils.getClassDescription(User.class);
		for (List<PropertyDescriptor> propDescs : classDescription.propertyMap.values()) {
			for (PropertyDescriptor propDesc : propDescs) {
				System.out.println(propDesc.getName() + ":" + asmBeanFactory.value(user, propDesc.getName()));
				AssertJUnit.assertEquals(getValue(user, propDesc), asmBeanFactory.value(user, propDesc.getName()));
			}
		}
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "填写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "填"));
	}


	private static Object getValue(User user, PropertyDescriptor propDesc)
			throws InvocationTargetException, IllegalAccessException, IllegalArgumentException {
		return propDesc.getReadMethod().invoke(user);
	}


}
