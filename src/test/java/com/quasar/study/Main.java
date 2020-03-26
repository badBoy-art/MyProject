package com.quasar.study;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author badBoy
 * @create 2019-08-27
 */
public class Main {

    private static ExecutorService executorPool;
    private static int poolSize = 300;
    private static int requestSize = 300;

    private static void init() {
        MyService.DEFAULT_DEPEND_COUNT = 5; //MyService 调用其他服务的次数
        MyService.disposeType = MyService.DISPOSE_TYPE.COROUTINE; //MyService 处理类型，分为协程，单线程，多线程(forkjoin, executor)

        OtherService.IS_RANDOM_DELAY = Boolean.FALSE; // 延迟是否随即生成
        OtherService.DEFAULT_DELAY = 500; //默认延迟时间 IS_RANDOM_DELAY==FALSE 时候生效
        OtherService.DEFAULT_TIMEOUT = 10000; // 默认超时时间
        OtherService.DEFAULT_DELAY_UPPER = 10000; //随机延迟的上限 IS_RANDOM_DELAY==TRUE的时候生效

        poolSize = 1; // 请求方线程池的大小 这个无关乎MyService和OtherService
        requestSize = 1; //并发请求次数
        executorPool = Executors.newFixedThreadPool(poolSize);
    }

    public static void main(String[] args) {
        init();
        int successCount = 0;
        int failCount = 0;
        Long startTime = System.currentTimeMillis();

        final CountDownLatch countDownLatch = new CountDownLatch(requestSize);
        final List<Future<Boolean>> results = new ArrayList<Future<Boolean>>();
        for (int i = 0; i < requestSize; i++) {
            results.add(executorPool.submit(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    boolean result = new MyService().dispose();
                    countDownLatch.countDown();
                    return result;
                }
            }));
        }
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Future<Boolean> future : results) {
            try {
                if (future.get()) {
                    successCount++;
                } else {
                    failCount++;
                }
            } catch (Exception e) {
                failCount++;
            }
        }

        Long endTime = System.currentTimeMillis();

        System.out.println(String.format("请求完成, 耗时 %s ms, 请求成功%s次, 失败%s次", (endTime - startTime), successCount, failCount));

        finish();
    }

    private static void finish() {
        executorPool.shutdown();
    }
}
