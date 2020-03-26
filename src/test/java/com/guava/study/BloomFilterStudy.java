package com.guava.study;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;
import org.junit.Test;

/**
 * 布隆过滤器
 *
 * @author xuedui.zhao
 * @create 2019-04-29
 */
public class BloomFilterStudy {


     private final BloomFilter<String> dealIdBloomFilter = BloomFilter.create(new Funnel<String>() {

        private static final long serialVersionUID = 1L;

        @Override
        public void funnel(String arg0, PrimitiveSink arg1) {

            arg1.putString(arg0, Charsets.UTF_8);
        }

     }, 1024*1024*32, 0.0000001d);


    @Test
    public void testBloomFilter() {
        String deal_id = "deal_id";
        // dealIdBloomFilter.put(deal_id);
        boolean exists = dealIdBloomFilter.mightContain(deal_id);
        if(!exists){
            dealIdBloomFilter.put(deal_id);
        }
        System.out.println("deal_id_exit_result = " + exists);
    }
}
