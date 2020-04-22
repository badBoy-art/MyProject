package com.reflect.study;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 反射学习
 *
 * @author xuedui.zhao
 * @create 2018-03-31
 */
public class ReflectStudy {

    @Test
    public void testConstructor() throws Exception {
        //new 会对类进行加载 解析 初始化
        Student student = new Student();
        //反射只会加载 加载时：在加载的所有阶段，只会有静态变量和静态代码块初始化。成员变量并不会初始化
        Class clazz = Class.forName("com.reflect.study.Student");

        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }
    }

    @Test
    public void testDeclaredConstructor() throws Exception {
        System.out.println(sun.misc.Launcher.getLauncher().getClassLoader());

        Class clazz = Class.forName("com.reflect.study.Student");

        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }
    }

    @Test
    public void testField() throws Exception {
        Class clazz = Class.forName("com.reflect.study.Student");

        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }
    }

    @Test
    public void testDeclaredField() throws Exception {
        Class clazz = Class.forName("com.reflect.study.Student");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
        }
    }

    @Test
    public void testSetFieldValue() throws Exception {
        Class clazz = Class.forName("com.reflect.study.Student");
        Object obj = clazz.getConstructor().newInstance();

        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        field.set(obj, "test");

        Student stu = (Student) obj;
        System.out.println(stu.getName());
    }

    @Test
    public void testMethod() throws Exception {
        Class clazz = Class.forName("com.reflect.study.Student");

        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
    }

    @Test
    public void testDeclaredMethod() throws Exception {
        Class clazz = Student.class;

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
    }

    @Test
    public void testInvokeMethod() throws Exception {
        Class clazz = new Student().getClass();
        Object obj = clazz.getConstructor().newInstance();

        Method method = clazz.getDeclaredMethod("speakMyAge", int.class);
        Student student = (Student) obj;
        method.setAccessible(true);
        method.invoke(student, 30);

    }

    /**
     * https://mp.weixin.qq.com/s/XnQoqsI9CZ9j5DnUtKZXUw
     * Java 反射调用时，默认超过 15 次就会生成 java 类进行调用，那么问题是这个生成的类中为什么可以调用 private 的方法。
     * https://www.cnblogs.com/xiangnanl/p/9887983.html
     *
     * @throws Exception
     */
    @Test
    public void testPrivate() throws Exception {

        Method priStr = ReflectStudy.class.getDeclaredMethod("priStr");
        priStr.setAccessible(true);
        for (int i = 0; i < 16; i++) {
            String s = priStr.invoke(null).toString();
            System.out.println(s + i);
        }

        TimeUnit.SECONDS.sleep(1000000000);
    }


    private static String priStr() {
        return "private string";
    }
}
