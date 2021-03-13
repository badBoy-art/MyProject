package com.jdbc.test.mybatis.shared;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-09-28
 * @Description
 */
@Data
@Accessors(chain = true)
public class TableCreateConfig {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 自动建表Mapper类
     */
    private Class<?> autoCreateTableMapperClass;

    /**
     * 自动建表Mapper中的方法
     */
    private String autoCreateTableMapperMethodName;

}
