package com.thread.study;

import static com.github.phantomthief.util.MoreRunnables.runOnce;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-04-22
 * @Description
 */
public class RunOnceTest {

    @Test
    public void test() {
        runOnce(() -> System.out.println("1111"));
    }
}
