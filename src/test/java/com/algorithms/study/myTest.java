package com.algorithms.study;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuedui.zhao
 * @create 2019-08-22
 */
public class myTest {

    @Test
    public void testMyStrMultiply() {
        String num1 = "1234";
        String num2 = "2345";
    }

    @Test
    public void testReverseWords() {
        String str = " the sky is blue  ";
        String[] strs = str.split(" ");
        List<String> lits = new ArrayList<>(strs.length);
        for (int i = 0; i < strs.length; i++) {
            if (!strs[i].isEmpty()) {
                lits.add(strs[i]);
            }
        }

        for (int i = lits.size() - 1; i >= 0; i--) {
            System.out.println(lits.get(i));
        }
    }
}
