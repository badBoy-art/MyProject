package com.classLoader.study;

/**
 * @author xuedui.zhao
 * @create 2018-11-26
 */
public class Panda {

    static {
        System.out.println("Loading Panda");
    }

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Panda{" +
                "name='" + name + '\'' +
                '}';
    }
}
