package com.jvm.study;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import sun.misc.VMSupport;

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
        System.out.println(size >>> 1);
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

    @Test
    public void test06() {
        //isInterrupted() interrupted() 执行不到最后  会卡在LockSupport.park(this)
        Thread.currentThread().isInterrupted();
        //Thread.currentThread() 程序可执行到左后
        Thread.currentThread().interrupt();

        System.out.println("int = " + Thread.currentThread().isInterrupted());

        LockSupport.park(this);
        // 根据 park 方法 API描述，程序在下述三种情况会继续向下执行
        //  1. 被 unpark
        //  2. 被中断(interrupt)
        //  3. 其他不合逻辑的返回才会继续向下执行

        // 因上述三种情况程序执行至此，返回当前线程的中断状态，并清空中断状态
        // 如果由于被中断，该方法会返回 true
        System.out.println(Thread.currentThread().isInterrupted());
    }

}
