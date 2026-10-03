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
        elUtil.waitForElementPresence(firstName, AppConstants.DEFAULT_SHORT_TIME).sendKeys(fName);

        elUtil.doSendKeys(lastName, lName);
        elUtil.doSendKeys(email, emailId);
        elUtil.doSendKeys(telephone, phone);
        elUtil.doSendKeys(password, pass);
        elUtil.doSendKeys(confirmpassword, pass);

        if(subscribe.equalsIgnoreCase("yes"))
        {
            elUtil.doActionsClick(subscribeYes);
        }
        else
        {
            elUtil.doActionsClick(subscribeNo);
        }

        elUtil.doActionsClick(agreeCheckBox);
        elUtil.doActionsClick(continueButton);


        String expMessage=elUtil.waitForElementVisibility(successMessg, AppConstants.DEFAULT_SHORT_TIME).getText();

        log.info("Success Registration Message is: "+expMessage);

        elUtil.doActionsClick(logoutLink);
        elUtil.doActionsClick(registerLink);

        if(expMessage.equals(AppConstants.USER_REGISTRATION_SUCCESS_MESSAGE))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}