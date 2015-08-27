package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage {

    @FindBy(id = "main_cont")
    private WebElement myAccountContainer;

    public MyAccountPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isInAccountPage() {
        return myAccountContainer.isDisplayed();
    }

    public String getMyAccountHeader() {
        return myAccountContainer.findElement(By.tagName("h2")).getText();
    }

    public boolean isMenuLinkPresent(String link) {
        return myAccountContainer.findElement(By.linkText(link)).isDisplayed();
    }
}
