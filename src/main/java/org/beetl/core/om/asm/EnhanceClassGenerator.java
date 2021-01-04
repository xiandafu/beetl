package org.beetl.core.om.asm;

import java.util.List;

import org.beetl.ow2.asm.ClassWriter;
import org.beetl.ow2.asm.Label;
import org.beetl.ow2.asm.MethodVisitor;
import org.beetl.ow2.asm.Opcodes;

/**
 * 通过ASM生成{@link org.beetl.core.om.AttributeAccess}的子类
 *
 * <pre>
 *     假设 Bean 的类名为 User，则为生成的类为：User$AttributeAccess
 *     并为其实现 {@link org.beetl.core.om.AttributeAccess#value(Object, Object)} 方法
 *     以下是三种情况，以及示例
 *
 *          // (1) Bean 中没有 get(String) 或 get(Object) 方法
 *          public Object value(Object bean, Object attr) {
 *              String attrStr = attr.toString();
 *              int hash = attrStr.hashCode();
 *              User user = (User) bean;
 *              switch (hash) {
 *                  case 1:
 *                      return user.getName();
 *                  case 2:
 *                      return user.getAddress();
 *                  case 3:
 *                      if("numbers".equals(attrStr)){
 *                          return user.getNumbers();
 *                      }
 *                      if("birthDate".equals(attrStr)){
 *                          return user.getBirthDate();
 *                      }
 *              }
 *              throw new BeetlException(BeetlException.ATTRIBUTE_NOT_FOUND, "attribute : " + attrStr);
 *          }
 *
 *          // (2) Bean中有 get(String) 方法
 *          public Object value(Object bean, Object attr) {
 *              String attrStr = attr.toString();
 *              int hash = attrStr.hashCode();
 *              User user = (User) bean;
 *              switch (hash) {
 *                  case 1:
 *                      return user.getName();
 *                  case 2:
 *                      return user.getAddress();
 *                  case 3:
 *                      if("numbers".equals(attrStr)){
 *                          return user.getNumbers();
 *                      }
 *                      if("birthDate".equals(attrStr)){
 *                          return user.getBirthDate();
 *                      }
 *              }
 *              return user.get(attrStr);
 *          }
 *
 *          // (3) Bean中有 get(Object) 方法
 *          public Object value(Object bean, Object attr) {
 *              String attrStr = attr.toString();
 *              int hash = attrStr.hashCode();
 *              User user = (User) bean;
 *              switch (hash) {
 *                  case 1:
 *                      return user.getName();
 *                  case 2:
 *                      return user.getAddress();
 *                  case 3:
 *                      if("numbers".equals(attrStr)){
 *                          return user.getNumbers();
 *                      }
 *                      if("birthDate".equals(attrStr)){
 *                          return user.getBirthDate();
 *                      }
 *              }
 *              return user.get(attr);
 *          }
 *
 * </pre>
 *
 * @author laozhaishaozuo@foxmail.com
 */
class EnhanceClassGenerator implements Opcodes, Constants {

    /**
     * 私有构造方法
     */
    private EnhanceClassGenerator() {

    }

    /**
     * 生成beanClass对应的增强类的字节流
     */
    static byte[] generate(Class<?> beanClass) throws Exception {
        return generate(beanClass, true);
    }

    /**
     * 生成beanClass对应的增强类的字节流
     *
     * @param usePropertyDescriptor 是否使{@link java.beans.PropertyDescriptor}来生成属性描述
     */
    static byte[] generate(Class<?> beanClass, boolean usePropertyDescriptor) throws Exception {
        return generate(beanClass, InternalName.ATTRIBUTE_ACCESS, null, usePropertyDescriptor);
    }

    /**
     * 生成beanClass对应的增强类的字节流
     *
     * @param superName  父类 形式如 java.lang.String
     * @param interfaces 要实现的接口 形式如 java.lang.String
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
        generateMethod(cw, beanClass, usePropertyDescriptor);
        cw.visitEnd();
        return cw.toByteArray();
    }

    /**
     * 生成默认的构造方法
     *
     * @param superName 父类名称
     */
    static void generateDefaultConstruct(ClassWriter cw, String superName) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, MethodName.CONSTRUCTOR, MethodDesc.EMPTY_VOID, null, null);
        // 生成构造方法的字节码指令
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, superName, MethodName.CONSTRUCTOR, MethodDesc.EMPTY_VOID, false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

    }

    static String createGeneratedClassName(String className) {
        return className + Constants.GEN_SEP + GEN_CLASS_SUFFIX;
    }

    static String createGeneratedClassName(Class<?> beanClass) {
        return createGeneratedClassName(beanClass.getName());
    }

    /**
     * 生成方法 方法
     */
    private static void generateMethod(ClassWriter cw, Class<?> beanClass, boolean usePropertyDescriptor) {
        String internalClassName = BeanEnhanceUtils.getInternalName(beanClass.getName());
        ClassDesc classDesc = BeanEnhanceUtils.getClassDescription(beanClass, usePropertyDescriptor);
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, MethodName.VALUE, MethodDesc.VALUE, null, null);
        mv.visitCode();

        // 有属性，需要调用 getter 方法
        if (classDesc.hasField) {
            generateMethodWithFields(internalClassName, classDesc, mv);
        } else {
            generateMethodWithNoField(mv, classDesc, internalClassName);
        }

        mv.visitEnd();
    }

    /**
     * 为字段生成相应的方法
     *
     * @param internalClassName 内部名
     * @param classDesc  类描述
     * @param mv                asm 方法访问者
     */
    private static void generateMethodWithFields(String internalClassName, ClassDesc classDesc,
                                                 MethodVisitor mv) {
        Label toStringLabel = new Label();
        mv.visitLabel(toStringLabel);
        mv.visitVarInsn(ALOAD, VarIndex.PARAM_ATTR);
        mv.visitMethodInsn(INVOKEVIRTUAL, InternalName.OBJECT,
                MethodName.TO_STRING, MethodDesc.TO_STRING, false);
        mv.visitVarInsn(ASTORE, VarIndex.LOCAL_ATTR_STRING);// 对应attrName的toString变量

        Label hashCodeLabel = new Label();
        mv.visitLabel(hashCodeLabel);
        mv.visitVarInsn(ALOAD, VarIndex.LOCAL_ATTR_STRING);// toString变量
        mv.visitMethodInsn(INVOKEVIRTUAL, InternalName.OBJECT, MethodName.HASH_CODE, MethodDesc.EMPTY_INT, false);
        mv.visitVarInsn(ISTORE, VarIndex.LOCAL_HASH_CODE);// hashCode

        Label castLabel = new Label();
        mv.visitLabel(castLabel);
        mv.visitVarInsn(ALOAD, VarIndex.PARAM_BEAN);// 参数 bean
        mv.visitTypeInsn(CHECKCAST, internalClassName);
        mv.visitVarInsn(ASTORE, VarIndex.LOCAL_INTERNAL_CLASS);// 对应internalClassName类型的变量

        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitVarInsn(ILOAD, VarIndex.LOCAL_HASH_CODE);
        Label[] lookupSwitchLabels = new Label[classDesc.fieldDescMap.size()];
        int[] hashCodes = BeanEnhanceUtils
                .convertIntegerToPrimitiveType(classDesc.fieldDescMap.keySet().toArray(new Integer[0]));
        for (int i = 0; i < lookupSwitchLabels.length; i++) {
            lookupSwitchLabels[i] = new Label();
        }
        Label df = new Label();
        mv.visitLookupSwitchInsn(df, hashCodes, lookupSwitchLabels);
        List<FieldDesc> fieldDescs = null;
        FieldDesc curFieldDesc = null;
        for (int i = 0; i < lookupSwitchLabels.length; i++) {
            fieldDescs = classDesc.fieldDescMap.get(hashCodes[i]);
            mv.visitLabel(lookupSwitchLabels[i]);
            if (i == 0) {
                Object[] locals = new Object[]{InternalName.STRING, Opcodes.INTEGER, internalClassName};
                mv.visitFrame(Opcodes.F_APPEND, locals.length, locals, 0, null);
            } else {
                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            }
            if (fieldDescs.size() == 1) {
                curFieldDesc = fieldDescs.get(0);
                mv.visitVarInsn(ALOAD, VarIndex.LOCAL_INTERNAL_CLASS);// 对应internalClassName类型的变量
                mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, curFieldDesc.readMethodName,
                        curFieldDesc.readMethodDesc, false);
                addInvokeValueOfToPrimitive(mv, curFieldDesc.desc);
                mv.visitInsn(ARETURN);
            } else {
                handleSameHashAttr(classDesc, mv, fieldDescs, internalClassName, df);
            }

        }
        if (classDesc.generalGetMethodDesc != null && InternalName.OBJECT
                .equals(classDesc.generalGetMethodDesc.parameterInternalName)) {
            // 是否有get(Object)方法
            mv.visitLabel(df);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitVarInsn(ALOAD, VarIndex.LOCAL_INTERNAL_CLASS);
            mv.visitVarInsn(ALOAD, VarIndex.PARAM_ATTR);
            mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, MethodName.GET,
                    classDesc.generalGetMethodDesc.desc, false);
            mv.visitInsn(ARETURN);
        } else if (classDesc.generalGetMethodDesc != null && InternalName.STRING
                .equals(classDesc.generalGetMethodDesc.parameterInternalName)) {
            // 是否有get(String)方法
            mv.visitLabel(df);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitVarInsn(ALOAD, VarIndex.LOCAL_INTERNAL_CLASS);
            mv.visitVarInsn(ALOAD, VarIndex.LOCAL_ATTR_STRING);
            mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, MethodName.GET,
                    classDesc.generalGetMethodDesc.desc, false);
            mv.visitInsn(ARETURN);
        } else {
            mv.visitLabel(df);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitTypeInsn(NEW, InternalName.BEETL_EXCEPTION);
            mv.visitInsn(DUP);
            mv.visitLdcInsn("ATTRIBUTE_NOT_FOUND");
            mv.visitTypeInsn(NEW, InternalName.STRING_BUILDER);
            mv.visitInsn(DUP);
            mv.visitLdcInsn("属性未找到(" + classDesc.target.getName() + ") : ");
            mv.visitMethodInsn(INVOKESPECIAL, InternalName.STRING_BUILDER, MethodName.CONSTRUCTOR,
                    MethodDesc.CONSTRUCTOR_STRING_BUILDER, false);
            mv.visitVarInsn(ALOAD, VarIndex.LOCAL_ATTR_STRING);
            mv.visitMethodInsn(INVOKEVIRTUAL, InternalName.STRING_BUILDER, MethodName.APPEND,
                    MethodDesc.APPEND_STRING_BUILDER, false);
            mv.visitMethodInsn(INVOKEVIRTUAL, InternalName.STRING_BUILDER,
                    MethodName.TO_STRING, MethodDesc.TO_STRING,
                    false);
            mv.visitMethodInsn(INVOKESPECIAL, InternalName.BEETL_EXCEPTION, MethodName.CONSTRUCTOR,
                    MethodDesc.CONSTRUCTOR_BEETL_EXCEPTION, false);
            mv.visitInsn(ATHROW);
        }
        mv.visitMaxs(1, 6);
    }

    private static void generateMethodWithNoField(MethodVisitor mv, ClassDesc classDesc,
                                                  String internalClassName) {
        Label castLabel = new Label();
        mv.visitLabel(castLabel);
        mv.visitVarInsn(ALOAD, VarIndex.PARAM_BEAN);// 参数 bean
        mv.visitTypeInsn(CHECKCAST, internalClassName);
        mv.visitVarInsn(ASTORE, 3);// 此时不需要转换String与计算hashCode，所以是第三个变量
        Label df = new Label();
        if (classDesc.generalGetMethodDesc != null && InternalName.OBJECT
                .equals(classDesc.generalGetMethodDesc.parameterInternalName)) {
            mv.visitLabel(df);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{internalClassName}, 0, null);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(ALOAD, VarIndex.PARAM_ATTR);
            mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, MethodName.GET,
                    classDesc.generalGetMethodDesc.desc, false);
            mv.visitInsn(ARETURN);
        } else if (classDesc.generalGetMethodDesc != null && InternalName.STRING
                .equals(classDesc.generalGetMethodDesc.parameterInternalName)) {
            Label toStringLabel = new Label();
            mv.visitLabel(toStringLabel);
            mv.visitVarInsn(ALOAD, VarIndex.PARAM_ATTR);
            mv.visitMethodInsn(INVOKEVIRTUAL, InternalName.OBJECT,
                    MethodName.TO_STRING, MethodDesc.TO_STRING,
                    false);
            mv.visitVarInsn(ASTORE, 4);// 对应attrName的toString变量
            mv.visitLabel(df);
            mv.visitFrame(Opcodes.F_APPEND, 2,
                    new Object[]{internalClassName, InternalName.STRING}, 0, null);
            mv.visitVarInsn(ALOAD, 3);// internalClassName
            mv.visitVarInsn(ALOAD, 4);// attrName.toString()
            mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, MethodName.GET,
                    classDesc.generalGetMethodDesc.desc, false);
            mv.visitInsn(ARETURN);
        } else {
            Label toStringLabel = new Label();
            mv.visitLabel(toStringLabel);
            mv.visitVarInsn(ALOAD, VarIndex.PARAM_ATTR);
            mv.visitMethodInsn(INVOKEVIRTUAL, InternalName.OBJECT,
                    MethodName.TO_STRING, MethodDesc.TO_STRING,
                    false);
            mv.visitVarInsn(ASTORE, 4);// 对应attrName的toString变量
            mv.visitLabel(df);

            Object[] locals = new Object[]{internalClassName, InternalName.STRING};
            mv.visitFrame(Opcodes.F_APPEND, locals.length, locals, 0, null);
            mv.visitTypeInsn(NEW, InternalName.BEETL_EXCEPTION);
            mv.visitInsn(DUP);
            mv.visitLdcInsn("ATTRIBUTE_NOT_FOUND");
            mv.visitTypeInsn(NEW, InternalName.STRING_BUILDER);
            mv.visitInsn(DUP);
            mv.visitLdcInsn("attribute : ");
            mv.visitMethodInsn(INVOKESPECIAL, InternalName.STRING_BUILDER, MethodName.CONSTRUCTOR,
                    MethodDesc.CONSTRUCTOR_STRING_BUILDER, false);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitMethodInsn(INVOKEVIRTUAL, InternalName.STRING_BUILDER, MethodName.APPEND,
                    MethodDesc.APPEND_STRING_BUILDER, false);
            mv.visitMethodInsn(INVOKEVIRTUAL, InternalName.STRING_BUILDER,
                    MethodName.TO_STRING, MethodDesc.TO_STRING,
                    false);
            mv.visitMethodInsn(INVOKESPECIAL, InternalName.BEETL_EXCEPTION, MethodName.CONSTRUCTOR,
                    MethodDesc.CONSTRUCTOR_BEETL_EXCEPTION, false);
            mv.visitInsn(ATHROW);
        }
        mv.visitMaxs(1, 4);
    }

    private static void handleSameHashAttr(ClassDesc classDesc, MethodVisitor mv,
                                           List<FieldDesc> fieldDescs, String internalClassName, Label defaultLabel) {
        int fieldSize = fieldDescs.size();
        // 用于if跳转的Label
        Label[] ifLabels = new Label[fieldSize];
        Label[] invokeLabels = new Label[fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            ifLabels[i] = new Label();
            invokeLabels[i] = new Label();
        }

        FieldDesc curFieldDesc = null;
        for (int i = 0; i < fieldSize; i++) {
            // 第一个if不需要使用Label
            if (i != 0) {
                mv.visitLabel(ifLabels[i]);
            }
            curFieldDesc = fieldDescs.get(i);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitLdcInsn(curFieldDesc.name);
            mv.visitVarInsn(ALOAD, VarIndex.LOCAL_ATTR_STRING);
            mv.visitMethodInsn(INVOKEVIRTUAL, InternalName.OBJECT, MethodName.EQUALS,
                    MethodDesc.EQUALS, false);
            // 如果不相等，跳转到下一个if判断,都不相等跳转到default defaultLabel
            mv.visitJumpInsn(IFEQ, i + 1 == fieldSize ? defaultLabel : ifLabels[i + 1]);
            mv.visitLabel(invokeLabels[i]);// 相等则调用get方法
            mv.visitVarInsn(ALOAD, VarIndex.LOCAL_INTERNAL_CLASS);
            mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, curFieldDesc.readMethodName,
                    curFieldDesc.readMethodDesc, false);
            addInvokeValueOfToPrimitive(mv, curFieldDesc.desc);
            mv.visitInsn(ARETURN);
        }

    }

    /** 用于在 {@see addInvokeValueOfToPrimitive } 方法中代替 {@code switch(returnTypeDesc)} 语句，可提高 33% 的性能 */
    private static final int HASH_CODE_INT = TypeDescriptor.INT.hashCode();
    private static final int HASH_CODE_BOOLEAN = TypeDescriptor.BOOLEAN.hashCode();
    private static final int HASH_CODE_BYTE = TypeDescriptor.BYTE.hashCode();
    private static final int HASH_CODE_CHAR = TypeDescriptor.CHAR.hashCode();
    private static final int HASH_CODE_SHORT = TypeDescriptor.SHORT.hashCode();
    private static final int HASH_CODE_DOUBLE = TypeDescriptor.DOUBLE.hashCode();
    private static final int HASH_CODE_FLOAT = TypeDescriptor.FLOAT.hashCode();
    private static final int HASH_CODE_LONG = TypeDescriptor.LONG.hashCode();

    /**
     * 根据指定返回类型描述，添加 static 修饰的 'valueOf' 方法
     *
     * @param mv             asm 方法访问者，执行 {@code invokestatic owner name desc} 字节指令
     * @param returnTypeDesc 返回类型描述 {@see Constants.TypeDescriptor}
     */
    private static void addInvokeValueOfToPrimitive(MethodVisitor mv, String returnTypeDesc) {
        int opcode = INVOKESTATIC; // static 方法调用
        String methodName = MethodName.VALUE_OF; // 方法名为 valueOf
        int hashCode = returnTypeDesc.hashCode();
        if (hashCode == HASH_CODE_INT && TypeDescriptor.INT.equals(returnTypeDesc)) {
            mv.visitMethodInsn(opcode, InternalName.INTEGER, methodName, MethodDesc.VALUE_OF_INTEGER, false);
        } else if (hashCode == HASH_CODE_BOOLEAN && TypeDescriptor.BOOLEAN.equals(returnTypeDesc)) {
            mv.visitMethodInsn(opcode, InternalName.BOOLEAN, methodName, MethodDesc.VALUE_OF_BOOLEAN, false);
        } else if (hashCode == HASH_CODE_BYTE && TypeDescriptor.BYTE.equals(returnTypeDesc)) {
            mv.visitMethodInsn(opcode, InternalName.BYTE, methodName, MethodDesc.VALUE_OF_BYTE, false);
        } else if (hashCode == HASH_CODE_CHAR && TypeDescriptor.CHAR.equals(returnTypeDesc)) {
            mv.visitMethodInsn(opcode, InternalName.CHARACTER, methodName, MethodDesc.VALUE_OF_CHARACTER, false);
        } else if (hashCode == HASH_CODE_SHORT && TypeDescriptor.SHORT.equals(returnTypeDesc)) {
            mv.visitMethodInsn(opcode, InternalName.SHORT, methodName, MethodDesc.VALUE_OF_SHORT, false);
        } else if (hashCode == HASH_CODE_DOUBLE && TypeDescriptor.DOUBLE.equals(returnTypeDesc)) {
            mv.visitMethodInsn(opcode, InternalName.DOUBLE, methodName, MethodDesc.VALUE_OF_DOUBLE, false);
        } else if (hashCode == HASH_CODE_FLOAT && TypeDescriptor.FLOAT.equals(returnTypeDesc)) {
            mv.visitMethodInsn(opcode, InternalName.FLOAT, methodName, MethodDesc.VALUE_OF_FLOAT, false);
        } else if (hashCode == HASH_CODE_LONG && TypeDescriptor.LONG.equals(returnTypeDesc)) {
            mv.visitMethodInsn(opcode, InternalName.LONG, methodName, MethodDesc.VALUE_OF_LONG, false);
        }
    }

}
