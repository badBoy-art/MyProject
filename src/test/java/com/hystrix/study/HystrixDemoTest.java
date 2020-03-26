package com.hystrix.study;

import com.netflix.hystrix.strategy.HystrixPlugins;
import org.junit.Test;

import java.util.concurrent.Future;

/**
 * hystrix--学习
 * https://www.jianshu.com/p/189e6675fb28
 * https://my.oschina.net/u/1169457/blog/1787414/
 *
 * @author xuedui.zhao
 * @create 2019-01-14
 */
public class HystrixDemoTest {

    static {
        HystrixPlugins.getInstance().registerMetricsPublisher(new HystrixMetricsPublisherSelf());
    }

    /**
     * 测试同步
     */
    @Test
    public void testSync() {
        System.out.println(new CommandHelloWorld("World").execute());
    }

    /**
     * 测试异步
     *
     * @throws Exception
     */
    @Test
    public void testAsyn() throws Exception {
        Future<String> fWorld = new CommandHelloWorld("World").queue();
        //一步执行用get()来获取结果
        System.out.println(fWorld.get());
    }

}
