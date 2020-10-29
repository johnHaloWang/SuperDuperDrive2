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
public class NotePage {

    public final static String TAG_ = "NotePage";

    //nav-btn
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

    @FindBy(id="note-display-title")
    private WebElement displayTitle;

    @FindBy(id="note-display-description")
    private WebElement displayDescription;

    @FindBy(id="logout-btn")
    private WebElement logOutBtn;

    @FindBy(id="show-note-btn")
    private WebElement addBtn;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout(){
        logOutBtn.click();
    }

    public void addNote(WebDriver driver, String title, String description) throws InterruptedException {
        this.clickNoteTab(driver);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        try{
            wait.until(ExpectedConditions.visibilityOf(addBtn)).click();
            addBtn.click();
        }catch(ElementClickInterceptedException e){
            log.debug(NotePage.TAG_ + "-> Error occur on add note, the 'show-note-btn' was not visible");
        }
        WebElement noteTitleInput = wait.until(webDriver->webDriver.findElement(By.id("note-title")));
        WebElement noteDescriptionInput = wait.until(webDriver->webDriver.findElement(By.id("note-description")));
        WebElement saveBtn = wait.until(webDriver->webDriver.findElement(By.id("note-save-btn")));
        noteTitleInput.sendKeys(title);
        noteDescriptionInput.sendKeys(description);
        saveBtn.click();
    }

    public void deleteNote(WebDriver driver){
        this.clickNoteTab(driver);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement deleteBtn = wait.until(webDriver->webDriver.findElement(By.id("note-delete-btn")));

        try {
            wait.until(ExpectedConditions.visibilityOf(deleteBtn)).click();
            deleteBtn.click();
        } catch (Exception e) {
            log.debug(NotePage.TAG_ + "-> Error occur on add note, the 'delete-note-btn' was not visible");
        }
   }

   public void editNote(WebDriver driver, String title, String description){
       this.clickNoteTab(driver);
       WebDriverWait wait = new WebDriverWait(driver, 30);
       WebElement editBtn = wait.until(webDriver->webDriver.findElement(By.id("note-edit-btn")));
       try{
           wait.until(ExpectedConditions.visibilityOf(editBtn)).click();
           editBtn.click();
       }catch(Exception e){
           log.debug(NotePage.TAG_ + "-> Error occur on edit note, the 'note-edit-btn' was not visible");
       }
       WebElement noteTitleInput = wait.until(webDriver->webDriver.findElement(By.id("note-title")));
       WebElement noteDescriptionInput = wait.until(webDriver->webDriver.findElement(By.id("note-description")));
       WebElement saveBtn = wait.until(webDriver->webDriver.findElement(By.id("note-save-btn")));
       noteTitleInput.clear();
       noteTitleInput.sendKeys(title);
       noteDescriptionInput.clear();
       noteDescriptionInput.sendKeys(description);
       saveBtn.click();
   }

    public String getDisplayTitle(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String rst = null;
        try{
            wait.until(ExpectedConditions.visibilityOf(this.displayTitle));
            rst = this.displayTitle.getText();
        }catch(Exception e){
            log.debug(NotePage.TAG_ +  "-> Error occur on get note title, the 'display-title' was not visible");
        }
        log.debug(NotePage.TAG_ + "-> find title: " + rst);
        return rst;
    }

    public String getDisplayDes(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String rst = null;
        try{
            wait.until(ExpectedConditions.visibilityOf(this.displayDescription));
            rst = this.displayDescription.getText();
        }catch(Exception e){
            log.debug(NotePage.TAG_ + "-> Error occur on get note title, the 'display-descritpion' was not visible");
        }
        log.debug(NotePage.TAG_ + "-> find description: " + rst);
        return rst;
    }

}
