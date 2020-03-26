package com.effectsOfTypeErasureAndBridgeMethods.study;

/**
 * 对类型变量进行替换的规则有两条：
 * 若为无限定的类型，如<T>，被替换为Object
 * 若为限定类型，如<T extends Comparable & Serializable>，则用第一个限定的类型变量来替换，在这里被替换为Comparable
 *
 * @author xuedui.zhao
 * @create 2018-12-04
 */
public class Parent<T> {

    public T data;

    public Parent(T data) { this.data = data; }

    public void setData(T data) {
        System.out.println("Parent.setData");
        this.data = data;
    }

}
