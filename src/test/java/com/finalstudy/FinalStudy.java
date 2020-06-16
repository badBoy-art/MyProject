package com.finalstudy;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-06-12
 * @Description
 */
public class FinalStudy {

    @Test
    public void test() {
        System.out.println(Person.getName());
        Person.setName("zhangsan");
        System.out.println(Person.getName());
    }

}
