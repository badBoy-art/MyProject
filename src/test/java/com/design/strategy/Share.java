package com.design.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-04-27
 * @Description 策略模式
 */
public class Share {

    private static List<DealContext> algs = new ArrayList();

    //静态代码块,先加载所有的策略
    static {
        algs.add(new DealContext("Sina", new DealSina()));
        algs.add(new DealContext("WeChat", new DealWeChat()));
    }

    public void shareOptions(String type) {
        DealStrategy dealStrategy = null;
        for (DealContext deal : algs) {
            if (deal.options(type)) {
                dealStrategy = deal.getDeal();
                break;
            }
        }
        dealStrategy.dealMythod(type);
    }

    public static void main(String[] args) {
        Share share = new Share();
        share.shareOptions("WeChat");
    }
}
