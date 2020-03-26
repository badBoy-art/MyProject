package com.overload.study;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * @author xuedui.zhao
 * @create 2018-12-03
 */
public class OverloadStudy {

    @Test
    public void test() {

        System.out.println("----------非自动拆装箱-----------");
        add(1,3);
        add(Integer.valueOf(1),Integer.valueOf(3));
        System.out.println("----------自动拆装箱-----------");
        subtraction(3,1);
        subtraction(Integer.valueOf(3),1);
        subtraction(3, Integer.valueOf(1));
        System.out.println("----------可变参数-----------");
        //String为Object的子类，java编译器会认为第二个方法更贴切
        invoke(null,1);
        //此时10 不是String类型 则只能调两个参数的方法
        invoke(10, 5);
        invoke(null,1,2);
        invoke(null,1,2, 3);
        invoke(null, new Object[]{1});
    }

    private void add(int a,int b) {
       System.out.println("int a + b = " + a + b);
    }

    private void add(Integer a,Integer b) {
        System.out.println("Integer a + b = " + a + b);
    }

    private void subtraction(Integer a, int b) {
        System.out.println("subtraction a - b =" + (a - b));
    }

    private void invoke(Object object, Object... args) {
        System.out.println("invoke two obj = " + object + " args = " + JSON.toJSON(args));
    }

    private void invoke(String str, Object object, Object... args) {
        System.out.println("invoke three str = " + str + " obj = " + object + " args = " + JSON.toJSON(args));
    }

}
