package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AccountPage {
    private final By headers =By.tagName("h2"); // mutiple headers
    private final By logoutLink=By.linkText("Logout"); // Logout Link
    private final By searchIcon=By.xpath("//button[@type='submit']");
    private final By searchBar=By.name("search");

    private static final Logger log= LogManager.getLogger(AccountPage.class);


    private WebDriver driver;
    private ElementUtils elUtil;

    public AccountPage(WebDriver driver){
        this.driver=driver;
        elUtil=new ElementUtils(driver);
    }

    public List<String> getAccHeaders()
    {
        List<WebElement> headerElements = elUtil.getElements(headers);
        List<String> headerList=new ArrayList<>();

        for(WebElement e:headerElements){
            String text=e.getText();
            headerList.add(text);
        }
        return headerList;
    }

    public boolean isLogoutLinkExist()
    {
        boolean flag=elUtil.isElementDisplayed(logoutLink);
        return flag;
    }

    public SearchResultsPage doSearch(String searchValue)
    {
        WebElement el = elUtil.waitForElementVisibility(searchBar, AppConstants.DEFAULT_SHORT_TIME);
        el.clear();
        elUtil.doSendKeys(searchBar,searchValue );
        elUtil.doActionsClick(searchIcon);
        return new SearchResultsPage(driver);
    }
}
