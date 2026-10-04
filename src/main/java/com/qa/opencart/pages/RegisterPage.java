package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class RegisterPage {

    private final By firstName = By.id("input-firstname");
    private final By lastName = By.id("input-lastname");
    private final By email = By.id("input-email");
    private final By telephone = By.id("input-telephone");
    private final By password = By.id("input-password");
    private final By confirmpassword = By.id("input-confirm");
    private final By subscribeYes = By.xpath("(//input[@type='radio'])[1]");
    private final By subscribeNo = By.xpath("(//input[@type='radio'])[2]");
    private final By agreeCheckBox = By.name("agree");
    private final By continueButton = By.xpath("//button[@type='submit' and text()='Continue']");
    private final By successMessg = By.xpath("//h1[text()='Your Account Has Been Created!']");
    private final By logoutLink = By.linkText("Logout");
    private final By registerLink = By.linkText("Register");

    private static final Logger log= LogManager.getLogger(RegisterPage.class);



    private WebDriver driver;
    private ElementUtils elUtil;

    public RegisterPage(WebDriver driver)
    {
        this.driver=driver;
        elUtil=new ElementUtils(driver);
    }

    public boolean userRegistration(String fName, String lName, String emailId, String phone, String pass, String subscribe)
    {
        log.info("Starting user registration with email: "+emailId);
        elUtil.waitForElementPresence(firstName, AppConstants.DEFAULT_SHORT_TIME).sendKeys(fName);
        log.info("First name entered: "+fName);

        elUtil.doSendKeys(lastName, lName);
        log.info("Last name entered: "+lName);
        elUtil.doSendKeys(email, emailId);
        log.info("Email entered: "+emailId);
        elUtil.doSendKeys(telephone, phone);
        log.info("Telephone entered: "+phone);
        elUtil.doSendKeys(password, pass);
        log.info("Password entered");
        elUtil.doSendKeys(confirmpassword, pass);
        log.info("Confirm password entered");

        if(subscribe.equalsIgnoreCase("yes"))
        {
            log.info("Newsletter subscription: Yes");
            elUtil.doActionsClick(subscribeYes);
        }
        else
        {
            log.info("Newsletter subscription: No");
            elUtil.doActionsClick(subscribeNo);
        }

        elUtil.doActionsClick(agreeCheckBox);
        log.info("Agreement checkbox clicked");
        elUtil.doActionsClick(continueButton);
        log.info("Continue button clicked");


        String expMessage=elUtil.waitForElementVisibility(successMessg, AppConstants.DEFAULT_SHORT_TIME).getText();

        log.info("Success Registration Message is: "+expMessage);

        elUtil.doActionsClick(logoutLink);
        log.info("Logged out after registration");
        elUtil.doActionsClick(registerLink);
        log.info("Navigated back to Register page");

        if(expMessage.equals(AppConstants.USER_REGISTRATION_SUCCESS_MESSAGE))
        {
            log.info("User registration successful");
            return true;
        }
        else
        {
            log.error("User registration failed. Expected message: "+AppConstants.USER_REGISTRATION_SUCCESS_MESSAGE+", Actual: "+expMessage);
            return false;
        }
    }

}