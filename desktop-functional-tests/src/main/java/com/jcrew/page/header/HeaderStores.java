package com.jcrew.page.header;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ngarcia on 3/22/17.
 */
public class HeaderStores extends HeaderWrap {

    @FindBy(className = "primary-nav__text--stores")
    private WebElement stores;

    public HeaderStores(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(stores));
    }

    public void clickStores() {
        stores.click();
    }

}
