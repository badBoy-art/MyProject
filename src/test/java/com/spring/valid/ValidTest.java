package com.spring.valid;

import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;


/**
 * @author badBoy
 * @create 2020-02-03
 */
public class ValidTest {

    @Test
    public void test01() {
        BindingResult bindingResult = new BindException("error", "Error ");
        System.out.println(bindingResult);
    }
}
