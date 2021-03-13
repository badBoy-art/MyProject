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


    private static final Singleton singleton = new Singleton();

    private Singleton() {
        System.out.println("-----Demo::constructor");
    }


    private volatile static Singleton demo;

    public static Singleton getInstance() {
        if (demo == null) {
            System.out.println(" demo ");
            demo = new Singleton();
        }
        return demo;
    }

    private static class Inner {
        public Inner() {
            System.out.println("init Inner");
        }

        private static final Singleton instance = new Singleton();
    }

    private enum SingletonEnum {
        INSTANCE;

        private final Singleton singleton;


        SingletonEnum() {
            System.out.println(" singleton_enum ");
            this.singleton = new Singleton();
        }

        private Singleton getSingleton() {
            return singleton;
        }
    }

    public static Singleton getSingleton() {
        return SingletonEnum.INSTANCE.getSingleton();
    }

}
