package com.jcrew.page;

import java.util.Collections;
import java.util.List;


import com.google.common.base.Predicate;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.util.CurrencyChecker;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import com.jcrew.util.TestDataReader;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

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
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.titleContains("Shopping Bag")));
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
        Util.waitLoadingBar(driver);

        Product product = (Product) stateHolder.get("recentlyAdded");      
        String xpath = ".//span[contains(@class,'item-value') and text()='" + product.getProductCode() + "']/../../li[4]/a[@class='item-edit']";
        
        WebElement order_listing = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(By.id("order-listing"))));
        WebElement item_product_edit = Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(order_listing.findElement(By.xpath(xpath))));
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
    
    public boolean isCorrectCurrencySymbol(String type) {
        Country c = (Country) stateHolder.get("context");

        String xpath = "";

        switch (type) {
            case "item":
                xpath = "//div[contains(@class,'item-price') or contains(@class,'item-total')]";
                break;
            case "summary":
                xpath = "//span[contains(@class,'summary-value')]";
                break;
            case "shipping method":
                xpath = "//span[contains(@class,'method-price')]";
                break;
            case "shipping":
                xpath = "//span[contains(@class,'shipping-price')]";
                break;
        }
        
        Util.waitLoadingBar(driver);
        List<WebElement> productPriceElements = null;
        int i = 0;
        while(i<=2){
        	try{
        		productPriceElements = Util.createWebDriverWait(driver,20).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
        		break;
        	}
        	catch(StaleElementReferenceException sere){
        		i++;
        	}
        }
        

        boolean result = CurrencyChecker.validatePrices(productPriceElements, c);

        if(result){
        	logger.info("Currency symbol is displayed correctly on all on item prices on " + type);
        }
        else{
        	logger.debug("Currency symbol is not displayed correctly on all / any of the item prices on " + type);
        }

        return result;
    }

    public void applyCreditCardThreshold(){
        Country country = (Country) stateHolder.get("context");
        String countryname = country.getCountry().toLowerCase();

        String subtotalValue = getSubtotalValue().replaceAll("[^0-9\\.]", "").trim();
        Double dblSubTotalValue = Double.parseDouble(subtotalValue);

        TestDataReader dataReader = TestDataReader.getTestDataReader();
        String ccThresholdValue = dataReader.getData(countryname+".CreditCardThreshold");
        Double dblCCThresholdValue = Double.parseDouble(ccThresholdValue);

        if (dblSubTotalValue > dblCCThresholdValue)
           removeProductsAboveThreshold(dblSubTotalValue, dblCCThresholdValue, country);
    }


    public void removeProductsAboveThreshold(Double dblSubTotalValue, Double dblCCThresholdValue, Country country) {
        String countryname = country.getCountry().toLowerCase();
        List<WebElement> productprices = orderListing.findElements(By.className("item-price"));
        int count = productprices.size();
        if (count == 0)
            throw new WebDriverException("Unable to retrieve product price from shopping bag page");

        List<Double> prices = new ArrayList<>();
        for (WebElement productprice : productprices) {
            String strPrice = productprice.getText();
            if (countryname == "de")
                strPrice = CurrencyChecker.formatEuroCurrencyFormatToUSCurrencyFormat(strPrice,country);
            strPrice = strPrice.replaceAll("[^0-9\\.]", "").trim();
            Double dblPrice = Double.parseDouble(strPrice);
            prices.add(dblPrice);
        }
        Collections.sort(prices);
        Collections.reverse(prices);
        Double lowestPrice = prices.get(count-1);

        logger.info("Actual subtotal is {} and number of items in Bag are {} ", dblSubTotalValue, count );
        int currentItem = 0;
        while ((dblSubTotalValue > dblCCThresholdValue) && (currentItem < count - 1 ) ) {
            String price =  prices.get(currentItem).toString();
            price = CurrencyChecker.convertToCurrency(price, country);
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(orderListing));
            WebElement productContainer = orderListing.findElement(By.xpath(".//div[@class='item-price' and contains(text(),'" + price + "')]/ancestor::div[@class='item-row-multi clearfix']"));
            productContainer.findElement(By.className("item-remove")).click();
            Util.waitLoadingBar(driver);
            dblSubTotalValue = dblSubTotalValue - prices.get(currentItem) + lowestPrice;
            currentItem = currentItem + 1;
        }

        int remainingProducts  = count-currentItem;
        int counter = 1;
        Boolean addQuantity = true;
        do {
            String price = prices.get(count-counter).toString();
            price = CurrencyChecker.convertToCurrency(price, country);
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(orderListing));
            WebElement productContainer = orderListing.findElement(By.xpath(".//div[@class='item-price' and contains(text(),'" + price + "')]/ancestor::div[@class='item-row-multi clearfix']"));
            WebElement qty = productContainer.findElement(By.className("item-qty"));
            try {
                Select quantity = new Select(qty);
                String requiredQuantity = "" + (currentItem +1);
                quantity.selectByVisibleText(requiredQuantity);
                Util.waitLoadingBar(driver);
                addQuantity = false;
            } catch (Exception e) {
                logger.debug("Unable to increase quantity for product {} from bottom", counter);
            }

            if (addQuantity == false) {
                String newSubTotalValue = getSubtotalValue().replaceAll("[^0-9\\.]", "").trim();
                logger.info("Updated subtotal is {} and number of items in Bag are {} ", newSubTotalValue, count);
            }

            if (addQuantity == true && counter == remainingProducts)
                throw new WebDriverException("Deleted " + currentItem + " high price item(s) from Bag. But, unable to increase corresponding count on low priced items");
            counter = counter + 1;
        } while (addQuantity = true && counter <= remainingProducts);

    }

}
