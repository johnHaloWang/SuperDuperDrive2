package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
public class FilePage {

    public final static String TAG_ = "FilePage";
    //LOGGER.debug(FilePage.TAG_ + " find description: " + rst);

    //nav-btn
    @FindBy(id="logout-btn")
    private WebElement logOutBtn;

    public void logout(){
        logOutBtn.click();
    }

    @FindBy(id="nav-files-tab")
    private WebElement fileTabLink;

    public void clickFileTab(WebDriver driver){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", fileTabLink);
    }
    @FindBy(id="nav-notes-tab")
    private WebElement noteTabLink;

    public void clickNoteTab(WebDriver driver){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", noteTabLink);
    }

    @FindBy(id="nav-credentials-tab")
    private WebElement credentialsTabLink;

    public void clickCredentialTab(WebDriver driver){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", credentialsTabLink);
    }


    public FilePage(WebDriver driver) { PageFactory.initElements(driver, this); }

    @FindBy(id="fileUpload")
    private WebElement fileUpload;

    @FindBy(id="file-add-btn")
    private WebElement addBtn;

    public void addFile(WebDriver driver, String filePath){
        //LOGGER.debug(TAG_ + " testing file: " + filePath );
        log.debug(TAG_ + "-> testing file: " + filePath );
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(fileUpload)).sendKeys(filePath);
        wait.until(ExpectedConditions.visibilityOf(addBtn)).click();
    }

    @FindBy(id="file-delete-btn")
    private WebElement deleteBtn;

    public void deleteFile(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(deleteBtn)).click();
    }

    @FindBy(id="file-name")
    private WebElement fileName;

    public String getFileName(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(fileName));
        return fileName.getText();

    }


}
