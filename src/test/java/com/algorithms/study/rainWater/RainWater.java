package com.algorithms.study.rainWater;

import java.util.Stack;

import org.junit.Test;

/**
 * 积水算法
 * 有一些高度不同的台阶，有一个正数数组表示，数组中每个数是台阶的高度，当雨下的够大时，
 * 求台阶间的积水
 * * @author xuedui.zhao
 *
 * @create 2019-08-15
 */
public class RainWater {

    @Test
    public void test() {
        int[] list = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int maxValueIndex = getMaxValueIndex(list);
        int left = getRainWaterLeft(list, maxValueIndex);
        int right = getRainWaterRight(list, maxValueIndex);
        System.out.println(left + right);
    }

    /**
     * 得到右侧雨量
     *
     * @param list
     * @param maxValueIndex
     * @return
     */
    private int getRainWaterRight(int[] list, int maxValueIndex) {
        int rainWater = 0;
        int middleValue = 0;
        int init = list[0];
        int index1 = 0;
        for (int i = 1; i <= maxValueIndex; i++) {
            if (init <= list[i]) {
                rainWater = rainWater + (i - index1 - 1) * init;
                index1 = i;
                init = list[i];
            } else {
                middleValue += list[i];
            }
        }
        return rainWater - middleValue;
    }

    /**
     * 得到左侧雨量
     *
     * @param list
     * @param maxValueIndex
     * @return
     */
    private int getRainWaterLeft(int[] list, int maxValueIndex) {
        int rainWater = 0;
        int middleValue = 0;
        int init = list[list.length - 1];
        int index1 = list.length - 1;
        for (int i = list.length - 2; i >= maxValueIndex; i--) {
            if (init <= list[i]) {
                rainWater = rainWater + (index1 - i - 1) * init;
                index1 = i;
                init = list[i];
            } else {
                middleValue += list[i];
            }
        }
        return rainWater - middleValue;
    }

    private int getMaxValueIndex(int[] list) {
        int index = 0;
        for (int i = 1; i < list.length; i++) {
            if (list[i] > list[index]) {
                index = i;
            }
        }
        return index;
    }

    /**
     * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
     * 求在该柱状图中，能够勾勒出来的矩形的最大面积
     */
    @Test
    public void testPillar() {
        int[] heights = new int[]{2, 1, 5, 6, 2, 3};
        //最终结果
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        //给定原数组左右各添加一个0元素
        int[] newHeights = new int[heights.length + 2];
        newHeights[0] = 0;
        newHeights[newHeights.length - 1] = 0;
        System.arraycopy(heights, 0, newHeights, 1, heights.length + 1 - 1);
        //开始遍历
        for (int i = 0; i < newHeights.length; i++) {
            //如果栈不为空且当前考察的元素值小于栈顶元素值
            //则表示栈顶元素值为高的矩形面积可以确定
            while (!stack.isEmpty() && newHeights[i] < newHeights[stack.peek()]) {
                //弹出栈顶元素
                int cur = stack.pop();
                //获取栈顶元素对应的高度
                int curHeight = newHeights[cur];
                //栈顶元素弹出后，新的栈顶元素就是其左侧边界
                int leftIndex = stack.peek();
                //右侧边界测试当前考察的索引
                int rightIndex = i;
                //计算矩形宽度
                int curWidth = rightIndex - leftIndex - 1;
                //计算面积
                res = Math.max(res, curWidth * curHeight);
            }
            //当前索引入栈
            stack.push(i);
        }
        System.out.println(res);
    }
}
