package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class SignupPage {

    public final static String TAG_ = "SignupPage";

    @FindBy(id = "inputFirstName")
    private WebElement firstNameInput;

    @FindBy(id = "inputLastName")
    private WebElement lastNameInput;

    @FindBy(id = "inputUsername")
    private WebElement usermameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id="signup-btn")
    private WebElement signupBtn;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String firstName,
                       String lastName,
                       String username,
                       String password){
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        passwordInput.sendKeys(password);
        usermameInput.sendKeys(username);
        signupBtn.click();
    }









}
