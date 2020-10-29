package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends Rule {

    String getUserByUsernameSql = "SELECT * FROM USERS WHERE  username= #{username}";
    String insertByUserObjSql = "INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})";
    String deleteByIdSql = "DELETE FROM USERS WHERE userid = #{userId}";
    String deleteAllSql = "DELETE FROM USERS";

    @Select(getUserByUsernameSql)
    User getUser(String username);

    @Delete(deleteByIdSql)
    int delete(Integer userId);

    @Delete((deleteAllSql))
    int deleteAll();

    @Override
    @Insert(insertByUserObjSql)
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(Object add);

}
