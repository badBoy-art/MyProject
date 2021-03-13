package com.tree.study;

import org.junit.Test;

/**
 * 二叉树测试
 *
 * @author xuedui.zhao
 * @create 2018-05-10
 */
public class TestBinaryTree {

    @Test
    public void testInsert() {
        BinaryTree binaryTree = new BinaryTree(10);
        binaryTree.insert(1);
        binaryTree.insert(5);
        binaryTree.insert(15);
        binaryTree.insert(20);
        binaryTree.preOrderTraverse();
        binaryTree.postOrderTraverse();
        binaryTree.levelTraverse();
        binaryTree.inOrderTraverse();
        binaryTree.delete(15);
        binaryTree.postOrderTraverse();
    }

}
