package com.jcrew.page;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

/**
 * Created by ravi kumar on 04/05/17.
 */
public class CheckoutPromoCode extends Checkout {

    @FindBy (id = "promoCodeContainer")
    private WebElement promoContainer;

    public CheckoutPromoCode(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(promoContainer));
    }

    @Override
    public boolean isDisplayed() {
        return promoContainer.isDisplayed();
    }
    
    public String getPromoCodeAppliedState(String promoCodeText){
        WebElement appliedPromoCodeElement = promoContainer.findElement(By.xpath(".//span[contains(@class, 'module-name') and contains(text(), '" + promoCodeText.toUpperCase() + "')]"));
        String className = appliedPromoCodeElement.getAttribute("class");
        
        if(className.contains("inactive"))
        	return "inactive";
        else
        	return "active";
    }
    
    public Double getPromoDiscountedAmount(Double orderSubtotal, String promoCode){
    	
    	Double promoDiscountedAmount = 0.0;
    	Double percentage;
    	Double discount;
    	Double thresholdValue1;
    	Double thresholdValue2;
    	Double thresholdValue3;
    	String freeShippingRequired;
    	Double  freeShippingThresholdVal;
    	
    	TestDataReader testDataReader = TestDataReader.getTestDataReader(); 
    	switch(promoCode){
    		case "stack10p":
    		case "test-10p":
    			percentage = Double.valueOf(testDataReader.getData(promoCode + ".percentage"));
    			promoDiscountedAmount = orderSubtotal * (percentage/100);
    			break;
    	}
    	
    	return promoDiscountedAmount;
    }
    
    public void removePromo(){
    	getPromoRemoveElement().click();
    	Util.waitForPageFullyLoaded(driver);
    	Util.waitLoadingBar(driver);
    }
}