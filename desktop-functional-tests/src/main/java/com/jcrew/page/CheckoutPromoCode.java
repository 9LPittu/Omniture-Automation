package com.jcrew.page;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.jcrew.utils.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.jcrew.utils.TestDataReader;

import java.util.List;

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
        WebElement appliedPromoCodeElement = promoContainer.findElement(
                By.xpath(".//span[contains(@class, 'module-name') and contains(text(), '"
                        + promoCodeText.toUpperCase() + "')]"));
        String className = appliedPromoCodeElement.getAttribute("class");
        
        if(className.contains("inactive"))
        	return "inactive";
        else
        	return "active";
    }
    
    public Double getPromoDiscountedAmount(Double orderSubtotal, String promoCode){
    	
    	Double promoDiscountedAmount = 0.0;
    	Double percentage;
    	Double freeShippingThresholdAmt;
    	
    	DecimalFormat df = new DecimalFormat(".###");
    	df.setRoundingMode(RoundingMode.HALF_DOWN);
    	
    	TestDataReader testDataReader = TestDataReader.getTestDataReader();
    	switch(promoCode){
    		case "stack10p":
    		case "test-10p":
    			percentage = Double.valueOf(testDataReader.getData(promoCode + ".percentage"));
    			promoDiscountedAmount = Double.valueOf(df.format(orderSubtotal * (percentage/100)));
    			break;
    		case "stack-fs-50":
    			freeShippingThresholdAmt = Double.valueOf(testDataReader.getData(promoCode + ".percentage"));
    			if(orderSubtotal > freeShippingThresholdAmt){
    				stateHolder.put("shippingCost", "0");
    			}
    			break;
    		case "test-15pf-fs":
    			percentage = Double.valueOf(testDataReader.getData(promoCode + ".percentage"));
    			promoDiscountedAmount = Double.valueOf(df.format(orderSubtotal * (percentage/100)));
    			stateHolder.put("shippingCost", "0");
    			break;
    		default:
    			throw new WebDriverException(promoCode + " is not recognized!");
    	}
    	
    	return promoDiscountedAmount;
    }
    
    public void removePromo(){
    	getPromoRemoveElement().click();
    	Util.waitForPageFullyLoaded(driver);
    	Util.waitLoadingBar(driver);
    }

    private WebElement getPromoCodeField() {
        List<WebElement> fieldList = promoContainer.findElements(By.id("promotionCode1"));
        WebElement field = null;

        if (fieldList.size() > 0) {
            field = fieldList.get(0);
        }

        return field;
    }

    public void addPromoCode(String code) {
        WebElement promoHeader = promoContainer.findElement(By.id("summary-promo-header"));
        Util.scrollToElement(driver, promoHeader);
        promoHeader.click();

        WebElement promoCodeField = getPromoCodeField();

        if (promoCodeField != null ) {
            promoCodeField.clear();
            promoCodeField.sendKeys(code);
            stateHolder.put("promocode", code);

            WebElement apply = promoContainer.findElement(By.id("promoApply"));
            Util.scrollPage(driver, "down");
            apply.click();

            wait.until(ExpectedConditions.stalenessOf(promoCodeField));
            wait.until(ExpectedConditions.visibilityOf(promoContainer));

        } else {
            throw new WebDriverException("Promo Code field is not displayed, not able to apply promo");
        }
    }

    public String getPromoCodeMessage() {
        wait.until(ExpectedConditions.visibilityOf(promoContainer));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("promoCodeMessage")));

        return message.getText();
    }

    public String getPromoName() {
        WebElement message = promoContainer.findElement(By.className("module-name"));

        return message.getText();
    }

    public String getPromoDetails() {
        WebElement message = wait.until(ExpectedConditions.visibilityOf(promoContainer.findElement(By.className("module-details-last"))));
        return message.getText();
    }

    public WebElement getPromoRemoveElement(){
        return promoContainer.findElement(By.className("item-remove"));
    }

    public boolean isPromoCodeApplied(String promoCodeText){
        try{
            WebElement appliedPromoCodeElement = promoContainer.findElement(
                    By.xpath(".//span[@class='module-name' and contains(text(), '" + promoCodeText.toUpperCase() + "')]"));
            return appliedPromoCodeElement.isDisplayed();
        }
        catch(NoSuchElementException nsee){
            return false;
        }
    }

    public int getAppliedPromoCodesCount(){
        try{
            List<WebElement> appliedPromoCodeElements = promoContainer.findElements(By.xpath(".//span[@class='module-name']"));
            return appliedPromoCodeElements.size();
        }
        catch(NoSuchElementException nsee){
            return -1;
        }
    }

    public boolean isPromoTextBoxDisplayed(){
        WebElement promoCodeField = getPromoCodeField();
        boolean isDisplayed = false;

        if (promoCodeField != null)
            isDisplayed = promoCodeField.isDisplayed();

        return isDisplayed;
    }
}