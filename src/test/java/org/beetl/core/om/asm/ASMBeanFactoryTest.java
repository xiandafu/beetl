package org.beetl.core.om.asm;


import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.beetl.core.BasicTestCase;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.lab.TestUser;
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
		boolean usePropertyDescriptor = false;
		ClassDescription classDescription = BeanEnhanceUtils.getClassDescription(User.class, usePropertyDescriptor);
		ASMBeanFactory asmBeanFactory = new ASMBeanFactory();
		asmBeanFactory.setUsePropertyDescriptor(usePropertyDescriptor);
		for (List<FieldDescription> nodes : classDescription.fieldDescMap.values()) {
			for (FieldDescription node : nodes) {
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
		ClassDescription classDescription = BeanEnhanceUtils.getClassDescription(User.class, true);
		for (List<FieldDescription> nodes : classDescription.fieldDescMap.values()) {
			for (FieldDescription node : nodes) {
				System.out.println(node.name + ":" + asmBeanFactory.value(user, node.name));
				AssertJUnit.assertEquals(getValue(user, node.name), asmBeanFactory.value(user, node.name));
			}
		}
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "填写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "填"));
	}


	@Test
	public void testOnlyGet() throws Exception {
		OnlyGet onlyGet = new OnlyGet();
		ASMBeanFactory asmBeanFactory = new ASMBeanFactory();
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(onlyGet, "填写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(onlyGet, "写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(onlyGet, "填"));
	}

	@Test
	public void testEmpty() throws Exception {
		String name = "zhangsan";
		TestUser empty = new TestUser(name);
		ASMBeanFactory asmBeanFactory = new ASMBeanFactory();
		asmBeanFactory.setUsePropertyDescriptor(true);
		try {
			asmBeanFactory.value(empty, "agexxddfdf");
			AssertJUnit.fail();
		} catch (BeetlException e) {
			AssertJUnit.assertEquals(BeetlException.ATTRIBUTE_NOT_FOUND, e.detailCode);
			AssertJUnit.assertTrue(e.getMessage().contains("agexxddfdf"));
		}
		AssertJUnit.assertEquals(name, asmBeanFactory.value(empty, "name"));
	}

}
