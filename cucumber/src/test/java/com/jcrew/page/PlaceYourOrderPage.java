package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertFalse;


public class PlaceYourOrderPage {

    private final WebDriver driver;
    @FindBy(xpath = ".//*[@id='orderSummaryContainer']/div/a")
    WebElement placeYourOrder;

    @FindBy(id = "errors")
    WebElement errorsModal;

    public PlaceYourOrderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void user_places_its_order() {

        new WebDriverWait(driver, 10).
                until(ExpectedConditions.elementToBeClickable(placeYourOrder));

        placeYourOrder.click();
    }

    public boolean containsErrors() {
        return errorsModal.isDisplayed();
    }
}
