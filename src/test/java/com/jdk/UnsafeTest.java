package com.jdk;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import org.junit.Test;

import sun.misc.Unsafe;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-02-26
 * @Description <href a="https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html"></href>
 */
public class UnsafeTest {

    /**
     * 抛异常
     */
    @Test
    public void test() {
        Unsafe unsafe = Unsafe.getUnsafe();
        System.out.println(unsafe.addressSize());
    }

    @Test
    public void testRef() {
        Unsafe unsafe = reflectGetUnsafe();
        System.out.println(unsafe.addressSize());
        System.out.println(unsafe.pageSize());
    }

    private static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Test
    public void testLam() {
        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("lambda");
    }
}
