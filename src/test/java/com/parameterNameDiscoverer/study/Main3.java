package com.parameterNameDiscoverer.study;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * java8: javac -parameters
 *
 * @author xuedui.zhao
 * @create 2019-08-17
 */
public class Main3 {

    public static void main(String[] args) throws NoSuchMethodException {
        Method method = Main3.class.getMethod("testArgName", String.class, Integer.class);
        System.out.println("paramCount:" + method.getParameterCount());
        for (Parameter parameter : method.getParameters()) {
            System.out.println(parameter.getType().getName() + "-->" + parameter.getName());
        }
    }

    public String testArgName(String name, Integer age) {
        return null;
    }

}
