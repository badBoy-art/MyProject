package com.javacallpython.study;

/**
 * @author xuedui.zhao
 * @create 2018-08-20
 */
public class Orange implements Fruit{
    @Override
    public String getName() {
        return "java Orange";
    }
    
    @Override
    public String getType() {
        return "Orange";
    }
    
    @Override
    public void show() {
        System.out.println("I am a java Orange");
    }
}
