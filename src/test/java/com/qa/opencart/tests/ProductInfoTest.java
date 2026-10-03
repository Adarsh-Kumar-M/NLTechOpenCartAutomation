package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
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



    @Test
    public void productInfoTest() throws InterruptedException
    {
        SoftAssert sf=new SoftAssert();
        searchResultsPage=accountPage.doSearch("Macbook");
        productInfoPage=searchResultsPage.selectProduct("MacBook Pro");
        Map<String, String> completeProductInfo = productInfoPage.getCompleteProductInfo();
        sf.assertEquals(completeProductInfo.get("Brand"), "Apple");
        sf.assertEquals(completeProductInfo.get("Availability"), "In Stock");
        sf.assertEquals(completeProductInfo.get("Product Price"), "$2,000.00");
        sf.assertEquals(completeProductInfo.get("Product Code"), "Product 18");
        sf.assertEquals(completeProductInfo.get("Reward Points"), "800");
        sf.assertAll();
    }
}
