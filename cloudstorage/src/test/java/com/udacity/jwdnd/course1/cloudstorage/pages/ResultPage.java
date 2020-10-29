package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class ResultPage {
    //private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public final static String TAG_ = "ResultPage";
    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="back-home")
    private WebElement successBtn;

    public void clickOnSuccess(WebDriver driver){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", successBtn);
    }
}
