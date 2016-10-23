package com.jcrew.page;

import com.jcrew.pojo.ShippingMethod;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 5/6/16.
 */
public class CheckoutShippingOptions extends Checkout {

    @FindBy(id = "frmSelectShippingMethod")
    private WebElement shippingMethodForm;
    
    @FindBy(id = "method0")
    private WebElement firstShipMethod;

    public CheckoutShippingOptions(WebDriver driver) {
        super(driver);

        wait.until(ExpectedConditions.visibilityOf(shippingMethodForm));
    }

    @Override
    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("method");
    }

    public List<ShippingMethod> getShippingMethods() {
        List<WebElement> methods = shippingMethodForm.findElements(By.className("form-shipmethod"));
        List<ShippingMethod> shippingMethods = new ArrayList<>();

        for (WebElement method : methods) {
            shippingMethods.add(getShippingMethod(method));
        }

        return shippingMethods;
    }

    public ShippingMethod getSelectedShippingMethod() {
        WebElement selectedLabel = shippingMethodForm.findElement(By.xpath(".//label[@class='form-label radio-checked']"));
        WebElement selectedMethod = selectedLabel.findElement(By.xpath(".//ancestor::div[contains(@class,'form-shipmethod')]"));

        return getShippingMethod(selectedMethod);
    }
    
    public String getSelectedShippingMethodName() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(shippingMethodForm));
        WebElement selectedLabel = shippingMethodForm.findElement(By.xpath(".//label[@class='form-label radio-checked']"));
        WebElement shipMethodName = selectedLabel.findElement(By.xpath(".//span[@class='method-group']/span[contains(@class,'label')]"));
        String ShippingMethodText = shipMethodName.getText().trim();
        return ShippingMethodText;
    }

    private ShippingMethod getShippingMethod(WebElement method) {
        wait.until(ExpectedConditions.visibilityOf(method));
        String priceText = null;
        String text=null;
        String methodType=null;
        
        boolean retry = true;
        int attempts = 0;
        do {
        	try {
		        WebElement methodElement = method.findElement(By.className("method-group"));
		        String methodText = methodElement.getText().trim();
		
		        WebElement priceElement = method.findElement(By.className("method-price"));
		        priceText = priceElement.getText().trim();
		
		        List<WebElement> textElement = method.findElements(By.className("method-text"));
		        text = "";
		
		        if (textElement.size() > 0) {
		            text = textElement.get(0).getText();
		        }
		
		        methodType = methodText.replace(priceText, "").replace(text, "").trim().toLowerCase();
		        
		        retry = false;
		        
        	} catch (NoSuchElementException e)  {
        		attempts ++;
        		Util.wait(1000);
        	}
        
        } while (retry & attempts < 5);
        
        return new ShippingMethod(methodType, priceText, text);
    }

    public void selectShippingMethod() {
        List<WebElement> methods = shippingMethodForm.findElements(By.className("form-shipmethod"));
        int random = Util.randomIndex(methods.size());

        WebElement method = methods.get(random);
        WebElement label = method.findElement(By.tagName("label"));
        String labelClass = label.getAttribute("class");

        logger.debug("Selected shipping method: {}", label.getText());

        if (!labelClass.contains("radio-checked")) {
        	WebElement radio = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("input-radio")));
        	radio.click();
        } else {
            logger.debug("Selected method is already selected");
        }

        ShippingMethod shippingMethod = getShippingMethod(method);

        stateHolder.put("selectedShippingMethod", shippingMethod.getMethod());
    }

    public void continueCheckout() {
        nextStep(shippingMethodForm);
    }

    public boolean hasGiftOption() {
        WebElement giftOption = shippingMethodForm.findElement(By.className("form-section-gift-options"));

        return giftOption.isDisplayed();
    }

    public void addGiftOption() {
        WebElement add = shippingMethodForm.findElement(By.id("includesGifts"));
        add.click();
    }

    public void addGiftMessage(String message) {
        WebElement textArea = shippingMethodForm.findElement(By.id("gift-receipt-msg0"));
        textArea.sendKeys(message);
    }
    
    public String getGiftReceiptInfoMessage(){
    	WebElement giftReceiptInfoMessage = shippingMethodForm.findElement(By.className("gift-receipt-info"));
    	return giftReceiptInfoMessage.getText().trim();
    }
    
    public boolean isFirstShippingMethod() {
    	try{
    		return firstShipMethod.isSelected();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }
}
