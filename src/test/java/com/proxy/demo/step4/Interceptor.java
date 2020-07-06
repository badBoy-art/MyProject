package com.proxy.demo.step4;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-02
 * @Description
 */
public interface Interceptor {

    //这种拦截方式会拦截 target 的所有方法，假如 Target 接口有多个方法 如果只想拦截一个方法该怎么搞？ ->step5
    Object intercept(Invocation invocation)throws Throwable ;

}
