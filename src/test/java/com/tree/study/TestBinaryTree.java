package com.tree.study;

import org.junit.Before;
import org.junit.Test;

/**
 * 二叉树测试
 *
 * @author xuedui.zhao
 * @create 2018-05-10
 */
public class TestBinaryTree {

    BinaryTree binaryTree = new BinaryTree(10);

    @Before
    public void initBinaryTree() {
        for (int i = 1; i <= 500; i++) {
            binaryTree.insert(i);
        }
    }

    @Test
    public void testInsert() {
        //前序遍历
        binaryTree.preOrderTraverse();
        //层级遍历
        binaryTree.levelTraverse();
        //中序遍历
        binaryTree.inOrderTraverse();
        //后续递归
        binaryTree.postOrderTraverse();
        //前序非递归 -- stack
        binaryTree.preOrderByStack();
        //前序非递归 -- morris
        binaryTree.preOrderMorris();
    }

}
