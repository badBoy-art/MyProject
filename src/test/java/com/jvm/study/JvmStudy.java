package com.jvm.study;

/**
 * @author xuedui.zhao
 * @create 2018-11-28
 */
public class JvmStudy {

    public static void main(String[] args) {
        while (true) {
            System.out.println("ThreadName = " + Thread.currentThread().getName()+ " " + System.currentTimeMillis());
            System.out.println("----"+Runtime.getRuntime().availableProcessors());
        }
    }
}
