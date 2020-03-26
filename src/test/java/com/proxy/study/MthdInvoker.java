package com.proxy.study;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author xuedui.zhao
 * @create 2018-11-24
 */
public class MthdInvoker implements MethodInterceptor {

    private Cglib s;
    public MthdInvoker(Cglib s) {
        this.s = s;
    }
    private void aopMethod() {
        System.out.println("i am aopMethod");
    }
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        aopMethod();
        Object a = method.invoke(s, args);
        return a;
    }

}
