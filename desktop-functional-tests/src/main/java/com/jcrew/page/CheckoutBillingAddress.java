package com.jcrew.page;

import com.jcrew.pojo.Address;
import com.jcrew.pojo.User;
import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by nadiapaolagarcia on 5/9/16.
 */
public class CheckoutBillingAddress extends Checkout {

    @FindBy(id = "shipping-address")
    private WebElement shippingForm;
    @FindBy(id = "firstNameAM")
    private WebElement firstName;
    @FindBy(id = "lastNameAM")
    private WebElement lastName;
    @FindBy(id = "address1")
    private WebElement address1;
    @FindBy(id = "address2")
    private WebElement address2;
    @FindBy(id = "zipcode")
    private WebElement zipcode;
    @FindBy(id = "phoneNumAM")
    private WebElement phoneNum;

    public CheckoutBillingAddress(WebDriver driver) {
        super(driver);

        wait.until(ExpectedConditions.visibilityOf(shippingForm));
    }

    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");
        logger.debug("Billing address id: {}", bodyId);

        return bodyId.equals("billing");
    }

    public void fillFormData() {
        Address address = new Address("billing");
        User user = User.getFakeUser();

        firstName.sendKeys(user.getFirstName());
        lastName.sendKeys(user.getLastName());
        address1.sendKeys(address.getLine1());
        address2.sendKeys(address.getLine2());
        zipcode.sendKeys(address.getZipcode());
        phoneNum.sendKeys(address.getPhone());

        WebElement usState = shippingForm.findElement(By.id("dropdown-us-city-state"));
        wait.until(ExpectedConditions.visibilityOf(usState));
    }

    public void continueCheckout() {
    	shippingForm.findElement(By.className("button-submit")).click();
    	Util.waitForPageFullyLoaded(driver);
    	Util.waitLoadingBar(driver);
    }
}
