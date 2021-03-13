package com.guava.study;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.Uninterruptibles;

/**
 * Guava限流工具
 *
 * @author xuedui.zhao
 * @create 2018-03-29
 * <a href="https://mp.weixin.qq.com/s/iyiC3jytdMGvs2EQMkYPDQ"></a>
 */
public class GuavaRateLimiter {

    @Test
    public void testRateLimiter01() {
        Long start = System.currentTimeMillis();
        // 每秒不超过10个任务被提交
        RateLimiter rateLimiter = RateLimiter.create(5);
        for (int i = 0; i < 30; i++) {
            // 请求RateLimiter, 超过permits会被阻塞
            rateLimiter.acquire();
            System.out.println("call execute.." + i);
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * 限流抽象类，定义限流器的基本接口
     */
    @Test
    public void testRateLimiter() {
    }

    /**
     * 平滑限流实现器，也是一个抽象类
     */
    @Test
    public void testSmoothRateLimiter() {
    }

    /**
     * 自带预热机制的限流器实现类型
     */
    @Test
    public void testSmoothWarmingUp() {
    }

    /**
     * 适应于突发流量的限流器
     */
    @Test
    public void testSmoothBursty() {
    }

    /**
     * 无打断线程sleep
     */
    @Test
    public void testSleep() {
        long startTime = System.currentTimeMillis();
        Uninterruptibles.sleepUninterruptibly(300000, TimeUnit.MICROSECONDS);
        System.out.println(System.currentTimeMillis() - startTime);
    }

    // Can't be initialized in the constructor because mocks don't call the constructor.
    private volatile Object mutexDoNotUseDirectly;

    /**
     * google获得锁对象
     *
     * @return
     */
    private Object mutex() {
        Object mutex = mutexDoNotUseDirectly;
        if (mutex == null) {
            synchronized (this) {
                mutex = mutexDoNotUseDirectly;
                if (mutex == null) {
                    mutexDoNotUseDirectly = mutex = new Object();
                }
            }
        }
        return mutex;
    }

    public void testGetLockObj() {
        synchronized (mutex()) {
            //方法内操作全局变量
            System.out.println("threadName == " + Thread.currentThread().getName());
        }
    }
}
