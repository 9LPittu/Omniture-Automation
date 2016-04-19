package com.jcrew.page;

import com.jcrew.pojo.Address;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.User;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
    private final StateHolder stateHolder = StateHolder.getInstance();

    @FindBy(id = "shipping-address")
    private WebElement shippingAddress;
    @FindBy(id = "order-listing")
    private WebElement order_listing;
    @FindBy(id = "frmSelectShippingAddress")
    private WebElement frmSelectShippingAddress;
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
    @FindBy(id = "state")
    private WebElement state;
    @FindBy(id = "dropdown-state-province")
    private WebElement state_province;
    @FindBy(id = "shoppingAddressValidate")
    private WebElement addresValidate;

    public Shipping(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(order_listing));
    }

    public void fillGuestData() {
        Country country = (Country) stateHolder.get("context");
        Address address = new Address(country.getCountry());
        User user = User.getNewFakeUser();

        firstNameInput.sendKeys(user.getFirstName());
        lastNameInput.sendKeys(user.getLastName());

        addressInput.sendKeys(address.getLine1());
        address2Input.sendKeys(address.getLine2());
        phoneInput.sendKeys(address.getPhone());

        switch (country.getCountry()) {
            case "uk":
            case "de":
            case "sg":
            case "ch":
                city.sendKeys(address.getCity());
                break;
            case "jp":
            case "hk":
                city.sendKeys(address.getCity());
                state.sendKeys(address.getState());
                break;
            case "au":
                city.sendKeys(address.getCity());
                Select state_dropdown = new Select(state_province);
                state_dropdown.selectByVisibleText(address.getState());
                break;
        }

        if (!"HK".equalsIgnoreCase(country.getCountry())) {
            zipcodeInput.sendKeys(address.getZipcode());
        }
    }

    public void saveShippingAddress() {
        WebElement saveShippingAddress = shippingAddress.findElement(By.className("button-submit-bg"));
        saveShippingAddress.click();
    }

    public void continueWithDefaultAddress() {
        WebElement continueWithDefault = frmSelectShippingAddress.findElement(By.className("button-submit-bg"));
        continueWithDefault.click();
    }

}
