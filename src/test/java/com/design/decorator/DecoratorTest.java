package com.design.decorator;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description 装饰模式，适合于装饰类或者说是增强类的场景
 */
public class DecoratorTest {

    public static void main(String[] args) {
        // 首先，我们需要一个基础饮料，红茶、绿茶或咖啡
        Beverage beverage = new GreenTea();
        // 开始装饰
        beverage = new Lemon(beverage); // 先加一份柠檬
        beverage = new Mango(beverage); // 再加一份芒果

        System.out.println(beverage.getDescription() + " 价格：￥" + beverage.cost());
    }

}
