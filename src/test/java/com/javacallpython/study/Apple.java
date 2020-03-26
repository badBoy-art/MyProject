package com.javacallpython.study;

/**
 * @author xuedui.zhao
 * @create 2018-08-20
 */
public class Apple implements Fruit {
    @Override
    public String getName() {
        return "java apple";
    }
    
    @Override
    public String getType() {
        return "apple";
    }
    
    @Override
    public void show() {
        System.out.println("I am a java apple");
    }
}
