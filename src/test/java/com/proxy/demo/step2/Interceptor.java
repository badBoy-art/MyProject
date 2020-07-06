package com.proxy.demo.step2;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-02
 * @Description
 */
public interface Interceptor {
    //该方法是无参的  实际业务中没什么意义  ->step3
    void intercept();
}
