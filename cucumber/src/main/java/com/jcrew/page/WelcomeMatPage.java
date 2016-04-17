package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 9hvenaga on 4/15/2016.
 */
public class WelcomeMatPage {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(WelcomeMatPage.class);

    @FindBy(className = "c-header__welcomemat")
    private WebElement welcomeMat;

    public WelcomeMatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean  isWelcomeMatDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(welcomeMat));
        return welcomeMat.isDisplayed();
    }


    public boolean isWelcomeMatNotDisplayed() {
        try {
            return !(welcomeMat.isDisplayed());
        } catch(NoSuchElementException ne) {
            logger.debug("welcome mat is not found");
            return true;
        }
    }




}
