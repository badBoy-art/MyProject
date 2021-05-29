package com.staticstu.study;

/**
 * @author badBoy
 * @create 2019-11-11
 */
public class Super {

    //包括静态代码和变量，它们的级别是相同的，按照代码中出现的顺序初始化
    public static int m = 11;

    static {
        System.out.println("执行了super类静态语句块 m = " + m);
    }
}
