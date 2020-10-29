package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

public interface Rule {

    int delete(Integer itemId);
    int deleteAll();
    List<?> getAll(Integer userId);
    int insert(Object add);
    <T> T getItemByItemId(Integer itemId);
    int update(Object update);

}
