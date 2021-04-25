package com.proxy.demo.step6.impl;

import com.proxy.demo.step4.Invocation;
import com.proxy.demo.step5.MethodName;
import com.proxy.demo.step6.Interceptor;
import com.proxy.demo.step6.TargetProxy;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-07-02
 * @Description
 */
@MethodName("execute1")
public class InterceptorImpl implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println(invocation.getMethod().getName() + " Go Go Go!!!");
        return invocation.proceed();
    }

    public Object register(Object target) {
        return TargetProxy.bind(target, this);
    }

}
