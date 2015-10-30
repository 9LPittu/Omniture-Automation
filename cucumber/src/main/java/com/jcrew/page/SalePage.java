package com.jcrew.page;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by 9hvenaga on 10/29/2015.
 */
public class SalePage {

    private final Logger logger = LoggerFactory.getLogger(SearchPage.class);

    private final WebDriver driver;

    public SalePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public boolean isSalePage() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(headerSearch));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(footer));
        return headerSearch.isDisplayed() && footer.isDisplayed();
    }
}
