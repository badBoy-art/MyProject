package com.algorithms.study.rainWater;

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
}
