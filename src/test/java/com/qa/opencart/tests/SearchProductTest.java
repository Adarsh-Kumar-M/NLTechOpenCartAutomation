package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
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
        searchResultsPage=accountPage.doSearch(AppConstants.SEARCH_PAGE_PRODUCT_SEARCH);
        productInfoPage=searchResultsPage.selectProduct(AppConstants.SEARCH_PAGE_SELECT_PRODUCT);
        String actualHeaderValue=productInfoPage.getProductHeader();
        Assert.assertEquals(actualHeaderValue, AppConstants.SEARCH_PAGE_PRODUCT);
    }


}
