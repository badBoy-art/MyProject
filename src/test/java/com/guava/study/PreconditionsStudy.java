package com.guava.study;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Preconditions学习
 *
 * @author xuedui.zhao
 * @create 2019-07-26
 */
public class PreconditionsStudy {

    @Test
    public void testPreconditions() {
        List<String> list = Lists.newArrayList("a", "b", "c");
        System.out.println(
                Preconditions.checkElementIndex(4, list.size()));
    }
}
