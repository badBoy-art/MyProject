package com.design.strategy;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-04-27
 * @Description
 */
public class DealSina implements DealStrategy {

    @Override
    public void dealMythod(String option) {
        System.out.println("DealSina" + " " + option);
    }
}
