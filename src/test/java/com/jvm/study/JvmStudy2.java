package com.jvm.study;

/**
 * @author badBoy
 * @create 2019-08-27
 */
public class JvmStudy2 {

    public static void main(String[] args) {
        while (true) {
            System.out.println("ThreadName = " + Thread.currentThread().getName()+ " " + System.currentTimeMillis());
        }
    }


}
