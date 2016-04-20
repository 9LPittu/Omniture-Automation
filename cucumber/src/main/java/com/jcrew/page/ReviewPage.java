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


public class ReviewPage {

	private final StateHolder stateHolder = StateHolder.getInstance();
	private final Logger logger = LoggerFactory.getLogger(ReviewPage.class);
    private boolean isProduction = false;
	
    private final WebDriver driver;
    @FindBy(xpath = ".//*[@id='orderSummaryContainer']/div/a")
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

    public ReviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        if(propertyReader.getProperty("url").contains("www.jcrew.com")){
            isProduction = true;
        }
    }

    public void user_places_its_order() {
        Util.waitForPageFullyLoaded(driver);
        if(!isProduction) {
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
    		
    		List<Product> productList = (List<Product>) stateHolder.get("productList");
    		for(int i=0;i<productList.size();i++){
    			Product product = productList.get(i);
    			if(product.getProductName().equalsIgnoreCase(reviewPageproductName.getText())){				
					if(reviewPageproductPrice.getText().trim().equalsIgnoreCase(product.getPriceList())){
						blnResult = true;
						break;
					}
    			}
    			
    			if(blnResult){
    				logger.debug(reviewPageproductName.getText() + " product details matches on review page");
    				break;
    			}
    		}
    		
    		if(!blnResult){
				logger.debug(reviewPageproductName.getText() + " product details does not match on review page");
				break;
    		}
    	}
    	
    	return blnResult;    	
    }
}