package com.algorithms.study;

import org.junit.Test;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-14
 * @Description 奇偶数组 奇数在前
 */
public class PointStudy {

    @Test
    public void testDoublePoint() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        int left = 0;
        int right = arr.length - 1;
        int temp;
        while (left < right) {
            while (left < right && (arr[left] % 2 != 0)) {
                left++;
            }
            while ((left < right && (arr[right] % 2 == 0))) {
                right--;
            }
            temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }
        for (int a : arr) {
            System.out.println(a);
        }
    }

}
