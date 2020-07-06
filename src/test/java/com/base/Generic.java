package com.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-06-17
 * @Description
 */
public class Generic<T> {
    private Class<T> entityClass;

    protected Generic() {
        Type type = getClass().getGenericSuperclass();
        System.out.println(type);
        System.out.println(((ParameterizedType) type).getActualTypeArguments());
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        this.entityClass = (Class<T>) trueType;
    }
}
