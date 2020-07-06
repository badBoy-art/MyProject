package com.proxy.demo.step3;

import java.lang.reflect.Method;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-02
 * @Description
 */
public interface Interceptor {

    void intercept(Method method, Object[] args);

}
