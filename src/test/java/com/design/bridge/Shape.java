package com.design.bridge;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public abstract class Shape {

    protected DrawAPI drawAPI;

    protected Shape(DrawAPI drawAPI) {
        this.drawAPI = drawAPI;
    }

    public abstract void draw();

}
