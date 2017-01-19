package com.jcrew.page;

import com.jcrew.pojo.Address;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

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
    @FindBy(id = "order-listing")
    private WebElement order_listing;
    @FindBy(id = "frmSelectShippingAddress")
    private WebElement frmSelectShippingAddress;
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
    
    @FindBy(name="frm_shipping")
    private WebElement addShippingAddressForm;


    public CheckoutShippingAdd(WebDriver driver) {
        super(driver);
        
        isDisplayed();
    }

    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("shipping");
    }

    private void fillFormData(Address address) {
        User user = User.getFakeUser();

        firstName.sendKeys(user.getFirstName().replaceAll("'", ""));
        lastName.sendKeys(user.getLastName().replaceAll("'", ""));
        address1.sendKeys(address.getLine1());
        address2.sendKeys(address.getLine2());
        phoneNum.sendKeys(address.getPhone());

        Country country = (Country) stateHolder.get("context");
        String countryCode = country.getCountry().toLowerCase();

        switch (countryCode) {
            case "us":
            case "ca":
                zipcode.clear();
                zipcode.sendKeys(address.getZipcode());

                wait.until(ExpectedConditions.visibilityOf(us_city_state));
                break;

            case "au":
                city.sendKeys(address.getCity());

                Select select = new Select(state_province);
                select.selectByVisibleText(address.getState());

                zipcode.clear();
                zipcode.sendKeys(address.getZipcode());
                break;

            case "jp":
            case "uk":
                city.sendKeys(address.getCity());

                state.sendKeys(address.getState());

                zipcode.clear();
                zipcode.sendKeys(address.getZipcode());
                break;

            case "hk":
                city.sendKeys(address.getCity());

                state.sendKeys(address.getState());
                break;

            default:
                city.sendKeys(address.getCity());

                zipcode.clear();
                zipcode.sendKeys(address.getZipcode());
                break;
        }
    }

    public void fillQASShippingData() {
        Address address = new Address("QAS");
        fillFormData(address);
    }

    public void fillShippingData() {
        Address address = new Address();
        fillFormData(address);
    }
    
    public void fillShippingData(Address address) {
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
    
    public void saveShippingAddress() {
        String currentUrl = driver.getCurrentUrl();
        WebElement saveShippingAddress = shippingForm.findElement(By.className("button-submit-bg"));
        saveShippingAddress.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
    }

    public void continueWithDefaultAddress() {
        String url = driver.getCurrentUrl();
        WebElement continueWithDefault = frmSelectShippingAddress.findElement(By.className("button-submit-bg"));
        continueWithDefault.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
    }
    
    public void sameBillingAddressCheckbox(String actionName){
    	if(actionName.equalsIgnoreCase("UNCHECK")){
    		WebElement sameBillingAddressCheckboxElement = shippingForm.findElement(By.id("sameBillShip"));
    		sameBillingAddressCheckboxElement.click();
    	}
    }
    
    public void selectMultipleAddressesRadioButton(){
    	selectMultipleShippingAddressRadioButton(addShippingAddressForm);
    }

}
