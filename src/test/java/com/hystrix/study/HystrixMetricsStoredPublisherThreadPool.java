package com.hystrix.study;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolMetrics;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisherThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xuedui.zhao
 * @create 2019-05-22
 */
public class HystrixMetricsStoredPublisherThreadPool implements HystrixMetricsPublisherThreadPool {

    private final HystrixThreadPoolKey threadPoolKey;
    private final HystrixThreadPoolMetrics metrics;
    private final HystrixThreadPoolProperties properties;

    public HystrixMetricsStoredPublisherThreadPool(
            HystrixThreadPoolKey threadPoolKey,
            HystrixThreadPoolMetrics metrics,
            HystrixThreadPoolProperties properties) {
        // 需要把  getMetricsPublisherFor**ThreadPool** 传入的几个参数的引用保存起来
        this.threadPoolKey = threadPoolKey;
        this.metrics = metrics;
        this.properties = properties;
    }

    @Override
    public void initialize() {
        System.out.println("HystrixMetricsStoredPublisherThreadPool ----------- ");
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(
                () -> {
                    StringBuilder sb = new StringBuilder("hystrix-metrics ------------------ ");
                    sb.append("\r\n");
                    sb.append("CurrentQueueSize: " + metrics.getCurrentQueueSize());
                    sb.append("CurrentCorePoolSize: " + metrics.getCurrentCorePoolSize());
                    sb.append("CumulativeCountThreadsRejected: " + metrics.getCumulativeCountThreadsRejected());
                    sb.append("CurrentActiveCount: " + metrics.getCurrentActiveCount());

                    // 在开启的线程中，定时读取 threadPoolKey、metrics、properties 三个字段的属性值
                    // 写入 mq 或者 db 供后续数据统计分析
                });
    }
}
