package com.jdbc.test.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jdbc.test.mybatis.shared.TableNameStrategy;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-09-29
 * @Description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface TableShared {

    /**
     * 如果自己设置了基础表的名字,那么我们分表的时候只会处理这些指定的表.
     */
    String tableName();

    Class<? extends TableNameStrategy> strategy() default TableNameStrategy.class;
}
