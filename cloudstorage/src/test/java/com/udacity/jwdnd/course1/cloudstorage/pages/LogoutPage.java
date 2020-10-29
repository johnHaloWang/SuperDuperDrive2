package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Slf4j
public class LogoutPage {
    @FindBy(id="inputUsername")
    private WebElement usernameInput;

    @FindBy(id="inputPassword")
    private WebElement passwordInput;

    @FindBy(id="submit-btn")
    private WebElement loginBtn;

    @FindBy(id="logout-msg")
    private WebElement logoutMsgDiv;

    public LogoutPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String loginOut(){
        String returnMsg = "";
        if(!logoutMsgDiv.getText().equals("")){
            returnMsg = logoutMsgDiv.getText();
        }
        return returnMsg;
    }
}
