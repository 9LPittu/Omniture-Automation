package com.jcrew.page;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
}

