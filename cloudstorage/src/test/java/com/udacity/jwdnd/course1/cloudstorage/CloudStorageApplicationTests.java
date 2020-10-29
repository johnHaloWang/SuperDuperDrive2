package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Locale;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	public final static String TAG_ = "CloudStorageApplicationTests";

	@LocalServerPort
	private int port=8080;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		Locale.setDefault(new Locale("en","US"));
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(2000);
		String result = driver.getTitle();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignPage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		Thread.sleep(2000);
		String result = driver.getTitle();
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getHomePage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(2000);
		String result = driver.getTitle();
		Assertions.assertEquals("Login", driver.getTitle());

	}

	@Test
	public void getResultPage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/result");
		Thread.sleep(2000);
		String result = driver.getTitle();
		Assertions.assertEquals("Login", driver.getTitle());

	}
}
