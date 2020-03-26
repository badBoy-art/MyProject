package com.guava.study;

import org.junit.Test;

import com.google.common.collect.Range;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-06
 * @Description
 */
public class RangeStudy {

    @Test
    public void test01() {
        Range<Integer> range = Range.closed(0, 30);
        System.out.println(range.contains(31));
    }

}
