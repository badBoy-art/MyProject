package com.current.study;

import org.junit.Test;

/**
 * @author xuedui.zhao
 * @create 2018-09-21
 */
public class VolatileStudy {

    public volatile int inc = 0;

    public void increase() {
        inc++;
        System.out.println(Thread.currentThread().getName() + "inc = " + inc);
    }

    @Test
    public void test01() {
        final VolatileStudy test = new VolatileStudy();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++)
                    test.increase();
            }).start();
        }

        while (Thread.activeCount() > 2) {//保证前面的线程都执行完
            Thread.currentThread().getThreadGroup().list();  // 打印当前线程组的线程
            Thread.yield();
        }
        System.out.println(test.inc);
    }

    @Test
    public void testActiveCount() {
        System.out.println(Thread.activeCount());  //eclipse或java执行是1，idea执行是2
        /**
         * java.lang.ThreadGroup[name=main,maxpri=10]
         *     Thread[main,5,main]
         *     Thread[Monitor Ctrl-Break,5,main]
         *
         *     idea用的是反射，还有一个monitor监控线程。
         */
        Thread.currentThread().getThreadGroup().list(); // 打印当前线程组的线程
    }

    public static void main(String[] args) {

    }
}
