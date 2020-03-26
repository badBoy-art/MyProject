package com.parameterNameDiscoverer.study;


import org.junit.Test;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


/**
 * javap -verbose MainTest2.class
 * <p>
 * LineNumberTable  LocalVariableTable
 *
 * @authorxuedui.zhao
 * @create 2019-08-17
 * @see <a href = "https://blog.csdn.net/f641385712/article/details/99112603"/>
 */
public class ParameterNameDiscovererStudy {

    @Test
    public void test() throws Exception {
        Method method = Main.class.getMethod("test", String.class, Integer.class);
        MethodParameter nameParameter = new MethodParameter(method, 0);
        MethodParameter ageParameter = new MethodParameter(method, 1);

        Annotation[] annotations = nameParameter.getParameterAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            System.out.println("Annotation" + " i " + annotations[i]);
        }

        // 打印输出：
        // 使用Parameter输出
        Parameter[] parameters = method.getParameters();

// spring-core 5.0.2版本才有
//        Parameter nameOriginParameter = nameParameter.getParameter();
//        Parameter ageOriginParameter = ageParameter.getParameter();

        System.out.println("===================源生Parameter结果=====================");
        System.out.println(parameters[0].getType() + "----" + parameters[0].getName());
        System.out.println(parameters[1].getType() + "----" + parameters[1].getName());
        System.out.println("===================SpringAop源生Parameter结果=====================");
//        System.out.println(nameOriginParameter.getType() + "----" + nameOriginParameter.getName());
//        System.out.println(ageOriginParameter.getType() + "----" + ageOriginParameter.getName());
        System.out.println("===================MethodParameter结果=====================");
        System.out.println(nameParameter.getParameterType() + "----" + nameParameter.getParameterName());
        System.out.println(ageParameter.getParameterType() + "----" + ageParameter.getParameterName());
        System.out.println("==============设置上ParameterNameDiscoverer后MethodParameter结果===============");
        ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        nameParameter.initParameterNameDiscovery(parameterNameDiscoverer);
        ageParameter.initParameterNameDiscovery(parameterNameDiscoverer);
        System.out.println(nameParameter.getParameterType() + "----" + nameParameter.getParameterName());
        System.out.println(ageParameter.getParameterType() + "----" + ageParameter.getParameterName());

    }
}
