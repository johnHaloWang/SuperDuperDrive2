package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Data
@ToString(includeFieldNames = true)
public class File {
    private int fileId;
    private String filename;
    private String contentType;
    private String fileSize;
    private int userId;
    private byte[] fileData;

    public File(String filename, String contentType, String filesize, int userId, byte[] fileData) {
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = filesize;
        this.userId = userId;
        this.fileData = fileData;
    }

    public File() {
    }

    public File(MultipartFile fileUpload, int userId) throws IOException {
        try{
            this.contentType= fileUpload.getContentType();
            this.fileData = fileUpload.getBytes();
            this.filename = fileUpload.getOriginalFilename();
            this.userId = userId;
            this.fileSize = Long.toString(fileUpload.getSize());
        }catch(IOException e){
            throw e;
        }
    }

    public File(int fileId, String filename, String contentType, String filesize, int userId, byte[] fileData) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = filesize;
        this.userId = userId;
        this.fileData = fileData;
    }

}
