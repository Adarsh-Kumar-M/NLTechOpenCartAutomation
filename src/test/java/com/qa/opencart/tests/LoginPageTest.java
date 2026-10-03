package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginPageTitleTest()
    {
        String title=loginPage.getLoginPageTitle();
        Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
    }

    @Test
    public void loginPageUrlTest()
    {
        String url=loginPage.getLoginPageUrl();
        Assert.assertTrue(url.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
    }

    @Test
    public void isForgotPwdLinkExistTest()
    {
        Boolean flag=loginPage.IsForgotPwdLinkExist();
        Assert.assertTrue(flag);
    }

    @Test
    public void isHeaderExistTest()
    {
        Boolean flag=loginPage.isHeaderExist();
        Assert.assertTrue(flag);
    }

    @Test
    public void loginTest() throws InterruptedException {
       accountPage= loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
       Assert.assertTrue(accountPage.isLogoutLinkExist());

    }
}
