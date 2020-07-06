package com.proxy.demo.step5;

import com.proxy.demo.step4.Invocation;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-02
 * @Description
 */
public interface Interceptor {

    //客户端第一步创建 target 对象和 interceptor 对象，通过传入 target 和 interceptor 调用 bind 方法生成代理对象，
    //最终代理对象调用 execute 方法，根据迪米特法则，客户端不需要了解 TargetProxy，只需要关注拦截器的内部逻辑和可调用的方法即可
    //-> step6
    Object intercept(Invocation invocation) throws Throwable;

}
