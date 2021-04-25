package com.guava.study;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * @author Created on 2020-03-23
 * @Description <a href="https://xiaobazhang.github.io/2018/06/19/MurmurHash%E7%AE%97%E6%B3%95/"></a>
 */
public class MurmurHashStudy {

    /**
     * 相同的String值一定得出相同hash值，就一定要新创建hasher
     */
    @Test
    public void test01() {

        HashFunction hf = Hashing.murmur3_128();
        String str = "test";
        System.out.println(hf.newHasher().putString(str, Charsets.UTF_8).hash().asInt());
        System.out.println(hf.newHasher().putString(str, Charsets.UTF_8).hash().asInt());
    }

    @Test
    public void test02() {
        HashFunction hf = Hashing.murmur3_128();
        Hasher hasher = hf.newHasher();
        String str = "test";
        System.out.println(hasher.putString(str, Charsets.UTF_8).hash().asInt());
        System.out.println(hasher.putString(str, Charsets.UTF_8).hash().asInt());
    }
}
