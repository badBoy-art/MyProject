package com.design.decorator;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description 装饰模式-饮料抽象基类
 */
public abstract class Beverage {

    // 返回描述
    public abstract String getDescription();

    // 返回价格
    public abstract double cost();

}
