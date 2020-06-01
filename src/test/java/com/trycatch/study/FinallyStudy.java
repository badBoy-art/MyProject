package com.trycatch.study;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * @author badBoy
 * @create 2019-11-01
 */
public class FinallyStudy {

    @Test
    public void testFinally() {
        System.out.println(getResult());
    }

    private String getResult() {
        String str;
        try {
            str = "abc";
            return str;
        } finally {
            str = "edc";
            System.out.println(str);
        }
    }

    @Test
    public void test() {
        try {
            throw new IllegalStateException(
                    String.format("测试而已 %s", "Hello World"));
        } catch (Throwable t) {
            System.out.println("我Catch住了: " + t.getMessage());
        }
    }

}
