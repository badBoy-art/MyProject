package com.thread.study;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

/**
 * @author badBoy
 * @create 2019-11-14
 */
public class ThreadIdTest {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> System.out.println(" priority：" + Thread.currentThread().getPriority() + " id：" + Thread.currentThread().getId())

            ).start();
        }
        new Thread(() -> System.out.println("priority：" + Thread.currentThread().getPriority() + " id：" + Thread.currentThread().getId())).start();

        new Thread(() -> System.out.println("priority：" + Thread.currentThread().getPriority() + " id：" + Thread.currentThread().getId())).start();

        Thread.sleep(3000);
    }

    @Test
    public void test() {
        System.out.println(ThreadLocalRandom.current().nextLong(MINUTES.toMillis(1), MINUTES.toMillis(2)));
    }
}
