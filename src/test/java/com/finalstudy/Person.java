package com.finalstudy;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-06-12
 * @Description
 */
public final class Person {

    private static ThreadLocal<String> operator = new ThreadLocal<>();

    private static String name;

    public static String getOperator() {
        return operator.get();
    }

    public static void setOperator(String operator) {
        Person.operator.set(operator);
    }

    public static void setName(String name) {
        Person.name = name;
    }

    public static String getName() {
        return name;
    }

}
