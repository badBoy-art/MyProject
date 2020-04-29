package com.design.strategy;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-04-27
 * @Description
 */
public class DealWeChat implements DealStrategy {

    @Override
    public void dealMythod(String option) {
        System.out.println("DealWeChat " + option);
    }
}
