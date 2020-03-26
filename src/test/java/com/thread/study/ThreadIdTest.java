package com.thread.study;

/**
 * @author badBoy
 * @create 2019-11-14
 */
public class ThreadIdTest {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                System.out.println(" priority：" + Thread.currentThread().getPriority() + " id：" + Thread.currentThread().getId());
            }

            ).start();
        }
        new Thread(() -> {
            System.out.println("priority：" + Thread.currentThread().getPriority() + " id：" + Thread.currentThread().getId());
        }).start();

        new Thread(() -> {
            System.out.println("priority：" + Thread.currentThread().getPriority() + " id：" + Thread.currentThread().getId());
        }).start();
        
        Thread.sleep(3000);
    }
}