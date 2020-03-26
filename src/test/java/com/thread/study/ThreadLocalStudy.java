package com.thread.study;

import org.junit.Test;

/**
 * ThreadLocal学习
 *
 * @author xuedui.zhao
 * @create 2019-05-09
 */
public class ThreadLocalStudy {

    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    private static InheritableThreadLocal<Integer> threadLocalI = new InheritableThreadLocal<>();

    @Test
    public void test() {
        threadLocal.set("zhangsan");
        threadLocalI.set(new Integer(10));
        System.out.println(threadLocal.get());
        System.out.println(threadLocalI.get());
        threadLocal.remove();
        threadLocalI.remove();
        System.out.println(threadLocal.get());
        System.out.println(threadLocalI.get());
    }
}
