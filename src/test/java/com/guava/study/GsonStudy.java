package com.guava.study;

import org.junit.Test;

import com.base.Person;
import com.google.gson.Gson;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-09-24
 * @Description
 */
public class GsonStudy {

    @Test
    public void test() {
        Person person = Person.builder()
                .name("zhangsan").age(11).build();

        String jstr = new Gson().toJson(person);
        System.out.println(jstr);
    }

}
