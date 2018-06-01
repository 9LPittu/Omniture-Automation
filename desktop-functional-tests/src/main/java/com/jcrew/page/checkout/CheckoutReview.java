package com.jcrew.page.checkout;

import com.google.common.base.Function;
import com.jcrew.page.header.HeaderWrap;
import com.jcrew.utils.E2EPropertyReader;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

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
    @FindBy(xpath = "//ul/li/label/span[@class='wallet-brand wallet-line notranslate']")
    private static List<WebElement> cardNames;
    @FindBy(xpath = ".//*[@id='orderSummaryContainer']/div/a")
    private WebElement placeYourOrder;
    
    @FindBy(id = "shipping-details")
    private WebElement shipping_details;
    
    @FindBy(id = "gifting-details")
    private WebElement gifting_details;
    
    //@FindBy(className = "wallet-brand")
    
    @FindBy(xpath ="//*[@class='wallet-brand wallet-line']")
    private WebElement cardName;
    @FindBy(xpath = "//ul/li/a[contains(text(),'Billing')]")
	public static WebElement billingPage;
    @FindBy(xpath = "//a[contains(text(),'pay with two cards?')]")
	public static WebElement payWithTwoCards;
    @FindBy(xpath = "//a[contains(text(),' Continue')]")
	public static WebElement continueButton;
    
    
    @SuppressWarnings("unused")
	private HeaderWrap header;
  
    public CheckoutReview(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);

        wait.until(ExpectedConditions.visibilityOf(orderListing));
        header = new HeaderWrap(driver);
        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return isDisplayed();
            }
        });
    }
    public static void clickOnBilling() {
    	billingPage.click();
    	List<String> cardNameList = new ArrayList<String>();
		for(int i=0;i<cardNames.size();i++) {
			cardNameList.add(cardNames.get(i).getText());
		}
		for(int i=0;i<cardNameList.size();i++) {
			System.out.println("========="+cardNameList.get(i));
		}
		payWithTwoCards.click();
		continueButton.click();
    }
    public void splitPayment(String paymentMethod1 , String paymentMethod2) throws InterruptedException{
    	E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
    	String cardShortName1 = e2ePropertyReader.getProperty(paymentMethod1.toLowerCase() + ".short.name"); 
    	String cardShortName2 = e2ePropertyReader.getProperty(paymentMethod2.toLowerCase() + ".short.name");
    	
    	String cardNumber1 = e2ePropertyReader.getProperty(paymentMethod1.toLowerCase() + ".card.number");
    	String cardNumber2 = e2ePropertyReader.getProperty(paymentMethod2.toLowerCase() + ".card.number");
    	
    	String lastFourDigitsOfCardNum1 = cardNumber1.substring(cardNumber1.length() - 4);
    	String lastFourDigitsOfCardNum2 = cardNumber2.substring(cardNumber2.length() - 4);
    	
    	WebElement splitPaymentForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-multitender")));
    	
    	int numberofCardsAvailable = /*stateHolder.get("numberofCardsAvailable")*/3;
    	String valueToBeSelected;
    	Select select;
    	List<WebElement> paymentDropdowns = splitPaymentForm.findElements(By.xpath(".//select[contains(@id, 'distributionCard')]"));
    	Thread.sleep(10000);
    	
    	if(numberofCardsAvailable > 2){    		
    		valueToBeSelected = cardShortName1.toUpperCase() + " ending in " + lastFourDigitsOfCardNum1;
    		select = new Select(paymentDropdowns.get(0));
    		select.selectByVisibleText(valueToBeSelected);
    	}
    	
    	valueToBeSelected = cardShortName2.toUpperCase() + " ending in " + lastFourDigitsOfCardNum2;
		select = new Select(paymentDropdowns.get(1));
		select.selectByVisibleText(valueToBeSelected);
    	
    	String orderTotal = /*stateHolder.get("total")*/"$100.76";
    	System.out.println("============"+orderTotal);
    	Double dblOrderTotal = Double.parseDouble(orderTotal.replaceAll("[^\\d.]*", ""));
    	dblOrderTotal = dblOrderTotal/2;
    	
    	WebElement secondAmountElement = splitPaymentForm.findElement(By.id("secondAmount"));
    	secondAmountElement.clear();
    	secondAmountElement.sendKeys(dblOrderTotal.toString());
    	secondAmountElement.sendKeys(Keys.TAB);
    	
    	splitPaymentForm.findElement(By.id("multiTenderDistributionSubmit")).click();    	
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("multiTenderDistributionSubmit")));
    	System.out.println("split amount entered");
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
            List<WebElement> place_my_order_elements;
            
            try{
            	place_my_order_elements = slidertrack.findElements(By.id("button-submitorder"));
            	Util.createWebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(place_my_order_elements.get(0)));
            }catch(TimeoutException toe){
            	place_my_order_elements = slidertrack.findElements(By.className("button-submit-bg"));
            }
            
            place_my_order_elements.get(0).click();
            
           wait.until(ExpectedConditions.invisibilityOfAllElements(place_my_order_elements));
        } else {
            logger.info("Trying to place an order in production, ignoring");
        }
    }

    public void fillSecurityCode() {
    	TestDataReader testDataReader = TestDataReader.getTestDataReader();
    	if(cardName.getText().equalsIgnoreCase("Mastercard")){
    		WebElement securityCode = billing_details.findElement(By.id("securityCode"));
    		securityCode.sendKeys(testDataReader.getData("card.cvv"));
    	}
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
        
        if(!stateHolder.hasKey("isShippingDisabled")){
        	wait.until(ExpectedConditions.visibilityOf(shipping_details));
        }
        
        wait.until(ExpectedConditions.visibilityOf(orderListing));

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
                changeButton = orderListing.findElement(By.className("item-button"));
                break;
            default:
                logger.error("Not recognized change button!");
                changeButton = null;
        }

        if(changeButton != null) {
            Util.scrollPage(driver, Util.BOTTOM);
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
    
    public void enterSecurityCode(){
    	List<WebElement> securityCode = billing_details.findElements(By.id("securityCode"));
    	
    	if(securityCode.size()==0)
    		return;
    	
    	String className = securityCode.get(0).getAttribute("class");
    	String[] arrClassName = className.split(" "); 
    	String cardType = arrClassName[arrClassName.length - 1];
    	String paymentMethodName = "";
    	/*String paymentMethodName1 = "";
    	String paymentMethodName2 = "";
    	if(cardType.toUpperCase().equals("VISA")) {
    		paymentMethodName1 = "visa";
    		paymentMethodName2 = "master";
    		E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
    		String securityCodeText1 = e2ePropertyReader.getProperty(paymentMethodName1.toLowerCase() + ".security.code");
    		securityCode.get(0).sendKeys(securityCodeText1);
        	String securityCodeText2 = e2ePropertyReader.getProperty(paymentMethodName2.toLowerCase() + ".security.code");
        	securityCode.get(1).sendKeys(securityCodeText2);
    	}*/
    	switch(cardType.toUpperCase()){
    		case "VISA":
    			paymentMethodName = "visa";
    			break;
    		case "MC":
    			paymentMethodName = "master";
    			break;
    		case "AMEX":
    			paymentMethodName = "amex";
    			break;
    		case "DISC":
    			paymentMethodName = "discover";
    			break;
    		case "JCB":
    			paymentMethodName = "jcb";
    			break;
    	}
    	
    	E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
    	String securityCodeText = e2ePropertyReader.getProperty(paymentMethodName.toLowerCase() + ".security.code");
    	securityCode.get(0).sendKeys(securityCodeText);
    }
    
    public void enterSecurityCode(String paymentMethodName){
    	if(paymentMethodName.equalsIgnoreCase("JCC"))
    		return;
    	if(paymentMethodName.equalsIgnoreCase("Gift Card"))
    		return;
    	
    	E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
    	String paymentMethodShortName = e2ePropertyReader.getProperty(paymentMethodName.toLowerCase() + ".short.name");
    	String securityCodeText = e2ePropertyReader.getProperty(paymentMethodName.toLowerCase() + ".security.code");
    	
    	WebElement securityCode = billing_details.findElement(By.xpath(".//input[@class='textbox-manager security-code-id form-textbox " +  paymentMethodShortName.toUpperCase() + "']"));
        securityCode.sendKeys(securityCodeText);
    }
}
