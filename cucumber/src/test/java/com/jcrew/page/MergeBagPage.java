package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MergeBagPage {


    @FindBy(id = "mergedCartActionTop")
    private WebElement mergeCartActionTop;

    public MergeBagPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isMergeBagPage() {
        boolean result;
        try {
            result = mergeCartActionTop.isDisplayed();
        } catch (NoSuchElementException nse) {
            result = false;
        }
        return result;
    }

    public void click_save_to_wishlist_and_continue_checkout() {
        final WebElement saveToWishlistAndContinueCheckoutButton = mergeCartActionTop.
                findElement(By.className("button-submit"));

        saveToWishlistAndContinueCheckoutButton.click();

    }
}
