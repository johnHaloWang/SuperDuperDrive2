package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Slf4j
public class LoginErrorPage {

    @FindBy(id="inputUsername")
    private WebElement usernameInput;

    @FindBy(id="inputPassword")
    private WebElement passwordInput;

    @FindBy(id="submit-btn")
    private WebElement loginBtn;

    @FindBy(id="error-msg")
    private WebElement errorMsgDiv;

    public LoginErrorPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String loginFailed(String username, String password){
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginBtn.click();
        String returnMsg = "";
        if(!errorMsgDiv.getText().equals("")){
            returnMsg = errorMsgDiv.getText();
        }
        return returnMsg;
    }
}
