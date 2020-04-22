package com.algorithms.study.huffman;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-04-21
 * @Description
 */
@Data
@Accessors(chain = true)
public class Node implements Comparable<Node> {
    public String chars = "";
    public int frequence = 0;
    public Node parent;
    public Node leftNode;
    public Node rightNode;

    @Override
    public int compareTo(Node n) {
        return frequence - n.frequence;
    }

    public boolean isLeaf() {
        return chars.length() == 1;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeftChild() {
        return parent != null && this == parent.leftNode;
    }

}
