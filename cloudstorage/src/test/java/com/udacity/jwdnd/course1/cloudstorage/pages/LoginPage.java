package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class LoginPage {

    @FindBy(id="inputUsername")
    private WebElement usernameInput;

    @FindBy(id="inputPassword")
    private WebElement passwordInput;

    @FindBy(id="submit-btn")
    private WebElement loginBtn;

    final private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public final static String TAG_ = "LoginPage";

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(WebDriver driver, String username, String password){
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginBtn.click();
    }
}
