package com.algorithms.study.reverselink;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-05-06
 * @Description
 */
public class reverseKGroupStudy {

    @Test
    public void test() {

    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode pre = null;
        ListNode cur = head;//当前节点
        ListNode next = head;//后继节点
        ListNode sum = head;//用来计算是否有K个节点的遍历
        int count = 0;
        int i = 0;
        while (sum != null) {
            sum = sum.next;
            count++;
        }
        if (count < k) {
            return head;
        } else {
            //对每一组进行翻转
            while (i < k && cur != null) {
                next = cur.next;//记录下一个节点
                //翻转节点
                cur.next = pre;//当前节点的下一个节点指向前继节点
                pre = cur;//当前节点变成前继节点
                cur = next;//下一个节点变成当前节点
                //该while完成后，该组的翻转就完成了，pre就会指向原本的最后一个节点，翻转之后的第一个节点。head节点就变成了翻转之后的最后一个节点
                i++;
            }
            //如果next节点不为空，即后面还有节点
            if (next != null) {
                //递归
                head.next = reverseKGroup(next, k);
            }
            return pre;
        }
    }
}
