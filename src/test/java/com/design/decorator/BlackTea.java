package com.design.decorator;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public class BlackTea extends Beverage {

    @Override
    public String getDescription() {
        return "红茶";
    }

    @Override
    public double cost() {
        return 10;
    }
}
