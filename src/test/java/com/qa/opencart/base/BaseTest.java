package com.qa.opencart.base;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Properties;

//@Listeners({ChainTestListener.class, TestAllureListener.class})
public class BaseTest {

    public WebDriver driver;
    DriverFactory df;

    public Properties prop;
    public LoginPage loginPage;
    public AccountPage accountPage;
    public SearchResultsPage searchResultsPage;
    public ProductInfoPage productInfoPage;
    public RegisterPage registerPage;


    @Parameters({"browser"})
    @BeforeTest
    public void setUp(@Optional("chrome") String browserName) throws IOException
    {
        df=new DriverFactory();
        prop=df.initProp();

        if(browserName!=null)
        {
            prop.setProperty("browser", browserName);
        }
        driver=df.initDriver(prop);

        loginPage=new LoginPage(driver);

    }


    @AfterMethod
    public void attachScreenshot(ITestResult result)
    {
        if(!result.isSuccess())
        {
            ChainTestListener.embed(DriverFactory.getScreenshotAsFile(), "image/png");
        }
    }


    @AfterTest
    public void tearDown()
    {
        driver.quit();
    }

}
