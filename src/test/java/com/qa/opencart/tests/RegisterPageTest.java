package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CSVUtils;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

public class RegisterPageTest extends BaseTest {

    @BeforeClass
    public void registerSetup() throws InterruptedException
    {
        registerPage=loginPage.navigateToRegisterPage();
    }


    @DataProvider
    public Object[][] getDataFromSheet()
    {
        Object obj[][]=ExcelUtil.getTestData("registration");
        return obj;
    }

    @DataProvider
    public Object[][] getCSVFileData()
    {
        Object obj[][]=CSVUtils.csvData("registration");
        return obj;
    }
    @Test (dataProvider = "getDataFromSheet")
    public void userRegisrationTestFromSheetData(String firName, String lasName, String tele, String pass, String subscribe)
    {
        boolean flag=registerPage.userRegistration(firName, lasName, StringUtils.generateRandomEmailId(),tele, pass, subscribe);
        Assert.assertTrue(flag);
    }

    @Test (dataProvider = "getCSVFileData")
    public void userRegisrationTest(String firName, String lasName, String tele, String pass, String subscribe)
    {
        boolean flag=registerPage.userRegistration(firName, lasName, StringUtils.generateRandomEmailId(),tele, pass, subscribe);
        Assert.assertTrue(flag);
    }

}
