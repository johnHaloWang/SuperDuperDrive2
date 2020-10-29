package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.utils.TestConstant;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import com.udacity.jwdnd.course1.cloudstorage.model.*;

import java.util.List;
import java.util.Locale;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialMapperTests {

    private static Credential test ;
    private User user;
    @LocalServerPort
    private int port;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CredentialMapper credentialMapper;

    @BeforeAll
    static void beforeAll() {
        Locale.setDefault(new Locale("en","US"));

    }

    @BeforeEach
    public void beforeEach() {
        user = TestConstant.getUser();
        //new User(null, "John", "test", "password", "first", "last");;
        userMapper.insert(user);
        user = userMapper.getUser(user.getUsername());
        test = TestConstant.getCredential(user.getUserId());
    }

    @AfterEach
    public void afterEach() {
        credentialMapper.deleteAll();
        userMapper.deleteAll();
    }

    @Test
    public void testInsertCredential(){
        int addRow = credentialMapper.insert(test);
        Assertions.assertEquals(1, addRow);
    }

    @Test void testSelectCredential(){
        int addRow = credentialMapper.insert(test);
        List<Credential> list = credentialMapper.getAll(user.getUserId());
        int credentialId = list.get(0).getCredentialId();
        test.setCredentialId(credentialId);
        Credential result = credentialMapper.getItemByItemId(credentialId);
        Assertions.assertEquals(test.toString(), result.toString());
    }

    @Test void testUpdateCredential(){
        int addRow = credentialMapper.insert(test);
        List<Credential> list = credentialMapper.getAll(user.getUserId());
        int credentialId = list.get(0).getCredentialId();
        test.setCredentialId(credentialId);
        Credential expected = credentialMapper.getItemByItemId(credentialId);
        expected.setUsername("testing");
        int result = credentialMapper.update(expected);
        Assertions.assertEquals(1, result);
        Credential resultCredential = credentialMapper.getItemByItemId(credentialId);
        Assertions.assertEquals(expected.toString(), resultCredential.toString());
    }

    @Test void testDeleteNote(){
        int addRow = credentialMapper.insert(test);
        List<Credential> list = credentialMapper.getAll(user.getUserId());
        int credentialId = list.get(0).getCredentialId();
        credentialMapper.delete(credentialId);
        List<Credential> result = credentialMapper.getAll(user.getUserId());
        Assertions.assertEquals(0, result.size());

    }

    @Test void testListOfNote(){
        credentialMapper.deleteAll();
        int expected = 5;
        for(int i = 0; i< expected; i++) {
            //test = new Credential("url", "username", "key", "password",user.getUserId());
            test = TestConstant.getCredential(user.getUserId());
            credentialMapper.insert(test);
        }
        List<Credential> result = credentialMapper.getAll(user.getUserId());
        Assertions.assertEquals(expected, result.size());

    }



}
