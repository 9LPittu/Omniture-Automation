package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by 9hvenaga on 4/15/2016.
 */
public class WelcomeMatPage {

    private final WebDriver driver;

    public WelcomeMatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean  isWelcomeMatDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(By.className("c-header__welcomemat"))));
        return driver.findElement(By.className("c-header__welcomemat")).isDisplayed();
    }


}
