package com.proxy.demo.step3.impl;

import java.lang.reflect.Method;

import com.proxy.demo.step3.Interceptor;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-02
 * @Description
 */
public class InterceptorImpl implements Interceptor {

    @Override
    public void intercept(Method method, Object[] args) {
        //可以在 interceptor 中调用 method.invoke，但是拦截器中并没有 invoke 方法需要的关键参数 target -> step4
        System.out.println(method.getName() + " args = " + args);
    }
}
