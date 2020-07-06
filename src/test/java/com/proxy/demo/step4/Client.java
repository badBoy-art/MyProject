package com.proxy.demo.step4;

import com.proxy.demo.step1.Target;
import com.proxy.demo.step1.impl.TargetImpl;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-02
 * @Description
 */
public class Client {

    public static void main(String[] args) {
        //没被代理之前
        Target target = new TargetImpl();
        target.execute();
        System.out.println("被代理之后");

        //客户端可以在此处定义多种拦截逻辑
        Interceptor interceptor = new Interceptor() {
            public Object intercept(Invocation invocation) throws Throwable {
                System.out.println("Go Go Go!!!");
                return invocation.proceed();
            }
        };
        //被代理之后
        target = (Target) TargetProxy.bind(target, interceptor);
        target.execute();
    }

}
