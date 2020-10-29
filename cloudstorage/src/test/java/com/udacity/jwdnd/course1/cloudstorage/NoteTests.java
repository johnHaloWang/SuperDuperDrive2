package com.udacity.jwdnd.course1.cloudstorage;

import ch.qos.logback.core.spi.LifeCycle;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.TestConstant;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;


import java.util.Locale;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class NoteTests {

    @Autowired
    private UserService userService;

    @LocalServerPort
    private int port;

    private WebDriver driver;
    public static String BASEURL;
    public final static String TAG_ = "NoteTests";

    private SignupPage signupPage;
    private ResultPage resultPage;
    private NotePage notePage;
    private static User user;
    private LoginPage loginPage;

    public NoteTests() {
    }


    @BeforeAll
    void beforeAll() throws InterruptedException {
        Locale.setDefault(new Locale("en","US"));
        WebDriverManager.chromedriver().setup();
        int addRow = userService.createUser(TestConstant.getUser());
        Assertions.assertEquals(1, addRow);
    }

    @BeforeEach
    public void beforeEach() throws InterruptedException {
        BASEURL = TestConstant.LOCALHOST + port;
        driver = new ChromeDriver();
        notePage = new NotePage(driver);
        resultPage = new ResultPage(driver);
        driver.get(NoteTests.BASEURL+
                TestConstant.LOGIN_URL);
        loginPage = new LoginPage(driver);

        loginPage.login(driver, TestConstant.USERNAME, TestConstant.PASSWORD);
        Thread.sleep(3000);


    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }
    @AfterAll
    void afterAll(){

    }

    @Test
    @Order(1)
    public void testAddNote() throws InterruptedException {
        String expectedTitle = "title";
        String expectedDescription = "description";
        notePage.addNote(driver, expectedTitle, expectedDescription);

        resultPage.clickOnSuccess(driver);
        notePage.clickNoteTab(driver);
        String resultTitle = notePage.getDisplayTitle(driver);
        String resultDes = notePage.getDisplayDes(driver);
        Assertions.assertEquals(expectedTitle, resultTitle);
        Assertions.assertEquals(expectedDescription, resultDes);
    }

    @Test
    @Order(2)
    public void testDeleteNote() throws InterruptedException {
        notePage.deleteNote(driver);
        resultPage.clickOnSuccess(driver);
        String resultTitle = notePage.getDisplayTitle(driver);
        String resultDes = notePage.getDisplayDes(driver);
        Assertions.assertEquals(null, resultTitle);
        Assertions.assertEquals(null, resultDes);
        String expectedTitle = "title";
        String expectedDescription = "description";
        notePage.clickNoteTab(driver);
        for(int i = 0; i<2; i++) {
            notePage.addNote(driver, expectedTitle, expectedDescription);
            resultPage.clickOnSuccess(driver);
        }
        notePage.deleteNote(driver);
        resultPage.clickOnSuccess(driver);
        notePage.deleteNote(driver);
        resultPage.clickOnSuccess(driver);
        notePage.clickNoteTab(driver);
        resultTitle = notePage.getDisplayTitle(driver);
        resultDes = notePage.getDisplayDes(driver);
        Assertions.assertEquals(null, resultTitle);
        Assertions.assertEquals(null, resultDes);
    }

    @Test
    @Order(3)
    public void testEditNote() throws InterruptedException {
        String expectedTitle = "title";
        String expectedDescription = "description";
        notePage.addNote(driver, expectedTitle, expectedDescription);
        resultPage.clickOnSuccess(driver);
        notePage.clickNoteTab(driver);
        String resultTitle = notePage.getDisplayTitle(driver);
        String resultDes = notePage.getDisplayDes(driver);
        Assertions.assertEquals(expectedTitle, resultTitle);
        Assertions.assertEquals(expectedDescription, resultDes);

        expectedTitle = "new title";
        expectedDescription = "new description";
        notePage.editNote(driver, expectedTitle, expectedDescription);
        resultPage.clickOnSuccess(driver);
        notePage.clickNoteTab(driver);
        resultTitle = notePage.getDisplayTitle(driver);
        resultDes = notePage.getDisplayDes(driver);
        Assertions.assertEquals(expectedTitle, resultTitle);
        Assertions.assertEquals(expectedDescription, resultDes);
    }
}
