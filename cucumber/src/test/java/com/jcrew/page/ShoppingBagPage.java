package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ShoppingBagPage {


    @FindBy(id = "button-checkout")
    WebElement checkoutLink;

    public ShoppingBagPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public void wants_to_checkout() {
        checkoutLink.click();
    }
}
