package com.json.study.interfacedeser;

import com.json.study.interfacedeser.Animal;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-06-02
 * @Description
 */
public class People implements Animal {

    private String name;

    private String address;

    public People(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public People() {

    }

    @Override
    public String say(String input) {
        return "I am people say : " + input;
    }
}
