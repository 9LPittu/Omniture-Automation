package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderConfirmationPage {
	
	 private final WebDriver driver;
	 private Logger logger = LoggerFactory.getLogger(OrderConfirmationPage.class);

    @FindBy(id = "confirmation-number")
    private WebElement confirmationNumber;
    
    @FindBy(className="order-number")
	private WebElement orderNumber;

    public OrderConfirmationPage(WebDriver driver) {
    	this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isOrderConfirmationPage() {
        return confirmationNumber.isDisplayed();
    }
    
    public boolean verifyOrderNumberGenerated(){
		 new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOf(orderNumber));
		 
		 if(orderNumber.isDisplayed()){
			 logger.debug("Order number is generated. Order number is {}", orderNumber.getText().trim());
		 }
		 else{
			 logger.debug("Order number is generated. Order number is {}");
		 }
		 
		 return orderNumber.isDisplayed();
	 }

}
