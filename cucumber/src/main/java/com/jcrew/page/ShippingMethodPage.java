package com.jcrew.page;

import java.util.*;

import com.jcrew.util.Util;
import com.jcrew.pojo.ShippingMethod;

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
    private WebElement firstShipMethod;

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

    @FindBy(id = "frmSelectShippingMethod")
    private WebElement shippingMethodForm;

    public ShippingMethodPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void click_continue_button() {
        Util.clickWithStaleRetry(continueButton);
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
    		e.printStackTrace();
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
    
    public List<ShippingMethod> getShippingMethods(){
        List<WebElement> methods = shippingMethodForm.findElements(By.className("form-shipmethod"));
        List<ShippingMethod> shippingMethods = new ArrayList<>();

        for (WebElement method : methods) {
            if(isShippingMethod(method)) {
                shippingMethods.add(getShippingMethod(method));
            }
        }

        return shippingMethods;
    }

    public String getSelectedShippingMethod() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(shippingMethodForm));
        WebElement selectedLabel = shippingMethodForm.findElement(By.xpath(".//label[@class='form-label radio-checked']"));
        WebElement shipMethodName = selectedLabel.findElement(By.xpath(".//span[@class='method-group']/span[contains(@class,'label')]"));
        String ShippingMethodText = shipMethodName.getText().trim();
        return ShippingMethodText;
    }

    private boolean isShippingMethod(WebElement method) {
        String id = method.getAttribute("id");
        return !"delivery-message".equalsIgnoreCase(id);
    }

    private ShippingMethod getShippingMethod(WebElement method) {
        WebElement methodElement = method.findElement(By.className("method-group"));
        String methodText = methodElement.getText().trim();

        WebElement priceElement = method.findElement(By.className("method-price"));
        String priceText = priceElement.getText().trim();

        List<WebElement> textElement = method.findElements(By.className("method-text"));
        String text = "";

        if (textElement.size() > 0) {
            text = textElement.get(0).getText();
        }

        String methodType = methodText.replace(priceText, "").replace(text, "").trim();

        return new ShippingMethod(methodType, priceText, text);
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
    
    public boolean hasGiftOption() {
        WebElement giftOption = shippingMethodForm.findElement(By.className("form-section-gift-options"));
        return giftOption.isDisplayed();
    }
    
    public String getSelectedShippingMethodName() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(shippingMethodForm));
        WebElement selectedLabel = shippingMethodForm.findElement(By.xpath(".//label[@class='form-label radio-checked']"));
        WebElement shipMethodName = selectedLabel.findElement(By.xpath(".//span[@class='method-group']/span[contains(@class,'label')]"));
        String ShippingMethodText = shipMethodName.getText().trim();
        return ShippingMethodText;
    }
    
   
}