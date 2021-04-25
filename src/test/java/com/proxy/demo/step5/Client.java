package com.proxy.demo.step5;

import com.proxy.demo.step5.impl.InterceptorImpl;
import com.proxy.demo.step5.impl.TargetImpl;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-07-02
 * @Description
 */
public class Client {

    public static void main(String[] args) {
        //没被代理之前
        Target target = new TargetImpl();
        target.execute1();
        target.execute2();
        System.out.println("被代理之后");

        //客户端可以在此处定义多种拦截逻辑
        Interceptor interceptor = new InterceptorImpl();
        //被代理之后
        target = (Target) TargetProxy.bind(target, interceptor);
        target.execute1();
        target.execute2();
    }

}
