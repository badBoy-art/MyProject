package com.algorithms.study.dynamicProgramming;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
