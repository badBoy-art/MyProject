package com.effectsOfTypeErasureAndBridgeMethods.study;

/**
 * Bridge
 *
 * @author xuedui.zhao
 * @create 2018-12-04
 */
public class MyNode extends Node {

    public MyNode(Integer data) { super(data); }

    // 编译时生成桥接方法
    //
    public void setData(Object data) {
        setData((Integer) data);
    }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }

    public void say(String str) {
        System.out.println("MyNode--- str");
    }

    @Override
    public void doSomeThing(String str) {
        System.out.println("MyNode--- str");
    }
}
