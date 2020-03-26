package com.parameterNameDiscoverer.study;

import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author xuedui.zhao
 * @create 2019-08-18
 */
public class Main6 {

    // private static final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private static final ParameterNameDiscoverer parameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();


    public void testArguments(String test, Integer myInteger, boolean booleanTest) {
    }

    public void test() {
    }

    public static void main(String[] args) {
        Method[] methods = Main6.class.getDeclaredMethods();
        for (Method method : methods) {
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            System.out.println("方法：" + method.getName() + " 参数为：" + Arrays.asList(parameterNames));
        }
    }
}
