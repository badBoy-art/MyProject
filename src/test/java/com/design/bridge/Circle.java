package com.design.bridge;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public class Circle extends Shape {

    private int radius;

    public Circle(int radius, DrawAPI drawAPI) {
        super(drawAPI);
        this.radius = radius;
    }

    public void draw() {
        drawAPI.draw(radius, 0, 0);
    }

}
