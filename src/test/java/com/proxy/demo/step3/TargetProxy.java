package com.proxy.demo.step3;

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
    //拦截器
    private Interceptor interceptor;

    private TargetProxy(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    //通过传入客户端封装好 interceptor 的方式为 target 生成代理对象，使得客户端可以灵活使用不同的拦截器逻辑
    public static Object bind(Object target, Interceptor interceptor) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new TargetProxy(target, interceptor));
    }

    public Object invoke(Object proxy, Method method,
                         Object[] args) throws Throwable {
        //客户端实现自定义的拦截逻辑
        interceptor.intercept(method, args);
        return method.invoke(target, args);
    }

}
