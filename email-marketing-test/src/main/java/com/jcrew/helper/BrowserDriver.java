package com.jcrew.helper;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BrowserDriver {
    private static final Logger LOGGER = Logger.getLogger(BrowserDriver.class.getName());
    public static String ChromeDriverPath = "Drivers" + File.separator + "chromedriver.exe";
    private static WebDriver mDriver;
    public static final Integer defaultTimeoutSeconds = 10;

    public synchronized static WebDriver getCurrentDriver(String browserType) {

        if (mDriver == null) {
            try {
                mDriver = BrowserFactory.getBrowser(browserType);
            } catch (UnreachableBrowserException e) {
                mDriver = BrowserFactory.getBrowser();
            } catch (WebDriverException e) {
                mDriver = BrowserFactory.getBrowser();
            } finally {
                //Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
            }
        }
        return mDriver;
    }

    public synchronized static WebDriver getCurrentDriver() {
        return getCurrentDriver(PropertyLoader.getBrowserType());
    }

    public static void close() {
        try {
            getCurrentDriver().quit();
            mDriver = null;
            //LOGGER.info("closing the browser");
        } catch (UnreachableBrowserException e) {
            LOGGER.info("cannot close browser: unreachable browser");
        }
    }

    private static class BrowserCleanup implements Runnable {
        public void run() {
            close();
        }
    }

    public static void loadPage(String url) {
        getCurrentDriver();
        LOGGER.info("Directing browser to:" + url);
        //LOGGER.info("try to loadPage [" + url + "]");
        getCurrentDriver().get(url);
    }

    public static void reopenAndLoadPage(String url) {
        mDriver = null;
        getCurrentDriver();
        loadPage(url);
    }

    public static void setImplicitWaitToDefault() {
        getCurrentDriver().manage().timeouts().implicitlyWait(BrowserFactory.DEFAULT_IMPLICIT_WAIT_TIME_IN_SECONDS, TimeUnit.SECONDS);
    }

    public static WebElement waitForElement(By selector) {
        return waitForElement(getCurrentDriver().findElement(selector));
    }

    public static WebElement waitForElement(By selector, Integer waitTimeInSeconds) {
        return waitForElement(getCurrentDriver().findElement(selector), waitTimeInSeconds);
    }

    public static WebElement waitForElement(WebElement elementToWaitFor) {
        return waitForElement(elementToWaitFor, null);
    }

    public static WebElement waitForElement(WebElement elementToWaitFor, Integer waitTimeInSeconds) {
        if (waitTimeInSeconds == null) {
            waitTimeInSeconds = defaultTimeoutSeconds;
        }
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), waitTimeInSeconds);
        return wait.until(ExpectedConditions.visibilityOf(elementToWaitFor));
    }

    public static Boolean waitForTextToPresent(WebElement elementToWaitFor, Integer waitTimeInSeconds, String txt) {
        if (waitTimeInSeconds == null) {
            waitTimeInSeconds = defaultTimeoutSeconds;
        }
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), waitTimeInSeconds);
        return wait.until(ExpectedConditions.textToBePresentInElement(elementToWaitFor, txt));
    }

    /**
     * Wait for the element to be present in the DOM, regardless of being displayed or not.
     * And returns the first WebElement using the given method.
     *
     * @param driver           The driver object to be used
     * @param by               selector to find the element
     * @param timeOutInSeconds The time in seconds to wait until returning a failure
     * @return WebElement the first WebElement using the given method, or null (if the timeout is reached)
     */
    public static WebElement waitForElementPresent(WebDriver driver, final By by, int timeOutInSeconds) {
        WebElement element;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            setImplicitWaitToDefault();
            return element; //return the element
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Wait for the Text to be present in the given element, regardless of being displayed or not.
     *
     * @param driver           The driver object to be used to wait and find the element
     * @param by               selector of the given element, which should contain the text
     * @param text             The text we are looking
     * @param timeOutInSeconds The time in seconds to wait until returning a failure
     * @return boolean
     */
    public static boolean waitForTextPresent(WebDriver driver, final By by, final String text, int timeOutInSeconds) {
        boolean isPresent = false;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driverObject) {
                    return isTextPresent(driverObject, by, text); //is the Text in the DOM
                }
            });
            isPresent = isTextPresent(driver, by, text);
            driver.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS); //reset implicitlyWait
            return isPresent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if the text is present in the element.
     *
     * @param driver - The driver object to use to perform this element search
     * @param by     - selector to find the element that should contain text
     * @param text   - The Text element you are looking for
     * @return true or false
     */
    private static boolean isTextPresent(WebDriver driver, By by, String text) {
        try {
            return driver.findElement(by).getText().contains(text);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static WebElement waitForElementAndSleep(WebElement elementToWaitFor, long mills) throws Exception {
        WebElement we = waitForElement(elementToWaitFor, null);
        Thread.sleep(mills);
        return we;
    }

    public static void waitForElementToBeClickable(WebElement elementToWaitFor, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), timeOutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(elementToWaitFor));
    }

    public static void waitForElementClickAndSleep(WebElement elementToWaitFor, long mills) {
        WebElement we = waitForElement(elementToWaitFor, null);
        we.click();
        try {
            Thread.sleep(mills);
        } catch (Exception e) {

        }
    }

    public static void waitForElementToDisappearIfVisible(By elementToWaitFor, Integer waitTimeInSeconds) {
        if (isVisible(elementToWaitFor)) {
            WebDriverWait wait = new WebDriverWait(getCurrentDriver(), waitTimeInSeconds);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(elementToWaitFor));
        }
    }

    private static boolean isVisible(By element) {
        BrowserDriver.getCurrentDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        List<WebElement> loadingIcon = BrowserDriver.getCurrentDriver().findElements(element);
        return !loadingIcon.isEmpty();
    }

    public static void waitForElementToDisappear(By elementToWaitFor, Integer waitTimeInSeconds) {
        WebDriver localDriver = getCurrentDriver();
        // Give 1 second to show up if not already present - this happens a lot with mouse wait and ajax loading messages
        BrowserDriver.waitForElementPresent(localDriver, elementToWaitFor, 1);
        // Wait for element to disappear
        localDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(localDriver, waitTimeInSeconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(elementToWaitFor));
    }

    public static WebElement getParent(WebElement element) {
        return element.findElement(By.xpath(".."));
    }

    public static List<WebElement> getDropDownOptions(WebElement webElement) {
        Select select = new Select(webElement);
        return select.getOptions();
    }

    public static WebElement getDropDownOption(WebElement webElement, String value) {
        WebElement option = null;
        List<WebElement> options = getDropDownOptions(webElement);
        for (WebElement element : options) {
            if (element.getAttribute("value").equalsIgnoreCase(value)) {
                option = element;
                break;
            }
        }
        return option;
    }

    /**
     * @param selector
     * @return true if there are any displayed element found.
     */
    public static boolean isElementDisplayed(By selector) {
        return getDisplayedElements(selector).size() > 0;
    }

    /**
     * Get elements where isDisplayed (Display: none doesn't exist in its css) is true within 1 sec.
     *
     * @param selector
     * @return
     */
    public static List<WebElement> getDisplayedElements(By selector) {
        getCurrentDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        List<WebElement> found = BrowserDriver.getCurrentDriver().findElements(selector);
        getCurrentDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        Iterator<WebElement> iteratorFound = found.iterator();
        while (iteratorFound.hasNext()) {
            if (!iteratorFound.next().isDisplayed()) {
                iteratorFound.remove();
            }
        }

        return found;
    }

    public static void clearField(WebElement element, String text) {
        for (int i = 0; i < text.length(); i++) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    public static void captureScreenshot(String fileNamePrefix) {
        try {
            String strBasePath = new File(".").getCanonicalPath();
            DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
            String destDir = strBasePath + PropertyLoader.getScreenCaptureLocation();
            new File(destDir).mkdirs();
            String destFile = fileNamePrefix + "_" + dateFormat.format(new Date()) + ".png";
            try {
                FileUtils.copyFile(((TakesScreenshot) getCurrentDriver()).getScreenshotAs(OutputType.FILE),
                        new File(destDir + "/" + destFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            LOGGER.info("Saved Screen Shot: " + destDir + "/" + destFile);
        } catch (Exception e) {
            LOGGER.info("Exception creating screen shot: " + e.getMessage());
            // No need to crash the tests if the screenshot fails
        }
    }
}