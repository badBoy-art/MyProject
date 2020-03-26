package com.current.study;

import org.junit.Test;

/**
 * @author xuedui.zhao
 * @create 2018-09-21
 */
public class VolatileStudy {
    
    public volatile int inc = 0;
    
    public void increase() {
        inc++;
    System.out.println(Thread.currentThread().getName() + "inc = " + inc);
    }
    
    @Test
    public void test01() {
        final VolatileStudy test = new VolatileStudy();
        for(int i=0;i<5;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<10;j++)
                        test.increase();
                };
            }.start();
        }
    
        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
    }
    
    
}
