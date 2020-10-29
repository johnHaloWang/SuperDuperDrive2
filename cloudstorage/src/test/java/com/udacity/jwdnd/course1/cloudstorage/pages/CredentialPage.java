package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class CredentialPage {
    public final static String TAG_ = "CredentialPage";

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

    @FindBy(id="nav-credentials-tab")
    private WebElement credentialsTabLink;

    public void clickCredentialTab(WebDriver driver){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", credentialsTabLink);
    }

    @FindBy(id="credential-display-url")
    private WebElement displayUrl;

    @FindBy(id="credential-display-username")
    private WebElement displayUsername;

    @FindBy(id="credential-display-password")
    private WebElement displayPassword;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="show-credential-btn")
    private WebElement addBtn;


    public void clickNoteTab(WebDriver driver){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", noteTabLink);
    }

    public String getDisplayUsername(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String rst = null;
        try{
            wait.until(ExpectedConditions.visibilityOf(this.displayUsername));
            rst = this.displayUsername.getText();
        }catch(Exception e){
            log.debug(TAG_ +  "-> Error occur on get credential username, the 'display-password' was not visible");

        }
        log.debug(TAG_ + "-> find username: " + rst);
        return rst;
    }

    public String getDisplayUrl(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String rst = null;
        try{
            wait.until(ExpectedConditions.visibilityOf(this.displayUrl));
            rst = this.displayUrl.getText();
        }catch(Exception e){
            //LOGGER.debug(CredentialPage.TAG_ +  "Error occur on get credential url, the 'display-url' was not visible");
            log.debug(TAG_ +  "-> Error occur on get credential url, the 'display-url' was not visible");
        }
        log.debug(TAG_ + "-> find url: " + rst);
        return rst;
    }

    public String getDisplayPassword(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String rst = null;
        try{
            wait.until(ExpectedConditions.visibilityOf(this.displayPassword));
            rst = this.displayPassword.getText();
        }catch(Exception e){
            log.debug(TAG_ + "-> Error occur on get credential password, the 'display-password' was not visible");
        }
        //LOGGER.debug(CredentialPage.TAG_ + " find password: " + rst);
        log.debug(TAG_ + "-> find password: " + rst);
        return rst;
    }

    public void addCredential(WebDriver driver, String username, String password, String url) throws InterruptedException {
        this.clickCredentialTab(driver);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        try{
            wait.until(ExpectedConditions.visibilityOf(addBtn)).click();
            addBtn.click();
        }catch(ElementClickInterceptedException e){
            log.debug(TAG_ + "-> Error occur on add credential, the 'show-credential-btn' was not visible");
        }
        WebElement credentialUsernameInput = wait.until(webDriver->webDriver.findElement(By.id("credential-username")));
        WebElement credentialPasswordInput = wait.until(webDriver->webDriver.findElement(By.id("credential-password")));
        WebElement credentialUrlInput = wait.until(webDriver->webDriver.findElement(By.id("credential-url")));
        WebElement saveBtn = wait.until(webDriver->webDriver.findElement(By.id("credential-save-btn")));
        credentialUsernameInput.sendKeys(username);
        credentialPasswordInput.sendKeys(password);
        credentialUrlInput.sendKeys(url);
        saveBtn.click();
    }

    public void deleteCredential(WebDriver driver){
        this.clickCredentialTab(driver);
        WebDriverWait wait = new WebDriverWait(driver, 40);
        WebElement deleteBtn = wait.until(webDriver->webDriver.findElement(By.id("credential-delete-btn")));

        try {
            wait.until(ExpectedConditions.visibilityOf(deleteBtn)).click();
            deleteBtn.click();
        } catch (Exception e) {
            log.debug(TAG_ + "-> Error occur on delete credential, the 'delete-credential-btn' was not visible");
        }
    }

    public void editCredential(WebDriver driver, String username, String password, String url){
        this.clickCredentialTab(driver);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement editBtn = wait.until(webDriver->webDriver.findElement(By.id("credential-edit-btn")));

        try{
            wait.until(ExpectedConditions.visibilityOf(editBtn)).click();
            editBtn.click();
        }catch(ElementClickInterceptedException e){
            //LOGGER.debug(CredentialPage.TAG_ + " Error occur on edit credential, the 'credential-edit-btn' was not visible");
            log.debug(TAG_ + "-> Error occur on edit credential, the 'credential-edit-btn' was not visible");
        }
        WebElement credentialUsernameInput = wait.until(webDriver->webDriver.findElement(By.id("credential-username")));
        WebElement credentialPasswordInput = wait.until(webDriver->webDriver.findElement(By.id("credential-password")));
        WebElement credentialUrlInput = wait.until(webDriver->webDriver.findElement(By.id("credential-url")));
        WebElement saveBtn = wait.until(webDriver->webDriver.findElement(By.id("credential-save-btn")));
        credentialUsernameInput.clear();
        credentialUsernameInput.sendKeys(username);
        credentialPasswordInput.clear();
        credentialPasswordInput.sendKeys(password);
        credentialUrlInput.clear();
        credentialUrlInput.sendKeys(url);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", saveBtn);

    }

    public void closeCredential(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement closeBtn = wait.until(webDriver->webDriver.findElement(By.id("credential-close-btn")));
        try{
            wait.until(ExpectedConditions.visibilityOf(closeBtn)).click();
            closeBtn.click();
        }catch(ElementClickInterceptedException e){
            log.debug(TAG_ + "-> Error occur on edit credential, the 'credential-close-btn' was not visible");
        }
    }

    public String clickEditBtn(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement editBtn = wait.until(webDriver->webDriver.findElement(By.id("credential-edit-btn")));
        try{
            wait.until(ExpectedConditions.visibilityOf(editBtn)).click();
            editBtn.click();
        }catch(Exception e){
            log.debug(CredentialPage.TAG_ + "-> Error occur on edit credential, the 'credential-edit-btn' was not visible");
        }
        WebElement credentialPasswordInput = wait.until(webDriver->webDriver.findElement(By.id("credential-password")));
        WebElement closeBtn = wait.until(webDriver->webDriver.findElement(By.id("credential-close-btn")));
        closeBtn.click();
        return credentialPasswordInput.getAttribute("value");
    }

}
