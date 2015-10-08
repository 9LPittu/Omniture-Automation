package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ReviewPage {

    private final WebDriver driver;
    @FindBy(xpath = ".//*[@id='orderSummaryContainer']/div/a")
    private WebElement placeYourOrder;

    @FindBy(id = "errors")
    private WebElement errorsModal;

    @FindBy(id = "billing-details")
    private WebElement billingSection;

    @FindBy(id = "securityCode")
    private WebElement securityCode;

    @FindBy(id = "shipping-details")
    private WebElement shippingSection;

    public ReviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void user_places_its_order() {

        Util.createWebDriverWait(driver).
                until(ExpectedConditions.elementToBeClickable(placeYourOrder));

        placeYourOrder.click();
    }

    public boolean containsErrors() {
        return errorsModal.isDisplayed();
    }

    public boolean isBillingSectionDisplayed() {
        return billingSection.isDisplayed();
    }

    public void input_credit_card_security_code() {
        securityCode.sendKeys("123");
    }

    public boolean isShippingSectionDisplayed() {
        return shippingSection.isDisplayed();
    }
}
