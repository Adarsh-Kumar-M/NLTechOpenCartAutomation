package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class ProductInfoTest extends BaseTest {

    @BeforeClass
    public void productInfoSetup() throws InterruptedException {
        accountPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
    }

    @DataProvider
    public Object[][] getProductsData()
    {
        Object obj[][]=new Object[3][2];
        obj[0][0]="macbook";
        obj[0][1]="MacBook Pro";

        obj[1][0]="samsung";
        obj[1][1]="Samsung Galaxy Tab 10.1";

        obj[2][0]="canon";
        obj[2][1]="Canon EOS 5D";
        return obj;
    }

    @Test(dataProvider="getProductsData")
    public void productHeaderTest(String searchKey,String productName ) throws InterruptedException {
        searchResultsPage=accountPage.doSearch(searchKey);
        productInfoPage=searchResultsPage.selectProduct(productName);
        String actualHeaderValue=productInfoPage.getProductHeader();
        Assert.assertEquals(actualHeaderValue,productName );
    }

    @DataProvider
    public Object[][] getProductDataForImages()
    {
        Object obj[][]=new Object[3][3];
        obj[0][0]="macbook";
        obj[0][1]="MacBook Pro";
        obj[0][2]=4;

        obj[1][0]="samsung";
        obj[1][1]="Samsung Galaxy Tab 10.1";
        obj[1][2]=7;

        obj[2][0]="canon";
        obj[2][1]="Canon EOS 5D";
        obj[2][2]=3;
        return obj;
    }

    @Test(dataProvider = "getProductDataForImages")
    public void productImagesCountTest(String searchKey,String productName, int count ) throws InterruptedException {
        searchResultsPage=accountPage.doSearch(searchKey);
        productInfoPage=searchResultsPage.selectProduct(productName);
        int actualCount=productInfoPage.getProductImagesCount();

        Assert.assertEquals(actualCount,count );
    }



    @DataProvider
    public Object[][] getProductInfoData()
    {
        Object obj[][]=new Object[1][5];
        obj[0][0]=AppConstants.PRODUCT_INFO_BRAND;
        obj[0][1]=AppConstants.PRODUCT_INFO_AVAILABILITY;
        obj[0][2]=AppConstants.PRODUCT_INFO_PRICE;
        obj[0][3]=AppConstants.PRODUCT_INFO_CODE;
        obj[0][4]=AppConstants.PRODUCT_INFO_REWARD_POINTS;
        return obj;
    }

    @Test(dataProvider = "getProductInfoData")
    public void productInfoTest(String expectedBrand, String expectedAvailability,
                                 String expectedPrice, String expectedCode, String expectedRewardPoints) throws InterruptedException
    {
        SoftAssert sf=new SoftAssert();
        searchResultsPage=accountPage.doSearch(AppConstants.SEARCH_PAGE_PRODUCT_SEARCH);
        productInfoPage=searchResultsPage.selectProduct(AppConstants.SEARCH_PAGE_SELECT_PRODUCT);
        Map<String, String> completeProductInfo = productInfoPage.getCompleteProductInfo();
        sf.assertEquals(completeProductInfo.get("Brand"), expectedBrand);
        sf.assertEquals(completeProductInfo.get("Availability"), expectedAvailability);
        sf.assertEquals(completeProductInfo.get("Product Price"), expectedPrice);
        sf.assertEquals(completeProductInfo.get("Product Code"), expectedCode);
        sf.assertEquals(completeProductInfo.get("Reward Points"), expectedRewardPoints);
        sf.assertAll();
    }
}
