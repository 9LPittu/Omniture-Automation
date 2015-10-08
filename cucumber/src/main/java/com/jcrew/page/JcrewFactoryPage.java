package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class JcrewFactoryPage {

    private final WebDriver driver;

    public JcrewFactoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public Boolean isJcrewFactoryPage() {
        return Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains("factory.jcrew.com"));
    }
}
