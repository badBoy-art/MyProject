package com.guava.study;

import com.google.common.base.Joiner;
import org.junit.Test;

/**
 * @author xuedui.zhao
 * @create 2018-12-13
 */
public class JoinerStudy {

    @Test
    public void test() {
    System.out.println(
        Joiner.on(' ').join("","",1, 2, 3));
    }
}
