package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.TestConstant;
import lombok.extern.slf4j.Slf4j;
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
public class CredentialServiceTests {
    public final static String TAG_ = "CredentialServiceTests";
    private User user;
    private Credential credential;

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private CredentialMapper credentialMapper;

    @BeforeAll
    static void beforeAll() {
        Locale.setDefault(new Locale("en","US"));

    }

    @BeforeEach
    public void beforeEach() throws IOException {
        user = TestConstant.getUser();
        userService.createUser(user);
        user = userService.getUser(user.getUsername());
        credential = TestConstant.getCredential(user.getUserId());
    }

    @AfterEach
    public void afterEach() {
        credentialMapper.deleteAll();
        userService.deleteAll();
    }

    @Test
    public void testAddCredential() throws IOException {
        int addRow = credentialService.addCredential(credential);
        Assertions.assertEquals(1, addRow);
    }

    @Test
    public void testGetCredentialByUserId() throws IOException {
        int addRow = credentialService.addCredential(credential);
        Assertions.assertEquals(1, addRow);
        List<Credential> list = credentialService.getAllUserCredentials(user.getUserId());
        Assertions.assertEquals(1, list.size());
    }

    @Test
    public void testGetCredentialByCredentialId() throws IOException {
        int addRow = credentialService.addCredential(credential);
        Assertions.assertEquals(1, addRow);
        List<Credential> list = credentialService.getAllUserCredentials(user.getUserId());
        Assertions.assertEquals(1, list.size());
        Credential result = credentialService.getCredentialById(list.get(0).getCredentialId());
        Assertions.assertEquals(list.get(0).toString(), result.toString());
    }

    @Test
    public void testEditCredential() {
        credentialService.addCredential(credential);
        List<Credential> list = credentialService.getAllUserCredentials(user.getUserId());
        credential.setUrl("new url");
        credential.setCredentialId(list.get(0).getCredentialId());
        int editRow = credentialService.editCredential(credential);
        Assertions.assertEquals(1, editRow);
        Credential result = credentialService.getCredentialById(list.get(0).getCredentialId());
        Assertions.assertEquals(credential.toString(), result.toString());
    }

    @Test
    public void testDeleteNoteById(){
        credentialService.addCredential(credential);
        List<Credential> list = credentialService.getAllUserCredentials(user.getUserId());
        int credentialId = list.get(0).getCredentialId();
        int deleteRow = credentialService.deleteCredential(credentialId);
        Assertions.assertEquals(1, deleteRow);
        Assertions.assertEquals(0, credentialService.getAllUserCredentials(user.getUserId()).size());
    }



    @Test
    public void testDeleteAll(){
        int expected = 3;
        for(int i = 0; i<expected; i++)
            credentialService.addCredential(credential);
        Assertions.assertEquals(expected, credentialService.deleteAll());
    }
}
