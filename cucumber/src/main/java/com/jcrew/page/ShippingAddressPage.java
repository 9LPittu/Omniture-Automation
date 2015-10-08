package com.jcrew.page;

import com.github.javafaker.Faker;
import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShippingAddressPage {

    private final WebDriver driver;

    private Faker faker = new Faker();

    @FindBy(id = "firstNameSA")
    private WebElement firstNameSA;

    @FindBy(id = "lastNameSA")
    private WebElement lastNameSA;

    @FindBy(id = "address3")
    private WebElement address3;

    @FindBy(id = "address1")
    private WebElement address1;

    @FindBy(id = "address2")
    private WebElement address2;

    @FindBy(id = "zipcode")
    private WebElement zipcode;

    @FindBy(id = "phoneNumSA")
    private WebElement phoneNumSA;

    @FindBy(xpath = ".//*[@id='shipping-address']/div/a")
    private WebElement continueCheckout;

    @FindBy(id = "dropdown-us-city-state")
    private WebElement dropdownUsCityState;

    @FindBy (id = "sameBillShip")
    private WebElement sameBillingAndShippingAddress;

    public ShippingAddressPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fills_shipping_address() {

        firstNameSA.sendKeys(faker.name().firstName());
        lastNameSA.sendKeys(faker.name().lastName());
        address3.sendKeys("JCrew");
        address1.sendKeys("770 Broadway");
        address2.sendKeys("14th Floor");
        zipcode.sendKeys("10003");
        phoneNumSA.sendKeys(faker.phoneNumber().phoneNumber());

    }

    public void presses_continue_button_on_shipping_address() {
        continueCheckout.click();
    }

    public String getSelectedCityAndState() {
        WebElement cityStateElement = (Util.createWebDriverWait(driver)).
                until(ExpectedConditions.visibilityOf(dropdownUsCityState));

        Select cityStateSelect = new Select(cityStateElement);

        WebElement cityStateOptionSelected = cityStateSelect.getFirstSelectedOption();

        return cityStateOptionSelected.getText();
    }

    public boolean isBillingAndShippingSameAddress() {
        return sameBillingAndShippingAddress.isSelected();
    }
}
