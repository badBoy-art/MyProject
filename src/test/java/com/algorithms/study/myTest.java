package com.algorithms.study;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author xuedui.zhao
 * @create 2019-08-22
 * 算法题链接：<a href = "https://mp.weixin.qq.com/s/AWsL7G89RtaHyHjRPNJENA"></a>
 */
public class myTest {

    @Test
    public void testMyStrMultiply() {
        String num1 = "1234";
        String num2 = "2345";
    }

    @Test
    public void testReverseWords() {
        String str = " the sky is blue  ";
        String[] strs = str.split(" ");
        List<String> lits = new ArrayList<>(strs.length);
        for (int i = 0; i < strs.length; i++) {
            if (!strs[i].isEmpty()) {
                lits.add(strs[i]);
            }
        }

        for (int i = lits.size() - 1; i >= 0; i--) {
            System.out.println(lits.get(i));
        }
    }

    /**
     * 数组奇数放前边、偶数放后边
     */
    @Test
    public void testOddEvenNumber() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // 定义头指针 left ，尾指针 right ,临时变量 tmp
        int left = 0, right = arr.length - 1, tmp;

        //重复操作，直到 left == right 为止
        while (left < right) {
            // nums[left] & 1 与 nums[right] & 1 都是用来判断数字奇偶性。
            // left 一直往右移，直到它指向的值为偶数
            while (left < right && (arr[left] & 1) == 1) left++;
            // right 一直往左移， 直到它指向的值为奇数
            while (left < right && (arr[right] & 1) == 0) right--;

            // 交换 nums[left] 和 nums[right]*
            tmp = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
        }

        for (int i : arr) {
            System.out.println(i);
        }
    }

    @Test
    public void testListNode() {
        //初始化两个指针 former 和 latter，一开始都指向链表的头节点
        ListNode head = getListNode();

        ListNode former = head, latter = head;
        int k = 3;
        //前指针 former 先向前走 k 步
        for (int i = 0; i < k; i++) {
            former = former.next;
        }
        // 两个指针 former 和 latter 同时向前移动，直到前指针 former 指向 NULL
        while (former != null) {
            former = former.next;
            latter = latter.next;
        }
        //最后返回 latter
        System.out.println(latter);
    }

    private ListNode getListNode() {
        ListNode listNode = new ListNode(0);
        ListNode temp = listNode;
        listNode.setValue(0);
        for (int i = 1; i < 10; i++) {
            ListNode next = new ListNode(i);
            temp.setNext(next);
            temp = next;
        }

        return listNode;
    }
}
