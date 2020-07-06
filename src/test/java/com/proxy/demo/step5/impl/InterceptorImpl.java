package com.proxy.demo.step5.impl;

import com.proxy.demo.step4.Invocation;
import com.proxy.demo.step5.Interceptor;
import com.proxy.demo.step5.MethodName;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-02
 * @Description
 */
@MethodName("execute1")
public class InterceptorImpl implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("Go Go Go!!!");
        return invocation.proceed();
    }
}
