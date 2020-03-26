package com.tree.study;

/**
 * 二叉树节点
 *
 * @author xuedui.zhao
 * @create 2018-05-10
 */
public class Node {

    Integer value;
    Node leftChild;
    Node rightChild;

    Node (Integer value) {
        this.value = value;
    }

    public void display() {
        System.out.println(this.value + "\t");
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
