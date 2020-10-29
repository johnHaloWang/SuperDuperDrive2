package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Locale;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTests {

    //private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public final static String TAG_ = "UserServiceTest";
    private User user;
    private static TestConstant testConstant;
    private String username = TestConstant.USERNAME;
    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HashService hashService;

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
        userMapper.deleteAll();
    }

    @Test
    public void testIsUsernameAvailableTest(){
        boolean result;
        result= userService.isUsernameAvailable(username);
        Assertions.assertEquals(result, false);
        userMapper.delete(user.getUserId());
        result= userService.isUsernameAvailable(username);
        Assertions.assertEquals(result, true);
    }

    @Test
    public void testGetUserTest(){
        User result = userService.getUser(username);
        Assertions.assertEquals(result.toString(), user.toString());
    }

    @Test
    public void testDeleteUserTest(){
        int delRow = userService.deleteUser(user);
        Assertions.assertEquals(delRow, 1);
        Assertions.assertEquals(null, userService.getUser(username));
    }

    @Test
    public void testCreateUserTest(){
        username = "Jack";
        user = TestConstant.getUser();
        user.setUsername(username);
        int addRow = userService.createUser(user);
        Assertions.assertEquals(addRow, 1);
        User expected = userMapper.getUser(user.getUsername());
        user.setUserId(expected.getUserId());
        user.setSalt(expected.getSalt());
        user.setPassword(expected.getPassword());
        Assertions.assertEquals(expected.toString(), user.toString());
    }

}
