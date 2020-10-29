package com.udacity.jwdnd.course1.cloudstorage;

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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CredentialTests {
    @Autowired
    private UserService userService;

    @LocalServerPort
    private int port;

    private WebDriver driver;
    public static String BASEURL;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public final static String TAG_ = "CredentialTests";

    private SignupPage signupPage;
    private ResultPage resultPage;
    private LoginPage loginPage;
    private CredentialPage credentialPage;

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
        driver.get(CredentialTests.BASEURL+
                TestConstant.SIGNUP_URL);

        credentialPage = new CredentialPage(driver);
        resultPage = new ResultPage(driver);

        driver.get(CredentialTests.BASEURL+
                TestConstant.LOGIN_URL);
        loginPage = new LoginPage(driver);

        loginPage.login(driver, TestConstant.USERNAME, TestConstant.PASSWORD);

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
    public void testAddCred() throws InterruptedException {
        String expectedUsername = "username1";
        String expectedPassword = "password2";
        String expectedUrl = "url1";
        credentialPage.addCredential(driver, expectedUsername, expectedPassword, expectedUrl);

        resultPage.clickOnSuccess(driver);
        credentialPage.clickCredentialTab(driver);
        String resultUsername = credentialPage.getDisplayUsername(driver);
        String resultUrl = credentialPage.getDisplayUrl(driver);
        Assertions.assertEquals(expectedUsername, resultUsername);
        Assertions.assertEquals(expectedUrl, resultUrl);
    }

    @Test
    @Order(2)
    public void TestPassword() throws InterruptedException{
        credentialPage.clickCredentialTab(driver);
        String resultPassword = credentialPage.clickEditBtn(driver);
        String expectedPassword = "password2";
        Assertions.assertEquals(expectedPassword, resultPassword);
    }

    @Test
    @Order(3)
    public void testDelete() throws InterruptedException {
        credentialPage.deleteCredential(driver);
        resultPage.clickOnSuccess(driver);
        credentialPage.clickCredentialTab(driver);
        String resultUrl = credentialPage.getDisplayUrl(driver);
        String resultUsername = credentialPage.getDisplayUsername(driver);
        String resultPassword = credentialPage.getDisplayPassword(driver);
        Assertions.assertEquals(null, resultUrl);
        Assertions.assertEquals(null, resultUsername);
        Assertions.assertEquals(null, resultPassword);
    }

    @Test
    @Order(4)
    public void testEdit() throws InterruptedException {
        String expectedUrl = "title3";
        String expectedPassword = "password3";
        String expectedUsername = "username3";
        credentialPage.addCredential(driver, expectedUsername, expectedPassword, expectedUrl);
        resultPage.clickOnSuccess(driver);
        credentialPage.clickCredentialTab(driver);


        String resultUsername = credentialPage.getDisplayUsername(driver);
        String resultUrl = credentialPage.getDisplayUrl(driver);

        Assertions.assertEquals(expectedUsername, resultUsername);
        Assertions.assertEquals(expectedUrl, resultUrl);

        expectedUrl = "new url";
        expectedPassword = "new password";
        expectedUsername = "new username";

        credentialPage.editCredential(driver, expectedUsername, expectedPassword, expectedUrl);
        resultPage.clickOnSuccess(driver);
        credentialPage.clickCredentialTab(driver);

        resultUsername = credentialPage.getDisplayUsername(driver);
        resultUrl = credentialPage.getDisplayUrl(driver);

        Assertions.assertEquals(expectedUsername, resultUsername);
        Assertions.assertEquals(expectedUrl, resultUrl);

    }
}
