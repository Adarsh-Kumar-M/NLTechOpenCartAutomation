package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoPage {

    private final By header = By.tagName("h1");
    private final By images = By.xpath("//div[@class='image magnific-popup']//img");
    private final By productMetaData = By.xpath("(//div[@class='col-sm']//ul[@class='list-unstyled'])[1]//li");
    private final By productPrice = By.xpath("(//div[@class='col-sm']//ul[@class='list-unstyled'])[2]//span");

    private static final Logger log= LogManager.getLogger(ProductInfoPage.class);


    public Map<String,String> productMap;

    private WebDriver driver;
    private ElementUtils elUtil;

    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
        elUtil = new ElementUtils(driver);
    }

    public String getProductHeader() {
        String headerText = elUtil.waitForElementVisibility(header, AppConstants.DEFAULT_SHORT_TIME).getText();
        log.info("Product header text is : " + headerText);
        return headerText;
    }

    public int getProductImagesCount() {
        int imagesCount = elUtil.waitforElementsVisibility(images, AppConstants.DEFAULT_SHORT_TIME).size();
        log.info("Total Size of the image is : " + imagesCount);
        return imagesCount;
    }

    private void getProductMetaData()
    {
        List<WebElement> metaList = elUtil.waitforElementsVisibility(productMetaData, AppConstants.DEFAULT_SHORT_TIME);
        log.info("Total size of the meta data is : "+metaList.size());

        for(WebElement e:metaList)
        {
            String text=e.getText();
            String meta[]=text.split(":");
            String key=meta[0];
            String value=meta[1].trim();
            productMap.put(key,value);
        }
    }

    private void getProductPriceData()
    {
        WebElement price=elUtil.waitForElementVisibility(productPrice, AppConstants.DEFAULT_SHORT_TIME);
        String priceData=price.getText();
        productMap.put("Product Price", priceData);
    }

    public Map<String, String> getCompleteProductInfo()
    {
        productMap=new HashMap<String, String>();
        getProductMetaData();
        getProductPriceData();
        log.info("Entire product info : "+ productMap);
        return productMap;
    }

}
