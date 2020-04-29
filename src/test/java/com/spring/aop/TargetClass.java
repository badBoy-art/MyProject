package com.spring.aop;

import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ClassUtils;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-04-27
 * @Description
 */
public class TargetClass {

    @Test
    public void testSpringAop() {
        System.out.println(AopUtils.isAopProxy(this));
    }

    @Test
    public void testCglib() {
        System.out.println(ClassUtils.isCglibProxyClass(this.getClass()));
    }
}
