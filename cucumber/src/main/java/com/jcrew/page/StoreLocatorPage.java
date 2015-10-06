package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StoreLocatorPage {


    @FindBy(id = "bwt-q")
    private WebElement findByLocation;

    public StoreLocatorPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public boolean isFindByLocationPresent() {
        return findByLocation.isDisplayed();
    }
}
