package com.guava.study;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Ticker;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author badBoy
 * @create 2019-09-04
 */
public class GuavaCacheStudy {

    MyTicker myTicker = new MyTicker();

    LoadingCache<String, List<String>> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .refreshAfterWrite(5, TimeUnit.MINUTES)
            .ticker(myTicker)
            .removalListener((RemovalListener<String, List<String>>) notification -> {
                notification.getValue();
            })
            .build(new CacheLoader<String, List<String>>() {
                @Override
                public List<String> load(String key) throws Exception {
                    return createListByKey(key);
                }

//                @Override
//                public Map<String, List<String>> loadAll(Iterable<? extends String> keys) throws Exception {
//                    return createListByKeys(Lists.newArrayList(keys));
//                }
            });

    @Test
    public void testGuavaCache() throws Exception {
        System.out.println("---------------start---------------");
        System.out.println(cache.getAll(Lists.newArrayList("zhangsan", "lisi", "wangwu")));
        System.out.println("zhangsan = " + cache.get("zhangsan"));

        cache.put("wangwu", Lists.newArrayList("wangwu"));
        System.out.println("wangwu = " + cache.get("wangwu"));
        System.out.println(cache.getAll(Lists.newArrayList("zhangsan", "wangwu")));
        System.out.println(cache.getAll(Lists.newArrayList("zhangsan", "lisi", "wangwu")));

        // 模拟时间流逝
        myTicker.addElapsedTime(TimeUnit.NANOSECONDS.convert(6, TimeUnit.MINUTES));

        System.out.println("expire wangwu =  " + cache.get("wangwu"));
        System.out.println("callable lisi = " + cache.get(("lisi"), () -> Lists.newArrayList("lisi")));
        System.out.println("naliu =  " + cache.get("naliu"));
        ConcurrentMap<String, List<String>> map = cache.asMap();
        System.out.println("asMap = " + map);

        map.put("naliu", Lists.newArrayList("naliu"));
        System.out.println("map put naliu = " + cache.get("naliu"));

        cache.invalidate("naliu");
        System.out.println("after invalidate naliu map = " + cache.asMap());
    }

    private Map<String, List<String>> createListByKeys(List<String> keys) {
        System.out.println("----------getAll------------");
        Map<String, List<String>> result = Maps.newHashMap();
        for (String key : keys) {
            result.put(key, createListByKey(key));
        }
        return result;
    }


    private List<String> createListByKey(String key) {
        System.out.println("----------getOne---------");
        List<String> values = Lists.newArrayListWithCapacity(1);
        if ("zhangsan".equalsIgnoreCase(key)) {
            values.add("zhangsan");
        } else if ("lisi".equalsIgnoreCase(key)) {
            values.add("lisi");
        }
        return values;
    }

    private static class MyTicker extends Ticker {
        private long start = Ticker.systemTicker().read();
        private long elapsedNano = 0;

        @Override
        public long read() {
            return start + elapsedNano;
        }

        public void addElapsedTime(long elapsedNano) {
            this.elapsedNano = elapsedNano;
        }
    }
}
