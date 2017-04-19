package com.jcrew.page.checkout;

import com.jcrew.pojo.Address;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.User;
import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutShippingAdd extends Checkout {

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
    
    private WebElement shippingAddressForm;
    private WebElement addNewShippingAddressForm;

    public CheckoutShippingAdd(WebDriver driver) {
        super(driver);        
        isDisplayed();        
    }
    
    private WebElement getShippingAddressForm(){
    	Util.waitForPageFullyLoaded(driver);
    	Util.waitLoadingBar(driver);
    	shippingAddressForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/article/descendant::form")));
    	return shippingAddressForm;
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

        Country country = (Country) stateHolder.get("context");
        String countryCode = country.getCountry().toLowerCase();

        switch (countryCode) {
            case "us":
            case "ca":
                zipcode.clear();
                zipcode.sendKeys(address.getZipcode());
                
                try{
                	Util.createWebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(us_city_state));
                }catch(TimeoutException toe){	
                	city.sendKeys(address.getCity());
                	Select select = new Select(state_province);
                	select.selectByVisibleText(address.getState());
                }
                
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
        nextStep(getShippingAddressForm());
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
        WebElement saveShippingAddress = getShippingAddressForm().findElement(By.className("button-submit-bg"));
        saveShippingAddress.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
    }

    public void continueWithDefaultAddress() {
        String url = driver.getCurrentUrl();
        WebElement continueWithDefault = getShippingAddressForm().findElement(By.className("button-submit-bg"));
        continueWithDefault.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
    }
    
    public void selectMultipleAddressesRadioButton(){
    	selectMultipleShippingAddressRadioButton(getShippingAddressForm());
    }
    
    public void clickAddNewShippingAddress(){
    	WebElement addNewShippingAddress = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@id='address-new']")));
    	addNewShippingAddress.click();
    	
    	addNewShippingAddressForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("frm_new_shipping_address")));
    }
    
    public void addNewShippingAddress(String isQASAddress, Address address){
    	User user = User.getNewFakeUser();
    	
    	WebElement newShippingAddress_FirstName = addNewShippingAddressForm.findElement(By.id("firstNameAM"));
    	newShippingAddress_FirstName.sendKeys(user.getFirstName());
    	
    	WebElement newShippingAddress_LastName = addNewShippingAddressForm.findElement(By.id("lastNameAM"));
    	newShippingAddress_LastName.sendKeys(user.getLastName());
    	
    	WebElement newShippingAddress_Address1 = addNewShippingAddressForm.findElement(By.id("address1"));
    	newShippingAddress_Address1.sendKeys(address.getLine1());
        
    	WebElement newShippingAddress_Address2 = addNewShippingAddressForm.findElement(By.id("address2"));
    	newShippingAddress_Address2.sendKeys(address.getLine2());
        
    	WebElement newShippingAddress_PhoneNum = addNewShippingAddressForm.findElement(By.id("phoneNumAM"));
    	newShippingAddress_PhoneNum.sendKeys(address.getPhone());
    	
    	WebElement newShippingAddress_ZipCode = addNewShippingAddressForm.findElement(By.id("zipcode"));
    	newShippingAddress_ZipCode.clear();
    	newShippingAddress_ZipCode.sendKeys(address.getZipcode());

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown-us-city-state")));
        
        WebElement saveButton = addNewShippingAddressForm.findElement(By.id("submit-new-shipping-address"));
        saveButton.click();
        
        if(isQASAddress.equalsIgnoreCase("YES")){
        	handleQAS();
        }else{
        	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("submit-new-shipping-address")));
        }
    }
}
