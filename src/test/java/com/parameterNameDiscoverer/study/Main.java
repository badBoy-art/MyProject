package com.parameterNameDiscoverer.study;

import org.apache.ibatis.annotations.Param;

/**
 * @author xuedui.zhao
 * @create 2019-08-17
 */
public class Main {

    public void test(@Param("namm") String name, Integer age) {
        System.out.println(name + " " + age);
    }

}
