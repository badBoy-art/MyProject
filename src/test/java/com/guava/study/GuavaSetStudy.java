package com.guava.study;

import com.google.common.collect.Sets;
import com.jdbc.test.SexEnum;
import org.junit.Test;

import java.util.Set;

/**
 * guava Set学习
 *
 * @author xuedui.zhao
 * @create 2019-05-09
 */
public class GuavaSetStudy {

    @Test
    public void test() {
        Set<Integer> sets = Sets.newHashSet(1, 2, 3, 4, 5, 6);
        Set<Integer> sets2 = Sets.newHashSet(3, 4, 5, 6, 7, 8, 9);
        // 交集
        System.out.println("交集为：");
        Sets.SetView<Integer> intersection = Sets.intersection(sets, sets2);
        for (Integer temp : intersection) {
            System.out.print(temp + " ");
        }
        // 差集
        System.out.println("差集为：");
        Sets.SetView<Integer> diff = Sets.difference(sets2, sets);
        for (Integer temp : diff) {
            System.out.print(temp + " ");
        }
        // 并集
        System.out.println("并集为：");
        Sets.SetView<Integer> union = Sets.union(sets, sets2);
        for (Integer temp : union) {
            System.out.print(temp + " ");
        }
    }

    @Test
    public void testMultisets() {
        Set<SexEnum> set = Sets.immutableEnumSet(SexEnum.man);

    }
}
