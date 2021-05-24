package com.design.state;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public class Context {

    private State state;
    private String name;

    public Context(String name) {
        this.name = name;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

}
