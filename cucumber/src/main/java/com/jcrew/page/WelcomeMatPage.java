package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.*;

/**
 * Created by 9hvenaga on 4/15/2016.
 */
public class WelcomeMatPage {

    private final WebDriver driver;

    public WelcomeMatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isWelcomeMatDisplayed() {
        return driver.findElement(By.className("c-header__welcomemat")).isDisplayed();
    }


}
