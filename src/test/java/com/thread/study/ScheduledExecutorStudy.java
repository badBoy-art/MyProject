package com.thread.study;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

/**
 * @author badBoy <badBoy>
 * Created on 2021-04-28
 * @Description
 */
public class ScheduledExecutorStudy {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "scheduler"));

    @Test
    public void test() {
        executorService.schedule(() -> System.out.println(System.currentTimeMillis()), 100, TimeUnit.MILLISECONDS);
        executorService.scheduleWithFixedDelay(() -> System.out.println("fiexd " + System.currentTimeMillis()), 30, 20, TimeUnit.MILLISECONDS);
        LockSupport.parkUntil(System.currentTimeMillis() + 300);
        System.out.println("over");
    }
}
