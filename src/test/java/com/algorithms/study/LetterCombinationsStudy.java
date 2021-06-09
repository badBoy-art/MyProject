package com.algorithms.study;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author badBoy <badBoy>
 * Created on 2021-06-09
 * @Description
 */
public class LetterCombinationsStudy {

    @Test
    public void test() {
        System.out.println(letterCombinations("74"));
    }

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        String[] strs = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        //1.先算出一共有几种 基于组合
        int len = 1;
        for (int i = 0; i < digits.length(); i++) {
            int c = digits.charAt(i) - '0';
            len *= strs[c].length();
        }

        //再用求余方法拿到每一种:可以当做是状态压缩吧，000代表都是第0位，001代表前两个第0位，第三个第1位，002。。。以此类推
        for (int i = 0; i < len; i++) {
            int last = i;
            StringBuilder sb = new StringBuilder();
            for (int j = digits.length() - 1; j >= 0; j--) {
                int c = digits.charAt(j) - '0';
                int pos = last % strs[c].length();
                sb.append(strs[c].charAt(pos));
                last = last / strs[c].length();
            }
            result.add(sb.reverse().toString());
        }

        return result;
    }


}
