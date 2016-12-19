package com.jcrew.page;

import com.google.common.base.Predicate;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class CheckoutReview extends Checkout{
    @FindBy(id = "slidertrack")
    private WebElement slidertrack;
    @FindBy(id = "billing-details")
    private WebElement billing_details;
    @FindBy(id = "confirmOrder")
    private WebElement pageMessage;

    @FindBy(xpath = ".//*[@id='orderSummaryContainer']/div/a")
    private WebElement placeYourOrder;
    
    @FindBy(id = "shipping-details")
    private WebElement shipping_details;
    
    @FindBy(id = "gifting-details")
    private WebElement gifting_details;

    private HeaderWrap header;
    
    public CheckoutReview(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);

        wait.until(ExpectedConditions.visibilityOf(order__listing));
        header = new HeaderWrap(driver);
        wait.until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                return isDisplayed();
            }
        });
    }
    
    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");
        logger.debug("Review id: {}", getBodyAttribute("id"));

        return bodyId.equals("review");
    }

    public void placeOrder() {
        Util.waitForPageFullyLoaded(driver);

        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String env = propertyReader.getProperty("environment");

        if (!"production".equals(env)) {
            String currentUrl = driver.getCurrentUrl();
            WebElement place_my_order = slidertrack.findElement(By.className("button-submit-bg"));

            place_my_order.click();
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
        } else {
            logger.info("Trying to place an order in production, ignoring");
        }
    }

    public void fillSecurityCode() {
        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        WebElement securityCode = billing_details.findElement(By.id("securityCode"));
        securityCode.sendKeys(testDataReader.getData("card.cvv"));
    }
    
    public String getBillingAddress() {
        WebElement billingAddress = billing_details.findElement(By.className("billing-address"));
        return billingAddress.getText().trim();
    }
    
    public String getPaymentMethod(){
    	WebElement paymentMethod = billing_details.findElement(By.className("wallet-cards"));
    	return paymentMethod.getText().trim();    	
    }
    
    public String getShippingAddress() {
        WebElement shippingAddress = shipping_details.findElement(By.className("shipping-address"));
        return shippingAddress.getText().trim();
    }

    public String getShippingMethod() {
        WebElement shippingMethod = shipping_details.findElement(By.className("shipping-method"));
        return shippingMethod.getText();
    }

    public void editDetails(String group) {
        logger.debug("Editing {}", group);

        WebElement changeButton;

        wait.until(ExpectedConditions.visibilityOf(billing_details));
        wait.until(ExpectedConditions.visibilityOf(shipping_details));
        wait.until(ExpectedConditions.visibilityOf(gifting_details));
        wait.until(ExpectedConditions.visibilityOf(order__listing));

        switch (group) {
            case "billing":
                changeButton = billing_details.findElement(By.className("item-button"));
                break;
            case "shipping":
                changeButton = shipping_details.findElement(By.className("item-button"));
                break;
            case "gifting":
                changeButton = gifting_details.findElement(By.className("item-button"));
                break;
            case "order":
                changeButton = order__listing.findElement(By.className("item-button"));
                break;
            default:
                logger.error("Not recognized change button!");
                changeButton = null;
        }

        if(changeButton != null) {
            changeButton.click();
            Util.waitLoadingBar(driver);
            header = new HeaderWrap(driver);
        }
    }
    
    public void selectRandomShippingMethodOnReviewPage() {
    	WebElement shippingMethodSection =  shipping_details.findElement(By.className("shipping-method"));
    	
    	List<WebElement> shippingMethodRadioButtons = shippingMethodSection.findElements(By.xpath(".//input[@class='input-radio' and not(@checked='')]"));
    	int randomIndex = Util.randomIndex(shippingMethodRadioButtons.size());
    	
    	shippingMethodRadioButtons.get(randomIndex).click();
    	Util.waitLoadingBar(driver);
    	
    	String selectedShippingMethod = getShippingMethod();
        stateHolder.put("selectedShippingMethod", selectedShippingMethod);
    }
    
    public void enterSecurityCode(String paymentMethodName){
    	TestDataReader testDataReader = TestDataReader.getTestDataReader();
    	String paymentMethodShortName = testDataReader.getData(paymentMethodName.toLowerCase() + ".short.name");
    	String securityCodeText = testDataReader.getData(paymentMethodName.toLowerCase() + ".security.code");
    	
    	WebElement securityCode = billing_details.findElement(By.className("textbox-manager security-code-id form-textbox" +  paymentMethodShortName.toUpperCase()));
        securityCode.sendKeys(securityCodeText);
    }
}
