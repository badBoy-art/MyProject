package com.thread.study;

import org.junit.Test;

import java.util.concurrent.Semaphore;

/**
 * 信号量学习
 *
 * @author xuedui.zhao
 * @create 2019-08-20
 */
public class SemaphoneStudy {

    @Test
    public void test() throws Exception {
        MyRun myRun = new MyRun();
        for (int i = 0; i < 10; i++) {
            (new MyRunThread(myRun)).start();
        }
        Thread.sleep(10000);
    }

}


class MyRunThread extends Thread {
    private MyRun myRun;

    public MyRunThread(MyRun myRun) {
        this.myRun = myRun;
    }

    @Override
    public void run() {
        myRun.run();
    }
}

class MyRun {
    private Semaphore semaphore = new Semaphore(3);

    public void run() {
        try {
            // 从信号量中获取一个允许机会
            semaphore.acquire();
            System.out.println("threadName = " + Thread.currentThread().getName() + " start at " + System.currentTimeMillis());
            Thread.sleep(10);
            System.out.println("threadName = " + Thread.currentThread().getName() + " stop at " + System.currentTimeMillis());
            // 释放允许，将占有的信号量归还
            semaphore.release();
            System.out.println("semaphore == " + semaphore.availablePermits());
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

}