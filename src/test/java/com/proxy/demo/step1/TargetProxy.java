package com.proxy.demo.step1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-02
 * @Description
 */
public class TargetProxy implements InvocationHandler {

    private Object target;

    private TargetProxy(Object target) {
        this.target = target;
    }

    //代理对象生成目标对象
    public static Object bind(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new TargetProxy(target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //拦截逻辑在代理对象中写死了，这样到这客户端没有灵活的设置来拦截其逻辑  ->step2
        System.out.println("Begin");
        return method.invoke(target, args);
    }

}
