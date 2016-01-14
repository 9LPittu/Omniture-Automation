package com.jcrew.util;


import com.google.common.base.Predicate;
import com.jcrew.pojo.Product;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static final int DEFAULT_TIMEOUT = 180;
    private static final StateHolder stateHolder = StateHolder.getInstance();

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
    
}
