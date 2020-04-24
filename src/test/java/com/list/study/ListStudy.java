package com.list.study;

import static java.util.Arrays.copyOfRange;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import org.junit.Test;

import javax.annotation.Nullable;

import java.util.List;

/**
 * @author xuedui.zhao
 * @create 2019-01-24
 */
public class ListStudy {

    @Test
    public void test01() {
        List<String> lists = Lists.newArrayListWithCapacity(5);
        lists.add("1");
        lists.add("2");
        System.out.println(lists.size());

        String str = Iterators.find(lists.iterator(), input -> input.equals("3"));
        System.out.println(str);
    }

    @Test
    public void testArrays() {
        String[] args = {"12", "23", "44"};
        String[] args2 = copyOfRange(args, 1, args.length, String[].class);
        for (String str : args2) {
            System.out.println(str);
        }
    }
}
