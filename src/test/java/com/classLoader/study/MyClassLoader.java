package com.classLoader.study;


import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义classLoader
 *
 * @author xuedui.zhao
 * @create 2018-05-18
 */
public class MyClassLoader extends ClassLoader {

    private String root;

    protected Class<?> findClass(String name) throws ClassNotFoundException{
        byte[] classData = loadClassDate(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(null, classData, 0, classData.length);
        }
    }

    private byte[] loadClassDate(String className) {
        String fileName = root + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
        try {
            InputStream ins = new FileInputStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while ((length = ins.read(buffer)) != -1) {
                baos.write(buffer,0,length);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
    @Test
    public void test() {
        MyClassLoader classLoader = new MyClassLoader();
        classLoader.setRoot("/Users/xuedui.zhao/Projects/myProject/src/main/java");

        Class<?> testClass = null;
        try {
            testClass = classLoader.findClass("com.snow.spi.service.Demo");
            Object object = testClass.newInstance();
            System.out.println(object.getClass().getClassLoader());
            System.out.println(object);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
