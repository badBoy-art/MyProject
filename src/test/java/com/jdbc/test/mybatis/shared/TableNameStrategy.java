package com.jdbc.test.mybatis.shared;

import com.jdbc.test.mybatis.util.TableIdUtils;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-09-29
 * @Description
 */
public abstract class TableNameStrategy {

    public long tableName(long dependFieldValue) {
        return TableIdUtils.get(dependFieldValue, 10);
    }

}
