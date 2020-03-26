package com.jmx.study.standard;

/**
 * @author xuedui.zhao
 * @create 2018-12-17
 */
public interface HelloMBean {

    String getName();

    void setName(String name);

    String getAge();

    void setAge(String age);

    void helloWorld();

    void helloWorld(String str);

}
