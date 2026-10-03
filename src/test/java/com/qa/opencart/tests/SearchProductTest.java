package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchProductTest extends BaseTest {

    @BeforeClass
    public void searchProductSetup() throws InterruptedException {
        accountPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
    }

    @Test
    public void searchProductTest() throws InterruptedException {
        searchResultsPage=accountPage.doSearch("macbook");
        productInfoPage=searchResultsPage.selectProduct("MacBook Pro");
        String actualHeaderValue=productInfoPage.getProductHeader();
        Assert.assertEquals(actualHeaderValue, "MacBook Pro");
    }


}
