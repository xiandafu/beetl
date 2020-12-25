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
 * 工具类
 *
 * @author laozhaishaozuo@foxmail.com
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
            classDescription.generalGetMethodDesc = getGeneralGetMethodDescription(beanClass);
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
        List<PropertyDescriptor> propList = classDescription.propertyDescriptors;

        List<FieldDescription> fieldDescs = new ArrayList<>(propList.size() << 1);
        Method curPropReadMethod;

        for (PropertyDescriptor prop : propList) {
            curPropReadMethod = prop.getReadMethod();

            if ((curPropReadMethod != null) && !ignoreSet.contains(curPropReadMethod.getName())) {

                fieldDescs.add(new FieldDescription(
                        prop.getName(), Type.getType(curPropReadMethod.getReturnType()).toString(),
                        curPropReadMethod.getName(), getMethodDesc(curPropReadMethod)
                ));

                // 2.x兼容,{@see ObjectUtil#getInvokder}
                if (prop.getPropertyType() == Boolean.class || prop.getPropertyType() == boolean.class) {
                    fieldDescs.add(new FieldDescription(
                            curPropReadMethod.getName(), Type.getType(curPropReadMethod.getReturnType()).toString(),
                            curPropReadMethod.getName(), getMethodDesc(curPropReadMethod)
                    ));
                }

            }
        }

        buildFieldDescMap(classDescription, fieldDescs);
    }

    private static void buildFieldDescMap(ClassDescription classDescription, List<FieldDescription> allFieldDescs) {
        // 先对其按照hashCode进行排序，方便后续生产代码
        allFieldDescs.sort(Comparator.comparingInt(p -> p.name.hashCode()));

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
        return descriptor.substring(descriptor.indexOf('('));
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
            if (Constants.TypeDescriptor.BOOLEAN.equals(filedDesc.desc) && filedDesc.name.startsWith("is")) {
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
     */
    private static MethodDescription getGeneralGetMethodDescription(Class<?> target) {
        MethodDescription md = new MethodDescription();
        md.name = Constants.MethodName.GET;
        try {
            Method getMethod = target.getMethod(md.name, new Class[]{java.lang.Object.class});
            md.parameterInternalName = Constants.InternalName.OBJECT;
            md.desc = getMethodDesc(getMethod);
            md.returnTypeInternalName = getInternalName(getMethod.getReturnType().getName());
            return md;
        } catch (Exception ex) {
            // ingnore
        }

        try {
            Method getMethod = target.getMethod(md.name, new Class[]{java.lang.String.class});
            md.parameterInternalName = Constants.InternalName.STRING;
            md.desc = getMethodDesc(getMethod);
            md.returnTypeInternalName = getInternalName(getMethod.getReturnType().getName());
            return md;
        } catch (Exception ex) {
            // ingnore
        }

        return null;
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
        return className.substring(className.lastIndexOf(".") + 1);
    }

    static String getInternalName(final String className) {
        return className.replace('.', '/');
    }

    static int[] convertIntegerToPrimitiveType(Integer[] source) {
        int[] target = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            target[i] = source[i];
        }
        return target;
    }

}
