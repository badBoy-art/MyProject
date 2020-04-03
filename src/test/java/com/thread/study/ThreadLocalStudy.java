package com.thread.study;

import org.junit.Test;

/**
 * ThreadLocal学习
 *
 * @author xuedui.zhao
 * @create 2019-05-09
 * <a href="https://mp.weixin.qq.com/s/SysYihctu03RlUtI0pcG7w"></a>
 * Spring的事务管理，用ThreadLocal存储Connection，从而各个DAO可以获取同一Connection，可以进行事务回滚，提交等操作
 */
public class ThreadLocalStudy {

    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    private static InheritableThreadLocal<Integer> threadLocalI = new InheritableThreadLocal<>();

    @Test
    public void test() {
        threadLocal.set("zhangsan");
        threadLocalI.set(new Integer(10));
        System.out.println(threadLocal.get());
        System.out.println(threadLocalI.get());
        threadLocal.remove();
        threadLocalI.remove();
        System.out.println(threadLocal.get());
        System.out.println(threadLocalI.get());
    }

    public static void main(String args[]) {
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    threadLocalI.set(i);
                    System.out.println(Thread.currentThread().getName() + "====" + threadLocalI.get());
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                threadLocalI.remove();
            }
        }, "threadLocal1").start();
        new Thread(() -> {
            try {
                for (int i = 10; i < 20; i++) {
                    threadLocalI.set(i);
                    System.out.println(Thread.currentThread().getName() + "====" + threadLocalI.get());
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                threadLocalI.remove();
            }
        }, "threadLocal2").start();
    }
}
