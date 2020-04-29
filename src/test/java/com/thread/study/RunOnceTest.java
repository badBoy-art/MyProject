package com.thread.study;

import static com.github.phantomthief.util.MoreRunnables.runOnce;

import java.util.concurrent.ThreadFactory;

import org.junit.Test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

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

    @Test
    public void testThread() {
        System.out.println(Thread.getAllStackTraces());
    }

    @Test
    public void testThreadFactory() {
        ThreadFactory tf = new ThreadFactoryBuilder()
                .setNameFormat("scheduled-task-name" + "-%d").build();
        tf.newThread(() -> System.out.println(Thread.currentThread().getName())).start();
    }
}
