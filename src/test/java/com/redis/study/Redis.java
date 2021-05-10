package com.redis.study;

import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * redis学习
 *
 * @author xuedui.zhao
 * @create 2018-05-15
 * @Description <a href="https://mp.weixin.qq.com/s/GwjQalQ9ZkBbTBtEKpbkMw"></a>
 */
public class Redis {

    @Test
    public void test01() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("test", "hello world");
        System.out.println(jedis.get("test"));
    }

    @Test
    public void test02() {
        /**
         * 缓存穿透：缓存穿透是指用户请求的数据在缓存中不存在即没有命中，同时在数据库中也不存在，导致用户每次请求该数据都要去数据库中查询一遍，然后返回空
         * 解决方案：
         * 一、Bloom Filter
         * 二、缓存没有直接返回空
         */
    }

    @Test
    public void test03() {
        /**
         * 缓存击穿：缓存击穿，是指一个key非常热点，在不停的扛着大并发，大并发集中对这一个点进行访问，当这个key在失效的瞬间，持续的大并发就穿破缓存，直接请求数据库
         * 解决方案：
         * 一、使用互斥锁（mutex key）：这种思路比较简单，就是让一个线程回写缓存，其他线程等待回写缓存线程执行完，重新读缓存即可
         * 二、热点数据永不过期
         *   永不过期实际包含两层意思：
         *   1.物理不过期，针对热点key不设置过期时间
         *   2.逻辑过期，把过期时间存在key对应的value里，如果发现要过期了，通过一个后台的异步线程进行缓存的构建
         */
    }

    @Test
    public void test04() {
        /**
         * 缓存雪崩：缓存雪崩是指缓存中数据大批量到过期时间，而查询数据量巨大，请求直接落到数据库上，引起数据库压力过大甚至宕机。
         * 和缓存击穿不同的是，缓存击穿指并发查同一条数据，缓存雪崩是不同数据都过期了，很多数据都查不到从而查数据库
         * 解决方案：
         * 一：均匀过期
         * 二：加互斥锁
         * 三：缓存永不过期
         * 四：双层缓存策略
         */
    }

    @Test
    public void test05() {
        /**
         * 缓存预热：缓存预热就是系统上线后，将相关的缓存数据直接加载到缓存系统，这样就可以避免在用户请求的时候，先查询数据库，然后再将数据回写到缓存
         */
    }

    @Test
    public void test06() {
        /**
         * 缓存降级：指缓存失效或缓存服务器挂掉的情况下，不去访问数据库，直接返回默认数据或访问服务的内存数据
         * 降级是有损操作，需要考虑降级对业务的影响程度
         */
    }

}
