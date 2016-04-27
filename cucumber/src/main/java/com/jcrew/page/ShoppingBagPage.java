package com.jcrew.page;

import java.util.List;


import com.google.common.base.Predicate;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShoppingBagPage {

    private final Logger logger = LoggerFactory.getLogger(ShoppingBagPage.class);
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final WebDriver driver;
    @FindBy(id = "button-checkout")
    private WebElement checkoutLink;

    @FindBy(css = ".item-actions > .item-edit")
    private WebElement editAction;

    @FindBy(css = ".item-actions > .item-remove")
    private WebElement removeAction;

    @FindBy(css = ".item-group-price >.item-total")
    private WebElement itemTotal;

    @FindBy(css = ".summary-item > .summary-value")
    private WebElement subtotalValue;

    @FindBy(id = "checkout")
    private WebElement articleCheckout;

    @FindBy(className = "item-product")
    private WebElement itemProductSection;

    @FindBy(className = "item-qty")
    private WebElement itemQuantity;

    @FindBy(id = "order-listing")
    private WebElement orderListing;
    
    @FindBy(className="js-cart-size")
    private WebElement cartSize;
    
    @FindBy(css=".icon-header.icon-header-logo")
    private WebElement breadcrumbLink;

    @FindBy(className = "c-header__breadcrumb")
    private WebElement breadcrumbSection;

    public ShoppingBagPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }


    public void click_checkout_button() {
        String url = driver.getCurrentUrl();
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(checkoutLink));

        checkoutLink.click();
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
        Util.waitForPageFullyLoaded(driver);
    }

    public boolean isEditButtonPresent() {
        return editAction.isDisplayed();
    }

    public boolean isRemoveButtonPresent() {
        return removeAction.isDisplayed();
    }

    public String getTotalAmountPage() {
        return itemTotal.getText();
    }

    public String getSubtotalValue() {
        return subtotalValue.getText();
    }

    public boolean isArticleCheckoutPresent() {
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(articleCheckout));
        return true;
    }

    public void click_edit_button() {
        Util.waitForPageFullyLoaded(driver);

        Product product = (Product) stateHolder.get("recentlyAdded");

        String xpath;

        if (product.getProductName().contains("'")) {
            xpath = ".//a[" + Util.xpathGetTextLower + " = \"" + product.getProductName().toLowerCase() + "\"]" +
                    "/ancestor::div[@class='item-product']";
        } else {
            xpath = ".//a[" + Util.xpathGetTextLower + " = '" + product.getProductName().toLowerCase() + "']" +
                    "/ancestor::div[@class='item-product']";
        }

        WebElement order_listing = driver.findElement(By.id("order-listing"));
        WebElement item_product = order_listing.findElement(
                By.xpath(xpath));
        WebElement item_product_edit = item_product.findElement(By.className("item-edit"));

        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(item_product_edit));
        item_product_edit.click();

    }

    public boolean isProductColorDisplayed(String productColor) {
        WebElement productColorElement = getProductElementValue(productColor);

        return productColorElement.isDisplayed();
    }

    private WebElement getProductElementValue(String element) {
        return getDescriptionElementFor(ExpectedConditions.visibilityOf(
                itemProductSection.findElement(By.xpath(".//span[contains(@class, 'notranslate') and " +
                        "text()='" + element + "']"))));
    }

    private WebElement getDescriptionElementFor(ExpectedCondition<WebElement> isTrue) {
        return Util.createWebDriverWait(driver).until(
                isTrue);
    }

    public boolean isProductSizeDisplayed(String productSize) {
        WebElement productSizeElement = getProductElementValue(productSize);
        return productSizeElement.isDisplayed();
    }

    public String getItemQuantity() {
        Select select = new Select(itemQuantity);
        WebElement selectedOption = select.getFirstSelectedOption();
        return selectedOption.getText();
    }


    public String getProductName() {
        return itemProductSection.findElement(By.className("item-name")).getText();
    }

    public boolean isColorDisplayedForProduct(String productName, String selectedColor) {
        return isGenericElementDisplayed(productName, selectedColor);
    }

    public boolean isSizeDisplayedForProduct(String productName, String selectedSize) {
        return isGenericElementDisplayed(productName, selectedSize);
    }

    private boolean isGenericElementDisplayed(String productName, String element) {
        WebElement productRoot = getProductRoot(productName);

        String xpath;

        if (element.contains("'")) {
            xpath = ".//span[" + Util.xpathGetTextLower + " = \"" + element.toLowerCase() + "\"]";
        } else {
            xpath = ".//span[" + Util.xpathGetTextLower + " = '" + element.toLowerCase() + "']" ;
        }

        WebElement selectedElement = productRoot.findElement(By.xpath(xpath));
        return selectedElement.isDisplayed();
    }

    private WebElement getProductRoot(String productName) {
        productName = productName.replace(" (Pre-order)", "").replaceAll("&amp;", "&");

        String xpath;

        if (productName.contains("'")) {
            xpath = ".//a[contains(" + Util.xpathGetTextLower + "," +
                    "translate(\"" + productName.toLowerCase() + "\", 'ABCDEFGHJIKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghjiklmnopqrstuvwxyz'))]/../../..";
        } else {
            xpath = ".//a[contains(" + Util.xpathGetTextLower + "," +
                    "translate('" + productName +
                    "', 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz'))]/../../..";
        }

        return orderListing.findElement(By.xpath(xpath));
    }

    public String getPriceDisplayedForProduct(String productName) {
        WebElement productRoot = getProductRoot(productName);
        return productRoot.findElement(By.className("item-price")).getText().trim();
    }
    
    public boolean isPageTitleContains(String pageTitle){
        String title = driver.getTitle();
        logger.debug("Title is: {}", title);
        return title.contains(pageTitle);
    }
    
    public boolean isBagItemsCountMatches(int itemsCount){
        Util.waitForPageFullyLoaded(driver);
    	Util.waitWithStaleRetry(driver,cartSize);
    	String bagItemsCount = cartSize.getText().trim();
    	bagItemsCount = bagItemsCount.replace("(", "");
    	bagItemsCount = bagItemsCount.replace(")", "");
    	int actualItemsCount = Integer.parseInt(bagItemsCount);

    	return actualItemsCount == itemsCount;
    }
    
    public boolean isBreadcrumbDisplayed(String breadcrumbText){
        final String breadCrumbs[] = breadcrumbText.split("//");
        if(breadCrumbs.length == 0){
            logger.error("NOT VALID USE OF BREADCRUMBTEXT");
            return false;
        }

        //get the last breadcrumb expected
        final String lastBreadCrumb = breadCrumbs[breadCrumbs.length - 1].toLowerCase();

        Util.waitWithStaleRetry(driver,breadcrumbSection);

        //wait until breadcrumb contains the last expected breadcrumb and return
        Util.createWebDriverWait(driver).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver webDriver) {
                String pageBreadCrumbs = breadcrumbSection.getText().toLowerCase();
                return pageBreadCrumbs.contains(lastBreadCrumb);
            }
        });

        return breadcrumbSection.getText().equalsIgnoreCase(breadcrumbText);
    }
    
    public boolean isPDPPageColorDisplayedInShoppingBag(){

    	@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");
    	Product product = productList.get(0);
    	String productName = product.getProductName();
    	String expectedColorName = (product.getSelectedColor()).toUpperCase();

    	return isColorDisplayedForProduct(productName,expectedColorName);
    }

    public boolean isPDPPageSizeDisplayedInShoppingBag(){

    	@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");
    	Product product = productList.get(0);
    	String productName = product.getProductName();
    	String expectedSizeName = (product.getSelectedSize()).toUpperCase();

    	return isSizeDisplayedForProduct(productName,expectedSizeName);
    }
    
    public boolean isCorrectCurrencySymbolShoppingBagItemSection() {
        boolean result = true;
       
        Country c = (Country) stateHolder.get("context");
        String currency = c.getCurrency();
        
        List<WebElement> productpricess = driver.findElements(By.xpath("//div[contains(@class,'item-price') or contains(@class,'item-total')]"));
        	
        if(productpricess.isEmpty()) {
            logger.debug("Item Price  count not found on checkout page");
            result = true;
        } else {
        	for (WebElement price : productpricess) 
        	
        		if (!price.getText().contains(currency)) {
        			result = false;
        			break;
        		}
        }
        if(result){
        	logger.info("Currency symbol is displayed correctly on all on Item prices on Checkout page");
        	
        }
        else{
        	logger.debug("Currency symbol is not displayed correctly on all / any of the Item prices on Checkout page");
        }
        return result;
    }
    
    public boolean isCorrectCurrencySymbolShoppingBagSummarySection() {
        boolean result = true;
        
        Country c = (Country) stateHolder.get("context");
        String currency = c.getCurrency();
        
        List<WebElement> summarypricess = driver.findElements(By.xpath("//span[contains(@class,'summary-value')]"));
        	
        if(summarypricess.isEmpty()) {
            logger.debug("Summary values  count not found checkout page");
            result = true;
        } else {
        	for (WebElement price : summarypricess) 
        	
        		if (!price.getText().contains(currency) && (!price.getText().contains("- - - -"))) {
        			result = false;
        			break;
        		}
        }
        if(result){
        	logger.info("Currency symbol is displayed correctly on all on Summary values on Checkout page");
        	
        }
        else{
        	logger.debug("Currency symbol is not displayed correctly on all / any of the Summary on Checkout page");
        }
        return result;
    }
    
    public boolean isCorrectCurrencySymbolonShoppingBagMethodPrices() {
        boolean result = true;
        
        Country c = (Country) stateHolder.get("context");
        String currency = c.getCurrency();
        
        List<WebElement> summarypricess = driver.findElements(By.xpath("//span[contains(@class,'method-price')]"));
        	
        if(summarypricess.isEmpty()) {
            logger.debug("Method Price  count not found checkout page");
            result = true;
        } else {
        	for (WebElement price : summarypricess) 
        	
        		if (!price.getText().contains(currency) && (!price.getText().contains("FREE"))) {
        			result = false;
        			break;
        		}
        }
        if(result){
        	logger.info("Currency symbol is displayed correctly on all on Shipping method prices on Checkout page");
        	
        }
        else{
        	logger.debug("Currency symbol is not displayed correctly on all / any of the Shipping method prices on Checkout page");
        }
        return result;
    }
    
    
    public boolean isCorrectCurrencySymbolonShoppingBagShippingPrices() {
        boolean result = true;
        
        Country c = (Country) stateHolder.get("context");
        String currency = c.getCurrency();
        
        List<WebElement> summaryprices = driver.findElements(By.xpath("//span[contains(@class,'shipping-price')]"));
        	
        if(summaryprices.isEmpty()) {
            logger.debug("Method Price count not found checkout page");
            result = true;
        } else {
        	for (WebElement price : summaryprices) 
        	
        		if (!price.getText().contains(currency) && (!price.getText().contains("FREE"))) {
        			result = false;
        			break;
        		}
        }
        if(result){
        	logger.info("Currency symbol is displayed correctly on all on Shipping prices on Checkout page");
        	
        }
        else{
        	logger.debug("Currency symbol is not displayed correctly on all / any of the Shipping prices on Checkout page");
        }
        return result;
    }
}