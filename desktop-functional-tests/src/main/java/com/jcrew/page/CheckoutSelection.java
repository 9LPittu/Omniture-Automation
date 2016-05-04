package com.jcrew.page;

import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutSelection {

    @FindBy(xpath = ".//*[@id='frmGuestCheckOut']/a")
    private WebElement checkoutAsGuestLink;

    private final WebDriver driver;

    public CheckoutSelection(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void selects_to_checkout_as_guest() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(checkoutAsGuestLink));
        checkoutAsGuestLink.click();
        Util.waitLoadingBar(driver);
        Util.waitForPageFullyLoaded(driver);
    }
}
