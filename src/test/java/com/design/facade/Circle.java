package com.design.facade;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Circle::draw()");
    }
}
