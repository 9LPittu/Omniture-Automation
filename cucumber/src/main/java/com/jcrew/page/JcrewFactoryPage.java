package com.jcrew.page;

import com.jcrew.util.Util;

import ch.qos.logback.classic.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;

public class JcrewFactoryPage {

    private final WebDriver driver;

    public JcrewFactoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public Boolean isJcrewFactoryPage() {
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        System.out.println("# of tabs opened when j.crew factory link is clicked:" + tabs.size());
        driver.switchTo().window(tabs.get(1));
        return Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains("factory.jcrew.com"));
    }
}
