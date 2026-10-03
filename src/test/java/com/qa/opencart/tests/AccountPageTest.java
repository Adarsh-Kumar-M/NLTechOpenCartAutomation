package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class AccountPageTest extends BaseTest {

    @BeforeClass
    public void accSetUp() throws InterruptedException {
        accountPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
    }

    @Test
    public void accPageHeaderTest()
    {
        List<String> listHeader = accountPage.getAccHeaders();
        Assert.assertEquals(listHeader.size(),3);
    }

    @Test
    public void isLogoutLinkExist()
    {
        boolean flag=accountPage.isLogoutLinkExist();
        Assert.assertTrue(flag);
    }

    @Test
    public void searchProductTest(){
        accountPage.doSearch("imac");
    }
}
