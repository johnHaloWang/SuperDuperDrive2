package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper extends Rule {
    String getFilesByUserIdSql ="SELECT * FROM FILES WHERE  userid= #{userId}";
    String insertFileSql = "INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
                                    "VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})";
    String deleteFileByNoteIdSql = "DELETE FROM FILES WHERE fileId = #{fileId}";
    String getFileByFileIdSql = "SELECT * FROM FILES WHERE fileId=#{fileId}";
    String updateFileByNoteObjectSql =  "UPDATE FILES SET " +
                                        "filename = #{filename}, " +
                                        "filedata = #{fileData}, " +
                                        "contenttype = #{contentType}, " +
                                        "filesize = #{fileSize} , " +
                                        "userid = #{userId} " +
                                        "WHERE fileId = #{fileId}";
    String duplicateFileSql = "SELECT * FROM FILES WHERE  userid= #{userId} AND filename = #{filename}";

    String deleteAllSql = "DELETE FROM FILES";


    @Select(duplicateFileSql)
    File isDuplicateFile(int userId, String filename);

    @Delete(deleteAllSql)
    int deleteAll();

    @Delete(deleteFileByNoteIdSql)
    int delete(Integer itemId);

    @Select(getFilesByUserIdSql)
    List<File> getAll(Integer userId);

    @Select(getFileByFileIdSql)
    File getItemByItemId(Integer fileId);

    @Override
    @Insert(insertFileSql)
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(Object add);

    @Override
    @Update(updateFileByNoteObjectSql)
    int update(Object update);



}
