package com.design.observer;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public abstract class Observer {

    protected Subject subject;
    public abstract void update();

}
