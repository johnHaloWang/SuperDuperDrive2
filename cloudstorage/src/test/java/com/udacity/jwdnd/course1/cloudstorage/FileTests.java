package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.FilePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.utils.TestConstant;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.URL;
import java.util.Locale;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileTests {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    public static String BASEURL;
    public final static String TAG_ = "FileTests";
    private SignupPage signupPage;
    private ResultPage resultPage;
    private LoginPage loginPage;
    private FilePage filePage;

    @BeforeAll
    static void beforeAll() throws InterruptedException {
        Locale.setDefault(new Locale("en","US"));
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void beforeEach() throws InterruptedException {
        BASEURL = TestConstant.LOCALHOST + port;
        driver = new ChromeDriver();
        driver.get(FileTests.BASEURL+
                TestConstant.SIGNUP_URL);

        signupPage = new SignupPage(driver);
        filePage = new FilePage(driver);
        resultPage = new ResultPage(driver);

        signupPage.signup(TestConstant.FIRST_NAME, TestConstant.LAST_NAME, TestConstant.USERNAME, TestConstant.PASSWORD);
        Thread.sleep(3000);

        driver.get(FileTests.BASEURL+
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
    static void aferAll(){

    }

    @Test
    @Order(1)
    public void testAddFile() throws InterruptedException {
        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "testingFile.txt";
        URL resource = classLoader.getResource(fileName);
        log.debug(TAG_ + "-> testing file: " + resource.getPath() );
        String path = resource.getPath();
        path = (path.charAt(0)=='/')?path.substring(1, path.length()):path;
        //LOGGER.debug(TAG_ + " testing file: " + path );
        log.debug(TAG_ + "-> testing file: " + path );
        filePage.addFile(driver, path);
        resultPage.clickOnSuccess(driver);
        Assertions.assertEquals(filePage.getFileName(driver), fileName);
    }

    @Test
    @Order(2)
    public void testDeleteFile() throws InterruptedException {
        //LOGGER.debug(TAG_ + " Delete file");
        log.debug(TAG_ + "-> Delete file");
        filePage.deleteFile(driver);
        resultPage.clickOnSuccess(driver);
        Assertions.assertThrows(TimeoutException.class, () -> {
            filePage.getFileName(driver);
        });
    }

}
