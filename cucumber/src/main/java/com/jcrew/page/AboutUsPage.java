package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AboutUsPage {
    private final WebDriver driver;
    @FindBy(id = "aboutTopNav")
    private WebElement aboutTopNavigation;

    public AboutUsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isAboutUsPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(aboutTopNavigation));
        return aboutTopNavigation.isDisplayed();
    }
}
