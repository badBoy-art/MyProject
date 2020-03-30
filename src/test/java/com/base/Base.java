package com.base;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import com.google.common.collect.Lists;


/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-27
 * @Description
 */
public class Base {

    @Test
    public void tsetFilter() {
        List<String> strs = Lists.newArrayList("zhangsan","lisi","wangwu");
        System.out.println(strs.stream().filter(s -> !"lisi".equalsIgnoreCase(s)).collect(toList()));
    }
}
