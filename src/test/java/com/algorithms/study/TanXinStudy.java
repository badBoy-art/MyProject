package com.algorithms.study;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-12
 * @Description 贪心算法：在对问题求解时，总是做出在当前看来是最好的选择。也就是说，不从整体最优上加以考虑，算法得到的是在某种意义上的局部最优解
 */
public class TanXinStudy {

    @Test
    public void test() {
        System.out.println(canJump(new int[]{2, 3, 1, 1, 4}));
        System.out.println(canJump(new int[]{3, 2, 1, 0, 4}));
        System.out.println(jump(new int[]{2, 3, 1, 1, 4}));

        System.out.println(maxProfit(new int[]{2, 3, 7, 4, 5, 9, 8, 1}));
        System.out.println(maxProfit2(new int[]{2, 3, 7, 4, 5, 9, 8, 1}));
    }

    public boolean canJump(int[] nums) {
        int reach = 1;
        for (int i = 0; i < reach && reach < nums.length; ++i)
            reach = max(reach, i + 1 + nums[i]);
        return reach >= nums.length;
    }

    public int jump(int[] nums) {
        int result = 0;// the maximum distance that has been reached
        int last = 0;// the maximum distance that can be reached by using "ret+1" steps
        int cur = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (i > last) {
                last = cur;
                ++result;
            }
            cur = max(cur, i + nums[i]);
        }
        return result;
    }

    /**
     * Best Time to Buy and Sell Stock
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }
        int profit = 0;
        int cur_min = prices[0];

        for (int i = 1; i < prices.length; i++) {
            profit = max(profit, prices[i] - cur_min);
            cur_min = min(cur_min, prices[i]);
        }
        return profit;
    }

    public int maxProfit2(int[] prices) {
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            int diff = prices[i] - prices[i - 1];
            if (diff > 0) sum += diff;
        }
        return sum;
    }

    /**
     * 最长不重复子串的长度即为答案
     */
    @Test
    public void lengthOfLongestSubstring() {
        String s = "abdcefrgefskbscdhj";
        //a b c a b c d a d e
        //0 1 2 3 4 5 6 7 8 9
        int maxSize = 0;
        //记录ASCII 码字符出现的位置，以字符作为下标
        int[] dict = new int[128];
        //为了方便理解，这里把数组内容全部设为 -1，之后在记录的时候就可以从 0 开始，方便理解
        Arrays.fill(dict, -1);
        //记录重复ASCII 码字符出现出现的最大脚标
        int repeatValue = -1;
        // 当前下标
        int i = 0;
        int ASCII;
        char indexChar;
        while (i < s.length()) {
            indexChar = s.charAt(i);
            ASCII = s.charAt(i);
            //如果当前位置的值 > repeatValue，证明当前位置已经赋过一次值了，证明字符重复
            if (dict[ASCII] > repeatValue)
                //更新 repeatValue 为之前赋值的下标
                repeatValue = dict[ASCII];
            //将当前下标赋值到数组相应位置
            dict[ASCII] = i;
            //i - repeatValue(去除重复部分)
            // 比如 abcabcdade 中的三个 a 的计算  abca - a(3 - 0)=bca   abcabcda - abca(7 - 3)=bcda
            maxSize = Math.max(maxSize, i - repeatValue);
            //s.length() - repeatValue - 1 判断剩下的数有没有必要继续循环
            //比如 abcabcdade 最后的 a(当 i = 7 repeatValue = 3) ，abcabcdade - abca(10-3-1) = bcdade  剩下最多有六位
            //比如 abcabcdade 最后的 d(当 i = 8 repeatValue = 6) ，abcabcdade - abcabcd(10-6-1) = ade  剩下最多也是三位
            if (maxSize >= s.length() - repeatValue - 1) {
                System.out.println(maxSize);
            }
            i++;
        }
        System.out.println(maxSize);
    }

    @Test
    public void lengthOfLongestSubstring2() {
        String s = "abdcefrgefskbcdhj";
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            if (n - i <= ans) {
                System.out.println(ans);
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        System.out.println(ans);
    }

    /**
     * Container With Most Water
     */
    @Test
    public void containerMostWater() {
        int[] height = new int[]{2, 3, 4, 1, 6, 7, 8, 3, 2};
        int start = 0;
        int end = height.length - 1;
        int result = Integer.MIN_VALUE;
        while (start < end) {
            int area = min(height[end], height[start]) * (end - start);
            result = max(result, area);
            if (height[start] <= height[end]) {
                start++;
            } else {
                end--;
            }
        }
        System.out.println(result);
    }

}