package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class BrowseOurStyleGuidePage {

    @FindBy(className = "catalog_container")
    WebElement catalogContainer;

    public BrowseOurStyleGuidePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isBrowseOurStyleGuidePage() {

        return catalogContainer.isDisplayed();
    }
}
