package com.qa.opencart.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Properties;

public class OptionsManager {

    private Properties prop;
    ChromeOptions co;
    FirefoxOptions fo;
    EdgeOptions eo;

    private static final Logger log= LogManager.getLogger(OptionsManager.class);


    public OptionsManager(Properties prop)
    {
        this.prop=prop;
    }

    public ChromeOptions getChromeOptions()
    {
         co=new ChromeOptions();
         if(Boolean.parseBoolean(prop.getProperty("headless")))
        {
            log.info("Test cases are running on Headless Mode");
            co.addArguments("--headless");
        }

        if(Boolean.parseBoolean(prop.getProperty("incognito")))
        {
            log.info("Test cases are running on Incognito Mode");
            co.addArguments("--incognito");
        }
        return co;
    }

    public FirefoxOptions getFireFoxOptions()
    {
        fo=new FirefoxOptions();
        if(Boolean.parseBoolean(prop.getProperty("headless")))
        {
            log.info("Test cases are running on Headless Mode");
            fo.addArguments("--headless");
        }

        if(Boolean.parseBoolean(prop.getProperty("incognito")))
        {
            log.info("Test cases are running on Incognito Mode");
            fo.addArguments("--incognito");
        }
        return fo;
    }

    public EdgeOptions getEdgeOptions()
    {
        eo=new EdgeOptions();
        if(Boolean.parseBoolean(prop.getProperty("headless")))
        {
            log.info("Test cases are running on Headless Mode");
            eo.addArguments("--headless");
        }

        if(Boolean.parseBoolean(prop.getProperty("incognito")))
        {
            log.info("Test cases are running on Incognito Mode");
            eo.addArguments("--inprivate");
        }
        return eo;
    }
}
