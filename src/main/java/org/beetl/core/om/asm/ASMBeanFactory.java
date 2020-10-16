/**
 *
 */
package org.beetl.core.om.asm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.beetl.core.GroupTemplate;
import org.beetl.core.config.BeetlConfig;
import org.beetl.core.log.Log;
import org.beetl.core.misc.ByteClassLoader;
import org.beetl.core.om.AttributeAccess;
import org.beetl.core.om.ReflectBeanAA;

/**
 *
 * @author laozhaishaozuo@foxmail.com
 *
 */
public class ASMBeanFactory {

    /** DEBUG flag */
    private static final boolean DEBUG = BeetlConfig.DEBUG;
    /** Log TAG */
    private static final String TAG = "ASMBeanFactory";

    private final Map<Class<?>, AttributeAccess> beanMap = new ConcurrentHashMap<>();
    private final Map<ClassLoader, ByteClassLoader> classLoaders = new ConcurrentHashMap<>();

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader() != null
            ? Thread.currentThread().getContextClassLoader()
            : GroupTemplate.class.getClassLoader();

    ByteClassLoader byteContextLoader = new ByteClassLoader(classLoader);

    boolean usePropertyDescriptor;

    public ASMBeanFactory() {
        usePropertyDescriptor = false;
    }

    public ASMBeanFactory(boolean usePropertyDescriptor) {
        this.usePropertyDescriptor = usePropertyDescriptor;
    }

    public Object value(Object bean, String attrName) {
        Class<?> beanClass = bean.getClass();
        AttributeAccess generatedBean = generateBean(beanClass);
        return generatedBean == null ? null : generatedBean.value(bean, attrName);
    }

    public boolean isUsePropertyDescriptor() {
        return usePropertyDescriptor;
    }

    public void setUsePropertyDescriptor(boolean usePropertyDescriptor) {
        this.usePropertyDescriptor = usePropertyDescriptor;
    }

    public synchronized AttributeAccess generateBean(Class<?> beanClass) {
        if (beanMap.containsKey(beanClass)) {
            return beanMap.get(beanClass);
        }
        try {
            ClassLoader beanClassLoader = beanClass.getClassLoader();
            if (beanClassLoader == null) { // java自带类或者没有classloader的类
                beanMap.put(beanClass, ReflectBeanAA.INSTANCE);
                return ReflectBeanAA.INSTANCE;
            }

            byte[] code = EnhanceClassGenerator.generate(beanClass, this.usePropertyDescriptor);
            String generatedBeanName = EnhanceClassGenerator.createGeneratedClassName(beanClass);

            Object obj = loadContextClassLoader(code, generatedBeanName);
            if (obj != null) {
                beanMap.put(beanClass, (AttributeAccess) obj);
                return beanMap.get(beanClass);
            }
            //使用加载Bean的classloader，https://gitee.com/xiandafu/beetl/issues/IWLSS
            ByteClassLoader byteLoader = classLoaders.get(beanClassLoader);
            if (byteLoader == null) {
                byteLoader = new ByteClassLoader(beanClassLoader);
                classLoaders.putIfAbsent(beanClassLoader, byteLoader);
            }
            Class<?> enhanceClass = byteLoader.findClassByName(generatedBeanName);
            if (enhanceClass == null) {
                enhanceClass = byteLoader.defineClass(generatedBeanName, code);
            }
            // writeClassToFile(code, beanClass, generatedBeanName);
            obj = obj = enhanceClass.newInstance();
            beanMap.put(beanClass, (AttributeAccess) obj);
            return beanMap.get(beanClass);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }

    private Object loadContextClassLoader(byte[] code, String className) {
        Object obj;
        try {
            Class<?> enhanceClass = byteContextLoader.findClassByName(className);
            if (enhanceClass == null) {
                enhanceClass = byteContextLoader.defineClass(className, code);
            }
            obj = enhanceClass.newInstance();
        } catch (Exception ex) {
            return null;
        }
        return obj;
    }

    private static void writeClassToFile(final byte[] code, final Class<?> beanClass, final String generatedBeanName) {
        try {
            String classPath = beanClass.getResource("").getPath();
            // 将二进制流写到本地磁盘上
            File file = new File(classPath, BeanEnhanceUtils.getSimpleClassName(generatedBeanName) + ".class");
            if (DEBUG) {
                Log.d(TAG, file.getAbsolutePath());
            }
            System.out.println(file.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(code);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
