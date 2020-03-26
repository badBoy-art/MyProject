package com.singleton.study;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 去掉MutableSingleton的volatile验证结果不同
 *
 * @author xuedui.zhao
 * @create 2019-05-23
 */
public class MutableSingletonTest {

    private long counter = 0;

    public Object[] execute(Object... arguments) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                MutableSingleton.getInstance().setSomeFlag(true);
                System.out.println("Timer interrupted main thread ...");
                timer.cancel();
            }
        }, 1000);
        while (!MutableSingleton.getInstance().isSomeFlag()) {
            counter++;
        }
        ;
        System.out.println("Main thread was interrupted by timer ...");
        return new Object[]{counter, MutableSingleton.getInstance().isSomeFlag()};
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            Object[] result = execute();
            System.out.println(result[0] + "/" + result[1]);
        }
    }

    @Test
    public void test() throws InterruptedException {
        MutableSingletonTest volatileExample = new MutableSingletonTest();
        Thread thread1 = new Thread(volatileExample.new Worker(), "Worker-1");
        thread1.start();
        Thread.sleep(5000);
    }
}
