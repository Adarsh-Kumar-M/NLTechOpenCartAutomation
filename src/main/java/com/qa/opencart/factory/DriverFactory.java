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
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {
    public WebDriver driver;
    public Properties prop;
    public FileInputStream fp;

    public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();
    private static final Logger log= LogManager.getLogger(DriverFactory.class);
    public  OptionsManager optionManager;

    public WebDriver initDriver(Properties prop)
    {
        optionManager=new OptionsManager(prop);
        String browserName=prop.getProperty("browser");
        log.info("Browser name is : "+ browserName);

        switch (browserName.trim().toLowerCase())
        {
            case "chrome":
//                driver=new ChromeDriver();
                tlDriver.set(new ChromeDriver(optionManager.getChromeOptions()));
                break;

            case "safari":
//                driver=new SafariDriver();
                tlDriver.set(new SafariDriver());
                break;

            case "edge":
//                driver=new EdgeDriver();
                tlDriver.set(new EdgeDriver(optionManager.getEdgeOptions()));
                break;

            case "firefox":
//                driver=new FirefoxDriver();
                tlDriver.set(new FirefoxDriver(optionManager.getFireFoxOptions() ));
                break;

            default:
                log.error(AppError.INVALID_BROWSER_MESSG);
                throw  new FrameworkException("===INVALID BROWSER===");
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(prop.getProperty("url"));
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
        return prop;

    }

    public static WebDriver getDriver()
    {
        return tlDriver.get();
    }

    public static File getScreenshotAsFile()
    {
        File file=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
        return file;
    }
}
