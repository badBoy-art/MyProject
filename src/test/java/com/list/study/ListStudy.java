package com.list.study;

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
}
