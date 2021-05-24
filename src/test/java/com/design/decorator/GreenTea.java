package com.design.decorator;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public class GreenTea extends Beverage {

    @Override
    public String getDescription() {
        return "绿茶";
    }

    @Override
    public double cost() {
        return 12;
    }
}
