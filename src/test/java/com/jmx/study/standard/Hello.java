package com.jmx.study.standard;

import javax.management.NotificationBroadcasterSupport;

/**
 * @author xuedui.zhao
 * @create 2018-12-17
 */
public class Hello extends NotificationBroadcasterSupport implements HelloMBean{
    private String name;
    private String age;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getAge() {
        return null;
    }

    @Override
    public void setAge(String age) {

    }

    @Override
    public void helloWorld() {

    }

    @Override
    public void helloWorld(String str) {
       System.out.println(str);
    }
}
