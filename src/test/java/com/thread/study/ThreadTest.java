package com.thread.study;

import org.junit.Test;

/**
 * @author badBoy <badBoy>
 * Created on 2021-06-27
 * @Description
 */
public class ThreadTest {

    @Test
    public void testJoin() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        Thread bt = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("child_thread_name :" + Thread.currentThread().getName() + "_" + i);
                    Thread.sleep(1000);
                }
                System.out.println(Thread.currentThread().getName() + " end.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            bt.start(); //必须先启动线程thread, thread.join()才会生效
            bt.join();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Exception from main");
        }
        System.out.println(threadName + " end!");
    }

}
