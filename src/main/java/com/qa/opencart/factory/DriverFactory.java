package com.qa.opencart.factory;

import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.utils.AppError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {
    public WebDriver driver;
    public Properties prop;
    public FileInputStream fp;

    public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();
    private static final Logger log= LogManager.getLogger(DriverFactory.class);
    public  OptionsManager optionManager;

    public WebDriver initDriver(Properties prop) throws MalformedURLException {
        optionManager=new OptionsManager(prop);
        String browserName=prop.getProperty("browser");
        Boolean remoteFlag=Boolean.parseBoolean(prop.getProperty("remote"));
        log.info("Browser name is : "+ browserName);

        switch (browserName.trim().toLowerCase())
        {
            case "chrome":
//                driver=new ChromeDriver();
                if(remoteFlag)
                {
                    init_RemoteDriver(browserName);
                }
                else {
                    log.info("Initializing Chrome driver");
                    tlDriver.set(new ChromeDriver(optionManager.getChromeOptions()));
                }
                break;

            case "firefox ":
//                driver=new SafariDriver();
                if(remoteFlag){
                    init_RemoteDriver(browserName);
                }
                else {
                    log.info("Initializing Safari driver");
                    tlDriver.set(new SafariDriver());
                }
                break;

            case "edge":
//                driver=new EdgeDriver();
                if(remoteFlag){
                    init_RemoteDriver(browserName);
                }
                else {
                    log.info("Initializing Edge driver");
                    tlDriver.set(new EdgeDriver(optionManager.getEdgeOptions()));
                }
                break;

            case "safari":
//                driver=new FirefoxDriver();
                log.info("Initializing Firefox driver");
                tlDriver.set(new FirefoxDriver(optionManager.getFireFoxOptions() ));
                break;

            default:
                log.error(AppError.INVALID_BROWSER_MESSG);
                throw  new FrameworkException("===INVALID BROWSER===");
        }

        getDriver().manage().deleteAllCookies();
        log.info("All cookies deleted");
        getDriver().manage().window().maximize();
        log.info("Browser window maximized");
        getDriver().get(prop.getProperty("url"));
        log.info("Navigated to URL: "+prop.getProperty("url"));
        return getDriver();
    }

    public Properties initProp() throws IOException {
        prop=new Properties();
        String envName=System.getProperty("env");
        if(envName==null) {
            log.info("Test cases running on default environment since no env is selected...."+ envName);
            fp = new FileInputStream("src/test/resources/config/config.properties");
        }
        else {
            switch (envName.trim().toLowerCase()){
                case "uat":
                    log.info("Test cases running on "+envName+" environment");
                    fp=new FileInputStream("src/test/resources/config/config_uat.properties");
                    break;

                case "dev":
                    log.info("Test cases running on "+envName+" environment");
                    fp=new FileInputStream("src/test/resources/config/dev.properties");
                    break;

                case "stage":
                    log.info("Test cases running on "+envName+" environment");
                    fp=new FileInputStream("src/test/resources/config/stage.properties");
                    break;

                default:
                    log.info("Environment not supported "+ envName);
                    throw new FrameworkException("===INVALID ENVIRONMENT===");
            }
        }
        prop.load(fp);
        log.info("Properties file loaded successfully");
        return prop;

    }

    public static WebDriver getDriver()
    {
        return tlDriver.get();
    }

    public static File getScreenshotAsFile()
    {
        log.info("Capturing screenshot");
        File file=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
        log.info("Screenshot captured successfully");
        return file;
    }


    private void init_RemoteDriver(String browser) throws MalformedURLException {
        switch (browser.trim().toLowerCase())
        {
            case "chrome":
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionManager.getChromeOptions()));
                break;

            case "firefox":
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionManager.getFireFoxOptions()));
                break;

            case "edge":
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionManager.getEdgeOptions()));
                break;

            default:
                log.error(AppError.INVALID_BROWSER_MESSG);
                throw  new FrameworkException("===INVALID BROWSER===");

        }

    }



}
