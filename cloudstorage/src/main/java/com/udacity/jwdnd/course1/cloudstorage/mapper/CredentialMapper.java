package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper extends Rule {

    String getGetCredentialByUserIdSql ="SELECT * FROM CREDENTIALS WHERE  userid= #{userId}";
    String getCredentialByIdSql = "SELECT * FROM CREDENTIALS WHERE  credentialid= #{credentialId}";
    String insertByUserObjSql = "INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})";
    String deleteByIdSql = "DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}";
    String deleteAllSql = "DELETE FROM CREDENTIALS";
    String updateNoteByCredentialObjectSql =    "UPDATE CREDENTIALS SET " +
            "url = #{url}, " +
            "username = #{username}, " +
            "password = #{password} " +
            "WHERE credentialid = #{credentialId}";
    String getKeyByUserIdAndCredentialIdSql = "SELECT key FROM CREDENTIALS WHERE  credentialid= #{credentialId}";

    @Override
    @Delete((deleteAllSql))
    int delete(Integer itemId);

    @Override
    @Select(getGetCredentialByUserIdSql)
    List<Credential> getAll(Integer userId);

    @Override
    @Delete(deleteAllSql)
    int deleteAll();

    @Override
    @Select(getCredentialByIdSql)
    Credential getItemByItemId(Integer itemId);

    @Select(getKeyByUserIdAndCredentialIdSql)
    String getKey(int credetnialId);

    @Override
    @Insert(insertByUserObjSql)
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Object add);

    @Override
    @Update(updateNoteByCredentialObjectSql)
    int update(Object update);

}
