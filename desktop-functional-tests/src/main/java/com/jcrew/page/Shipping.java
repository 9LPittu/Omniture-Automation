package com.jcrew.page;

import com.jcrew.pojo.User;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class Shipping {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Shipping.class);
    private final WebDriverWait wait;

    @FindBy(id = "shipping-address")
    private WebElement shippingAddress;
    @FindBy(id = "firstNameSA")
    private WebElement firstNameInput;
    @FindBy(id = "lastNameSA")
    private WebElement lastNameInput;
    @FindBy(id = "address1")
    private WebElement addressInput;
    @FindBy(id = "address2")
    private WebElement address2Input;
    @FindBy(id = "zipcode")
    private WebElement zipcodeInput;
    @FindBy(id = "phoneNumSA")
    private WebElement phoneInput;
    @FindBy(id = "dropdown-us-city-state")
    private WebElement us_city_state;
    @FindBy(id = "city")
    private WebElement city;
    @FindBy(id = "dropdown-state-province")
    private WebElement state_province;
    @FindBy (id = "shoppingAddressValidate")
    private WebElement addresValidate;

    public Shipping(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(shippingAddress));
    }

    public void fillGuestData() {
        TestDataReader testData = TestDataReader.getTestDataReader();
        User user = User.getNewFakeUser();

        firstNameInput.sendKeys(user.getFirstName());
        lastNameInput.sendKeys(user.getLastName());
        addressInput.sendKeys(testData.getData("address.line1"));
        address2Input.sendKeys(testData.getData("address.line2"));
        zipcodeInput.sendKeys(testData.getData("address.zipcode"));
        phoneInput.sendKeys(testData.getData("address.phone"));
    }

    public void saveShippingAddress() {
        WebElement saveShippingAddress = shippingAddress.findElement(By.className("button-submit-bg"));
        saveShippingAddress.click();
    }

}
