package com.time.study;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author xuedui.zhao
 * @create 2018-09-25
 */
public class Demo {
    
    @Test
    public void test01() {
        long millis = TimeUnit.SECONDS.toMillis(5);
        System.out.println(millis);
    }

    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    @Test
    public void testTimerTask() throws InterruptedException {
        System.out.println("main start:"+getCurrentTime());
        startTimer();
    }

    public static void startTimer(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task   run:"+getCurrentTime());
                try {
                    Thread.sleep(100*3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000*5,100*10);
    }
}
