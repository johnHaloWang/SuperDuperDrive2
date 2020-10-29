package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper extends Rule{

    String getNotesByUserIdSql ="SELECT * FROM NOTES WHERE  userid= #{userId}";
    String insertNoteSql = "INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})";
    String deleteNoteByNoteIdSql = "DELETE FROM NOTES WHERE noteid = #{noteId}";
    String deleteAllSql = "DELETE NOTES";
    String getNoteByNoteIdSql = "SELECT * FROM NOTES WHERE noteid=#{noteId}";
    String updateNoteByNoteObjectSql =    "UPDATE NOTES SET " +
            "notetitle = #{noteTitle}, " +
            "notedescription = #{noteDescription} " +
            "WHERE noteid = #{noteId}";

    @Delete(deleteNoteByNoteIdSql)
    int delete(Integer itemId);

    @Delete(deleteAllSql)
    int deleteAll();

    @Select(getNotesByUserIdSql)
    List<Note> getAll(Integer userId);

    @Insert(insertNoteSql)
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Object add);

    @Select(getNoteByNoteIdSql)
    Note getItemByItemId(Integer noteId);

    @Update(updateNoteByNoteObjectSql)
    int update(Object update);



}
