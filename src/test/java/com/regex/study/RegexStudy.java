package com.regex.study;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * 正则表达式学习
 *
 * @author xuedui.zhao
 * @create 2019-04-24
 */
public class RegexStudy {

    @Test
    public void testRegex() {
        String MOBILE_REGEXP = "^1[3456789][0-9]{9}$";
        boolean match = Pattern.matches(MOBILE_REGEXP, "16900002120");
        System.out.println(match);
    }

}
