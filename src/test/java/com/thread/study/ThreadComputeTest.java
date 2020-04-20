package com.thread.study;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xuedui.zhao
 * @create 2019-04-19
 *
 */
public class ThreadComputeTest {
    Double d = new Double(20);

    @Test
    public void test() {
        ExecutorService executors = new ThreadPoolExecutor(4, 8, 60L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(20));


        MyPlusThread myPlusThread = new MyPlusThread();
        MyPlus2Thread myPlus2Thread = new MyPlus2Thread();
        MyMultiThread myMultiThread = new MyMultiThread();
        MyDiviThread myDiviThread = new MyDiviThread();

        executors.submit(myPlusThread);
        executors.submit(myPlus2Thread);
        executors.submit(myDiviThread);
        executors.submit(myMultiThread);

    }

    class MyPlusThread extends Thread {

        @Override
        public void run() {
            //synchronized(ThreadComputeTest.class){
            System.out.println("MyPlusThread start = " + d);
            d = d + 10;
            System.out.println("MyPlusThread result = " + d);
            //}
        }
    }

    class MyPlus2Thread extends Thread {

        @Override
        public void run() {
            //synchronized (ThreadComputeTest.class) {
            System.out.println("MyPlus2Thread start = " + d);
            d = d + 20;
            System.out.println("MyPlus2Thread result = " + d);
            //}
        }
    }

    class MyMultiThread implements Runnable {

        @Override
        public void run() {
            //synchronized (ThreadComputeTest.class) {
            System.out.println("MyMultiThread start = " + d);
            d = d / 3;
            System.out.println("MyMultiThread result = " + d);
            //}
        }
    }

    class MyDiviThread implements Runnable {

        @Override
        public void run() {
            //synchronized (ThreadComputeTest.class) {
            System.out.println("MyDiviThread start = " + d);
            d = d * 4;
            System.out.println("MyDiviThread result = " + d);
            //}
        }
    }

    @Test
    public void testRejectStrategy() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 20; i++) {
            MyTask myTask = MyTask.getInstance();
            myTask.setTaskNum(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行完的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }


}


class MyTask implements Runnable {
    private int taskNum;

    private volatile static MyTask instance;

    private MyTask() {
    }

    public static MyTask getInstance() {
        if (instance != null) {
            return instance;
        }

        if (instance == null) {
            synchronized (MyTask.class) {
                if (instance == null) {
                    instance = new MyTask();
                }
            }
        }
        return instance;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public void run() {
        System.out.println("线程名称：" + Thread.currentThread().getName() + "，正在执行task " + taskNum);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task " + taskNum + "执行完毕");
    }

}
