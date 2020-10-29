package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileServiceTests {
    public final static String TAG_ = "FileServiceTests";
    private User user;
    private File file1;
    private File file2;

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileMapper fileMapper;

    @BeforeAll
    static void beforeAll() {
        Locale.setDefault(new Locale("en","US"));

    }

    @BeforeEach
    public void beforeEach() throws IOException {
        user = TestConstant.getUser();
        userService.createUser(user);
        user = userService.getUser(user.getUsername());
        file1 = TestConstant.getFile(user.getUserId(), TestConstant.fileName1);
        file2 = TestConstant.getFile(user.getUserId(), TestConstant.fileName2);
    }

    @AfterEach
    public void afterEach() {
        fileMapper.deleteAll();
        userService.deleteAll();
    }

    @Test
    public void testAddFile() throws IOException {
        int addRow = fileService.addFile(TestConstant.getMultipartFile(TestConstant.fileName1), user.getUserId());
        Assertions.assertEquals(1, addRow);
    }

    @Test
    public void testGetFilesByUserId() throws IOException{
        int addRow = fileService.addFile(TestConstant.getMultipartFile(TestConstant.fileName1), user.getUserId());
        Assertions.assertEquals(1, addRow);
        List<File> list = fileService.getFilesByUserId(user.getUserId());
        Assertions.assertEquals(1, list.size());
    }

    @Test
    public void testGetFile() throws IOException{
        int addRow = fileService.addFile(TestConstant.getMultipartFile(TestConstant.fileName1), user.getUserId());
        Assertions.assertEquals(1, addRow);
        List<File> list = fileService.getFilesByUserId(user.getUserId());
        File file = fileService.getFile(list.get(0).getFileId());
        Assertions.assertEquals(file.toString(), list.get(0).toString());
    }

    @Test
    public void testUpdateFile() throws IOException {
        int addRow = fileService.addFile(TestConstant.getMultipartFile(TestConstant.fileName1), user.getUserId());
        List<File> list = fileService.getFilesByUserId(user.getUserId());
        int editRow = fileService.updateFile(TestConstant.getMultipartFile(TestConstant.fileName2), user.getUserId(), list.get(0).getFileId());
        File newFile = new File(TestConstant.getMultipartFile(TestConstant.fileName2), user.getUserId());
        newFile.setFileId(list.get(0).getFileId());
        Assertions.assertEquals(1, editRow);
        list = fileService.getFilesByUserId(user.getUserId());
        Assertions.assertEquals(list.get(0).toString(), newFile.toString());

    }

    @Test
    public void testDeleteNoteById() throws IOException, SizeLimitExceededException {
        fileService.addFile(TestConstant.getMultipartFile(TestConstant.fileName1), user.getUserId());
        int fileId = fileService.getFilesByUserId(user.getUserId()).get(0).getFileId();
        int deleteRow = fileService.deleteFile(fileId);
        Assertions.assertEquals(1, deleteRow);
        Assertions.assertEquals(0, fileService.getFilesByUserId(user.getUserId()).size());
    }

    @Test
    public void testDeleteAll() throws IOException, SizeLimitExceededException {
        int expected = 3;
        for(int i = 0; i<expected; i++)
            fileService.addFile(TestConstant.getMultipartFile(TestConstant.fileName1), user.getUserId());
        Assertions.assertEquals(expected, fileService.deleteAll());
    }

    @Test
    public void testDuplicatFunction() throws  IOException, SizeLimitExceededException{
        fileService.addFile(TestConstant.getMultipartFile(TestConstant.fileName1), user.getUserId());
        Assertions.assertTrue(fileService.isDupicateFileName(user.getUserId(), TestConstant.fileName1));
        Assertions.assertFalse(fileService.isDupicateFileName(user.getUserId(), TestConstant.fileName2));
    }
}
