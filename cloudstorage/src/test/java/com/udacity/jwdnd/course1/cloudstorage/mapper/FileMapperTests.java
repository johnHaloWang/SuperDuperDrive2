package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
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
public class FileMapperTests {

    public final static String TAG_ = "FileMapperTest";

    private File test;
    private User user;

    @LocalServerPort
    private int port;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;

    @BeforeAll
    static void beforeAll() {
        Locale.setDefault(new Locale("en","US"));
    }

    @BeforeEach
    public void beforeEach() {
        user = TestConstant.getUser();
        userMapper.insert(user);
        user = userMapper.getUser(user.getUsername());
    }


    @AfterEach
    public void afterEach() {
        fileMapper.deleteAll();
        userMapper.deleteAll();

    }

    @AfterAll
    static void afterAll(){

    }

    @Test
    public void testAddFile() throws IOException {
        test =TestConstant.getFile(user.getUserId(), TestConstant.fileName1);
        int addRow = fileMapper.insert(test);
        Assertions.assertEquals(1, addRow);
    }

    @Test
    public void testSelectFile() throws IOException {
        test =TestConstant.getFile(user.getUserId(), TestConstant.fileName1);
        int addRow = fileMapper.insert(test);
        Assertions.assertEquals(1, addRow);
        List<File> list = fileMapper.getAll(user.getUserId());
        test.setFileId(list.get(0).getFileId());
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(list.get(0).toString(), test.toString());
    }

    @Test
    public void testDeleteFile() throws IOException{
        test =TestConstant.getFile(user.getUserId(), TestConstant.fileName1);
        int addRow = fileMapper.insert(test);
        Assertions.assertEquals(1, addRow);
        List<File> list = fileMapper.getAll(user.getUserId());
        Assertions.assertEquals(1, list.size());
        int delRow = fileMapper.delete(list.get(0).getFileId());
        Assertions.assertEquals(1, delRow);
        File result = fileMapper.getItemByItemId(list.get(0).getFileId());
        Assertions.assertNull(result);
    }
}
