package com.guava.study;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.AttemptTimeLimiters;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.RetryListener;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * guavaRetryStudy
 *
 * @author xuedui.zhao
 * @create 2018-03-28
 */
public class GuavaRetry {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");

    @Test
    public void testRetry() {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.noWait())
                .build();

        System.out.println("begin..." + df.format(new Date()));

        try {
            retryer.call(new AlwaysExceptionTask());
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }

        System.out.println("end..." + df.format(new Date()));
    }

    @Test
    public void testRetry2() {
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 2;
            }
        };

        Retryer<Integer> retryer = RetryerBuilder.<Integer>newBuilder()
                .retryIfResult(Predicates.<Integer>isNull())
                .retryIfResult(Predicates.equalTo(2))
                .retryIfExceptionOfType(IOException.class)
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withWaitStrategy(WaitStrategies.fixedWait(300, TimeUnit.MILLISECONDS))
                .build();

        try {
            retryer.call(task);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (RetryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRetry3() {
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 2;
            }
        };

        Retryer<Integer> retry = RetryerBuilder.<Integer>newBuilder()
                .retryIfException()
                .retryIfResult(Predicates.equalTo(2))
                .withStopStrategy(StopStrategies.stopAfterDelay(10, TimeUnit.SECONDS))
                .withWaitStrategy(WaitStrategies.incrementingWait(2, TimeUnit.SECONDS, 1, TimeUnit.SECONDS))
                .withAttemptTimeLimiter(AttemptTimeLimiters.<Integer>fixedTimeLimit(3, TimeUnit.SECONDS))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        if (attempt.hasException()) {
                            attempt.getExceptionCause().printStackTrace();
                        }
                    }
                }).build();

        try {
            retry.call(task);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (RetryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRetry4() {
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("call");
                return 2;
            }
        };

        Retryer<Integer> retry = RetryerBuilder.<Integer>newBuilder()
                .retryIfException()
                .retryIfResult(Predicates.equalTo(2))
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .withWaitStrategy(WaitStrategies.incrementingWait(2, TimeUnit.SECONDS, 1, TimeUnit.SECONDS))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        System.out.println(attempt.getAttemptNumber());
                        if (attempt.hasException()) {
                            attempt.getExceptionCause().printStackTrace();
                        }
                    }
                }).build();

        try {
            retry.call(task);
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("ExecutionException " + e.getMessage());
        } catch (RetryException e) {
            e.printStackTrace();
            System.out.println("RetryException " + e.getMessage());
        }
        System.out.println("over");
    }
}

//        Attempt：一次执行任务；
//        AttemptTimeLimiter：单次任务执行时间限制（如果单次任务执行超时，则终止执行当前任务）；
//        BlockStrategies：任务阻塞策略（通俗的讲就是当前任务执行完，下次任务还没开始这段时间做什么……），默认策略为：BlockStrategies.THREAD_SLEEP_STRATEGY 也就是调用 Thread.sleep(sleepTime);
//        RetryException：重试异常；
//        RetryListener：自定义重试监听器，可以用于异步记录错误日志；
//        StopStrategy：停止重试策略，提供三种：
//        StopAfterDelayStrategy ：设定一个最长允许的执行时间；比如设定最长执行10s，无论任务执行次数，只要重试的时候超出了最长时间，则任务终止，并返回重试异常RetryException；
//        NeverStopStrategy ：不停止，用于需要一直轮训知道返回期望结果的情况；
//        StopAfterAttemptStrategy ：设定最大重试次数，如果超出最大重试次数则停止重试，并返回重试异常；
//        WaitStrategy：等待时长策略（控制时间间隔），返回结果为下次执行时长：
//        FixedWaitStrategy：固定等待时长策略；
//        RandomWaitStrategy：随机等待时长策略（可以提供一个最小和最大时长，等待时长为其区间随机值）
//        IncrementingWaitStrategy：递增等待时长策略（提供一个初始值和步长，等待时间随重试次数增加而增加）
//        ExponentialWaitStrategy：指数等待时长策略；
//        FibonacciWaitStrategy ：Fibonacci 等待时长策略；
//        ExceptionWaitStrategy ：异常时长等待策略；
//        CompositeWaitStrategy ：复合时长等待策略

class AlwaysExceptionTask implements Callable<Boolean> {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");
    private int times = 1;

    @Override
    public Boolean call() throws Exception {
        System.out.println(df.format(new Date()));
        int thisTimes = times;
        times++;

        if (thisTimes == 1) {
            throw new NullPointerException();
        } else if (thisTimes == 2) {
            throw new IOException();
        } else if (thisTimes == 3) {
            throw new ArithmeticException();
        } else {
            throw new Exception();
        }
    }
}