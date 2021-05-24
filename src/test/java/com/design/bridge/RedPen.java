package com.design.bridge;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public class RedPen implements DrawAPI {

    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("用红色笔画图，radius:" + radius + ", x:" + x + ", y:" + y);
    }

}
