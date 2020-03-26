package com.resilience4j.study;

import java.time.Duration;

import org.junit.Test;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-02-16
 * @Description
 */
public class RateLimiterStudy {

    @Test
    public void testRateLimiter01() {
        // 创建高频控制配置，控制频率为1QPS
        RateLimiterConfig config = RateLimiterConfig.custom()
                //频次阈值
                .limitForPeriod(1)
                //阈值刷新时间
                .limitRefreshPeriod(Duration.ofMillis(1000))
                //限流后的冷却时间
                .timeoutDuration(Duration.ofMillis(500))
                .build();

        // 创建高频控制注册中心
        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);

        // 从注册中心创建高频控制器实例
        RateLimiter rateLimiterWithDefaultConfig = rateLimiterRegistry.rateLimiter("backend");
        RateLimiter rateLimiterWithCustomConfig = rateLimiterRegistry.rateLimiter("backend#2", config);

        // 直接创建高频控制器实例
        RateLimiter rateLimiter = RateLimiter.of("NASDAQ :-)", config);
        rateLimiter.getEventPublisher()
                .onSuccess(event -> System.out.println("success " + event.getRateLimiterName()))
                .onFailure(event -> System.out.printf("failure " + event.getRateLimiterName()));

        // 使用上面定义的高频控制器装饰函数调用
        CheckedRunnable restrictedCall = RateLimiter
                .decorateCheckedRunnable(rateLimiter, () -> System.out.println("Do something"));

        // 第一次调用成功，第二次调用被高频限制
        Try.run(restrictedCall)
                .andThenTry(restrictedCall)
                .onFailure(throwable -> System.out.println("Wait before call it again :)"));

        //你可以在运行时动态修改高频控制器配置，但新的冷却时间不会影响当前处于冷却状态的线程，新的阈值也不会影响处于当前一轮控制的线程：
        // 在下一轮控制中，阈值变更为100
        rateLimiter.changeLimitForPeriod(100);
    }

    @Test
    //高频控制也支持RxJava/Reactor
    public void test02() {
        RateLimiter rateLimiter = RateLimiter.ofDefaults("backendName");
//        Observable.fromCallable(() -> System.out.println("Do something"))
//                .lift(RateLimiterOperator.of(rateLimiter));
//
//        Mono.fromCallable(() -> System.out.println("Do something"))
//                .transform(RateLimiterOperator.of(rateLimiter));
    }
}
