package com.util;

import org.junit.Test;

import java.util.ServiceLoader;

/**
 * java-util的使用
 *
 * @author xuedui.zhao
 * @create 2018-04-26
 */
public class JavaUtil {

    @Test
    public void test01() {
        ServiceLoader serviceLoader = ServiceLoader.load(String.class);
    }

    @Test
    public void test02() {
        Integer integer = new Integer(2);
        System.out.println(integer.equals(2));
    }
}
