package com.jcrew.util;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static final int DEFAULT_TIMEOUT = 60;
    public static final int DEFAULT_TIMEOUT_STEEL = 120;
    private static final StateHolder stateHolder = StateHolder.getInstance();
    public static final String xpathGetTextLower = "translate(text(), 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz')";

    public static int randomIndex(int size) {
        return (int) (Math.random() * (size));
    }

    public static Product getCurrentProduct() {
        @SuppressWarnings("unchecked")
		final List<Product> productList = (List<Product>) stateHolder.get("productList");        
        int currentProduct = productList.size() - 1;
        return productList.get(currentProduct);
    }

    public static WebDriverWait createWebDriverWait(WebDriver driver) {

        PropertyReader reader = PropertyReader.getPropertyReader();
        if (reader.getProperty("environment").equalsIgnoreCase("steel"))
            return new WebDriverWait(driver, DEFAULT_TIMEOUT_STEEL);
        else
            return new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    public static WebDriverWait createWebDriverWait(WebDriver driver, int timeInSeconds) {
        return new WebDriverWait(driver, timeInSeconds);
    }

    public static void waitForPageFullyLoaded(WebDriver driver) {
        createWebDriverWait(driver).until(new Predicate<WebDriver>() {
            public boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        });
    }

    public static void waitLoadingBar(WebDriver driver) {
        try {
            createWebDriverWait(driver).until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    WebElement html = webDriver.findElement(By.tagName("html"));
                    String htmlClass = html.getAttribute("class");
                    return !htmlClass.contains("nprogress-busy");
                }
            });
        } catch (StaleElementReferenceException stale) {
            logger.error("Stale Reference Exception when trying to check the loading bar; assuming is gone " +
                    "and ignoring exception.");
        }
    }

    public static String getPageVariableValue(WebDriver driver, final String variable) throws InterruptedException {
        WebDriverWait waitForVariable = new WebDriverWait(driver, 10);
        String value = "";

        try {
            value = waitForVariable.until(new Function<WebDriver, String>() {
                @Override
                public String apply(WebDriver webDriver) {
                    String value;
                    try {
                        value = (String) ((JavascriptExecutor) webDriver).executeScript("return " + variable);
                        if (value != null && value.isEmpty())
                            value = null;
                    } catch (WebDriverException wde) {
                        value = null;
                    }
                    return value;
                }
            });
        } catch (TimeoutException timeout) {
            logger.error("Variable {} not found in URL {} after waiting", variable, driver.getCurrentUrl(), timeout);
        }

        return value;
    }

    public static Map<String, String> getPageVariablesValue(WebDriver driver, Set<String> variables) throws InterruptedException {
        Map<String, String> variable_value = new HashMap<>();

        Iterator<String> variablesIterator = variables.iterator();
        String firstVariable = variablesIterator.next();

        variable_value.put(firstVariable, getPageVariableValue(driver, firstVariable));

        while (variablesIterator.hasNext()) {
            String variable = variablesIterator.next();
            String value;
            try {
                value = (String) ((JavascriptExecutor) driver).executeScript("return " + variable);
            } catch (WebDriverException wde) {
                value = "";
                logger.error("Not able to get variable {}", variable, wde);
            }
            variable_value.put(variable, value);
        }

        return variable_value;

    }

    public static void clickWithStaleRetry(WebElement element) throws StaleElementReferenceException {
        int attempts = 0;
        boolean success = false;

        while (attempts < 2 && !success) {
            try {
                element.click();
                success = true;
            } catch (StaleElementReferenceException staleException) {
                logger.debug("Stale Element Exception when retrying to click");
            }
            attempts++;
        }

        if (!success) {
            throw new StaleElementReferenceException("Failed to click element");
        }
    }

    public static void waitWithStaleRetry(WebDriver driver, WebElement element) throws StaleElementReferenceException {
        int attempts = 0;
        boolean success = false;
        WebDriverWait wait = createWebDriverWait(driver);

        while (attempts < 4 && !success) {
            try {
                wait.until(ExpectedConditions.visibilityOf(element));
                success = true;
            } catch (StaleElementReferenceException staleException) {
                logger.debug("Stale Element Exception when retrying to wait");
            }
            attempts++;
        }

        if (!success) {
            throw new StaleElementReferenceException("Failed to wait element");
        }
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        Actions action = new Actions(driver);
        createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(element));
        action.moveToElement(element);
    }

    public static boolean countryContextURLCompliance(WebDriver driver, Country country) {
        String url = driver.getCurrentUrl();
        String countryURL = country.getHomeurl();
        String countryCode = country.getCountry();

        boolean startsWith = url.startsWith(countryURL);
        boolean contains = url.contains("/" + countryCode + "/");

        return startsWith & contains == country.isContexturl();
    }
}
