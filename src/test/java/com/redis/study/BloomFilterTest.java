package com.redis.study;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.hash.Funnels;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-06-04
 * @Description
 */
public class BloomFilterTest {

    @Test
    public void test() {
        RedisBloomFilter redisBloomFilter = new RedisBloomFilter();
        int expectedInsertions = 1000;
        double fpp = 0.1;
        redisBloomFilter.delete("bloom");
        BloomFilterHelper<CharSequence> bloomFilterHelper = new BloomFilterHelper<>(Funnels.stringFunnel(Charset.defaultCharset()), expectedInsertions, fpp);
        int j = 0;
        // 添加100个元素
        List<String> valueList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            valueList.add(i + "");
        }
        long beginTime = System.currentTimeMillis();
        redisBloomFilter.addList(bloomFilterHelper, "bloom", valueList);
        long costMs = System.currentTimeMillis() - beginTime;
        System.out.println("布隆过滤器添加" + 100 + "个值，耗时：" + costMs + "{}ms");
        for (int i = 0; i < 1000; i++) {
            boolean result = redisBloomFilter.contains(bloomFilterHelper, "bloom", i + "");
            if (!result) {
                j++;
            }
        }
    }

}
