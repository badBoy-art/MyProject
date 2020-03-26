package com.redis.study;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * redis学习
 *
 * @author xuedui.zhao
 * @create 2018-05-15
 */
public class Redis {

    @Test
    public void test01() {
        Jedis jedis = new Jedis("localhost",6379);
        jedis.set("test","hello world");
        System.out.println(jedis.get("test"));
    }
}
