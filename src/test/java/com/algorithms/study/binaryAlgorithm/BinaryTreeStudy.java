package com.algorithms.study.binaryAlgorithm;

import org.junit.Test;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-12
 * @Description 导航装置
 */
public class BinaryTreeStudy {

    /**
     * 每个三叉点（该节点既有不为空的左右子树，又有父亲节点）的三条支路中，至少两条支路上有导航装置（对于有导航的支路，只要有就可以了）。
     * <p>
     * 简单证明下这种思路：
     * 对于树来说，没有环，而且又是树里面简单的二叉树，所以对每个点来说最多有三条支路。
     * （1）第一种情况：树里面没有三叉点，那么要么只有一个节点，要么是一条链，所以只用放一个导航，放在端点（叶子节点）处；
     * （2）第二种情况：树里面有三叉点，我们从只有一个三叉点来理解，再扩展到多个三叉点，
     * 由于树中没有环，任意选两个不在同一支路的节点，它们的最短路径必须经过该三叉点。
     * <1>假设三条支路上各放一个导航，都放在各自支路的端点处，这里自己想下，肯定能唯一标记所有节点，但是算下来它的导航数量不是最少最优的。
     * <2>假设三条支路上，只有一条支路放了导航，另外两条支路没有放导航，那么选择另外两条支路的第一个节点，他们到达导航的距离相同，所以无法唯一标记（即使扩展到多个三叉点，但在该三叉节点上，注意这两条支路没有导航，所以第三条支路后面再分叉增加导航，但对于这两条支路的第一个节点来说，距离都相同，也不能唯一标记）。
     * <3>假设三条支路上，都不放导航，那么整棵树都没有导航，就无法导航，更谈不上唯一标记。
     * <p>
     * 所以结论来了：每个三叉点（该节点既有不为空的左右子树，又有父亲节点）的三条支路中，至少两条支路上有导航装置（对于有导航的支路，只要有就可以了）。
     * <p>
     * 编码：
     * 我们标记每个点的状态：
     * 0：表示该点及孩子们都不放导航装置；
     * 1：表示左右孩子都没有导航装置时，必须选一个孩子支路放一个导航装置，另一个暂时不放，以1状态标记交上面节点去处理；
     * 2：表示左右孩子支路都有导航装置，该节点的父节点支路可以放也可以不放导航装置。
     * <p>
     * 注意：
     * 1.对每个节点来说，若它的一个孩子为空时，它的状态等于另一个孩子的状态（或者说等于该节点下面最近的三叉点状态），继续往上传递。
     * 2.根节点就比较BUG，需要单独处理，因为它最多只有两个支路，肯定不是三叉点。
     */

    int res = 0;

    @Test
    public void test() {
        TreeNode root = new TreeNode(1);
        TreeNode childLeft = new TreeNode(2);
        TreeNode childRight = new TreeNode(3);
        TreeNode sonLeft = new TreeNode(3);
        TreeNode sonRight = new TreeNode(4);
        childLeft.setLeft(sonLeft);
        childLeft.setRight(sonRight);
        root.setLeft(childLeft);
        root.setRight(childRight);
        System.out.println(navigation(root));
    }

    public int navigation(TreeNode root) {
        if (root == null) return 0;
        //根节点单独处理
        int left = dfs(root.left);
        int right = dfs(root.right);
        //下面这行代码是多种状态综合处理的表现形式，具体如下：
        //左右孩子状态集(左右交换就不写了)：
        //[0,0]：左右都没放导航(包含子树为空的情况)，那么要必须放一个导航，即res+1，而且总共放一个导航；
        //[0,1]：一个孩子没放导航，再和根节点放一起，视为最近的三叉点的一条支路。由于另一个孩子状态为1，表示该三叉点有两条支路未放导航，所以还要再增加一个导航，那么res+1；
        //[0,2]：一个孩子没放导航，再和根节点放一起，视为最近的三叉点的一条支路。另一个孩子状态为2，表示该三叉点有两条支路已放导航，所以不用再增加导航，返回res即可；
        //[1,1]：其中一个孩子和根节点放一起，视为一条支路，那么就表示该合并后的支路上有导航（只要有就可以了），所以该三叉点的两条支路有导航，返回res即可；
        //[1,2]：前面同上，该三叉点的三条支路都有导航，返回res即可；
        //[2,2]：直接同上。
        //综合上面分类情况，左右返回的状态值相加left+right>=2时，返回res即可，其他情况都要再加一个导航。
        if (left + right >= 2) {
            return res;
        }
        return res + 1;
    }

    public int dfs(TreeNode root) {
        //为空时，表示没放导航
        if (root == null) return 0;
        int left = dfs(root.left);
        int right = dfs(root.right);
        //由于上面根节点单独处理，所以这里只要左右不为空，即为三叉点
        if (root.left != null && root.right != null) {
            //左右子树都没放导航，那么必须选一条支路放一个导航，另一条支路暂时不放，返回状态1；
            if (left == 0 && right == 0) {
                res += 1;
                return 1;
            }
            //一条支路有导航，另一条支路没有导航，继续暂时不放，返回状态1，要不要加交给上面节点来判断处理；
            //这里多说一句，由于该三叉节点一条有导航另一条没导航，那么对于之前已经遍历的下面的三叉点来说，相当于它的父节点支路有导航了，所以当时处理它时，是否有一条支路未放导航就无所谓了；
            if (left == 0 || right == 0) {
                return 1;
            }
            //左右孩子支路都有导航，那么就返回状态2
            return 2;
        } else if (root.left == null) {//左孩子为空，该节点状态等于右孩子，或者说把最近的三叉点状态往上传递
            return right;
        } else if (root.right == null) {//同上理
            return left;
        }
        //由于编译器的原因，加这么一句，这里是你永远到不了的地方
        return 0;
    }

}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}