package com.classLoader.study;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author badBoy
 * @create 2019-10-25
 * <p>
 * Class.forName除了将类的.class文件加载到jvm中之外，还会对类进行解释，执行类中的static块。
 * 而classloader只干一件事情，就是将.class文件加载到jvm中，不会执行static中的内容，只有在newInstance才会去执行static块。
 * <a href="http://gityuan.com/2016/01/24/java-classloader/"></a>
 */
public class ClassLoaderStudy {

    @Test
    public void test01() {
        try {
            URL[] extURLs = ((URLClassLoader) ClassLoader.getSystemClassLoader().getParent()).getURLs();
            for (int i = 0; i < extURLs.length; i++) {
                System.out.println(extURLs[i]);
            }
            System.out.println();
            System.out.println("--------------------------------------------------");
            System.out.println();
            URL[] sysURLs = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs();
            for (int i = 0; i < sysURLs.length; i++) {
                System.out.println(sysURLs[i]);
            }
            //查看当前系统类路径中包含的路径条目
            System.out.println(System.getProperty("java.class.path"));
            //调用加载当前类的类加载器（这里即为系统类加载器）加载TestBean
            Class typeLoaded = Class.forName("com.classLoader.study.TestBean");
            //查看被加载的TestBean类型是被那个类加载器加载的
            System.out.println(typeLoaded.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test02() throws Exception {
        ClassLoader clazzLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String clazzName = name.substring(name.lastIndexOf(".") + 1) + ".class";

                    InputStream is = getClass().getResourceAsStream(clazzName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        String currentClass = "com.classLoader.study.ClassLoaderStudy";
        Class<?> clazz = clazzLoader.loadClass(currentClass);
        Object obj = clazz.newInstance();

        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderStudy);
    }
}
