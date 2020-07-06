package com.vavr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.vavr.control.Option;


/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-06
 * @Description
 */
public class VavrStudy {

    @Test
    public void test01() {
        Option<Object> someOption = Option.of("val");

        assertEquals("Some(val)", someOption.toString());
    }

}
