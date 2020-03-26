package com.hashmap.study;

import com.Extends.study.WhiteDog;
import com.google.common.base.Preconditions;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * hashMap 源码学习
 *
 * @author xuedui.zhao
 * @create 2018-03-09
 */
public class HashMapStudy {

    @Test
    public void hashMap() {
        Map<String, String> hashMap = new HashMap<>(15);
        hashMap.put(null, "a");
        hashMap.put("b", "b");
        System.out.println(hashMap);
    }

    @Test
    public void currentHashMap() {
        Map<String, String> currentHashMap = new ConcurrentHashMap<>(16);
        currentHashMap.put("a", "a");
    }

    @Test
    public void testFloatIsNaN() {
        Preconditions.checkArgument(Float.isNaN(Float.NaN));
    }

    @Test
    public void testInteger() {
        System.out.print(Integer.highestOneBit(10));
    }

    @Test
    public void testLinkedHashMap() {
        Map<String, WhiteDog> map = new LinkedHashMap<>();
        Map<String, WhiteDog> linkedMap = new LinkedHashMap<>(16, 0.75f, true);
        for (int i = 1; i < 3; i++) {
            WhiteDog whiteDog = new WhiteDog();
            whiteDog.setName("name" + i);
            whiteDog.setAge(i);
            map.put("name" + i, whiteDog);
            linkedMap.put("name" + i, whiteDog);
        }

        System.out.println(map);

        map.get("name2");

        System.out.println(map);

        System.out.println(linkedMap);

        linkedMap.get("name1");

        System.out.println(linkedMap);
    }
}
