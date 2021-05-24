package com.design.facade;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description 门面模式的优点是客户端不需要关心实例化过程，只要调用需要的方法即可
 */
public class FacadeTest {

    public static void main(String[] args) {
        ShapeMaker shapeMaker = new ShapeMaker();

        // 客户端调用现在更加清晰了
        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
    }

}
