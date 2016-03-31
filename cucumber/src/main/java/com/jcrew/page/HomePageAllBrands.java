package com.jcrew.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePageAllBrands {

    private final WebDriver driver;
    private Logger logger = LoggerFactory.getLogger(HomePageAllBrands.class);

    public HomePageAllBrands(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isGivenVarPresentInSourceCode(String var) {

        if(var.contains("src")) {
            logger.debug("looking for page source to contain "+var+":  {}",driver.getPageSource().contains("src=\"http"));
            return driver.getPageSource().contains("src=\"http");
        }
        logger.debug("looking for page source to contain "+var+":  {}",driver.getPageSource().contains(var));
        return driver.getPageSource().contains(var);
    }

    public String getSAccountValue(){
        String sAccountValue = (String)((JavascriptExecutor)driver).executeScript("return s_account;");
        logger.info("s_account value is : {}",sAccountValue);
        return sAccountValue;
    }

}

