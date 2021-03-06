package com.tree.study;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 二叉树
 *
 * @author xuedui.zhao
 * @create 2018-05-10
 * <a href="https://blog.csdn.net/My_Jobs/article/details/43451187"></a>
 */
public class BinaryTree {

    private Node root = null;

    BinaryTree(Integer value) {
        root = new Node(value);
        root.leftChild = null;
        root.rightChild = null;
    }

    //查找
    public Node findKey(Integer value) {
        Node current = root;
        while (true) {
            if (current.value == current.value) {
                return current;
            } else if (value < current.value) {
                current = current.rightChild;
            } else if (value > current.value) {
                current = current.leftChild;
            }

            if (current == null) {
                return null;
            }
        }
    }

    //插入
    public String insert(int value) {
        String error = null;
        Node node = new Node(value);

        if (root == null) {
            root = node;
            root.rightChild = null;
            root.leftChild = null;
        } else {
            Node current = root;
            Node parent = null;
            while (true) {
                if (value < current.value) {
                    parent = current;
                    current = current.leftChild;
                    if (current == null) {
                        parent.leftChild = node;
                        break;
                    }
                } else if (value > current.value) {
                    parent = current;
                    current = current.rightChild;
                    if (current == null) {
                        parent.rightChild = node;
                        break;
                    }
                } else {
                    error = "having same value in binary tree";
                    break;
                }
            }
        }

        return error;
    }

    public int getMinValue() {
        Node current = root;
        while (true) {
            if (current == null) {
                return current.value;
            }
            current = current.leftChild;
        }
    }

    /**
     * 左子树---> 根结点 ---> 右子树
     */
    //递归中序遍历
    public void inOrderTraverse() {
        System.out.println("中序遍历");
        inOrderTraverse(root);
        System.out.println();
    }

    public void inOrderTraverse(Node node) {
        if (node == null) {
            return;
        }
        inOrderTraverse(node.leftChild);
        node.display();
        inOrderTraverse(node.rightChild);
    }

    //中序遍历 非递归
    public void inOrderByStack() {
        System.out.println("中序遍历非递归");
        Stack<Node> stack = new Stack<Node>();

        Node current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.leftChild;
            }

            if (!stack.isEmpty()) {
                current = stack.pop();
                current.display();
                current = current.rightChild;
            }
        }
        System.out.println();
    }

    /**
     * 根结点 ---> 左子树 ---> 右子树
     */
    //前序递归遍历
    public void preOrderTraverse() {
        System.out.println("前序遍历:");
        preOrderTraverse(root);
        System.out.println();
    }

    private void preOrderTraverse(Node node) {
        if (node == null)
            return;

        node.display();
        preOrderTraverse(node.leftChild);
        preOrderTraverse(node.rightChild);
    }

    /**
     * 对于任意结点node，第一部分即直接访问之，之后在判断左子树是否为空，不为空时即重复上面的步骤，直到其为空。若为空，则需要访问右子树。
     * 注意，在访问过左孩子之后，需要反过来访问其右孩子，所以，需要栈这种数据结构的支持。对于任意一个结点node，具体步骤如下：
     * <p>
     * a)访问之，并把结点node入栈，当前结点置为左孩子；
     * b)判断结点node是否为空，若为空，则取出栈顶结点并出栈，将右孩子置为当前结点；
     * 否则重复a)步直到当前结点为空或者栈为空（可以发现栈中的结点就是为了访问右孩子才存储的）
     */
    //前序非递归
    public void preOrderByStack() {
        long startTime = System.currentTimeMillis();
        System.out.println("前序非递归遍历:");
        Stack<Node> stack = new Stack<Node>();
        Node current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current.display();
                current = current.leftChild;
            }

            if (!stack.isEmpty()) {
                current = stack.pop();
                current = current.rightChild;
            }
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    /**
     * 左子树 ---> 右子树 ---> 根结点
     */
    //后续递归
    public void postOrderTraverse() {
        System.out.println("后序遍历:");
        postOrderTraverse(root);
        System.out.println();
    }

    private void postOrderTraverse(Node node) {
        if (node == null)
            return;

        postOrderTraverse(node.leftChild);
        postOrderTraverse(node.rightChild);
        node.display();
    }

    //后续非递归遍历
    public void postOrderByStack() {
        System.out.println("后序非递归遍历:");
        Stack<Node> stack = new Stack<Node>();
        Node current = root;
        Node preNode = null;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.leftChild;
            }

            if (!stack.isEmpty()) {
                current = stack.peek().rightChild;
                if (current == null || current == preNode) {
                    current = stack.pop();
                    current.display();
                    preNode = current;
                    current = null;
                }
            }
        }
        System.out.println();
    }

    /**
     * 层级遍历
     *
     * @param
     */
    public void levelTraverse() {
        System.out.println("层级遍历");
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            node.display();
            if (node.leftChild != null) {
                queue.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                queue.offer(node.rightChild);
            }
        }
        System.out.println();
    }

    /**
     * 得到后继节点，即删除节点的左后代
     */
    private Node getSuccessor(Node delNode) {
        Node successor = delNode;
        Node successorParent = null;
        Node current = delNode.rightChild;

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }

        //如果后继节点不是删除节点的右子节点时，
        if (successor != delNode.rightChild) {
            //要将后继节点的右子节点指向后继结点父节点的左子节点，
            successorParent.leftChild = successor.rightChild;
            //并将删除节点的右子节点指向后继结点的右子节点
            successor.rightChild = delNode.rightChild;
        }
        //任何情况下，都需要将删除节点的左子节点指向后继节点的左子节点
        successor.leftChild = delNode.leftChild;

        return successor;
    }

    public boolean delete(int value) {
        Node current = root;    //需要删除的节点
        Node parent = null;     //需要删除的节点的父节点
        boolean isLeftChild = true;   //需要删除的节点是否父节点的左子树

        while (true) {
            if (value == current.value) {
                break;
            } else if (value < current.value) {
                isLeftChild = true;
                parent = current;
                current = current.leftChild;
            } else {
                isLeftChild = false;
                parent = current;
                current = current.rightChild;
            }

            //找不到需要删除的节点，直接返回
            if (current == null)
                return false;
        }

        //分情况考虑
        //1、需要删除的节点为叶子节点
        if (current.leftChild == null && current.rightChild == null) {
            //如果该叶节点为根节点，将根节点置为null
            if (current == root) {
                root = null;
            } else {
                //如果该叶节点是父节点的左子节点，将父节点的左子节点置为null
                if (isLeftChild) {
                    parent.leftChild = null;
                } else { //如果该叶节点是父节点的右子节点，将父节点的右子节点置为null
                    parent.rightChild = null;
                }
            }
        }
        //2、需要删除的节点有一个子节点，且该子节点为左子节点
        else if (current.rightChild == null) {
            //如果该节点为根节点，将根节点的左子节点变为根节点
            if (current == root) {
                root = current.leftChild;
            } else {
                //如果该节点是父节点的左子节点，将该节点的左子节点变为父节点的左子节点
                if (isLeftChild) {
                    parent.leftChild = current.leftChild;
                } else {  //如果该节点是父节点的右子节点，将该节点的左子节点变为父节点的右子节点
                    parent.rightChild = current.leftChild;
                }
            }
        }
        //2、需要删除的节点有一个子节点，且该子节点为右子节点
        else if (current.leftChild == null) {
            //如果该节点为根节点，将根节点的右子节点变为根节点
            if (current == root) {
                root = current.rightChild;
            } else {
                //如果该节点是父节点的左子节点，将该节点的右子节点变为父节点的左子节点
                if (isLeftChild) {
                    parent.leftChild = current.rightChild;
                } else {  //如果该节点是父节点的右子节点，将该节点的右子节点变为父节点的右子节点
                    parent.rightChild = current.rightChild;
                }
            }
        }
        //3、需要删除的节点有两个子节点，需要寻找该节点的后续节点替代删除节点
        else {
            Node successor = getSuccessor(current);
            //如果该节点为根节点，将后继节点变为根节点，并将根节点的左子节点变为后继节点的左子节点
            if (current == root) {
                root = successor;
            } else {
                //如果该节点是父节点的左子节点，将该节点的后继节点变为父节点的左子节点
                if (isLeftChild) {
                    parent.leftChild = successor;
                } else {  //如果该节点是父节点的右子节点，将该节点的后继节点变为父节点的右子节点
                    parent.rightChild = successor;
                }
            }
        }
        current = null;
        return true;
    }
    
    //前序非递归 Morris
    public void preOrderMorris() {
        long startTime = System.currentTimeMillis();
        System.out.println("前序Morris遍历:");
        if (root == null) {
            System.out.println("isEmpty");
            return;
        }
        Node p1 = root;
        Node p2 = null;
        while (p1 != null) {
            p2 = p1.leftChild;
            if (p2 != null) {
                //找到左子树的最右叶子节点
                while (p2.rightChild != null && p2.rightChild != p1) {
                    p2 = p2.rightChild;
                }
                //添加 right 指针，对应 right 指针为 null 的情况
                //标注 1
                if (p2.rightChild == null) {
                    p1.display();
                    p2.rightChild = p1;
                    p1 = p1.leftChild;
                    continue;
                }
                //对应 right 指针存在的情况，则去掉 right 指针
                p2.rightChild = null;
                //标注2
            } else {
                p1.display();
            }
            //移动 p1
            p1 = p1.rightChild;
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

}
