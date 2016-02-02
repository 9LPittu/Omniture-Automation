package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HelpStoreLocatorPage {

    private final WebDriver driver;

    @FindBy(className = "bwt-index-search-form-wrap")
    private
    WebElement storeLocatorFinder;

    public HelpStoreLocatorPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isHelpStoreLocatorPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(storeLocatorFinder));
        return storeLocatorFinder.isDisplayed();
    }
}
