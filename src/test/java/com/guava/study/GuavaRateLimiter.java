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
        for (int i = 0; i < 10; i++) {
            // 请求RateLimiter, 超过permits会被阻塞
            System.out.println(i + " time = " + rateLimiter.acquire());
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 限流抽象类，定义限流器的基本接口
     */
    @Test
    public void testRateLimiter02() {
        RateLimiter limiter = RateLimiter.create(5);
        //limiter.acquire(5) 表示桶的容量为 5 且每秒新增 5 个令牌，令牌桶算法允许一定程度的突发，所以可以一次性消费 5 个令牌
        System.out.println(limiter.acquire(5));
        //limiter.acquire(1) 将等待差不多 1 秒桶中才能有令牌，且接下来的请求也整形为固定速率了
        System.out.println(limiter.acquire(1));
        System.out.println(limiter.acquire(1));
    }

    /**
     * 限流抽象类，定义限流器的基本接口
     * 速率是梯形上升速率的，也就是说冷启动时会以一个比较大的速率慢慢到平均速率；然后趋于平均速率（梯形下降到平均速率）。可以通过调节 warmupPeriod 参数实现一开始就是平滑固定速率。
     */
    @Test
    public void testRateLimiter03() throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(5, 1000, TimeUnit.MILLISECONDS);
        for (int i = 0; i < 5; i++) {
            System.out.println(limiter.acquire());
        }
        Thread.sleep(1000L);
        for (int i = 0; i < 10; i++) {
            System.out.println(limiter.acquire());
        }
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
