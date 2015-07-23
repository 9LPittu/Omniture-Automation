package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutSelectionPage {

    @FindBy(xpath = ".//*[@id='frmGuestCheckOut']/a")
    private WebElement checkoutAsGuestLink;

    public CheckoutSelectionPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public void selects_to_checkout_as_guest() {

        checkoutAsGuestLink.click();
    }

}
