package com.proxy.demo.step1;

import com.proxy.demo.step1.impl.TargetImpl;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-07-02
 * @Description
 */
public class Client {

    public static void main(String[] args) {
        //没被代理之前
        Target target = new TargetImpl();
        System.out.println(target.getClass());
        target.execute();
        System.out.println("被代理之后");
        //被代理之后
        target = (Target) TargetProxy.bind(target);
        System.out.println(target.getClass());
        target.execute();
    }

}
