package com.thread.study;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * java8 runnable
 *
 * @author xuedui.zhao
 * @create 2018-03-31
 */
public class MyRunnable {

    @Test
    public void testRunnable() {
        Runnable task = () -> {
            try {
                String threadName = Thread.currentThread().getName();
                //TimeUnit.SECONDS.sleep(1);
                System.out.println("Hello " + threadName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        task.run();
        Thread thread = new Thread(task);
        thread.start();

        System.out.println("Done!");
    }

    @Test
    public void test() {
        final List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        new Thread(() -> System.out.println(list.size())).start();
        //LockSupport.parkNanos(10000);
        for (int i = 4; i < 100000; i++) {
            list.add(i + "");
        }

        System.out.println(list.size());
    }
}
