package com.jvm.study;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import sun.misc.VMSupport;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author badBoy
 * @create 2019-08-28
 */
public class ClassStudy {

    @Test
    public void test() {
        String str = new String("abd");
        System.out.println(str.getClass());
        System.out.println(JvmStudy.class.getGenericInterfaces().length);
    }

    @Test
    public void test02() {
        int n = 17 - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        System.out.println(n + 1);
        int cap = 4;
        System.out.println(cap <<= 1);
    }

    @Test
    public void test05() {
        int size = 4;
        System.out.println(size >>>1);
        System.out.println(size + (size >>> 1) + 1);
    }

    @Test
    public void test03() {
        Integer integer = new Integer(10);

        System.out.println(ObjectSizeCalculator.getObjectSize(integer));

        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        System.out.println("===================java options=============== ");

        System.out.println(inputArguments);

        System.out.println(VMSupport.getAgentProperties());

        System.out.println(VMSupport.getVMTemporaryDirectory());
        //查看对象内部信息
        System.out.println("ClassLayout : " + ClassLayout.parseInstance(integer).toPrintable());

        //查看对象外部信息
        System.out.println("GraphLayout : " + GraphLayout.parseInstance(integer).toPrintable());

        //获取对象总大小
        System.out.println("size : " + GraphLayout.parseInstance(integer).totalSize());
    }

    @Test
    public void test04() {
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
        System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());

        Thread thread = new Thread(() -> System.out.println(Thread.currentThread().getContextClassLoader()));
        thread.start();
        LockSupport.parkNanos(3000);
        System.out.println(System.getSecurityManager());
    }
}
