package com.thread.study;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * java8 runnable
 *
 * @author xuedui.zhao
 * @create 2018-03-31
 */
public class MyRunnable {

    @Test
    public void testRunnable() {
       Runnable task = ()->{
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
}
