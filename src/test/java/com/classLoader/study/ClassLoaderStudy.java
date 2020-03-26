package com.classLoader.study;

import org.junit.Test;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author badBoy
 * @create 2019-10-25
 */
public class ClassLoaderStudy {

    @Test
    public void test01() {
        try {
            URL[] extURLs = ((URLClassLoader) ClassLoader.getSystemClassLoader().getParent()).getURLs();
            for (int i = 0; i < extURLs.length; i++) {
                System.out.println(extURLs[i]);
            }

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

}
