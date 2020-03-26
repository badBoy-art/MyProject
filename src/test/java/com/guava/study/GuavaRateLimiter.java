package com.guava.study;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

/**
 * Guava限流工具
 *
 * @author xuedui.zhao
 * @create 2018-03-29
 */
public class GuavaRateLimiter {

    @Test
    public void testRateLimiter() {
        Long start = System.currentTimeMillis();
        // 每秒不超过10个任务被提交
        RateLimiter rateLimiter = RateLimiter.create(10);
        for (int i = 0; i < 30; i++) {
            // 请求RateLimiter, 超过permits会被阻塞
            rateLimiter.acquire();
            System.out.println("call execute.." + i);
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
