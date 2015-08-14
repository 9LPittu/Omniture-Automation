package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class OrderSummaryPage {

    @FindBy(id = "confirmation-number")
    private WebElement confirmationNumber;

    public OrderSummaryPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isOrderSummaryPage() {
        return confirmationNumber.isDisplayed();
    }

}
