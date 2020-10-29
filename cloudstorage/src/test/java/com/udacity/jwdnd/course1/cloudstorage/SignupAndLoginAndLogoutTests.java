package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import com.udacity.jwdnd.course1.cloudstorage.utils.TestConstant;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Locale;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SignupAndLoginAndLogoutTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    public static String BASEURL;

    @BeforeAll
    static void beforeAll() {
        Locale.setDefault(new Locale("en","US"));
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        BASEURL = TestConstant.LOCALHOST + port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }
    @AfterAll
    static void aferAll(){

    }

    @Test
    @Order(1)
    public void testLoginUserFailed() throws InterruptedException{
        driver.get(SignupAndLoginAndLogoutTests.BASEURL+
                TestConstant.LOGIN_URL);
        LoginErrorPage loginErrorPage = new LoginErrorPage(driver);
        String msg = loginErrorPage.loginFailed(TestConstant.USERNAME, TestConstant.PASSWORD);
        log.debug("TESTING LOGIN-USER FAILED: " + msg);
        Assertions.assertEquals(SignupAndLoginAndLogoutTests.BASEURL+ TestConstant.LOGIN_ERROR_URL, driver.getCurrentUrl());
        String expected = TestConstant.LOGIN_ERROR_MSG;
        Assertions.assertEquals(expected, msg);
        Thread.sleep(6000);
    }

    @Test
    @Order(2)
    public void testSignupUser() throws InterruptedException {
        driver.get(SignupAndLoginAndLogoutTests.BASEURL+
                TestConstant.SIGNUP_URL);
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(TestConstant.FIRST_NAME, TestConstant.LAST_NAME, TestConstant.USERNAME, TestConstant.PASSWORD);
        Thread.sleep(3000);
    }

    @Test
    @Order(3)
    public void testLoginUser() throws InterruptedException {
        driver.get(SignupAndLoginAndLogoutTests.BASEURL+
                TestConstant.LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(driver, TestConstant.USERNAME, TestConstant.PASSWORD);
        Thread.sleep(3000);
        Assertions.assertEquals(SignupAndLoginAndLogoutTests.BASEURL+ TestConstant.HOME_URL, driver.getCurrentUrl());
    }

    @Test
    @Order(4)
    public void testLogoutUser() throws InterruptedException{
        driver.get(SignupAndLoginAndLogoutTests.BASEURL+
                TestConstant.LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(driver, TestConstant.USERNAME, TestConstant.PASSWORD);
        Thread.sleep(3000);

        driver.get(SignupAndLoginAndLogoutTests.BASEURL+ TestConstant.HOME_URL);
        Assertions.assertEquals(SignupAndLoginAndLogoutTests.BASEURL+ TestConstant.HOME_URL, driver.getCurrentUrl());

        NotePage notePage = new NotePage(driver);
        notePage.logout();
        Thread.sleep(3000);
        LogoutPage logoutPage = new LogoutPage(driver);
        Assertions.assertEquals(SignupAndLoginAndLogoutTests.BASEURL+ TestConstant.LOGOUT_URL, driver.getCurrentUrl());
        String expected = TestConstant.LOGOUT_MSG;
        Assertions.assertEquals(expected, logoutPage.loginOut());
        Thread.sleep(3000);

    }



}
