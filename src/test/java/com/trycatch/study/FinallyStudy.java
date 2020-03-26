package com.trycatch.study;

import org.junit.Test;

/**
 * @author badBoy
 * @create 2019-11-01
 */
public class FinallyStudy {

    @Test
    public void testFinally() {
        System.out.println(getResult());
    }

    private String getResult() {
        String str;
        try {
            str = "abc";
            return str;
        } finally {
            str = "edc";
            System.out.println(str);
        }
    }

}
