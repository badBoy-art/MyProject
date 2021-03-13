package com.algorithms.study.reverselink;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-10-20
 * @Description
 */
@Setter
@Getter
public class MyLinkedList {

    int size;
    Node first, last;

    public MyLinkedList() {
        size = 0;
        first = new Node(0);
        last = new Node(0);
        first.next = last;
        last.prev = first;
    }

    private class Node {
        int val;
        Node next;
        Node prev;

        Node(int val) {
            this.val = val;
        }
    }

    public int get(int index) {
        if (index < 0 || index >= size) return -1;

        return getNode(index).val;

    }
    private Node getNode(int index){

        int i = 0;
        Node node = first.next;
        while(i!=index){
            node = node.next;
            i++;
        }


        return node;
    }


    public void addAtHead(int val) {
        Node prev = first;
        Node next = first.next;
        size++;
        Node node = new Node(val);
        node.prev = prev;
        node.next = next;
        prev.next = node;
        next.prev = node;
    }

    public void addAtTail(int val) {
        Node prev = last.prev;
        Node next = last;
        size++;
        Node node = new Node(val);
        node.prev = prev;
        node.next = next;
        prev.next = node;
        next.prev = node;
    }

    public void addAtIndex(int index, int val) {
        if(index>size) return;
        //如果index<0，在头部添加
        if(index<0) index = 0;
        Node node = getNode(index);
        Node newNode = new Node(val);
        newNode.next = node;
        newNode.prev = node.prev;
        node.prev.next = newNode;
        node.prev = newNode;
        size++;


    }

    public void deleteAtIndex(int index) {
        if(index<0||index>=size) return;
        size--;
        Node node = getNode(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node = null;
    }

}
