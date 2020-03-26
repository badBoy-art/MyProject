package com.effectsOfTypeErasureAndBridgeMethods.study;

import org.junit.Test;

/**
 * 擦除法学习
 *
 * @author xuedui.zhao
 * @create 2018-12-04
 */
public class ErasureStudy {

    @Test
    public void test() {
        Child clild = new Child("str");
        Parent parent = clild;            // A raw type - compiler throws an unchecked warning
        parent.setData("Hello");
        String x = clild.data;       // Causes a ClassCastException to be thrown.
        System.out.println("x = " + x);
    }

    @Test
    public void test02() {
        Child child = new Child("str");
        Parent parent = (Parent)child;            // A raw type - compiler throws an unchecked warning
        parent.setData("Hello");
        String x = child.data;       // Causes a ClassCastException to be thrown.
        System.out.println("x = " + x);
    }

}
