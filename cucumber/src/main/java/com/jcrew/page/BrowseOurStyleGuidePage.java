package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BrowseOurStyleGuidePage {

    @FindBy(className = "catalog_container")
    private
    WebElement catalogContainer;

    public BrowseOurStyleGuidePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isBrowseOurStyleGuidePage() {

        return catalogContainer.isDisplayed();
    }
}
