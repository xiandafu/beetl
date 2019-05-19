package org.beetl.core.om.asm;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;


/**
 * 通过ASM生成{@link org.beetl.core.om.AttributeAccess}的子类，
 * 假设类名称User，则为生成的类为：User$AttributeAccess
 * 实现   {@link org.beetl.core.om.AttributeAccess#value(Object, Object)}方法。
 *<p/>
 * <h3>Bean中没有get(String)或get(Object)方法:</h3> 
 * <pre>
   public Object value(Object bean, Object attr) {
   		String attrStr = attr.toString();
        int hash = attrStr.hashCode();
        User user = (User) bean;
        switch (hash) {
        case 1:
            return user.getName();
        case 2:
            return user.getAddress();
        case 3:
        	if("numbers".equals(attrStr)){
        		return user.getNumbers();
        	}
        	if("birthDate".equals(attrStr)){
        	 	return user.getBirthDate();
        	}
        }
        return null;        
    }
 *</pre>
 * <h3>Bean中有get(String)方法:</h3> 
 * <pre>
   public Object value(Object bean, Object attr) {
   		String attrStr = attr.toString();
        int hash = attrStr.hashCode();
        User user = (User) bean;
        switch (hash) {
        case 1:
            return user.getName();
        case 2:
            return user.getAddress();
        case 3:
        	if("numbers".equals(attrStr)){
        		return user.getNumbers();
        	}
        	if("birthDate".equals(attrStr)){
        	 	return user.getBirthDate();
        	}
        }
        return user.get(attrStr);        
    }
 *</pre>
 *</pre>
 * <h3>Bean中有get(Object)方法:</h3> 
 * <pre>
   public Object value(Object bean, Object attr) {
   		String attrStr = attr.toString();
        int hash = attrStr.hashCode();
        User user = (User) bean;
        switch (hash) {
        case 1:
            return user.getName();
        case 2:
            return user.getAddress();
        case 3:
        	if("numbers".equals(attrStr)){
        		return user.getNumbers();
        	}
        	if("birthDate".equals(attrStr)){
        	 	return user.getBirthDate();
        	}
        }
        return user.get(attr);        
    }
 *</pre>
 * @author laozhaishaozuo@foxmail.com
 *
 */
class EnhanceClassGenerator implements Opcodes {

	/**
	 * 实例方法this变量位置
	 */
	private static final int VAR_THIS_INDEX = 0;
	/**
	 * 方法中第一个参数的位置
	 */
	private static final int VAR_BEAN_INDEX = 1;
	/**
	 * 方法中第二个参数的位置
	 */
	private static final int VAR_ATTR_INDEX = 2;

	/**
	 * 本地变量对应attrName的toString变量
	 */
	private static final int LOCAL_VAR_ATTR_STRING_INDEX = 3;

	/**
	 * 本地变量hashCode位置
	 */
	private static final int LOCAL_VAR_HASH_CODE_INDEX = 4;
	/**
	 * 本地变量innerClass类型变量的位置
	 */
	private static final int LOCAL_VAR_INTERNAL_CLASS_INDEX = 5;

	private EnhanceClassGenerator() {

	}

	/**
	 * 生成beanClass对应的增强类的字节流
	 * 
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	static byte[] generate(Class<?> beanClass) throws Exception {
		return generate(beanClass, true);
	}

	/**
	 * 生成beanClass对应的增强类的字节流
	 * 
	 * @param beanClass
	 * @param usePropertyDescriptor
	 *            是否使{@link java.beans.PropertyDescriptor}来生成属性描述
	 * @return
	 * @throws Exception
	 */
	static byte[] generate(Class<?> beanClass, boolean usePropertyDescriptor) throws Exception {
		return generate(beanClass, BeanEnhanceConstants.SUPER_CLASS_NAME, null, usePropertyDescriptor);
	}

	/**
	 * 生成beanClass对应的增强类的字节流
	 * 
	 * @param beanClass
	 * @param superName
	 *            父类 形式如 java.lang.String
	 * @param interfaces
	 *            要实现的接口 形式如 java.lang.String
	 * @return
	 * @throws Exception
	 */
	static byte[] generate(Class<?> beanClass, String superName, String[] interfaces, boolean usePropertyDescriptor)
			throws Exception {
		String beanClassName = beanClass.getName();
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);// 自动计算maxStack
		String getterClassName = createGeneratedClassName(beanClassName);
		cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, BeanEnhanceUtils.getInternalName(getterClassName), null, superName,
				interfaces);
		// 生成默认构造函数
		generateDefaultConstruct(cw, superName);
		// 生成GetterClass
		if (usePropertyDescriptor) {
			generateMethodByPropertyDescriptory(cw, beanClass);
		} else {
			generateMethod(cw, beanClass);
		}
		cw.visitEnd();
		return cw.toByteArray();
	}

	/**
	 * 生成默认的构造方法
	 * 
	 * @param cw
	 * @param superName 父类名称
	 */
	static void generateDefaultConstruct(ClassWriter cw, String superName) {
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		// 生成构造方法的字节码指令
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, superName, "<init>", "()V", false);
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();

	}

	static String createGeneratedClassName(String className) {
		return className + "$" + BeanEnhanceConstants.GENETRATED_CLASS_SUFFIX;
	}

	static String createGeneratedClassName(Class<?> beanClass) {
		return createGeneratedClassName(beanClass.getName());
	}

	/**
	 * 生成方法 方法
	 * 
	 * @param cw
	 * @param beanClass
	 * @throws Exception
	 * @See {@link org.beetl.core.om.AttributeAccess#value(Object, Object)}
	 */
	private static void generateMethod(ClassWriter cw, Class<?> beanClass) throws Exception {
		String internalClassName = BeanEnhanceUtils.getInternalName(beanClass.getName());
		ClassDescription classDescription = BeanEnhanceUtils.getClassDescription(beanClass);
		MethodVisitor mv;
		{
			mv = cw.visitMethod(ACC_PUBLIC, BeanEnhanceConstants.METHOD_TO_GENERATE,
					BeanEnhanceConstants.METHOD_SIGNATURE, null, null);
			mv.visitCode();
			Label toStringLabel = new Label();
			mv.visitLabel(toStringLabel);
			mv.visitVarInsn(ALOAD, VAR_ATTR_INDEX);
			mv.visitMethodInsn(INVOKEVIRTUAL, BeanEnhanceConstants.OBJECT_INTERNAL_NAME, "toString",
					"()Ljava/lang/String;", false);
			mv.visitVarInsn(ASTORE, LOCAL_VAR_ATTR_STRING_INDEX);// 对应attrName的toString变量

			Label hashCodeLabel = new Label();
			mv.visitLabel(hashCodeLabel);
			mv.visitVarInsn(ALOAD, LOCAL_VAR_ATTR_STRING_INDEX);// toString变量
			mv.visitMethodInsn(INVOKEVIRTUAL, BeanEnhanceConstants.OBJECT_INTERNAL_NAME, "hashCode", "()I", false);
			mv.visitVarInsn(ISTORE, LOCAL_VAR_HASH_CODE_INDEX);// hashCode

			Label castLabel = new Label();
			mv.visitLabel(castLabel);
			mv.visitVarInsn(ALOAD, VAR_BEAN_INDEX);// 参数 bean
			mv.visitTypeInsn(CHECKCAST, internalClassName);
			mv.visitVarInsn(ASTORE, LOCAL_VAR_INTERNAL_CLASS_INDEX);// 对应internalClassName类型的变量

			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitVarInsn(ILOAD, LOCAL_VAR_HASH_CODE_INDEX);
			Label[] lookupSwitchLabels = new Label[classDescription.fieldMap.size()];
			int[] hashCodes = BeanEnhanceUtils
					.convertIntegerToPrimitiveType(classDescription.fieldMap.keySet().toArray(new Integer[1]));
			for (int i = 0; i < lookupSwitchLabels.length; i++) {
				lookupSwitchLabels[i] = new Label();
			}
			Label df = new Label();
			mv.visitLookupSwitchInsn(df, hashCodes, lookupSwitchLabels);
			List<FieldNode> fieldNodes = null;
			FieldNode curFieldNode = null;
			for (int i = 0; i < lookupSwitchLabels.length; i++) {
				fieldNodes = classDescription.fieldMap.get(hashCodes[i]);
				mv.visitLabel(lookupSwitchLabels[i]);
				if (i == 0) {
					mv.visitFrame(Opcodes.F_APPEND, 3,
							new Object[]{"java/lang/String", Opcodes.INTEGER, internalClassName}, 0, null);
				} else {
					mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				}
				if (fieldNodes.size() == 1) {
					curFieldNode = fieldNodes.get(0);
					mv.visitVarInsn(ALOAD, LOCAL_VAR_INTERNAL_CLASS_INDEX);// 对应internalClassName类型的变量
					mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName,
							BeanEnhanceUtils.createGetterMethodName(classDescription, curFieldNode.name),
							"()" + curFieldNode.desc, false);
					addInvokeValueOfToPrimitive(mv, curFieldNode.desc);
					mv.visitInsn(ARETURN);
				} else {
					handleSameHashAttr(classDescription, mv, fieldNodes, internalClassName, df);
				}

			}
			if (classDescription.generalGetType == 1) {
				mv.visitLabel(df);
				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				mv.visitVarInsn(ALOAD, LOCAL_VAR_INTERNAL_CLASS_INDEX);
				mv.visitVarInsn(ALOAD, VAR_ATTR_INDEX);
				mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, BeanEnhanceConstants.GET_METHOD_NAME,
						BeanEnhanceConstants.GET_METHOD_DESC, false);
				mv.visitInsn(ARETURN);
			} else if (classDescription.generalGetType == 2) {
				mv.visitLabel(df);
				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				mv.visitVarInsn(ALOAD, LOCAL_VAR_INTERNAL_CLASS_INDEX);
				mv.visitVarInsn(ALOAD, LOCAL_VAR_ATTR_STRING_INDEX);
				mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, BeanEnhanceConstants.GET_METHOD_NAME,
						BeanEnhanceConstants.GET_BY_STRING_METHOD_DESC, false);
				mv.visitInsn(ARETURN);
			} else {
				mv.visitLabel(df);
				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				mv.visitInsn(ACONST_NULL);
				mv.visitInsn(ARETURN);
			}
			mv.visitMaxs(1, 4);
			mv.visitEnd();
		}

	}

	private static void handleSameHashAttr(ClassDescription classDescription, MethodVisitor mv,
			List<FieldNode> fieldNodes, String internalClassName, Label defaultLabel) {
		int fieldSize = fieldNodes.size();
		// 用于if跳转的Label
		Label[] ifLabels = new Label[fieldSize];
		Label[] invokeLabels = new Label[fieldSize];
		for (int i = 0; i < fieldSize; i++) {
			ifLabels[i] = new Label();
			invokeLabels[i] = new Label();
		}

		FieldNode curFieldNode = null;
		for (int i = 0; i < fieldSize; i++) {
			// 第一个if不需要使用Label
			if (i != 0) {
				mv.visitLabel(ifLabels[i]);
			}
			curFieldNode = fieldNodes.get(i);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitLdcInsn(curFieldNode.name);
			mv.visitVarInsn(ALOAD, LOCAL_VAR_ATTR_STRING_INDEX);
			mv.visitMethodInsn(INVOKEVIRTUAL, BeanEnhanceConstants.OBJECT_INTERNAL_NAME, "equals",
					"(Ljava/lang/Object;)Z", false);
			// 如果不相等，跳转到下一个if判断,都不相等跳转到default defaultLabel
			mv.visitJumpInsn(IFEQ, i + 1 == fieldSize ? defaultLabel : ifLabels[i + 1]);
			mv.visitLabel(invokeLabels[i]);// 相等则调用get方法
			mv.visitVarInsn(ALOAD, LOCAL_VAR_INTERNAL_CLASS_INDEX);
			mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName,
					BeanEnhanceUtils.createGetterMethodName(classDescription, curFieldNode.name),
					"()" + curFieldNode.desc, false);
			addInvokeValueOfToPrimitive(mv, curFieldNode.desc);
			mv.visitInsn(ARETURN);
		}

	}

	private static void addInvokeValueOfToPrimitive(MethodVisitor mv, String returnTypeDesc) {
		switch (returnTypeDesc) {
			case TypeDescriptorConstants.INT_:
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", BeanEnhanceConstants.METHOD_VALUE_OF,
						"(I)Ljava/lang/Integer;", false);
				break;
			case TypeDescriptorConstants.BOOLEAN_:
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", BeanEnhanceConstants.METHOD_VALUE_OF,
						"(Z)Ljava/lang/Boolean;", false);
				break;
			case TypeDescriptorConstants.BYTE_:
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", BeanEnhanceConstants.METHOD_VALUE_OF,
						"(B)Ljava/lang/Byte;", false);
				break;
			case TypeDescriptorConstants.CHARACTER_:
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", BeanEnhanceConstants.METHOD_VALUE_OF,
						"(C)Ljava/lang/Character;", false);
				break;
			case TypeDescriptorConstants.SHORT_:
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", BeanEnhanceConstants.METHOD_VALUE_OF,
						"(S)Ljava/lang/Short;", false);
				break;
			case TypeDescriptorConstants.DOUBLE_:
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", BeanEnhanceConstants.METHOD_VALUE_OF,
						"(D)Ljava/lang/Double;", false);
				break;
			case TypeDescriptorConstants.FLOAT_:
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", BeanEnhanceConstants.METHOD_VALUE_OF,
						"(F)Ljava/lang/Float;", false);
				break;
			case TypeDescriptorConstants.LONG_:
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", BeanEnhanceConstants.METHOD_VALUE_OF,
						"(J)Ljava/lang/Long;", false);
				break;

			default:
				break;
		}
	}

	/**
	 * 生成方法 方法
	 * 
	 * @param cw
	 * @param beanClassName
	 * @throws Exception
	 * @See {@link org.beetl.core.om.AttributeAccess#value(Object, Object)}
	 */
	private static void generateMethodByPropertyDescriptory(ClassWriter cw, Class<?> beanClass) throws Exception {
		String internalClassName = BeanEnhanceUtils.getInternalName(beanClass.getName());
		ClassDescription classDescription = BeanEnhanceUtils.getClassDescription(beanClass);
		MethodVisitor mv;
		{
			mv = cw.visitMethod(ACC_PUBLIC, BeanEnhanceConstants.METHOD_TO_GENERATE,
					BeanEnhanceConstants.METHOD_SIGNATURE, null, null);
			mv.visitCode();
			Label toStringLabel = new Label();
			mv.visitLabel(toStringLabel);
			mv.visitVarInsn(ALOAD, VAR_ATTR_INDEX);
			mv.visitMethodInsn(INVOKEVIRTUAL, BeanEnhanceConstants.OBJECT_INTERNAL_NAME, "toString",
					"()Ljava/lang/String;", false);
			mv.visitVarInsn(ASTORE, LOCAL_VAR_ATTR_STRING_INDEX);// 对应attrName的toString变量

			Label hashCodeLabel = new Label();
			mv.visitLabel(hashCodeLabel);
			mv.visitVarInsn(ALOAD, LOCAL_VAR_ATTR_STRING_INDEX);// toString变量
			mv.visitMethodInsn(INVOKEVIRTUAL, BeanEnhanceConstants.OBJECT_INTERNAL_NAME, "hashCode", "()I", false);
			mv.visitVarInsn(ISTORE, LOCAL_VAR_HASH_CODE_INDEX);// hashCode

			Label castLabel = new Label();
			mv.visitLabel(castLabel);
			mv.visitVarInsn(ALOAD, VAR_BEAN_INDEX);// 参数 bean
			mv.visitTypeInsn(CHECKCAST, internalClassName);
			mv.visitVarInsn(ASTORE, LOCAL_VAR_INTERNAL_CLASS_INDEX);// 对应internalClassName类型的变量

			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitVarInsn(ILOAD, LOCAL_VAR_HASH_CODE_INDEX);
			Label[] lookupSwitchLabels = new Label[classDescription.propertyMap.size()];
			int[] hashCodes = BeanEnhanceUtils
					.convertIntegerToPrimitiveType(classDescription.propertyMap.keySet().toArray(new Integer[0]));
			for (int i = 0; i < lookupSwitchLabels.length; i++) {
				lookupSwitchLabels[i] = new Label();
			}
			Label df = new Label();
			mv.visitLookupSwitchInsn(df, hashCodes, lookupSwitchLabels);
			List<PropertyDescriptor> propDescriptors = null;
			PropertyDescriptor curPropDescriptor = null;
			Method curPropReadMethod = null;
			for (int i = 0; i < lookupSwitchLabels.length; i++) {
				propDescriptors = classDescription.propertyMap.get(hashCodes[i]);
				mv.visitLabel(lookupSwitchLabels[i]);
				if (i == 0) {
					mv.visitFrame(Opcodes.F_APPEND, 3,
							new Object[]{"java/lang/String", Opcodes.INTEGER, internalClassName}, 0, null);
				} else {
					mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				}
				if (propDescriptors.size() == 1) {
					curPropDescriptor = propDescriptors.get(0);
					curPropReadMethod = curPropDescriptor.getReadMethod();
					mv.visitVarInsn(ALOAD, LOCAL_VAR_INTERNAL_CLASS_INDEX);// 对应internalClassName类型的变量
					mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, curPropReadMethod.getName(),
							getMethodDesc(curPropReadMethod), false);
					addInvokeValueOfToPrimitive(mv, Type.getType(curPropReadMethod.getReturnType()).toString());
					mv.visitInsn(ARETURN);
				} else {
					handleSameHashAttrPropertyDescriptory(mv, propDescriptors, internalClassName, df);
				}

			}
			if (classDescription.generalGetType == 1) {
				mv.visitLabel(df);
				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				mv.visitVarInsn(ALOAD, LOCAL_VAR_INTERNAL_CLASS_INDEX);
				mv.visitVarInsn(ALOAD, VAR_ATTR_INDEX);
				mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, BeanEnhanceConstants.GET_METHOD_NAME,
						BeanEnhanceConstants.GET_METHOD_DESC, false);
				mv.visitInsn(ARETURN);
			} else if (classDescription.generalGetType == 2) {
				mv.visitLabel(df);
				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				mv.visitVarInsn(ALOAD, LOCAL_VAR_INTERNAL_CLASS_INDEX);
				mv.visitVarInsn(ALOAD, LOCAL_VAR_ATTR_STRING_INDEX);
				mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, BeanEnhanceConstants.GET_METHOD_NAME,
						BeanEnhanceConstants.GET_BY_STRING_METHOD_DESC, false);
				mv.visitInsn(ARETURN);
			} else {
				mv.visitLabel(df);
				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				mv.visitInsn(ACONST_NULL);
				mv.visitInsn(ARETURN);
			}
			mv.visitMaxs(1, 4);
			mv.visitEnd();
		}

	}

	private static String getMethodDesc(Method readMethod) {
		String descriptor = Type.getMethodDescriptor(readMethod);
		return descriptor.substring(descriptor.indexOf(PunctuationConstants.LEFT_BRACKET));
	}

	private static void handleSameHashAttrPropertyDescriptory(MethodVisitor mv,
			List<PropertyDescriptor> propDescriptors, String internalClassName, Label defaultLabel) {
		int propSize = propDescriptors.size();
		// 用于if跳转的Label
		Label[] ifLabels = new Label[propSize];
		Label[] invokeLabels = new Label[propSize];
		for (int i = 0; i < propSize; i++) {
			ifLabels[i] = new Label();
			invokeLabels[i] = new Label();
		}

		PropertyDescriptor curPropDescriptor = null;
		Method curPropReadMethod = null;
		for (int i = 0; i < propSize; i++) {
			// 第一个if不需要使用Label
			if (i != 0) {
				mv.visitLabel(ifLabels[i]);
			}
			curPropDescriptor = propDescriptors.get(i);
			curPropReadMethod = curPropDescriptor.getReadMethod();
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitLdcInsn(curPropDescriptor.getName());
			mv.visitVarInsn(ALOAD, LOCAL_VAR_ATTR_STRING_INDEX);
			mv.visitMethodInsn(INVOKEVIRTUAL, BeanEnhanceConstants.OBJECT_INTERNAL_NAME, "equals",
					"(Ljava/lang/Object;)Z", false);
			// 如果不相等，跳转到下一个if判断,都不相等跳转到default defaultLabel
			mv.visitJumpInsn(IFEQ, i + 1 == propSize ? defaultLabel : ifLabels[i + 1]);
			mv.visitLabel(invokeLabels[i]);// 相等则调用get方法
			mv.visitVarInsn(ALOAD, LOCAL_VAR_INTERNAL_CLASS_INDEX);
			mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, curPropReadMethod.getName(),
					getMethodDesc(curPropReadMethod), false);
			addInvokeValueOfToPrimitive(mv, Type.getType(curPropReadMethod.getReturnType()).toString());
			mv.visitInsn(ARETURN);
		}

	}

}
