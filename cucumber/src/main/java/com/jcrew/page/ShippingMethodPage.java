package com.jcrew.page;

import java.util.*;

import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
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
        Util.clickWithStaleRetry(continueButton);
    }

    public boolean isEconomyUps() {
    	try{
    		return economyUps.isSelected();
    	}
    	catch(Exception e){
    		return false;
    	}
    }

    public boolean isNoGifts() {
        return noGifts.isSelected();
    }


    public boolean isShippingMethodPage() {
    	try{
	        Util.waitForPageFullyLoaded(driver);
	        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(shippingMethodContainer));
	        return true;
    	}
    	catch(Exception e){
    		return false;
    	}
    }

    public boolean isPageLoaded() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(footerRowBottom));
        return true;
    }
    
    public void selectShippingMethod(){
    	List<WebElement> shippingMethodsRadioButtons = driver.findElements(By.name("shippingMethod"));
        int randomShippingIndex = Util.randomIndex(shippingMethodsRadioButtons.size());
        logger.debug("Selecting shipping method index: {}", randomShippingIndex);
    	WebElement shippingMethod = shippingMethodsRadioButtons.get(randomShippingIndex);
    	shippingMethod.click();
    }
    
    public boolean isShippingMethodsDisplayedCorrectly(){
    	boolean expectedMethodCopy = true;
    	String expectedShippingMethods = null;
    	TestDataReader testDataReader = TestDataReader.getTestDataReader();

    	//String country = countryName.getText().trim(); <--- commented until we have international enabled
        String country = "united states";
    	logger.debug("Country: {}", country);

    	switch(country.toLowerCase()){
    		case "united states":
    			expectedShippingMethods = testDataReader.getData("USA_Shipping_Methods");
    			break;
            default:
                expectedShippingMethods = testDataReader.getData("USA_Shipping_Methods");
    	}

        if(expectedShippingMethods != null) {
            //Add all expected shipping methods to List
            String[] arrShippingMethods = expectedShippingMethods.split(";");

            //Add all actual shipping methods from application to List
            for(String method:arrShippingMethods){
                logger.debug("Shipping method: {}",method);
                WebElement methodElement = shippingMethodContainer.findElement(By.id(method));
                String methodText = methodElement.getText();
                logger.debug("Copy: {} - Expected: {}", methodText, testDataReader.getData(method));
                expectedMethodCopy = expectedMethodCopy & methodText.contains(testDataReader.getData(method));
            }

            return expectedMethodCopy;
        }

        logger.debug("expectedShippingMethods is null");
        return false;
    }
}