package com.snow.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author xuedui.zhao
 * @create 2018-12-20
 */
public class ByteBuddyInterceptor {

     public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) {

        try {
                System.out.println("bytebuddy do something before invoke sayHello");

                return callable.call();
            } catch (Exception e) {
                System.out.println("Exception :" + e.getMessage());
                return null;
            } finally {
                System.out.println("bytebuddy do something after invoke sayHello");
            }
     }
}
