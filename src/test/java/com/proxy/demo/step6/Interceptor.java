package com.proxy.demo.step6;

import com.proxy.demo.step4.Invocation;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-07-02
 * @Description
 */
public interface Interceptor {

    Object intercept(Invocation invocation) throws Throwable;

    Object register(Object target);

}
