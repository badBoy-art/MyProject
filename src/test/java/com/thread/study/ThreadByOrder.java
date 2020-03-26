package com.thread.study;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author badBoy
 * @create 2019-09-06
 */
public class ThreadByOrder {

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new MyThread1();
        Thread t2 = new MyThread2();
        Thread t3 = new Thread(new MyThread3());
        for (int i = 0; i < 20; i++) {
            if (i % 3 == 0) {
                executor.submit(t1);
            } else if (i % 3 == 1) {
                executor.submit(t2);
            } else {
                executor.submit(t3);
            }
        }
        executor.shutdown();
    }
}


class MyThread1 extends Thread {
    @Override
    public void run() {
        System.out.println("I am thread 1");
    }
}

class MyThread2 extends Thread {
    @Override
    public void run() {
        System.out.println("I am thread 2");
    }
}

class MyThread3 implements Runnable {
    @Override
    public void run() {
        System.out.println("I am thread 3");
    }
}