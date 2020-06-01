package com.caffeine;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-06-01
 * @Description
 */
public class CaffeineCache {

    @Test
    public void test() {
        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterAccess(1,TimeUnit.SECONDS)
                .maximumSize(10)
                .build();
        cache.put("hello","hello");
        System.out.println(cache.asMap());
    }

}
