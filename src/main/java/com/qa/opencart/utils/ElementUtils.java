package com.qa.opencart.utils;

import com.qa.opencart.exceptions.ElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ElementUtils {


    private WebDriver driver;
    private Actions act;

    public ElementUtils(WebDriver driver)
    {
        this.driver=driver;
        act=new Actions(driver);
    }

    public WebElement getElement(By locator)
    {
        WebElement el=driver.findElement(locator);
        return el;
    }

    public List<WebElement> getElements(By locator)
    {
        List<WebElement> elList = driver.findElements(locator);
        return elList;
    }

    public void doSendKeys(By locator, String value)
    {
        if(value==null)
        {
            throw new ElementException("===Invalid String value. Null Not Allowed.===");
        }
        else
        {
            getElement(locator).sendKeys(value);
        }
    }

    public void doMultipleSendKeys(By locator, CharSequence...value)
    {
        getElement(locator).sendKeys(value);
    }

    public void doClick(By locator)
    {
        getElement(locator).click();

    }

    public String doElementGetText(By locator)
    {

        String text=getElement(locator).getText();
        return text;

    }

    public boolean isElementDisplayed(By locator)
    {
        try
        {
            getElement(locator).isDisplayed();
            return true;
        }
        catch (NoSuchElementException e) {

            System.out.println("Loctaors are not correct");
            e.printStackTrace();
            return false;

        }

    }

    public boolean isElementEnabled(By locator)
    {
        try
        {
            getElement(locator).isEnabled();
            return true;
        }
        catch (NoSuchElementException e) {

            System.out.println("Element is not interactable or enabled on the Page");
            e.printStackTrace();
            return false;

        }

    }

    public String getElementDomPropertyValue(By locator, String attrName)
    {

        String attrValue=getElement(locator).getDomProperty(attrName);

        return attrValue;
    }
    public String getElementDomAttributeValue(By locator, String attrName)
    {

        String attrValue=getElement(locator).getDomAttribute(attrName);

        return attrValue;
    }

    public int getTotalElementsCount(By locator)
    {
        int count=getElements(locator).size();
        return count;

    }

    public List<String> getElementsTextList(By locator)
    {

        List<WebElement> elList=getElements(locator);

        List<String> textList=new ArrayList<String>();

        for(WebElement e : elList)
        {
            String text=e.getText();
            if(text.length()!=0)
            {
                textList.add(text);

            }
        }

        return textList;

    }

    public boolean isElementExist(By locator)
    {

        int count=getTotalElementsCount(locator);

        if(count==1)
        {
            System.out.println("Element is available on the page 1 time");
            return true;
        }
        else
        {
            System.out.println("Element is not available on the webpage");
            return false;

        }

    }

    public boolean isElementExist(By locator, int expectedCount)
    {

        int count=getTotalElementsCount(locator);

        if(count==expectedCount)
        {
            System.out.println("Element is available on the page " +expectedCount + " times");
            return true;
        }
        else
        {
            System.out.println("Element is not available on the webpage");
            return false;

        }

    }

    public void clickSingleElementFromList(By locator, String value)
    {
        List<WebElement> elList=getElements(locator);
        for(WebElement e: elList)
        {
            String text=e.getText();
            System.out.println(text);
            if(text.equals(value))
            {
                e.click();
                break;
            }

        }

    }

// Google Auto SUggestion Click Common Method :

    public void doSearchAutoClickSuggestion(By searchLocator, String searchValue, By suggestionLocator,
                                            String suggestedValue) throws InterruptedException {

        Thread.sleep(2000);

        doSendKeys(searchLocator, searchValue);

        Thread.sleep(3000);

        List<WebElement>suggestionList=getElements(suggestionLocator);

        System.out.println("Total size of the suggestion is : " + suggestionList.size());

        for (WebElement e : suggestionList) {
            String text = e.getText();
            System.out.println(text);

            if (text.equals(suggestedValue)) {
                e.click();
                break;
            }

        }

    }
// **********************Select Class Methods ************************************


    public void doSelectByIndex(By locator, int index)
    {
        Select sec=new Select(getElement(locator));
        sec.selectByIndex(index);

    }

    public void doSelectByValue(By locator, String value)
    {
        Select sec=new Select(getElement(locator));
        sec.selectByValue(value);

    }

    public void doSelectByVisibleText(By locator,  String text)
    {
        Select sec=new Select(getElement(locator));
        sec.selectByVisibleText(text);

    }

    public void doDeSelectByIndex(By locator, int index)
    {
        Select sec=new Select(getElement(locator));
        sec.deselectByIndex(index);

    }

    public void doDeSelectByValue(By locator, String value)
    {
        Select sec=new Select(getElement(locator));
        sec.deselectByValue(value);

    }

    public void doDeSelectByVisibleText(By locator,  String text)
    {
        Select sec=new Select(getElement(locator));
        sec.deselectByVisibleText(text);

    }

    public List<String> getDropdownValuesList(By locator)
    {
        Select sec=new Select(getElement(locator));

        List<WebElement> optionsList = sec.getOptions();

        List<String> textList=new ArrayList<String>();

        System.out.println("Total country are : "+(optionsList.size()));

        for(WebElement e:optionsList)
        {
            String text=e.getText();

            textList.add(text);

        }

        return textList;

    }

    public int getDropdownOptionsCount(By locator)
    {
        Select sec=new Select(getElement(locator));
        List<WebElement> optionsList = sec.getOptions();

        int count=optionsList.size();
        return count;

    }


// ************************Actions class Methods ****************************

    public void menuSubMenuHandling(By parentMenuLocator, By childMenuLocator)
    {
        act.moveToElement(getElement(parentMenuLocator)).perform();
        act.click(getElement(childMenuLocator)).perform();

    }

    public void menuSubMenuHandlingLevel4(By parentMenuLocator, By subMenu1Locator,By subMenu2Locator,By subMenu3Locator) throws InterruptedException
    {

        doClick(parentMenuLocator);
        Thread.sleep(2000);
        act.moveToElement(getElement(subMenu1Locator)).perform();

        Thread.sleep(2000);

        act.moveToElement(getElement(subMenu2Locator)).perform();

        Thread.sleep(2000);

        act.moveToElement(getElement(subMenu3Locator))
                .click(driver.findElement(subMenu3Locator))
                .perform();

    }

    public void doActionsSendKeys(By locator, String value)
    {
        act.sendKeys(driver.findElement(locator)).perform();

    }

    public void doActionsClick(By locator)
    {
        act.click(driver.findElement(locator)).perform();

    }

// *******************Utility with Waits***************************

    public WebElement waitForElementPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        WebElement El = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return El;

    }

    public WebElement waitForElementVisibility(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        WebElement El = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return El;

    }



    public Alert waitforAlert(int timeOut)
    {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        Alert alert=wait.until(ExpectedConditions.alertIsPresent());
        return alert;


    }

    public String getAlertTextWithWait(int timeOut)
    {
        String alertText=waitforAlert(timeOut).getText();
        return alertText;


    }

    public void acceptAlertWithWait(int timeOut)
    {
        waitforAlert(timeOut).accept();


    }

    public void dismissAlertWithWait(int timeOut)
    {
        waitforAlert(timeOut).dismiss();


    }


    public String waitforTitleContains(String expectedValue, int timeOut)
    {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        try
        {
            wait.until(ExpectedConditions.titleContains(expectedValue));
        }
        catch(TimeoutException e)
        {
            System.out.println("Expected Title is not matching");
            e.printStackTrace();
        }

        return driver.getTitle();
    }

    public String waitforexactTitle(String expectedValue, int timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try
        {
            wait.until(ExpectedConditions.titleIs(expectedValue));
        }
        catch(TimeoutException e)
        {
            System.out.println("Expected Title is not matching");
            e.printStackTrace();
        }
        return driver.getTitle();
    }

    public String waitforUrlContains(String expectedValue, int timeOut)
    {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        try
        {
            wait.until(ExpectedConditions.urlContains(expectedValue));
        }
        catch(TimeoutException e)
        {
            System.out.println("Expected Url is not matching");
            e.printStackTrace();
        }

        return driver.getCurrentUrl();
    }

    public String waitforExactUrl(String expectedValue, int timeOut)
    {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        try
        {
            wait.until(ExpectedConditions.urlToBe(expectedValue));
        }
        catch(TimeoutException e)
        {
            System.out.println("Expected Url is not matching");
            e.printStackTrace();
        }

        return driver.getCurrentUrl();
    }

    public boolean waitforFrameAndSwitchtoIt(By locator, int timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        try
        {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
            return true;
        }
        catch (TimeoutException e) {

            System.out.println("Frame is not available");
            e.printStackTrace();

            return false;
        }

    }

    public List<WebElement> waitforElementsPresence(By locator, int timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        List<WebElement> elList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

        return elList;

    }

    public List<WebElement> waitforElementsVisibility(By locator, int timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        List<WebElement> elList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

        return elList;

    }

    public void waitForElementClickable(By locator, int timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

    }




}
