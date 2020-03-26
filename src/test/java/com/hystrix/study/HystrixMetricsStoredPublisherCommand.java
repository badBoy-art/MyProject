package com.hystrix.study;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisherCommand;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xuedui.zhao
 * @create 2019-05-22
 */
public class HystrixMetricsStoredPublisherCommand implements HystrixMetricsPublisherCommand {

    private final HystrixCommandKey commandKey;
    private final HystrixCommandGroupKey commandGroupKey;
    private final HystrixCommandMetrics metrics;
    private final HystrixCircuitBreaker circuitBreaker;
    private final HystrixCommandProperties properties;

    public HystrixMetricsStoredPublisherCommand(HystrixCommandKey commandKey, HystrixCommandGroupKey commandGroupKey, HystrixCommandMetrics metrics, HystrixCircuitBreaker circuitBreaker, HystrixCommandProperties properties) {
        this.commandKey = commandKey;
        this.commandGroupKey = commandGroupKey;
        this.metrics = metrics;
        this.circuitBreaker = circuitBreaker;
        this.properties = properties;
    }

    /**
     * 只会在注册的时候执行一次，所以需要我们自己开启线程定时监控 构造函数传入的这几个对象的状态
     */
    @Override
    public void initialize() {
        System.out.println("HystrixMetricsStoredPublisherThreadPool ----------- ");
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(
                () -> {
                    StringBuilder sb = new StringBuilder("hystrix-metrics ------------------ ");
                    sb.append("\r\n");
                    sb.append("CurrentThreadPoolKey: " + metrics.getThreadPoolKey());
                    sb.append("CommandKeyName" + commandKey.name());

                    // 在开启的线程中，定时读取 threadPoolKey、metrics、properties 三个字段的属性值
                    // 写入 mq 或者 db 供后续数据统计分析
                });
    }
}
