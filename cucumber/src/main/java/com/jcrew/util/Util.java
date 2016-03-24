package com.jcrew.util;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.primitives.Booleans;
import com.jcrew.pojo.Product;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import java.util.List;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static final int DEFAULT_TIMEOUT = 180;
    private static final StateHolder stateHolder = StateHolder.getInstance();
    public static final String xpathGetTextLower = "translate(text(), 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz')";

    public static int randomIndex(int size) {
        return (int) (Math.random() * (size - 1));
    }

    public static Product getCurrentProduct() {
        final List<Product> productList = (List<Product>) stateHolder.get("productList");
        int currentProduct = productList.size() - 1;
        return productList.get(currentProduct);
    }

    public static WebDriverWait createWebDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    public static void waitForPageFullyLoaded(WebDriver driver) {
        createWebDriverWait(driver).until(new Predicate<WebDriver>() {
            public boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        });
    }

    public static void waitLoadingBar(WebDriver driver){
        createWebDriverWait(driver).until(new Function<WebDriver, Boolean>(){
            @Override
            public Boolean apply(WebDriver webDriver) {
                WebElement html = webDriver.findElement(By.tagName("html"));
                String htmlClass = html.getAttribute("class");
                return !htmlClass.contains("nprogress-busy");
            }
        });
    }

    public static String getPageVariableValue(WebDriver driver, String variable){
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        String value = "";

        try{
            value = (String) javascriptExecutor.executeScript("return " + variable);
        } catch (WebDriverException wde){
            logger.error("Not able to get value from {}", driver.getCurrentUrl(), wde);
        }

        return value;
    }

    public static void clickWithStaleRetry(WebElement element) throws StaleElementReferenceException{
        int attempts = 0;
        boolean success = false;

        while (attempts < 2 && !success){
            try{
                element.click();
                success = true;
            } catch (StaleElementReferenceException staleException){
                logger.debug("Stale Element Exception when retrying to click");
            }
            attempts++;
        }

        if(!success){
            throw new StaleElementReferenceException("Failed to click element");
        }
    }

    public static void waitWithStaleRetry(WebDriver driver, WebElement element) throws StaleElementReferenceException{
        int attempts = 0;
        boolean success = false;
        WebDriverWait wait = createWebDriverWait(driver);

        while (attempts < 4 && !success){
            try{
                wait.until(ExpectedConditions.visibilityOf(element));
                success = true;
            } catch (StaleElementReferenceException staleException){
                logger.debug("Stale Element Exception when retrying to wait");
            }
            attempts++;
        }

        if(!success){
            throw new StaleElementReferenceException("Failed to wait element");
        }
    }

    public static void scrollToElement(WebDriver driver, WebElement element){
        Actions action = new Actions(driver);
        createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(element));
        action.moveToElement(element);
    }

}
