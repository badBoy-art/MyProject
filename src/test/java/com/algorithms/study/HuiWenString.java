package com.algorithms.study;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author badBoy <badBoy>
 * Created on 2021-04-27
 * @Description 回文串学习
 */
public class HuiWenString {

    private String baseStr = "abcderfredba";

    /**
     * 动态规划法获得最大回文串
     * 对于一个子串而言，如果它是回文串，并且长度大于 22，那么将它首尾的两个字母去除之后，它仍然是个回文串。
     * 例如对于字符串 “ababa”，如果我们已经知道 “bab” 是回文串，那么 “ababa” 一定是回文串，这是因为它的首尾两个字母都是 “a”。
     * <p>
     * 根据这样的思路，我们就可以用动态规划的方法解决本题
     */
    @Test
    public void test01() {
        int len = baseStr.length();
        if (len < 2) {
            System.out.println(baseStr);
        }

        int maxLen = 1;
        int begin = 0;
        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[len][len];
        // 初始化：所有长度为 1 的子串都是回文串 二维数组对角线上的字符是相等的
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        char[] charArray = baseStr.toCharArray();
        // 递推开始
        // 先枚举子串长度
        for (int L = 2; L <= len; L++) {
            // 枚举左边界，左边界的上限设置可以宽松一些
            for (int i = 0; i < len; i++) {
                // 由 L 和 i 可以确定右边界，即 j - i + 1 = L 得
                int j = L + i - 1;
                // 如果右边界越界，就可以退出当前循环
                if (j >= len) {
                    break;
                }

                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 只要 dp[i][L] == true 成立，就表示子串 s[i..L] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        System.out.println(baseStr.substring(begin, begin + maxLen));
    }

    /**
     * 中心扩展法
     * 边界情况即为子串长度为 1 或 2 的情况。我们枚举每一种边界情况，并从对应的子串开始不断地向两边扩展。
     * 如果两边的字母相同，我们就可以继续扩展，例如从 P(i+1,j-1) 扩展到 P(i,j)；如果两边的字母不同，
     * 我们就可以停止扩展，因为在这之后的子串都不能是回文串了
     */
    @Test
    public void test02() {
        if (baseStr == null || baseStr.length() < 1) {
            System.out.println("");
        }
        int start = 0, end = 0;
        for (int i = 0; i < baseStr.length(); i++) {
            //串为奇数
            int odd = expandAroundCenter(baseStr, i, i);
            //串为偶数
            int even = expandAroundCenter(baseStr, i, i + 1);
            int len = Math.max(odd, even);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        System.out.println(baseStr.substring(start, end + 1));
    }

    public int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return right - left - 1;
    }

    /**
     * Manacher 算法  马拉车
     * 在中心扩展算法的过程中，我们能够得出每个位置的臂长。那么当我们要得出以下一个位置 i 的臂长时，能不能利用之前得到的信息呢？
     * 基于这个想法来思考解决方案
     */
    @Test
    public void test03() {
        int start = 0, end = -1;
        StringBuffer t = new StringBuffer("#");
        for (int i = 0; i < baseStr.length(); ++i) {
            t.append(baseStr.charAt(i));
            t.append('#');
        }
        baseStr = t.toString();

        List<Integer> arm_len = new ArrayList<>();
        int right = -1, j = -1;
        for (int i = 0; i < baseStr.length(); ++i) {
            int cur_arm_len;
            if (right >= i) {
                int i_sym = j * 2 - i;
                int min_arm_len = Math.min(arm_len.get(i_sym), right - i);
                cur_arm_len = expand(baseStr, i - min_arm_len, i + min_arm_len);
            } else {
                cur_arm_len = expand(baseStr, i, i);
            }
            arm_len.add(cur_arm_len);
            if (i + cur_arm_len > right) {
                j = i;
                right = i + cur_arm_len;
            }
            if (cur_arm_len * 2 + 1 > end - start) {
                start = i - cur_arm_len;
                end = i + cur_arm_len;
            }
        }

        StringBuffer ans = new StringBuffer();
        for (int i = start; i <= end; ++i) {
            if (baseStr.charAt(i) != '#') {
                ans.append(baseStr.charAt(i));
            }
        }

        System.out.println(ans.toString());
    }

    public int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return (right - left - 2) / 2;
    }

}
