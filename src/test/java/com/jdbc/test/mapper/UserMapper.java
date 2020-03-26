package com.jdbc.test.mapper;

import com.jdbc.test.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xuedui.zhao
 * @create 2018-06-04
 */
public interface UserMapper {

    User getUser(Integer id);

    int updateUser(@Param("id") int id, @Param("flag") boolean flag, @Param("name") String name);

    List<User> getUsers();

    int insertUser(@Param("name") String name, @Param("age") int age, @Param("sex") int sex);

    List<Map<String, String>> getAll();

    int updateBatch(@Param("users") List<User> users);

    List<User> getUserByIds(@Param("ids")int[] ids);

    User getUserById(Integer id);

}
