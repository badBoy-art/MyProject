package com.thread.study;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author xuedui.zhao
 * @create 2018-11-28
 */
public class MyThreadTimeOut {
    @Test
    public void test() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread());
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //没有return任务继续执行
                    return;
                }
                System.out.println("任务继续执行..........");
            }
        });

        try {
            System.out.println(Thread.currentThread());
            thread.start();
            TimeUnit.SECONDS.timedJoin(thread, 3);

            if (thread.isAlive()) {
                thread.interrupt();
                throw new TimeoutException("Thread did not finish within timeout");
            }
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        } catch (TimeoutException e) {
            System.out.println("TimeoutException");
        }
    }


    @Test
    public void testThread() throws Exception {
        Thread thread = new Thread(null, new Runnable() {
            int dep = 0;

            @Override
            public void run() {
                try {
                    d();
                } catch (Throwable e) {
                    System.out.println(e);
                }
                System.out.println(dep);
            }

            void d() {
                dep++;
                d();
            }
        }, "stackSizeThread", 1024);
        thread.start();
        Thread.sleep(3000);
    }

}
