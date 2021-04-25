package com.algorithms.study.substring;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Created on 2021-03-25
 *
 * @Description 最长子串
 */
public class SubString {

    @Test
    public void test() {
        String str = "abcdefsdfrhjuc";
        Map<Character, Integer> dic = new HashMap<>();
        int res = 0, tmp = 0;
        for (int i = 0; i < str.length(); i++) {
            int j = dic.getOrDefault(str.charAt(i), -1);
            dic.put(str.charAt(i), i);
            tmp = tmp < i - j ? tmp + 1 : i - j;
            res = Math.max(res, tmp);
        }
        System.out.println(res);
    }

}
