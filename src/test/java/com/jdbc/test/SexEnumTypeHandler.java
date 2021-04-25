package com.jdbc.test;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-07-02
 * @Description
 */
public class SexEnumTypeHandler extends BaseTypeHandler<SexEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SexEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public SexEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object object = rs.getObject(columnName);
        Integer sex = object != null && object instanceof Integer ? (Integer) object : null;
        return SexEnum.codeOf(sex);
    }

    @Override
    public SexEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object object = rs.getObject(columnIndex);
        Integer sex = object != null && object instanceof Integer ? (Integer) object : null;
        return SexEnum.codeOf(sex);
    }

    @Override
    public SexEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object object = cs.getObject(columnIndex);
        Integer sex = object != null && object instanceof Integer ? (Integer) object : null;
        return SexEnum.codeOf(sex);
    }
}
