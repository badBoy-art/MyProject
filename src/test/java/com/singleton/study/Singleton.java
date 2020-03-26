package com.singleton.study;

/**
 * 单例学习
 *
 * @author xuedui.zhao
 * @create 2018-12-12
 * @see MutableSingleton 双重加锁
 */
public class Singleton {

    {
        System.out.println(" module");
    }


    static {
        System.out.println("-----Demo::static code block");
    }

    public static void get() {
        System.out.println("-----Demo::static method");
    }


    private static Singleton singleton = new Singleton();

    Singleton() {
        System.out.println("-----Demo::constructor");
    }


    private static Singleton demo = null;

    public static Singleton getInstance() {
        if (demo == null) {
            demo = new Singleton();
        }
        return demo;
    }

}
