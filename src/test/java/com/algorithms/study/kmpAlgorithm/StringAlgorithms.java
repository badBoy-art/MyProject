package com.algorithms.study.kmpAlgorithm;

import org.junit.Test;

/**
 * 字符串查找算法学习
 *
 * @author xuedui.zhao
 * @create 2019-05-09
 */
public class StringAlgorithms {

    @Test
    public void testViolence() {
        long startTime = System.currentTimeMillis();
        String str = "abcdefghijklmnopqrstuvwxyzhhjiual";
        String pub = "jia";
        int sLen = str.length();
        int pLen = pub.length();

        int i = 0;
        int j = 0;
        while (i < sLen && j < pLen) {
            if (str.charAt(i) == pub.charAt(j)) {
                //①如果当前字符匹配成功（即S[i] == P[j]），则i++，j++
                i++;
                j++;
            } else {
                //②如果失配（即S[i]! = P[j]），令i = i - (j - 1)，j = 0
                i = i - j + 1;
                j = 0;
            }
        }
        //匹配成功，返回模式串p在文本串s中的位置，否则返回-1
        if (j == pLen)
            System.out.println("true time == " + (System.currentTimeMillis() - startTime));
        else
            System.out.println("false time == " + (System.currentTimeMillis() - startTime));

    }
}
