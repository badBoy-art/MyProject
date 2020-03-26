package com.reflect.study;

/**
 * 学生类
 *
 * @author xuedui.zhao
 * @create 2018-03-31
 */
public class Student {

    static {
    System.out.println("i am a static module");
    }

    {
    System.out.println("i am a module");
    }
    private String name;
    private Integer age;
    protected String address;
    public String sex;

    public Student() {
    }

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    private Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void speakMyName(String name) {
        System.out.println("myName is :" + name);
    }

    private void speakMyAge(int age) {
        System.out.println("myAge is :" + age);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
