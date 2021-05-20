package com.algorithms.study.dynamicProgramming;

import static java.lang.Integer.min;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * 动态规划
 *
 * @author badBoy
 * @create 2019-12-26
 */
public class DynamicProgramming {

    @Test
    public void test() {
        System.out.println(fib(7));
    }

    public int fib(int N) {
        List<Integer> dp = new ArrayList<Integer>(N + 1);
        dp.add(0, 1);
        dp.add(1, 1);
        for (int i = 2; i <= N - 1; i++)
            dp.add(i, dp.get(i - 1) + dp.get(i - 2));
        return dp.get(N - 1);
    }

    /**
     * 1分 2分 5分硬币 ：最少硬币数目
     */
    @Test
    public void test02() {
        List<Integer> coins = Lists.newArrayList(1, 2, 5);
        System.out.println(coinChange(coins, 11));
    }

    public int coinChange(List<Integer> coins, int amount) {
        Map<Integer, Integer> dp = new HashMap<Integer, Integer>(amount + 1);
        dp.put(0, 0);
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    Integer value = dp.get(i) == null ? dp.get(i - coin) + 1 : Math.min(dp.get(i), dp.get(i - coin) + 1);
                    dp.put(i, value);
                }
            }
        }
        return dp.get(amount) > amount ? -1 : dp.get(amount);
    }

    /**
     * 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1
     */
    @Test
    public void testTriangle() {
        //分析 设状态为f(i,j) = min{f(i+1,j),f(i+1,j+1)}+(i,j)
        int[][] triangle = {{2}, {3, 4}, {6, 5, 7}, {4, 2, 8, 3}};
        for (int i = triangle.length - 2; i >= 0; --i) {
            for (int j = 0; j < i + 1; ++j) {
                triangle[i][j] += min(triangle[i + 1][j], triangle[i + 1][j + 1]);
            }
        }
        System.out.println(triangle[0][0]);
    }
}
