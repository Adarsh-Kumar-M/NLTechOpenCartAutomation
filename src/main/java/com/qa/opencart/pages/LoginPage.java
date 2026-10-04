package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class LoginPage {
    //    private By locators
    //    public constructors
    //    public page class action methods

    private final By email = By.id("input-email");
    private final By password =By.id("input-password");
    private final By loginBtn= By.xpath("(//button[@type='submit'])[2]");
    private final By forgotPwdLink=By.xpath("(//a[text()='Forgotten Password'])[1]");
    private final By header=By.tagName("h2");
    private final By register=By.xpath("(//a[text()='Register'])[2]");

    private static final Logger log= LogManager.getLogger(LoginPage.class);


    private WebDriver driver;
    private ElementUtils elUtil;

    public LoginPage(WebDriver driver){
        this.driver=driver;
        elUtil=new ElementUtils(driver);
    }

    public String getLoginPageTitle(){
        String title=elUtil.waitforexactTitle("Account Login", AppConstants.DEFAULT_SHORT_TIME);
        log.info("Login Page Title is : "+title);
        return title;
    }

    public String getLoginPageUrl(){
        String url=elUtil.waitforUrlContains("account/login",  AppConstants.DEFAULT_SHORT_TIME);
        log.info("Login Page Url is : "+ url);
        return url;
    }

    public boolean IsForgotPwdLinkExist(){
       log.info("Checking if Forgot Password link exists");
       boolean flag= elUtil.isElementDisplayed(forgotPwdLink);
       log.info("Forgot Password link exists: "+flag);
       return flag;
    }

    public boolean isHeaderExist(){
        log.info("Checking if Login page header exists");
        boolean flag=elUtil.isElementDisplayed(header);
        log.info("Login page header exists: "+flag);
        return flag;
    }

    public AccountPage doLogin(String username, String pass) throws InterruptedException {
        log.info("Login credentials are : "+ username+" : "+ pass);
        elUtil.waitForElementVisibility(email,AppConstants.DEFAULT_SHORT_TIME);
        elUtil.doSendKeys(email,username);
        elUtil.doSendKeys(password, pass);
        elUtil.doActionsClick(loginBtn);
        elUtil.waitforexactTitle("My Account", AppConstants.DEFAULT_SHORT_TIME );
        return new AccountPage(driver);
    }

    public RegisterPage navigateToRegisterPage()
    {
        log.info("Navigating to Register Page");
        elUtil.waitForElementPresence(register, AppConstants.DEFAULT_SHORT_TIME).click();
        return new RegisterPage(driver);
    }
}
