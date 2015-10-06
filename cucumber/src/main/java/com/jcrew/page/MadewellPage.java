package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class MadewellPage {
    private final WebDriver driver;

    public MadewellPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public Boolean isMadewellPage() {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains("www.madewell.com"));
    }
}
