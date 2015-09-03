package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class OrderConfirmationPage {

    @FindBy(id = "confirmation-number")
    private WebElement confirmationNumber;

    public OrderConfirmationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isOrderConfirmationPage() {
        return confirmationNumber.isDisplayed();
    }

}
