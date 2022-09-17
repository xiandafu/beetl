package org.beetl.core.om.asm;

import java.util.List;

import org.beetl.core.BasicTestCase;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.fun.ObjectUtil;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class AsmBeanFactoryTest extends BasicTestCase {

	static User user = new User();
	static {
		// user.setName("shaozuo");
		// user.setAddress("北京");
		// user.setNumbers(15);
		// user.setBirthDate(new Date());
		// user.setAge((short) 12);
		// user.setDistance(44L);
		// user.setFlag((byte) 1);
		user.setManager(true);
		// user.setHeight(1.73F);
		// user.setGender('M');
		// user.setAaAa(12);
		// user.setAaBB(13);
	}

	@Test
	public void testAttrByAsm() throws Exception {
		boolean usePropertyDescriptor = false;
		ClassDesc classDesc = BeanEnhanceUtils.getClassDescription(User.class, usePropertyDescriptor);
		AsmBeanFactory asmBeanFactory = new AsmBeanFactory();
		asmBeanFactory.setUsePropertyDescriptor(usePropertyDescriptor);
		for (List<FieldDesc> nodes : classDesc.fieldDescMap.values()) {
			for (FieldDesc node : nodes) {
				System.out.println(node.name + ":" + asmBeanFactory.value(user, node.name));
				AssertJUnit.assertEquals(ObjectUtil.getInvokder(User.class, node.name).get(user),
						asmBeanFactory.value(user, node.name));
			}
		}
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "填写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "填"));
	}

	@Test
	public void testByProp() throws Exception {
		ClassDesc classDesc = BeanEnhanceUtils.getClassDescription(User.class, true);
		AsmBeanFactory asmBeanFactory = new AsmBeanFactory();
		asmBeanFactory.setUsePropertyDescriptor(true);
		for (List<FieldDesc> nodes : classDesc.fieldDescMap.values()) {
			for (FieldDesc node : nodes) {
				System.out.println(node.name + ":" + node.name.hashCode());
				System.out.println(node.name + ":" + asmBeanFactory.value(user, node.name));
				AssertJUnit.assertEquals(ObjectUtil.getInvokder(User.class, node.name).get(user),
						asmBeanFactory.value(user, node.name));
			}
		}
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "填写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(user, "填"));
	}

	@Test
	public void testOnlyGet() throws Exception {
		OnlyGet onlyGet = new OnlyGet();
		AsmBeanFactory asmBeanFactory = new AsmBeanFactory();
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(onlyGet, "填写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(onlyGet, "写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(onlyGet, "填"));
	}

	@Test
	public void testOnlyGetString() throws Exception {
		OnlyGetString onlyGetStr = new OnlyGetString();
		AsmBeanFactory asmBeanFactory = new AsmBeanFactory();
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(onlyGetStr, "填写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(onlyGetStr, "写"));
		AssertJUnit.assertEquals("哈哈是", asmBeanFactory.value(onlyGetStr, "cs"));
	}

	@Test
	public void testEmpty() throws Exception {
		String name = "zhangsan";
		TestObject empty = new TestObject(name);
		AsmBeanFactory asmBeanFactory = new AsmBeanFactory();
		try {
			asmBeanFactory.value(empty, "ss");
			AssertJUnit.fail();
		} catch (BeetlException e) {
			AssertJUnit.assertEquals(BeetlException.ATTRIBUTE_NOT_FOUND, e.detailCode);
			AssertJUnit.assertTrue(e.getMessage().contains("ss"));
		}
		AssertJUnit.assertEquals(name, asmBeanFactory.value(empty, "name"));
	}

}
