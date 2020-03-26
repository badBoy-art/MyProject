package com.effectsOfTypeErasureAndBridgeMethods.study;

/**
 * Child类的方法表里有哪些东西：
 *
 * 1. sayHello(Object value) : 从类型被擦除后的超类中继承过来
 * 2. sayHello(String value) : 自己新增的方法，和超类毫无联系
 * 3. 一些从Object类继承来的方法，这里忽略
 *
 * @author xuedui.zhao
 * @create 2018-12-04
 */
public class Child extends Parent<String> {

    public Child(String data) { super(data); }

    public void setData(String data) {
        System.out.println("Child.setData");
        super.setData(data);
    }

}
