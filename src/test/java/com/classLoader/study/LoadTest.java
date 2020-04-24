package com.classLoader.study;

import org.junit.Test;

/**
 * 类加载测试
 *
 * @author xuedui.zhao
 * @create 2018-11-26
 */
public class LoadTest {

    @Test
    public void test1() {
        print("inside study.main");
        new Panda();
        print("After creating Panda");
        //加载Lion
        //new Lion();
        try {
            //如果Lion没被加载过，Class.forName会导致Lion被加载  如果Lion被加载了，则不会重复加载
            Class.forName("com.classLoader.study.Lion");
        } catch(ClassNotFoundException e) {
            print("Couldn't find Lion");
        }
        print("After Class.forName(\"com.classLoader.study.Lion\")");

        new Tigger();
        print("After creating Tigger");

    }

    public void print(Object obj) {
        System.out.println(obj);
    }

    @Test
    public void test2() {
        Class clazz = null;
        try{
            //通过Class.forName获取Gum类的Class对象
            clazz = Class.forName("com.classLoader.study.Lion");
            System.out.println("forName=clazz:"+clazz.getName());
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        //通过实例对象获取Gum的Class对象
        Lion lion = new Lion();
        Class clazz2 = lion.getClass();
        System.out.println("new=clazz2:"+clazz2.getName());

        System.out.println(clazz2 == clazz);
    }
}
