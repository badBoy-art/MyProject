package com.design.decorator;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public class Lemon extends Condiment {

    private Beverage beverage;

    public Lemon(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        // 装饰
        return beverage.getDescription() + ", 加柠檬";
    }

    public double cost() {
        // 装饰
        return beverage.cost() + 2; // 加柠檬需要 2 元
    }
}
