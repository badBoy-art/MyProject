package com.proxy.demo.step5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

import com.proxy.demo.step4.Invocation;


/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
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

    @Override
    public Object invoke(Object proxy, Method method,
                         Object[] args) throws Throwable {
        MethodName methodName = this.interceptor.getClass().getAnnotation(MethodName.class);
        if (Objects.isNull(methodName)) {
            throw new NullPointerException("xxxx");
        }
        //如果方法名称和注解标记的方法名称相同，则拦截
        String name = methodName.value();
        if (name.equals(method.getName())) {
            System.out.println("proxy " + name);
            return interceptor.intercept(new Invocation(target, method, args));
        }
        return method.invoke(this.target, args);
    }


}
