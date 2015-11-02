package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VeryPersonalStylistPage {

    @FindBy(id = "navContainer-sticky-wrapper")
    private WebElement navigationContainerStickyWrapper;

    public VeryPersonalStylistPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isVeryPersonalStylistPage() {
        return navigationContainerStickyWrapper.isDisplayed();
    }
}
