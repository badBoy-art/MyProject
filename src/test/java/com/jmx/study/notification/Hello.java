package com.jmx.study.notification;


import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

/**
 * @author xuedui.zhao
 * @create 2018-12-17
 */
public class Hello extends NotificationBroadcasterSupport implements HelloMBean {

    private String name;
    private String age;

    //用于记录Notification的order
    private long sequenceNumber = 1;

    @Override
    public String getName() {
        System.out.println("getName " + name);
        return this.name;
    }

    @Override
    public void setName(String name) {
        System.out.println("setName " + name);
        this.name = name;
    }

    @Override
    public String getAge() {
        System.out.println("getAge " + age);
        return this.age;
    }

    @Override
    public void setAge(String age) {
        System.out.println("setAge " + age);
        String oldAge = this.age;
        this.age = age;

        Notification n =
                new AttributeChangeNotification(this,
                        sequenceNumber++,
                        System.currentTimeMillis(),
                        "Age changed",
                        "age",
                        "String",
                        oldAge,
                        age);

        sendNotification(n);
    }

    @Override
    public void helloWorld() {

    }

    @Override
    public void helloWorld(String str) {

    }
}
