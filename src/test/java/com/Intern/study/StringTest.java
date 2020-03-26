package com.Intern.study;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试String的intern
 *
 * @author xuedui.zhao
 * @create 2018-04-25
 */
public class StringTest {

    @Test
    public void test01() {
        String str1 = "string";
        String str2 = new String("string");
        String str3 = str1.intern();

        System.out.println(str1 == str2);//#1
        System.out.println(str1 == str3);//#2
    }

    @Test
    public void test02() {
        String baseStr = "baseStr";
        final String baseFinalStr = "baseStr";

        String str1 = "baseStr01";
        String str2 = "baseStr"+"01";
        String str3 = baseStr + "01";
        String str4 = baseFinalStr+"01";
        String str5 = new String("baseStr01").intern();

        System.out.println(str1 == str2);//#3
        System.out.println(str1 == str3);//#4
        System.out.println(str1 == str4);//#5
        System.out.println(str1 == str5);//#6
    }

    @Test
    public void test03() {
        String str2 = new String("str")+new String("01");
        str2.intern();
        String str1 = "str01";
        System.out.println(str2==str1);//#7
    }

    @Test
    public void test04() {
        String str1 = "str01";
        String str2 = new String("str")+new String("01");
        str2.intern();
        System.out.println(str2 == str1);//#8
    }

    @Test
    public void test05() {
        String s1 = "abc";
        String s2 = "a";
        String s3 = "bc";
        String s4 = s2 + s3;
        String s5 = "a" + "bc";
        System.out.println(s1 == s4);
        System.out.println(s1 == s5);
    }

    @Test
    public void test06() {
        String s1 = "abc";
        final String s2 = "a";
        final String s3 = "bc";
        String s4 = s2 + s3;
        System.out.println(s1 == s4);
    }

    @Test
    public void test07() {
        String s = new String("abc");
        String s1 = "abc";
        String s2 = new String("abc");

        System.out.println(s == s1.intern());
        System.out.println(s == s2.intern());
        System.out.println(s1 == s2.intern());
    }
}
