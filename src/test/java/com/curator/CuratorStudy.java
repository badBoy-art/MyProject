package com.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-18
 * @Description <a href="http://www.throwable.club/2018/12/16/zookeeper-curator-usage/"></a>
 */
public class CuratorStudy {

    @Test
    public void test() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString("host1:port1,host2:port2")
                        .connectionTimeoutMs(3000)
                        .sessionTimeoutMs(5000)
                        .maxCloseWaitMs(2000)
                        .retryPolicy(retryPolicy)
                        .namespace("test")
                        .build();
    }
}
