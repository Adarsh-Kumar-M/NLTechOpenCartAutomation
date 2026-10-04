package com.qa.opencart.utils;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.exceptions.ElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ElementUtils {

    private static final Logger log = LogManager.getLogger(ElementUtils.class);
    private WebDriver driver;
    private Actions act;

    public ElementUtils(WebDriver driver) {
        this.driver = driver;
        act = new Actions(driver);
    }

    public WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public void doSendKeys(By locator, String value) {
        if (value == null) {
            log.error("Null value not allowed for sendKeys");
            throw new ElementException("===Invalid String value. Null Not Allowed.===");
        }
        getElement(locator).sendKeys(value);
    }

    public void doMultipleSendKeys(By locator, CharSequence... value) {
        getElement(locator).sendKeys(value);
    }

    public void doClick(By locator) {
        getElement(locator).click();
    }

    public String doElementGetText(By locator) {
        return getElement(locator).getText();
    }

    public String getTitle() {
        return driver.getTitle();
    }
    public boolean isElementDisplayed(By locator) {
        try {
            return getElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            log.error("Locator is not correct or element not found: " + locator);
            return false;
        }
    }

    public boolean isElementEnabled(By locator) {
        try {
            return getElement(locator).isEnabled();
        } catch (NoSuchElementException e) {
            log.error("Element is not interactable or enabled: " + locator);
            return false;
        }
    }

    public String getElementDomPropertyValue(By locator, String attrName) {
        return getElement(locator).getDomProperty(attrName);
    }

    public String getElementDomAttributeValue(By locator, String attrName) {
        return getElement(locator).getDomAttribute(attrName);
    }

    public int getTotalElementsCount(By locator) {
        return getElements(locator).size();
    }

    public List<String> getElementsTextList(By locator) {
        List<WebElement> elList = getElements(locator);
        List<String> textList = new ArrayList<>();

        for (WebElement e : elList) {
            String text = e.getText();
            if (!text.isEmpty()) {
                textList.add(text);
            }
        }
        return textList;
    }

    public boolean isElementExist(By locator) {
        int count = getTotalElementsCount(locator);
        if (count == 1) {
            log.info("Element is available on the page 1 time");
            return true;
        } else {
            log.info("Element is not available on the webpage");
            return false;
        }
    }

    public boolean isElementExist(By locator, int expectedCount) {
        int count = getTotalElementsCount(locator);
        if (count == expectedCount) {
            log.info("Element is available on the page " + expectedCount + " times");
            return true;
        } else {
            log.info("Element is not available on the webpage");
            return false;
        }
    }

    public void clickSingleElementFromList(By locator, String value) {
        List<WebElement> elList = getElements(locator);
        for (WebElement e : elList) {
            String text = e.getText();
            log.debug("Element text: " + text);
            if (text.equals(value)) {
                e.click();
                log.info("Clicked on element with text: " + value);
                break;
            }
        }
    }

    public void doSearchAutoClickSuggestion(By searchLocator, String searchValue, By suggestionLocator,
                                            String suggestedValue) {
        log.info("Searching for: " + searchValue);
        waitForElementPresence(searchLocator, AppConstants.DEFAULT_SHORT_TIME);
        doSendKeys(searchLocator, searchValue);

        List<WebElement> suggestionList = waitforElementsPresence(suggestionLocator, AppConstants.DEFAULT_SHORT_TIME);
        log.info("Total suggestions found: " + suggestionList.size());

        for (WebElement e : suggestionList) {
            String text = e.getText();
            log.debug("Suggestion: " + text);
            if (text.equals(suggestedValue)) {
                e.click();
                log.info("Clicked on suggestion: " + suggestedValue);
                break;
            }
        }
    }
    public void doSelectByIndex(By locator, int index) {
        Select sec = new Select(getElement(locator));
        sec.selectByIndex(index);
        log.info("Selected option by index: " + index);
    }

    public void doSelectByValue(By locator, String value) {
        Select sec = new Select(getElement(locator));
        sec.selectByValue(value);
        log.info("Selected option by value: " + value);
    }

    public void doSelectByVisibleText(By locator, String text) {
        Select sec = new Select(getElement(locator));
        sec.selectByVisibleText(text);
        log.info("Selected option by visible text: " + text);
    }

    public void doDeSelectByIndex(By locator, int index) {
        Select sec = new Select(getElement(locator));
        sec.deselectByIndex(index);
        log.info("Deselected option by index: " + index);
    }

    public void doDeSelectByValue(By locator, String value) {
        Select sec = new Select(getElement(locator));
        sec.deselectByValue(value);
        log.info("Deselected option by value: " + value);
    }

    public void doDeSelectByVisibleText(By locator, String text) {
        Select sec = new Select(getElement(locator));
        sec.deselectByVisibleText(text);
        log.info("Deselected option by visible text: " + text);
    }

    public List<String> getDropdownValuesList(By locator) {
        Select sec = new Select(getElement(locator));
        List<WebElement> optionsList = sec.getOptions();
        List<String> textList = new ArrayList<>();

        log.info("Total dropdown options: " + optionsList.size());
        for (WebElement e : optionsList) {
            textList.add(e.getText());
        }
        return textList;
    }

    public int getDropdownOptionsCount(By locator) {
        Select sec = new Select(getElement(locator));
        List<WebElement> optionsList = sec.getOptions();
        int count = optionsList.size();
        log.info("Dropdown options count: " + count);
        return count;
    }


    public void menuSubMenuHandling(By parentMenuLocator, By childMenuLocator) {
        log.info("Handling menu and submenu");
        act.moveToElement(getElement(parentMenuLocator)).perform();
        act.click(getElement(childMenuLocator)).perform();
    }

    public void menuSubMenuHandlingLevel4(By parentMenuLocator, By subMenu1Locator, By subMenu2Locator, By subMenu3Locator) {
        log.info("Handling level 4 menu navigation");
        doClick(parentMenuLocator);
        waitForElementPresence(subMenu1Locator, AppConstants.DEFAULT_SHORT_TIME);
        act.moveToElement(getElement(subMenu1Locator)).perform();

        waitForElementPresence(subMenu2Locator, AppConstants.DEFAULT_SHORT_TIME);
        act.moveToElement(getElement(subMenu2Locator)).perform();

        waitForElementPresence(subMenu3Locator, AppConstants.DEFAULT_SHORT_TIME);
        act.moveToElement(getElement(subMenu3Locator))
                .click(driver.findElement(subMenu3Locator))
                .perform();
        log.info("Level 4 menu navigation completed");
    }

    public void doActionsSendKeys(By locator, String value) {
        log.debug("Sending keys using Actions: " + value);
        act.sendKeys(driver.findElement(locator)).perform();
    }

    public void doActionsClick(By locator) {
        log.debug("Clicking using Actions");
        act.click(driver.findElement(locator)).perform();
    }

    public WebElement waitForElementPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        log.debug("Element present: " + locator);
        return element;
    }

    public WebElement waitForElementVisibility(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        log.debug("Element visible: " + locator);
        return element;
    }

    public Alert waitforAlert(int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        log.info("Alert is present");
        return alert;
    }

    public String getAlertTextWithWait(int timeOut) {
        String alertText = waitforAlert(timeOut).getText();
        log.info("Alert text: " + alertText);
        return alertText;
    }

    public void acceptAlertWithWait(int timeOut) {
        waitforAlert(timeOut).accept();
        log.info("Alert accepted");
    }

    public void dismissAlertWithWait(int timeOut) {
        waitforAlert(timeOut).dismiss();
        log.info("Alert dismissed");
    }


    public String waitforTitleContains(String expectedValue, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.titleContains(expectedValue));
            log.info("Title contains: " + expectedValue);
        } catch (TimeoutException e) {
            log.error("Expected title not matching. Expected to contain: " + expectedValue);
        }
        return driver.getTitle();
    }

    public String waitforexactTitle(String expectedValue, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.titleIs(expectedValue));
            log.info("Title matches exact: " + expectedValue);
        } catch (TimeoutException e) {
            log.error("Expected title not matching. Expected: " + expectedValue);
        }
        return driver.getTitle();
    }

    public String waitforUrlContains(String expectedValue, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.urlContains(expectedValue));
            log.info("URL contains: " + expectedValue);
        } catch (TimeoutException e) {
            log.error("Expected URL not matching. Expected to contain: " + expectedValue);
        }
        return driver.getCurrentUrl();
    }

    public String waitforExactUrl(String expectedValue, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.urlToBe(expectedValue));
            log.info("URL matches exact: " + expectedValue);
        } catch (TimeoutException e) {
            log.error("Expected URL not matching. Expected: " + expectedValue);
        }
        return driver.getCurrentUrl();
    }

    public boolean waitforFrameAndSwitchtoIt(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
            log.info("Frame is available and switched");
            return true;
        } catch (TimeoutException e) {
            log.error("Frame is not available: " + locator);
            return false;
        }
    }

    public List<WebElement> waitforElementsPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        List<WebElement> elList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        log.debug("Elements present count: " + elList.size());
        return elList;
    }

    public List<WebElement> waitforElementsVisibility(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        List<WebElement> elList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        log.debug("Elements visible count: " + elList.size());
        return elList;
    }

    public void waitForElementClickable(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        log.info("Element clicked after waiting for clickability: " + locator);
    }
}
