package com.udacity.jwdnd.course1.cloudstorage.utils;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class TestConstant {
    public static final String SIGNUP_URL = "/signup";
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/login?logout";
    public static final String HOME_URL = "/home";
    public static final String LOCALHOST = "http://localhost:";
    public static final String LOGIN_ERROR_URL = "/login?error";
    public static final String LOGOUT_MSG = "You have been logged out";
    public static final String LOGIN_ERROR_MSG = "Invalid username or password";
    public static final String USERNAME = "j";
    public static final String PASSWORD = "j";
    public static final String FIRST_NAME = "j";
    public static final String LAST_NAME = "j";
    public static final String SALT = "test";
    public static final String fileName1 = "testingFile.txt";
    public static final String fileName2 = "testingFile2.txt";

    public TestConstant() {

    }

    public static User getUser(){
        return new User(null,TestConstant.USERNAME, TestConstant.SALT, TestConstant.PASSWORD, TestConstant.FIRST_NAME, TestConstant.LAST_NAME);
    };

    public static File getFile(int userId, String fileName) throws IOException {
        ClassLoader classLoader = TestConstant.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        String path = resource.getPath();
        path = (path.charAt(0)=='/')?path.substring(1, path.length()):path;
        java.io.File file = new java.io.File(path);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        return new File(multipartFile, userId);
    }

    public static MultipartFile getMultipartFile(String fileName) throws IOException{
        ClassLoader classLoader = TestConstant.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        String path = resource.getPath();
        path = (path.charAt(0)=='/')?path.substring(1, path.length()):path;
        java.io.File file = new java.io.File(path);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        return multipartFile;
    }

    public static Credential getCredential(int userId){
        return new Credential(0, "url", "username", "key","password", userId);
    }

    public static Note getNote(int userId){
        return new Note(0, "title", "description", userId);
    }

}
