package com.jcrew.page;

import com.github.javafaker.Faker;
import com.jcrew.util.Util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ShippingAddressPage {

    private final WebDriver driver;

    private final Faker faker = new Faker();

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

    @FindBy(className = "button-submit")
    private WebElement continueCheckout;

    @FindBy(id = "dropdown-us-city-state")
    private WebElement dropdownUsCityState;

    @FindBy (id = "sameBillShip")
    private WebElement sameBillingAndShippingAddress;
    
    @FindBy(xpath=".//*[@id='address-new']/span[2]")
    private WebElement addNewShippingAddress;
    
    @FindBy(id="firstNameAM")
    private WebElement addNewShippingAddress_FirstName;
    
    @FindBy(id="lastNameAM")
    private WebElement addNewShippingAddress_LastName;
    
    @FindBy(id="address1")
    private WebElement addNewShippingAddress_address1;
    
    @FindBy(id="address2")
    private WebElement addNewShippingAddress_address2;
    
    @FindBy(id="zipcode")
    private WebElement addNewShippingAddress_zipcode;
    
    @FindBy(id="dropdown-us-city-state")
    private WebElement cityStateDropdown;
    
    @FindBy(id="phoneNumAM")
    private WebElement addNewShippingAddress_PhoneNumber;
    
    @FindBy(xpath="//a[@id='submit-new-shipping-address' and text()='Save & Continue']")
    private WebElement addNewShippingAddress_SaveAndContinue;
    
    @FindBy(id="qasPageTitle")
    private WebElement qasVerification;
    
    @FindBy(xpath=".//a[@class='button-submit' and text()='Use Address as Entered']")
    private WebElement checkYourAddress_UseAddressAsEntered;
    
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
        Util.waitForPageFullyLoaded(driver);
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
    
    public void enterFirstNameOnShippingAddressPage(){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(firstNameSA));
    	firstNameSA.sendKeys(faker.name().firstName());
    }
    
    public void enterLastNameOnShippingAddressPage(){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(lastNameSA));
    	lastNameSA.sendKeys(faker.name().lastName());
    }
    
    public void enterAddressLine1OnShippingAddressPage(String addressLine1){
    	address1.sendKeys(addressLine1);
    }
    
    public void enterAddressLine2OnShippingAddressPage(String addressLine2){
    	address2.sendKeys(addressLine2);
    }
    
    public void enterZipCodeOnShippingAddressPage(String zipCode){
    	zipcode.sendKeys(zipCode);
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(cityStateDropdown));
    }
    
    public void enterPhoneNumberOnShippingAddressPage(){
    	phoneNumSA.sendKeys(faker.phoneNumber().phoneNumber());
    }
    
    public void clickAddNewShippingAddress(){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(addNewShippingAddress));
    	addNewShippingAddress.click();
    }
    
    public void enterFirstNameOnNewShippingAddressForm(){
    	addNewShippingAddress_FirstName.sendKeys(faker.name().firstName());
    }
    
    public void enterLastNameOnNewShippingAddressForm(){
    	addNewShippingAddress_LastName.sendKeys(faker.name().lastName());
    }
    
    public void enterAddressLine1OnAddNewShippingAddressForm(String address1){
    	addNewShippingAddress_address1.sendKeys(address1);
    }
    
    public void enterAddressLine2OnAddNewShippingAddressForm(String address2){
    	addNewShippingAddress_address2.sendKeys(address2);
    }
    
    public void enterZipCodeOnAddNewShippingAddressForm(String zipCode){
    	addNewShippingAddress_zipcode.sendKeys(zipCode);
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(dropdownUsCityState));
    }
    
    public void enterPhoneNumberOnAddNewShippingAddressForm(){
    	addNewShippingAddress_PhoneNumber.sendKeys(faker.phoneNumber().phoneNumber());
    }
    
    public void clickSaveAndContinueInAddNewShippingAddressForm(){    	
    	addNewShippingAddress_SaveAndContinue.click();
    }
    
    public boolean isQASVerificationDisplayed(){
        Util.waitWithStaleRetry(driver,qasVerification);
    	return qasVerification.isDisplayed();
    }
    
    public void clickUseAddressAsEnteredButton(){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(checkYourAddress_UseAddressAsEntered));
    	Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(checkYourAddress_UseAddressAsEntered));
    	Util.clickWithStaleRetry(checkYourAddress_UseAddressAsEntered);
    }
}