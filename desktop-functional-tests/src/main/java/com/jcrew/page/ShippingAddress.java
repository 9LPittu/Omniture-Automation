package com.jcrew.page;

import com.github.javafaker.Faker;
import com.jcrew.pojo.Country;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ShippingAddress {
	
	 private final WebDriver driver;
	 private final Logger logger = LoggerFactory.getLogger(ShippingAddress.class);
	 private final StateHolder stateHolder = StateHolder.getInstance();
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
	 
	 @FindBy(id="city")
	 private WebElement townCity;
	 
	 @FindBy(id="state")
	 private WebElement provinceStateCounty;
	 
	 @FindBy(id="dropdown-state-province")
	 private WebElement dropdownIntProvince;
	 
	 @FindBy (id = "sameBillShip")
	 private WebElement sameBillingAndShippingAddress;
	 
	 @FindBy(className = "button-submit")
	 private WebElement continueCheckout;
	 
	 public ShippingAddress(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	 }

	 public void fills_shipping_address_testdata() {
    	
    	PropertyReader propertyReader = PropertyReader.getPropertyReader();
    	String url = propertyReader.getProperty("url");
    	
    	Country country = (Country)stateHolder.get("context");
    	String countryName = country.getCountryName();
        firstNameSA.sendKeys(faker.name().firstName());
        lastNameSA.sendKeys(faker.name().lastName());
        
        address3.sendKeys(country.getCompanyName());
        address1.sendKeys(country.getAddress1());
        address2.sendKeys(country.getAddress2());
        
        if(!countryName.equals("Hong Kong")){
        	zipcode.sendKeys(country.getZipCode());
        }
        phoneNumSA.sendKeys(faker.phoneNumber().phoneNumber());
        
        if(countryName.equals("United States") ||countryName.equals("Canada") ){
        	selectCityAndState();
        }
        else{
            selectIntlCityAndState(country.getCity(),country.getState());
        }
    }
	 
	 public void selectCityAndState() {
	    	
	    	try{
	    		WebElement cityStateElement = (Util.createWebDriverWait(driver,5)).
	    				until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown-us-city-state")));

				 Select cityStateSelect = new Select(cityStateElement);
		
				 cityStateSelect.getFirstSelectedOption();
	    	}
	    	catch(Exception e){
	    		logger.info("There is no city & state dropdown displayed!!!");
	    		
	    		townCity.sendKeys("New York");
	    		try{
	    			 provinceStateCounty.sendKeys("NY");
	    		}
	    		catch(Exception e1){
	    			logger.info("Province/State/County is not displayed!!!");
	    		}
	    	}
	 }
	 
	 public void selectIntlCityAndState(String cityname,String statename) {
		 
		 	Country c = (Country)stateHolder.get("context");
	         String currentCountry = c.getCountryName();
	    	
	    	try{
	    		WebElement city = driver.findElement(By.id("city"));
	    		city.sendKeys(cityname);
	    		
	    		if(!currentCountry.equals("Germany") && !currentCountry.equals("Switzerland") && !currentCountry.equals("Singapore")){
		    		if(isIntlProvincePresent()){
		    			WebElement stateElement = (Util.createWebDriverWait(driver,5)).
		    			until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown-state-province")));
		    			Select stateSelect = new Select(stateElement);
		    			stateSelect.selectByVisibleText(statename);;
		    		}
	    		}
	    	}
	    	catch(Exception e){
	    		if(isProvinceStateCountyPresent()){
	    			provinceStateCounty.sendKeys(statename);
	    		}
	    		logger.info("There is no city & state dropdown displayed!!!");
	    	}
	    }
	 
	 
	 public boolean isIntlProvincePresent(){
	    	return dropdownIntProvince.isDisplayed();
	 }
	    
	 public boolean isProvinceStateCountyPresent(){
	    	return provinceStateCounty.isDisplayed();
	 }
	 
	 public boolean isBillingAndShippingSameAddress() {
	        return sameBillingAndShippingAddress.isSelected();
	 }
	 
	 public void presses_continue_button_on_shipping_address() {
	        continueCheckout.click();
	        Util.waitForPageFullyLoaded(driver);
	 }
}