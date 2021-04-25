package com.caffeine;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-06-01
 * @Description <a href="https://mp.weixin.qq.com/s/OMeLlOy0DSF1hnuF2soekg"></a>
 */
public class CaffeineCache {

    @Test
    public void test() {
        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .expireAfterAccess(10, TimeUnit.SECONDS)
                .maximumSize(1000)
                .build();

        com.google.common.cache.Cache<String, String> loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .expireAfterAccess(10, TimeUnit.SECONDS)
                .maximumSize(1000)
                .build();

        for (int i = 0; i < 500; i++) {
            cache.put("hello" + i, "hello" + i);
            loadingCache.put("hello" + i, "hello" + i);
        }
        long startTime = System.nanoTime();
        cache.getIfPresent("hello100");
        System.out.println(System.nanoTime() - startTime);

        long startTime2 = System.nanoTime();
        loadingCache.getIfPresent("hello100");
        System.out.println(System.nanoTime() - startTime2);

    }


}
