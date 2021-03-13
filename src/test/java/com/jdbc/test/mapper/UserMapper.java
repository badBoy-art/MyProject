package com.jdbc.test.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jdbc.test.User;
import com.jdbc.test.mybatis.annotation.Shared;
import com.jdbc.test.mybatis.annotation.TableShardParam;
import com.jdbc.test.mybatis.annotation.TableShared;

/**
 * @author xuedui.zhao
 * @create 2018-06-04
 */
@Shared(tableShared = {@TableShared(tableName = "user")})
public interface UserMapper {

    User getUser(@Param("id") @TableShardParam("id") Integer id);

    int updateUser(@Param("id") int id, @Param("flag") boolean flag, @Param("name") String name);

    List<User> getUsers();

    int insertUser(@Param("name") String name, @Param("age") int age, @Param("sex") int sex);

    List<Map<String, String>> getAll();

    int updateBatch(@Param("users") List<User> users);

    List<User> getUserByIds(@Param("ids") int[] ids);

    User getUserById(Integer id);

}
