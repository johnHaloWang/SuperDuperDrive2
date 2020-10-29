package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.utils.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.util.Locale;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserMapperTests {

    private static User test;
    @LocalServerPort
    private int port;

    @Autowired
    private UserMapper userMapper;

    @BeforeAll
    static void beforeAll() {
        Locale.setDefault(new Locale("en","US"));
    }

    @BeforeEach
    public void beforeEach() {
        test = TestConstant.getUser();
    }

    @AfterEach
    public void afterEach() {
        userMapper.deleteAll();
    }

    @Test
    public void testInsertUser(){

        int addRow = userMapper.insert(test);
        Assertions.assertEquals(addRow, 1);

    }

    @Test void testSelectUser(){
        int addRow = userMapper.insert(test);
        User result = userMapper.getUser(test.getUsername());
        test.setUserId(result.getUserId());
        Assertions.assertEquals(result.toString(), test.toString());
    }

    @Test void testDeleteUser(){
        int addRow = userMapper.insert(test);
        User result = userMapper.getUser(test.getUsername());
        int delRow = userMapper.delete(result.getUserId());
        Assertions.assertEquals(delRow, 1);
        User result2 = userMapper.getUser(test.getUsername());
        Assertions.assertEquals(result2, null);
    }

}
