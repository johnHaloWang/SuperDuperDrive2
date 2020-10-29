package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class FileService {
    private FileMapper fileMapper;
    public final static String TAG_ = "FileService";

    @Autowired
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int addFile(MultipartFile fileUpload, int userId) throws IOException{
        File file = new File(fileUpload, userId);
        log.debug(TAG_ +  "->  file detail check:  " + file.toString());
        return fileMapper.insert(file);
    }

    public List<File> getFilesByUserId(int userId){
        return fileMapper.getAll(userId);
    }

    public int updateFile(MultipartFile fileUpload, int userId, int fileId) throws IOException {
        File file = new File(fileUpload, userId);
        file.setFileId(fileId);
        return fileMapper.update(file);
    }

    public int deleteFile(int fileId){
        return fileMapper.delete(fileId);
    }

    public File getFile(int fileId){
        return fileMapper.getItemByItemId(fileId);
    }

    public boolean isDupicateFileName(int userId, String fileName){
        return (fileMapper.isDuplicateFile(userId, fileName)!= null);
    }

    public int deleteAll(){ return fileMapper.deleteAll(); }
}
