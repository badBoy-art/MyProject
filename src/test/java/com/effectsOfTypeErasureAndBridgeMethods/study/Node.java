package com.effectsOfTypeErasureAndBridgeMethods.study;

/**
 * Bridge
 *
 * @author xuedui.zhao
 * @create 2018-12-04
 */
public class Node {

    public Object data;

    public Node(Object data) { this.data = data; }

    public void setData(Object data) {
        System.out.println("Node.setData");
        this.data = data;
    }

    public void say(Object obj) {
        System.out.println("Node ---- obj");
    }

    public void doSomeThing(String str) {
        System.out.println("Node ---- str");
    }

    public void doSomeThing(Object obj) {
        System.out.println("Node ---- obj");
    }
}
