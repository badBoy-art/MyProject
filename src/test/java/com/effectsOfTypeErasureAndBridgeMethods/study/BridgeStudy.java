package com.effectsOfTypeErasureAndBridgeMethods.study;

import org.junit.Test;

/**
 * 桥接法
 *
 * @author xuedui.zhao
 * @create 2018-12-04
 */
public class BridgeStudy {

    @Test
    public void test() {
        MyNode mn = new MyNode(5);
        Node n = mn;            // A raw type - compiler throws an unchecked warning
        n.setData(23);
        Integer x =(Integer)mn.data;
    }

    @Test
    public void testOverride() {
        String string = "";
        Node node = new MyNode(0);
        node.say(string);
    }

    @Test
    public void testOverride1() {
        String string = "";
        Node node = new MyNode(0);
        node.doSomeThing(string);
    }
}
