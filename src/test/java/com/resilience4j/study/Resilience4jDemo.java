package com.resilience4j.study;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.AsyncRetry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.control.Try;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * 熔断器学习
 *
 * @author xuedui.zhao
 * @create 2018-12-01
 */
public class Resilience4jDemo {

    @Test
    public void circuitbreakerTest() {

    }

    @Test
    public void testRatelimiter() {
        BackendServiceImpl backendService = new BackendServiceImpl();

        RateLimiterConfig config = RateLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(100))
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(1)
                .build();
        // Create a RateLimiter
        RateLimiter rateLimiter = RateLimiter.of("backendName", config);

        // Decorate your call to BackendService.doSomething()
        Supplier<String> restrictedSupplier = RateLimiter
                .decorateSupplier(rateLimiter, backendService::doSomething);

        // First call is successful
        Try<String> firstTry = Try.ofSupplier(restrictedSupplier);
        System.out.println(firstTry.isSuccess());

        // Second call fails, because the call was not permitted
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Try<String> secondTry = Try.ofSupplier(restrictedSupplier);
        System.out.println(secondTry.isFailure());
        System.out.println(secondTry.getCause());

    }

    private int executeTimes = 0;

    RetryConfig config = RetryConfig.custom()
            .maxAttempts(5)
            .waitDuration(Duration.ofMillis(3000))
            .retryExceptions(RetryNeedException.class)
            .ignoreExceptions(RetryNoNeedException.class)
            .retryOnException(throwable -> throwable instanceof Exception)
            .retryOnResult(resp -> resp.toString().contains("result cause retry"))
            .build();

    Retry retry = Retry.of("sync retry", config);


    /**
     * 同步
     * * @throws Exception
     */
    @Test
    public void testSyncRetry() throws Exception {
        retryOnSyncException();
    }

    public void retryOnSyncException() {
        Retry.decorateRunnable(
                retry,
                () -> {
                    if (executeTimes++ < 10) {
                        System.out.println("executeTimes == " + executeTimes);
                        throw new RuntimeException();
                    }
                })
                .run();
    }


    private AsyncRetry asyncRetry = AsyncRetry.of("async retry", config);
    private AtomicInteger atomicExecuteTimes = new AtomicInteger(0);

    /**
     * 异步
     * * @throws Exception
     */
    @Test
    public void testASyncRetry() throws Exception {
        Long startTime = System.currentTimeMillis();
        System.out.println("");
        Supplier<CompletionStage<String>> result = asyncRetryOnException();

        Assert.assertTrue(result.get().toCompletableFuture().get().contains("async retry"));
        Assert.assertEquals(5, atomicExecuteTimes.get());

        System.out.println("executeTime = " + (System.currentTimeMillis() - startTime));
    }

    private static ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1, new RetryThreadFactory());

    public Supplier<CompletionStage<String>> asyncRetryOnException() {
        return AsyncRetry.decorateCompletionStage(asyncRetry, executor, () -> exceptionCauseRetry());
    }

    private CompletionStage<String> exceptionCauseRetry() {
        if (atomicExecuteTimes.getAndIncrement() < 4) {
            System.out.println("atomicExecuteTimes == " + atomicExecuteTimes + " threadName = " + Thread.currentThread().getName());
            throw new RetryNeedException();
        }
        return CompletableFuture.completedFuture("async retry");
    }

}
