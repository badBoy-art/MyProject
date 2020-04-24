package com.zookeeper.study;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

/**
 * Curator-Demo
 *
 * @author xuedui.zhao
 * @create 2018-05-07
 *
 * <a href="https://mp.weixin.qq.com/s/vZOVlKCuT9IzTgJmLxueIQ"></a>
 */
public class ZookeeperStudy {

    @Test
    public void testCurator() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", 2000, 2000, retryPolicy);
        client.start();
        CuratorFrameworkState state = client.getState();
        client.create().forPath("/zookeeper/data");
        client.delete().forPath("/zookeeper/data");
    }
}
