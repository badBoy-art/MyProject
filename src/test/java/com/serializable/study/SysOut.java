package com.serializable.study;

import java.util.concurrent.TimeUnit;

/**
 * @author xuedui.zhao
 * @create 2019-08-19
 */
public class SysOut {

    private static boolean condition = true;

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            System.out.println("start");
            while (condition) {
                System.out.println("ing");
            }
            System.out.println("end");
        }).start();

        TimeUnit.SECONDS.sleep(1);
        condition = false;
        System.out.println("study.main 线程修改了condition变量 改为false" + condition);
    }

}
