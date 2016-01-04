package com.jcrew.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShippingMethodPage {

    private final WebDriver driver;

    private final Logger logger = LoggerFactory.getLogger(ShippingMethodPage.class);

    @FindBy(id = "method0")
    private WebElement economyUps;

    @FindBy(id = "noGifts")
    private WebElement noGifts;

    @FindBy(className = "button-submit")
    private WebElement continueButton;

    @FindBy(className = "shippingmethod-container")
    private WebElement shippingMethodContainer;

    @FindBy(className = "footer__row--bottom")
    private WebElement footerRowBottom;
    
    @FindBys({@FindBy(name="shippingMethod")})
    private WebElement shippingMethodsRadioButtons;
    
    @FindBy(className="footer__country-context__country")
    private WebElement countryName;

    public ShippingMethodPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void click_continue_button() {
        try {
            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(continueButton));
            continueButton.click();
        } catch(StaleElementReferenceException e) {
            click_continue_button();
        }
    }

    public boolean isEconomyUps() {
        return economyUps.isSelected();
    }

    public boolean isNoGifts() {
        return noGifts.isSelected();
    }


    public boolean isShippingMethodPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(shippingMethodContainer));
        return true;
    }

    public boolean isPageLoaded() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(footerRowBottom));
        return true;
    }
    
    public void selectShippingMethod(){
    	List<WebElement> shippingMethodsRadioButtons = driver.findElements(By.name("shippingMethod"));
    	WebElement shippingMethod = shippingMethodsRadioButtons.get(Util.randomIndex(shippingMethodsRadioButtons.size()));
    	shippingMethod.click();
    }
    
    public boolean isShippingMethodsDisplayedCorrectly(){
    	
    	String expectedShippingMethods = null;
    	TestDataReader testDataReader = new TestDataReader();
    	
    	String country = countryName.getText().trim();
    	
    	switch(country.toLowerCase()){
    		case "united states":
    			expectedShippingMethods = testDataReader.getData("USA_Shipping_Methods");
    			break;
    	}
    	
    	//Add all expected shipping methods to List
    	String[] arrShippingMethods=expectedShippingMethods.split(";");
    	List<String> lstExpectedShippingMethods = new ArrayList<String>();
    	for(String expectedShippingMethod:arrShippingMethods){
    		lstExpectedShippingMethods.add(expectedShippingMethod.toLowerCase());
    	}
    	
    	//Add all actual shipping methods from application to List
    	List<WebElement> actualShippingMethods = driver.findElements(By.className("method-group"));
    	List<String> lstActualShippingMethods = new ArrayList<String>();
    	for(WebElement actualShippingMethod:actualShippingMethods){
    		lstActualShippingMethods.add(actualShippingMethod.getText().trim().toLowerCase().split("\\r?\\n")[0]);
    	}
    	
    	//Sort the lists
    	Collections.sort(lstExpectedShippingMethods);
    	Collections.sort(lstActualShippingMethods);
    	
    	return lstActualShippingMethods.equals(lstExpectedShippingMethods);
    }
}