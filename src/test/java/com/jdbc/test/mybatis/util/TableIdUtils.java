package com.jdbc.test.mybatis.util;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-09-29
 * @Description
 */
public class TableIdUtils {

    public static int get(long key, int maxSplit) {
        return (int) Math.abs(key % maxSplit);
    }

}
