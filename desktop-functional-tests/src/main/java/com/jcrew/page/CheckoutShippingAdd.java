package com.madewell.page;

import com.madewell.pojo.Address;
import com.madewell.pojo.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutShippingAdd extends Checkout {

    @FindBy(id = "shipping-address")
    private WebElement shippingForm;
    @FindBy(id = "firstNameSA")
    private WebElement firstName;
    @FindBy(id = "lastNameSA")
    private WebElement lastName;
    @FindBy(id = "address1")
    private WebElement address1;
    @FindBy(id = "address2")
    private WebElement address2;
    @FindBy(id = "zipcode")
    private WebElement zipcode;
    @FindBy(id = "phoneNumSA")
    private WebElement phoneNum;

    public CheckoutShippingAdd(WebDriver driver) {
        super(driver);

        wait.until(ExpectedConditions.visibilityOf(shippingForm));
    }

    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("shipping");
    }

    private void fillFormData(Address address) {
        User user = User.getFakeUser();

        firstName.sendKeys(user.getFirstName());
        lastName.sendKeys(user.getLastName());
        address1.sendKeys(address.getLine1());
        address2.sendKeys(address.getLine2());
        phoneNum.sendKeys(address.getPhone());

        zipcode.clear();
        zipcode.sendKeys(address.getZipcode());

        WebElement usState = shippingForm.findElement(By.id("dropdown-us-city-state"));
        wait.until(ExpectedConditions.visibilityOf(usState));
    }

    public void fillQASShippingData() {
        Address address = new Address("QAS");
        fillFormData(address);
    }

    public void fillShippingData() {
        Address address = new Address();
        fillFormData(address);
    }

    public void continueCheckout() {
        nextStep(shippingForm);
    }

    public void fillAPOShippingData() {
        Address address = new Address("apo");
        fillFormData(address);
    }

    public void fillFPOShippingData() {
        Address address = new Address("apo");
        fillFormData(address);
    }
}
