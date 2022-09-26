package org.beetl.core.om.asm;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beetl.core.exception.BeetlException;
import org.beetl.ow2.asm.ClassReader;
import org.beetl.ow2.asm.Type;
import org.beetl.ow2.asm.tree.ClassNode;
import org.beetl.ow2.asm.tree.FieldNode;

/**
 * Bean 增强工具类
 *
 * @author laozhaishaozuo@foxmail.com
 */
final class BeanEnhanceUtils implements Constants {

    /**
     * 不可实例化
     */
    private BeanEnhanceUtils() {
    }

    /** 不获取的方法名，{@see #buildFieldDescMapByProperty} */
    private static final Set<String> IGNORE_METHOD_NAME_SET = new HashSet<>();

    static {
        IGNORE_METHOD_NAME_SET.add("getClass");// 避免获取到java.lang.Object.getClass()方法
    }

    /**
     * 注意，使用propertyDescriptor获取的属性，与字段名称可能不一致
     */
    static ClassDesc getClassDescription(Class<?> beanClass, boolean usePropertyDescriptor) {
        ClassDesc classDesc = new ClassDesc();
        InputStream in = null;
        try {
            setPropertyDescriptors(classDesc, beanClass);
            if (usePropertyDescriptor) {
                buildFieldDescMapByProperty(classDesc);
            } else {
                in = beanClass.getClassLoader().getResourceAsStream(getInternalName(beanClass.getName()) + ".class");
                ClassReader reader = new ClassReader(in);
                ClassNode cn = new ClassNode();
                reader.accept(cn, 0);
                buildFieldDescMapByAsm(classDesc, cn);
            }
            classDesc.target = beanClass;
            classDesc.generalGetMethodDesc = getGeneralGetMethodDescription(beanClass);
            classDesc.hasField = !classDesc.fieldDescMap.isEmpty();
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
        return classDesc;
    }

    private static void setPropertyDescriptors(ClassDesc classDesc, Class<?> beanClass)
            throws IntrospectionException {
        PropertyDescriptor[] propDescriptors = Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
        List<PropertyDescriptor> propList = new ArrayList<>(propDescriptors.length);
        propList.addAll(Arrays.asList(propDescriptors));
        classDesc.propertyDescriptors = propList;
    }

    private static void buildFieldDescMapByProperty(ClassDesc classDesc) {
        List<PropertyDescriptor> propList = classDesc.propertyDescriptors;

        List<FieldDesc> fieldDescs = new ArrayList<>(propList.size() << 1);
        Method curPropReadMethod;

        for (PropertyDescriptor prop : propList) {
            curPropReadMethod = prop.getReadMethod();

            if ((curPropReadMethod != null) && !IGNORE_METHOD_NAME_SET.contains(curPropReadMethod.getName())) {

                fieldDescs.add(new FieldDesc(
                        prop.getName(),
                        Type.getType(curPropReadMethod.getReturnType()).toString(),
                        curPropReadMethod.getName(),
                        getMethodDesc(curPropReadMethod)
                ));

                // 2.x兼容,{@see ObjectUtil#getInvokder}
                if (prop.getPropertyType() == Boolean.class || prop.getPropertyType() == boolean.class) {
                    fieldDescs.add(new FieldDesc(
                            curPropReadMethod.getName(),
                            Type.getType(curPropReadMethod.getReturnType()).toString(),
                            curPropReadMethod.getName(),
                            getMethodDesc(curPropReadMethod)
                    ));
                }

            }
        }

        buildFieldDescMap(classDesc, fieldDescs);
    }

    private static void buildFieldDescMap(ClassDesc classDesc, List<FieldDesc> allFieldDescs) {
        // 先对其按照hashCode进行排序，方便后续生产代码
        allFieldDescs.sort(Comparator.comparingInt(p -> p.name.hashCode()));

        Map<Integer, List<FieldDesc>> filedDescMap = new LinkedHashMap<>();
        int hashCode = 0;
        List<FieldDesc> filedDescs = null;
        for (FieldDesc fieldDesc : allFieldDescs) {
            hashCode = fieldDesc.name.hashCode();
            filedDescs = filedDescMap.get(hashCode);
            if (filedDescs == null) {
                filedDescs = new ArrayList<>();
            }
            filedDescs.add(fieldDesc);
            filedDescMap.put(hashCode, filedDescs);
        }
        classDesc.fieldDescMap = filedDescMap;
    }

    private static String getMethodDesc(Method readMethod) {
        String descriptor = Type.getMethodDescriptor(readMethod);
        return descriptor.substring(descriptor.indexOf('('));
    }

    private static void buildFieldDescMapByAsm(ClassDesc classDesc, ClassNode cn) {
        @SuppressWarnings("unchecked")
        List<FieldNode> fieldList = cn.fields;

        List<FieldDesc> allFiledDescs = convertFieldNodeToFieldDesc(classDesc, fieldList);
        buildFieldDescMap(classDesc, allFiledDescs);
    }

    private static List<FieldDesc> convertFieldNodeToFieldDesc(ClassDesc classDesc,
                                                               List<FieldNode> fieldList) {
        List<FieldDesc> fieldDescs = new ArrayList<>(fieldList.size() * 2);
        FieldDesc filedDesc = null;
        for (FieldNode fieldNode : fieldList) {
            filedDesc = new FieldDesc(
                    fieldNode.name,
                    fieldNode.desc,
                    createGetterMethodName(classDesc, fieldNode.name),
                    "()" + fieldNode.desc
            );
            fieldDescs.add(filedDesc);
            if (TypeDescriptor.BOOLEAN.equals(filedDesc.desc) && filedDesc.name.startsWith(MethodName.IS)) {
                fieldDescs.add(getBooleanFieldDescription(filedDesc));
            }
        }
        return fieldDescs;
    }

    private static FieldDesc getBooleanFieldDescription(FieldDesc curFiledDesc) {
        return new FieldDesc(
                getFieldNameFromGetterMethod(curFiledDesc.name),
                curFiledDesc.desc,
                curFiledDesc.readMethodName,
                curFiledDesc.readMethodDesc
        );
    }

    /**
     * 将 getter 方法转换为字段名称
     *
     * @param getterMethodName getXXX 方法名称
     * @return {@param getterMethodName} 对应的字段名称
     */
    private static String getFieldNameFromGetterMethod(String getterMethodName) {
        String name = getterMethodName.replaceFirst(MethodName.GET, "");
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    /**
     * 生成 getter 方法的方法描述符
     */
    private static org.beetl.core.om.asm.MethodDesc getGeneralGetMethodDescription(Class<?> target) {
        String getStr = MethodName.GET;
        try {
            Method getMethod = target.getMethod(getStr, Object.class);
            return new org.beetl.core.om.asm.MethodDesc(
                    getStr,
                    getMethodDesc(getMethod),
                    InternalName.OBJECT,
                    getInternalName(getMethod.getReturnType().getName())
            );
        } catch (Exception ignored) {
        }

        try {
            Method getMethod = target.getMethod(getStr, String.class);
            return new org.beetl.core.om.asm.MethodDesc(
                    getStr,
                    getMethodDesc(getMethod),
                    InternalName.STRING,
                    getInternalName(getMethod.getReturnType().getName())
            );
        } catch (Exception ignored) {
        }

        return null;
    }

    /**
     * 创建 getter 方法的方法名称
     *
     * @param classDesc 类描述符
     * @param propertyName     属性名
     * @return getter 方法的方法名称
     */
    private static String createGetterMethodName(ClassDesc classDesc, String propertyName) {
        for (PropertyDescriptor ps : classDesc.propertyDescriptors) {
            if (ps.getName().equals(propertyName)) {
                return ps.getReadMethod().getName();
            }

            if (propertyName.startsWith(MethodName.IS) && ps.getReadMethod().getName().equals(propertyName)) {
                return ps.getReadMethod().getName();
            }
        }
        throw new IllegalStateException("找不到Getter方法 " + propertyName);
    }

    static String getSimpleClassName(String className) {
        return className.substring(className.lastIndexOf(".") + 1);
    }

    static String getInternalName(final String className) {
        return className.replace('.', '/');
    }

    /**
     * 将 Integer 数组转换为 int 数组
     *
     * @param source Integer 数组
     * @return {@param source} 对应的 int 数组
     */
    static int[] convertIntegerToPrimitiveType(Integer[] source) {
        int[] target = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            target[i] = source[i];
        }
        return target;
    }

}
