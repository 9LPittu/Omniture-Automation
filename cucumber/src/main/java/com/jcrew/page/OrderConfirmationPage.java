package com.jcrew.page;

import com.jcrew.util.PropertyReader;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderConfirmationPage {
	
	private final WebDriver driver;
	private Logger logger = LoggerFactory.getLogger(OrderConfirmationPage.class);
    private boolean isProduction = false;

    @FindBy(id = "confirmation-number")
    private WebElement confirmationNumber;
    
    @FindBy(className="order-number")
	private WebElement orderNumber;

	@FindBy(id = "brdialog-win")
	private WebElement dialog;

	@FindBy(id = "orderSummaryContainer")
	private WebElement orderSummaryContainer;

    public OrderConfirmationPage(WebDriver driver) {
    	this.driver=driver;
        PageFactory.initElements(driver, this);
		PropertyReader propertyReader = PropertyReader.getPropertyReader();
		if(propertyReader.getProperty("environment").contains("www.jcrew.com")){
			isProduction = true;
		}
    }

    public boolean isOrderConfirmationPage() {
        if(!isProduction) {
            Util.waitWithStaleRetry(driver, confirmationNumber);
            return confirmationNumber.isDisplayed();
        } else {
            logger.debug("waving step in production environment");
            return true;
        }
    }
    
    public boolean verifyOrderNumberGenerated(){
        if(!isProduction) {
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(orderNumber));

            if (orderNumber.isDisplayed()) {
                logger.debug("Order number is generated. Order number is {}", orderNumber.getText().trim());
            } else {
                logger.debug("Order number is NOT generated.");
            }

            return orderNumber.isDisplayed();
        } else {
            logger.debug("waving step in production environment");
            return true;
        }
	 }
}