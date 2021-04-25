package com.aviator;

import org.junit.Test;

import com.googlecode.aviator.AviatorEvaluator;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2021-04-22
 * @Description
 */
public class AviatorStudy {

    @Test
    public void test01() {
        Long result = (Long) AviatorEvaluator.execute("1+2+3");
        System.out.println(result);
    }

}

