package org.beetl.core.om.asm;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beetl.core.exception.BeetlException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

/**
 * 
 * 工具类
 * @author laozhaishaozuo@foxmail.com
 *
 */
final class BeanEnhanceUtils {

	private BeanEnhanceUtils() {

	}

	private static Set<String> ignoreSet = new HashSet<>();
	static {
		ignoreSet.add("getClass");// 避免获取到java.lang.Object.getClass()方法
	}


	/**
	 * 注意，使用propertyDescriptor获取的属性，与字段名称可能不一致
	 * @param beanClass
	 * @param usePropertyDescriptor
	 * @return
	 */
	static ClassDescription getClassDescription(Class<?> beanClass, boolean usePropertyDescriptor) {
		ClassDescription classDescription = new ClassDescription();
		InputStream in = null;
		try {
			setPropertyDescriptors(classDescription, beanClass);
			if (usePropertyDescriptor) {
				buildFieldDescMapByProperty(classDescription);
			} else {
				in = beanClass.getClassLoader().getResourceAsStream(getInternalName(beanClass.getName()) + ".class");
				ClassReader reader = new ClassReader(in);
				ClassNode cn = new ClassNode();
				reader.accept(cn, 0);
				buildFieldDescMapByAsm(classDescription, cn);
			}
			classDescription.target = beanClass;
			classDescription.generalGetType = checkGenreal(beanClass);
			classDescription.hasField = !classDescription.fieldDescMap.isEmpty();
		} catch (IOException | IntrospectionException e) {
			throw new BeetlException(BeetlException.ERROR, "ASM增强功能，生成类:" + beanClass.getName() + "时发生错误", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ioe) {
				// ignore
			}
		}
		return classDescription;
	}

	private static void setPropertyDescriptors(ClassDescription classDescription, Class<?> beanClass)
			throws IntrospectionException {
		PropertyDescriptor[] propDescriptors = Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
		List<PropertyDescriptor> propList = new ArrayList<>(propDescriptors.length);
		propList.addAll(Arrays.asList(propDescriptors));
		classDescription.propertyDescriptors = propList;
	}


	private static void buildFieldDescMapByProperty(ClassDescription classDescription) {
		buildFieldDescMap(classDescription, converPropToFieldDesc(classDescription.propertyDescriptors));
	}

	/**
	 * 构建 FieldDescription
	 * @param propList
	 * @return
	 */
	private static List<FieldDescription> converPropToFieldDesc(List<PropertyDescriptor> propList) {
		List<FieldDescription> fieldDescs = new ArrayList<>(propList.size() * 2);
		FieldDescription fieldDesc = null;
		Method curPropReadMethod = null;
		for (PropertyDescriptor prop : propList) {
			curPropReadMethod = prop.getReadMethod();
			if (curPropReadMethod != null && !ignoreSet.contains(curPropReadMethod.getName())) {
				fieldDesc = new FieldDescription();
				fieldDesc.name = prop.getName();
				fieldDesc.desc = Type.getType(curPropReadMethod.getReturnType()).toString();
				fieldDesc.readMethodName = curPropReadMethod.getName();
				fieldDesc.readMethodDesc = getMethodDesc(curPropReadMethod);
				fieldDescs.add(fieldDesc);
				// 2.x兼容,{@see ObjectUtil#getInvokder}
				if (prop.getPropertyType() == Boolean.class || prop.getPropertyType() == boolean.class) {
					// 再生成一个FieldDescription
					fieldDescs.add(getBooleanFieldDescription(prop));
				}

			}
		}
		return fieldDescs;
	}


	private static FieldDescription getBooleanFieldDescription(PropertyDescriptor prop) {
		Method curPropReadMethod = prop.getReadMethod();
		String name = curPropReadMethod.getName();
		FieldDescription booleanDesc = new FieldDescription();
		booleanDesc.name = name;
		booleanDesc.desc = Type.getType(curPropReadMethod.getReturnType()).toString();
		booleanDesc.readMethodName = curPropReadMethod.getName();
		booleanDesc.readMethodDesc = getMethodDesc(curPropReadMethod);
		return booleanDesc;
	}


	private static void buildFieldDescMap(ClassDescription classDescription, List<FieldDescription> allFieldDescs) {
		// 先对其按照hashCode进行排序，方便后续生产代码
		allFieldDescs.sort((p1, p2) -> Integer.compare(p1.name.hashCode(), p2.name.hashCode()));


		Map<Integer, List<FieldDescription>> filedDescMap = new LinkedHashMap<>();
		int hashCode = 0;
		List<FieldDescription> filedDescs = null;
		for (FieldDescription fieldDesc : allFieldDescs) {
			hashCode = fieldDesc.name.hashCode();
			filedDescs = filedDescMap.get(hashCode);
			if (filedDescs == null) {
				filedDescs = new ArrayList<>();
			}
			filedDescs.add(fieldDesc);
			filedDescMap.put(hashCode, filedDescs);
		}
		classDescription.fieldDescMap = filedDescMap;
	}


	private static String getMethodDesc(Method readMethod) {
		String descriptor = Type.getMethodDescriptor(readMethod);
		return descriptor.substring(descriptor.indexOf(PunctuationConstants.LEFT_BRACKET));
	}

	private static void buildFieldDescMapByAsm(ClassDescription classDescription, ClassNode cn) {
		@SuppressWarnings("unchecked")
		List<FieldNode> fieldList = cn.fields;

		List<FieldDescription> allFiledDescs = convertFieldNodeToFieldDesc(classDescription, fieldList);
		buildFieldDescMap(classDescription, allFiledDescs);
	}

	private static List<FieldDescription> convertFieldNodeToFieldDesc(ClassDescription classDescription,
			List<FieldNode> fieldList) {
		List<FieldDescription> fieldDescs = new ArrayList<>(fieldList.size() * 2);
		FieldDescription filedDesc = null;
		for (FieldNode fieldNode : fieldList) {
			filedDesc = new FieldDescription(fieldNode.name, fieldNode.desc,
					createGetterMethodName(classDescription, fieldNode.name), "()" + fieldNode.desc);
			fieldDescs.add(filedDesc);
			if (TypeDescriptorConstants.BOOLEAN_.equals(filedDesc.desc) && filedDesc.name.startsWith("is")) {
				fieldDescs.add(getBooleanFieldDescription(filedDesc));
			}
		}
		return fieldDescs;
	}

	private static FieldDescription getBooleanFieldDescription(FieldDescription curFiledDesc) {
		FieldDescription booleanDesc = new FieldDescription();
		String name = curFiledDesc.name.substring(2);
		booleanDesc.name = name.substring(0, 1).toLowerCase() + name.substring(1);
		booleanDesc.desc = curFiledDesc.desc;
		booleanDesc.readMethodName = curFiledDesc.readMethodName;
		booleanDesc.readMethodDesc = curFiledDesc.readMethodDesc;
		return booleanDesc;
	}

	/**
	 *
	 * @param target
	 * @return
	 */
	private static int checkGenreal(Class<?> target) {
		try {
			Method m = target.getMethod("get", new Class[]{java.lang.Object.class});
			return 1;
		} catch (Exception ex) {
			// ingnore
		}

		try {
			Method m = target.getMethod("get", new Class[]{java.lang.String.class});
			return 2;
		} catch (Exception ex) {
			// ingnore
		}


		return 0;
	}

	private static String createGetterMethodName(ClassDescription classDescription, String propertyName) {
		for (PropertyDescriptor ps : classDescription.propertyDescriptors) {
			if (ps.getName().equals(propertyName)) {
				return ps.getReadMethod().getName();
			}

			if (propertyName.startsWith("is") && ps.getReadMethod().getName().equals(propertyName)) {
				return ps.getReadMethod().getName();
			}
		}
		throw new IllegalStateException("找不到Getter方法 " + propertyName);
	}

	static String getSimpleClassName(String className) {
		return className.substring(className.lastIndexOf(PunctuationConstants.PERIOD) + 1);
	}


	static String getInternalName(final String className) {
		return className.replace(PunctuationConstants.PERIOD_CHAR, PunctuationConstants.SLASH_CHAR);
	}

	static int[] convertIntegerToPrimitiveType(Integer[] source) {
		int[] target = new int[source.length];
		for (int i = 0; i < source.length; i++) {
			target[i] = source[i];
		}
		return target;
	}

}
