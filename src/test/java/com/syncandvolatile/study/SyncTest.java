package com.syncandvolatile.study;

import lombok.Synchronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * sync学习
 *
 * @author xuedui.zhao
 * @create 2019-01-29
 */
public class SyncTest {

    int i = 0;

    private static ExecutorService executorService = Executors.newFixedThreadPool(20);

    public void syncBlock() {
        synchronized (this) {
            this.i = i + 1;
            System.out.println(Thread.currentThread().getName() + " i == " + this.i);
        }
    }

    public synchronized void syncMethod() {
        System.out.println("hello method");
    }

    @Synchronized
    public void synchronizedTest() {
        this.i = i + 1;
        System.out.println(Thread.currentThread().getName() + " i == " + this.i);
    }

    public static void main(String[] args) {

        SyncTest syncTest = new SyncTest();
        for (int j = 0; j < 20; j++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    syncTest.synchronizedTest();
                }
            });
        }
        System.out.println("is interrupt == " + Thread.currentThread().isInterrupted());
        Thread.currentThread().interrupt();
        System.out.println("is interrupt == " + Thread.currentThread().isInterrupted());

    }
}
