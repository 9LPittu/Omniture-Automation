package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AboutUsPage {
    @FindBy(id = "aboutTopNav")
    private WebElement aboutTopNavigation;

    public AboutUsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isAboutUsPage() {
        return aboutTopNavigation.isDisplayed();
    }
}
