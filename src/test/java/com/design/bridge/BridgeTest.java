package com.design.bridge;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description 实现解耦。桥梁模式的优点也是显而易见的，就是非常容易进行扩展
 */
public class BridgeTest {

    public static void main(String[] args) {
        Shape greenCircle = new Circle(10, new GreenPen());
        Shape redRectangle = new Rectangle(4, 8, new RedPen());
        greenCircle.draw();
        redRectangle.draw();
    }

}
