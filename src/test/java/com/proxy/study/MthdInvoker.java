package com.proxy.study;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author xuedui.zhao
 * @create 2018-11-24  CGLIB
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
