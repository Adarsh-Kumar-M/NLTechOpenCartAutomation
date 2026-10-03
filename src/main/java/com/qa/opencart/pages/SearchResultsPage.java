package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage{

    private final By searchResults = By.cssSelector("div.product-thumb");
    private final By resulltsHeader = By.tagName("h1");

    private static final Logger log= LogManager.getLogger(SearchResultsPage.class);


    private WebDriver driver;
    private ElementUtils elUtil;

    public SearchResultsPage(WebDriver driver)
    {
        this.driver=driver;
        elUtil=new ElementUtils(driver);
    }

    public int getSearchResultCount()
    {
        int count=elUtil.waitforElementsPresence(resulltsHeader, AppConstants.DEFAULT_SHORT_TIME).size();
        log.info("Total Seacrh Results Count is : "+count);
        return count;
    }

    public ProductInfoPage selectProduct(String productName) throws InterruptedException
    {
        log.info("Clicked Product is : "+productName);
        elUtil.waitForElementVisibility(By.linkText(productName),AppConstants.DEFAULT_SHORT_TIME);
        elUtil.doActionsClick(By.linkText(productName));
        return new ProductInfoPage(driver);

    }


}
