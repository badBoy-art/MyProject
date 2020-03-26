package com.hystrix.study;

import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserMetrics;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisherCollapser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xuedui.zhao
 * @create 2019-05-22
 */
public class HystrixMetricsStoredPublisherCollapser implements HystrixMetricsPublisherCollapser {

    private final HystrixCollapserKey collapserKey;
    private final HystrixCollapserMetrics metrics;
    private final HystrixCollapserProperties properties;

    public HystrixMetricsStoredPublisherCollapser(HystrixCollapserKey collapserKey, HystrixCollapserMetrics metrics, HystrixCollapserProperties properties) {
        this.collapserKey = collapserKey;
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
                    sb.append("CollapserKey: " + collapserKey.name());
                    sb.append("ShardSizeMean: " + metrics.getShardSizeMean());
                    sb.append("timerDelayInMilliseconds: " + properties.timerDelayInMilliseconds());

                    // 在开启的线程中，定时读取 threadPoolKey、metrics、properties 三个字段的属性值
                    // 写入 mq 或者 db 供后续数据统计分析
                });
    }
}
