package com.guava.study;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author badBoy
 * @create 2019-09-04
 */
public class GuavaCacheStudy {

    @Test
    public void testGuavaCache() throws Exception {
        LoadingCache<String, List<String>> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .removalListener((RemovalListener<String, List<String>>) notification -> {
                    notification.getValue();
                })
                .build(new CacheLoader<String, List<String>>() {
                    public List<String> load(String key) throws Exception {
                        return createListByKey(key);
                    }
                });
        System.out.println(cache.get("zhangsan"));

        System.out.println(cache.get("wangwu"));

        cache.put("wangwu", Lists.newArrayList("wangwu"));

        System.out.println(cache.get("wangwu"));

        System.out.println(cache.get(("lisi"), () -> Lists.newArrayList("lisi")));

        System.out.println(cache.get("naliu"));

        ConcurrentMap<String, List<String>> map = cache.asMap();
        System.out.println("map = " + map);

        map.put("naliu",Lists.newArrayList("naliu"));

        System.out.println(cache.get("naliu"));

        cache.invalidate("naliu");

        System.out.println(cache.get("naliu"));
    }

    private List<String> createListByKey(String key) {
        List<String> values = Lists.newArrayListWithCapacity(1);

        if ("zhangsan".equalsIgnoreCase(key))
            values.add("zhangsan");

        return values;
    }
}
