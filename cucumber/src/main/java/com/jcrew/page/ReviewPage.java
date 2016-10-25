package com.jcrew.page;

import java.util.List;

import com.jcrew.pojo.Product;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReviewPage extends Checkout{

	public final StateHolder stateHolder = StateHolder.getInstance();
	private final Logger logger = LoggerFactory.getLogger(ReviewPage.class);
    private boolean isProduction = false;
	
    private final WebDriver driver;
    @FindBy(xpath = ".//div[@id='orderSummaryContainer']/div/a")
    private WebElement placeYourOrder;

    @FindBy(id = "errors")
    private WebElement errorsModal;

    @FindBy(id = "billing-details")
    private WebElement billingSection;

    @FindBy(id = "securityCode")
    private WebElement securityCode;

    @FindBy(id = "shipping-details")
    private WebElement shippingSection;
    
    @FindBy(css = "div.billing-address.notranslate")
    private WebElement billingAddress;
    
    @FindBy(css = "div.shipping-address.notranslate")
    private WebElement shippingAddress;
    
    @FindBy(className="item-link-submit")
    private WebElement placeYourOrderButton;
    
    @FindBy(xpath=".//*[@id='billing-details']/h2/a")
    private WebElement reviewPage_BillingDetailsSection_ChangeButton;
    
    @FindBy(xpath=".//*[@id='shipping-details']/h2/a")
    private WebElement reviewPage_ShippingDetailsSection_ChangeButton;
    
    @FindBy(id = "gifting-details")
    private WebElement gifting_details;

    public ReviewPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        if(propertyReader.getProperty("url").contains("www.jcrew.com")){
            isProduction = true;
        }
    }
    
    public boolean isDisplayed() {
    	Util.wait(1000);;
        String bodyId = getBodyAttribute("id");
        logger.debug("Review id: {}", getBodyAttribute("id"));

        return bodyId.equals("review");
    }

    public void user_places_its_order() {
        Util.waitForPageFullyLoaded(driver);
        Util.waitLoadingBar(driver);
        if(!isProduction) {
        	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(placeYourOrder));
            Util.createWebDriverWait(driver).
                    until(ExpectedConditions.elementToBeClickable(placeYourOrder));

            placeYourOrder.click();
        }
    }

    public boolean containsErrors() {
        return errorsModal.isDisplayed();
    }

    public boolean isBillingSectionDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(billingSection));
        return billingSection.isDisplayed();
    }

    public void input_credit_card_security_code() {
        securityCode.sendKeys("123");
    }

    public boolean isShippingSectionDisplayed() {
        return shippingSection.isDisplayed();
    }
    
    public boolean isShippingDetailsSectionDisplayed(){
    	return shippingAddress.isDisplayed();
    }
    
    public boolean isBillingDetailsSectionDisplayed(){
    	return billingAddress.isDisplayed();
    }
    
    public void enter_credit_card_security_code(String creditCardSecurityCode) {
        securityCode.sendKeys(creditCardSecurityCode);
    }
    
    public void clickPlaceYourOrder() throws InterruptedException{
        placeYourOrderButton.click();
    }
    
    public boolean isItemsCountMatchesOnReviewPage(String itemsCount){
        List<WebElement> rows = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".item-row.clearfix")));
    	return rows.size() == Integer.parseInt(itemsCount);
    }
    
    public void selectBreadcrumbItem(String breadcrumbItemName){
    	WebElement breadcrumbElement = driver.findElement(By.id("breadCrumbs"));
    	List<WebElement> breadcrumbItems = breadcrumbElement.findElements(By.xpath("//ul/li/a[@class='crumbs-link']"));
    	
    	for(WebElement breadcrumbItem:breadcrumbItems){
    		if(breadcrumbItem.getText().trim().equalsIgnoreCase(breadcrumbItemName)){
    			breadcrumbItem.click();
    			break;
    		}
    	}
    }
    
    public void clickChangeButtonOfShippingDetailsOnReviewPage(){
    	reviewPage_ShippingDetailsSection_ChangeButton.click();
    }
    
    public void clickChangeButtonOfBillingDetailsOnReviewPage(){
    	reviewPage_BillingDetailsSection_ChangeButton.click();
    }
    
    public boolean isProductNamePriceMatchesOnReviewPage(){
    	
    	boolean blnResult = false;
    	Util.waitWithStaleRetry(driver,placeYourOrder);
    	List<WebElement> itemDetailsOnReviewPage = driver.findElements(By.cssSelector(".item-row.clearfix"));    	
    	
    	for(WebElement itemDetailOnReviewPage:itemDetailsOnReviewPage){
    		WebElement reviewPageproductName = itemDetailOnReviewPage.findElement(By.className("item-name"));
    		WebElement reviewPageproductPrice = itemDetailOnReviewPage.findElement(By.className("item-price"));

            String reviewPageProductNameText = reviewPageproductName.getText().trim();
            reviewPageProductNameText = cleanProductName(reviewPageProductNameText);

            String reviewPageProductPriceText = reviewPageproductPrice.getText().trim();
            reviewPageProductPriceText = reviewPageProductPriceText.replaceAll(",","");
    		
    		List<Product> productList = (List<Product>) stateHolder.get("productList");
            for (Product product : productList) {

                if (product.getProductName().equalsIgnoreCase(reviewPageProductNameText)) {
                    logger.debug("review price: {} product price: {}", reviewPageProductPriceText, product.getPriceList());
                    if (reviewPageProductPriceText.equalsIgnoreCase(product.getPriceList())) {
                        blnResult = true;
                        break;
                    }
                }

                if (blnResult) {
                    logger.debug(reviewPageProductNameText + " was not added to bag");
                    break;
                }
            }
    		
    		if(!blnResult){
				logger.debug(reviewPageProductNameText + " price in review page does not match");
				break;
    		}
    	}
    	
    	return blnResult;    	
    }

    private String cleanProductName(String productName) {
        productName = productName.toLowerCase();

        if(productName.startsWith("the ")) {
            productName = productName.replaceFirst("the ", "");
        } else if(productName.startsWith("a ")) {
            productName = productName.replaceFirst("a ", "");
        } else if(productName.startsWith("pre-order ")) {
            productName = productName.replaceFirst("pre-order ", "");
        }

        return productName;
    }
    
    public void editDetails(String group) {
        logger.debug("Editing {}", group);

        WebElement changeButton;

        wait.until(ExpectedConditions.visibilityOf(billingSection));
        wait.until(ExpectedConditions.visibilityOf(shippingSection));
        wait.until(ExpectedConditions.visibilityOf(gifting_details));
        wait.until(ExpectedConditions.visibilityOf(order__listing));

        switch (group) {
            case "billing":
                changeButton = billingSection.findElement(By.className("item-button"));
                break;
            case "shipping":
                changeButton = shippingSection.findElement(By.className("item-button"));
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
        }
    }
    
    public String getShippingAddress() {
        WebElement shippingAddress = shippingSection.findElement(By.className("shipping-address"));
        return shippingAddress.getText().trim();
    }
    
    public String getShippingMethod() {
        WebElement shippingMethod = shippingSection.findElement(By.className("shipping-method"));
        return shippingMethod.getText();
    }
    
    public String getBillingAddress() {
        WebElement billingAddress = billingSection.findElement(By.className("billing-address"));
        return billingAddress.getText().trim();
    }
    
    public String getPaymentMethod(){
    	WebElement paymentMethod = billingSection.findElement(By.className("wallet-cards"));
    	return paymentMethod.getText().trim();    	
    }
    
    public void selectRandomShippingMethodOnReviewPage() {
    	WebElement shippingMethodSection =  shippingSection.findElement(By.className("shipping-method"));
    	
    	List<WebElement> shippingMethodRadioButtons = shippingMethodSection.findElements(By.xpath(".//input[@class='input-radio' and not(@checked='')]"));
    	int randomIndex = Util.randomIndex(shippingMethodRadioButtons.size());
    	
    	shippingMethodRadioButtons.get(randomIndex).click();
    	Util.waitLoadingBar(driver);
    	
    	String selectedShippingMethod = getShippingMethod();
        stateHolder.put("selectedShippingMethod", selectedShippingMethod);
    }
}