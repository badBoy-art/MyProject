package com.algorithms.study.binaryAlgorithm;

import org.junit.Test;

/**
 * https://mp.weixin.qq.com/s/sCtPN_bCOtU19p5w8KGTwg
 *
 * @author xuedui.zhao
 * @create 2019-06-12
 */
public class BinarySearchStudy {


    @Test
    public void testGetNumIndex() {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(binarySearch(nums, 5));
    }

    /**
     * 缺陷
     * nums = [1,2,2,2,3]，target = 2，此算法返回的索引是 2，没错。但是如果我想得到 target 的左侧边界，即索引 1，或者我想得到 target 的右侧边界，即索引 3
     *
     * @param nums
     * @param target
     * @return
     */
    private int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1; // 注意

        /**
         * 1. 为什么 while 循环的条件中是 <=，而不是 < ？
         * 答：因为初始化 right 的赋值是 nums.length - 1，即最后一个元素的索引，而不是 nums.length。
         * 这二者可能出现在不同功能的二分查找中，区别是：前者相当于两端都闭区间 [left, right]，后者相当于左闭右开区间 [left, right)，
         * 因为索引大小为 nums.length 是越界的
         * while(left <= right)的终止条件是 left == right + 1
         * while(left < right)的终止条件是 left == right return nums[left] == target ? left : -1;
         */
        while (left <= right) { // 注意
            int mid = (right + left) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] < target)
                left = mid + 1; // 注意
            else if (nums[mid] > target)
                right = mid - 1; // 注意
        }
        return -1;
    }


    @Test
    public void testGetNumLeftIndex() {
        int[] nums = new int[]{1, 2, 2, 2, 5, 6, 7, 8, 9, 10};
        System.out.println(left_bound(nums, 3));
    }

    private int left_bound(int[] nums, int target) {
        if (nums.length == 0) return -1;
        int left = 0;
        int right = nums.length; // 注意

        /**
         *
         * 为什么 while(left < right) 而不是 <= ?
         * 答：用相同的方法分析，因为初始化 right = nums.length 而不是 nums.length - 1 。因此每次循环的「搜索区间」是 [left, right) 左闭右开。
         * while(left < right) 终止的条件是 left == right，此时搜索区间 [left, left) 恰巧为空，所以可以正确终止
         */
        while (left < right) { // 注意
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid; // 注意
            }
        }
        return nums[left] == target ? left : -1;
    }

    @Test
    public void testGetNumRightIndex() {
        int[] nums = new int[]{1, 2, 2, 2, 5, 6, 7, 8, 9, 10};
        System.out.println(right_bound(nums, 2));
    }

    private int right_bound(int[] nums, int target) {
        if (nums.length == 0) return -1;
        int left = 0, right = nums.length;

        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                left = mid + 1; // 注意
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid;
            }
        }
        return nums[right - 1] == target ? right - 1 : -1;// 注意
    }
}
