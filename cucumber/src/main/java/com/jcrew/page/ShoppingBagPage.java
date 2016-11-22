package com.jcrew.page;

import java.util.Collections;
import java.util.List;


import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
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

public class ShoppingBagPage extends Checkout {

    public final Logger logger = LoggerFactory.getLogger(ShoppingBagPage.class);
    public final StateHolder stateHolder = StateHolder.getInstance();

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
    
    @FindBy(id = "giftCardContainer")
    private WebElement giftCard;
    
    @FindBy(id = "help-header")
    private WebElement help; 
    
    @FindBy(className="checkout-container")
    private WebElement checkoutContainer;

    public ShoppingBagPage(WebDriver driver) {    	
    	super(driver);
    	
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void click_checkout_button() {
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
    
    public boolean isBagItemsCountMatches(int itemsCount) {
        Util.waitForPageFullyLoaded(driver);
        Util.waitLoadingBar(driver);
        Util.waitWithStaleRetry(driver, cartSize);
        int actualItemsCount = 0;
        Boolean success = false;
        int retry = 0;
        while (retry <= 3 && !success) {
            try {
                Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(cartSize));
                Util.createWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElement(cartSize, ")"));
                String bagItemsCount = cartSize.getText().trim();
                bagItemsCount = bagItemsCount.replace("(", "");
                bagItemsCount = bagItemsCount.replace(")", "");
                actualItemsCount = Integer.parseInt(bagItemsCount);
                success = true;
            } catch (Exception NumberFormatException) {
              retry ++;
            }
        }
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

        String subtotalValue = getSubtotalValue().trim();
        if (countryname.equalsIgnoreCase("de"))
            subtotalValue = CurrencyChecker.formatEuroCurrencyFormatToUSCurrencyFormat(subtotalValue,country);
        subtotalValue=subtotalValue.replaceAll("[^0-9\\.]", "").trim();
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
        if (count == 1)
            throw new WebDriverException("Shopping bag has only 1 item . So, even though subtotal " + dblSubTotalValue + ", is greater than credit card threshold amount for the country " + countryname + ", we cannot do any modifications to Bag." );

        List<Double> prices = new ArrayList<>();
        for (WebElement productprice : productprices) {
            String strPrice = productprice.getText();
            if (countryname.equalsIgnoreCase("de"))
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

            if (addQuantity == true && counter == remainingProducts && (countryname.equalsIgnoreCase("us") || remainingProducts == 1 ))
                throw new WebDriverException("Deleted " + currentItem + " high price item(s) from Bag. But, unable to increase corresponding count on low priced items");
            counter = counter + 1;
        } while (addQuantity = true && counter <= remainingProducts);

    }


	@Override
	public boolean isDisplayed() {		
		String bodyId = getBodyAttribute("id");
        return bodyId.equals("shoppingBag");
	}
	
	public boolean isEditedItemDisplayedFirst(){
    	List<WebElement> productsInBag = wait.until(ExpectedConditions.visibilityOfAllElements(
				 order__listing.findElements(By.className("item-row"))));
    	
    	WebElement firstItemInBag = productsInBag.get(0);
    	
    	WebElement nameElement = firstItemInBag.findElement(By.className("item-name"));
        String actualProductName = nameElement.getText().trim();

        String actualQuantity = getQuantity(firstItemInBag);

        WebElement priceElement = firstItemInBag.findElement(By.className("item-price"));
        String actualPrice = priceElement.getText().trim();
        actualPrice = actualPrice.replaceAll("[^0-9.,]", "");

        List<WebElement> descriptionElements = firstItemInBag.findElements(By.className("item-label"));

        WebElement numberElement = descriptionElements.get(0).findElement(By.tagName("span"));
        String actualItemNumber = numberElement.getText().trim();

        logger.debug("First product {}, item number {} with price {} in bag",
        		actualProductName, actualItemNumber, actualPrice);        
        
        List<Object> products = stateHolder.getList("editedItem");
        Product product = (Product) products.get(0);
        String expectedProductName = product.getProductName();
        
        String expectedProductPrice = product.getPriceList();
        expectedProductPrice = expectedProductPrice.replaceAll("[^0-9.,]", "");
        
        String expectedProductNumber = product.getProductCode();
        String expectedQuantity = product.getQuantity();
        
        boolean result = expectedProductName.equalsIgnoreCase(actualProductName) &&
        				 expectedProductPrice.equalsIgnoreCase(actualPrice) &&
        				 expectedProductNumber.equalsIgnoreCase(actualItemNumber) &&
        				 expectedQuantity.equalsIgnoreCase(actualQuantity);
        
        return result;
    }
	
	public boolean itemsButtons() {
        boolean result = true;
        List<WebElement> productsInBag = order__listing.findElements(By.className("item-row"));

        for (WebElement product : productsInBag) {
            List<WebElement> buttons = product.findElements(By.xpath(".//li[@class='item-actions']/a"));

            result &= buttons.size() == 2;
            result &= buttons.get(0).getText().equals("EDIT");
            result &= buttons.get(1).getText().equals("REMOVE");
        }

        return result;
    }
	
	public boolean giftCard() {
        return giftCard.isDisplayed();
    }

    public boolean summary() {
        return orderSummary.isDisplayed();
    }

    public boolean payPalButton() {
        WebElement payPal = orderSummary.findElement(By.className("button-paypal"));

        return payPal.isDisplayed();
    }

    public boolean help() {
        return help.isDisplayed();
    }

    public void estimateTax(String zipcode) {
        WebElement zipcodeField = orderSummary.findElement(By.id("zipcode"));
        zipcodeField.sendKeys(zipcode);

        WebElement zipcodeIndicator = wait.until(ExpectedConditions.visibilityOf(orderSummary.findElement(By.id("zipcode-transition-indicator"))));;
        wait.until(ExpectedConditions.stalenessOf(zipcodeIndicator));
        wait.until(ExpectedConditions.visibilityOf(checkoutLink));
    }
    
    public String getEstimatedShipping() {
        return getSummaryText("estimated shipping");
    }

    public String getEstimatedTax() {
        return getSummaryText("estimated tax");
    }

    public String getEstimatedTotal() {
        return getSummaryText("estimated total");
    }
    
    public String getSubtotalValue() {
        return subtotalValue.getText();
    }

    public String getQuestionsPhone() {
        WebElement phone = help.findElement(By.className("help-phone"));
        return phone.getText();
    }
    
    public void editItem(int index) {
        actionItem(index, "Edit");

    }
    
    public void removeItem(int index) {
        actionItem(index, "Remove");
    }
    
    private void actionItem(int index, String action) {
		List<Product> products = stateHolder.getList("toBag");
        List<WebElement> productsInBag = order__listing.findElements(By.className("item-row"));
        productsInBag = Lists.reverse(productsInBag);

        if (index < 0) {
            index = products.size() - 1;
        }

        WebElement product = productsInBag.get(index);
        WebElement productActionButton = product.findElement(By.xpath(".//li[@class='item-actions']/a[text()='" + action + "']"));
        
        if(action.equalsIgnoreCase("Remove")){
        	Product deletedProduct = products.get(index);
        	stateHolder.put("deleteditemprice", deletedProduct.getPriceList());
        	stateHolder.put("deleteditemqty", deletedProduct.getQuantity());
        }

        products.remove(index);
        stateHolder.put("toBag", products);

        productActionButton.click();
        Util.waitLoadingBar(driver);
    }
    
    public void editQuantity(int index) {	
        List<WebElement> productsInBag = order__listing.findElements(By.className("item-row"));
        productsInBag = Lists.reverse(productsInBag);

        if (index < 0) {
            index = productsInBag.size() - 1;
        }

        WebElement product = productsInBag.get(index);
        WebElement select = product.findElement(By.className("item-qty"));

        Select selectQty = new Select(select);        
        List<WebElement> options = selectQty.getOptions();
        String optionValue = options.get(Util.randomIndex(options.size())).getText();
        selectQty.selectByVisibleText(optionValue);

		List<Product> products = stateHolder.getList("toBag");
        Product editedProduct = products.get(index);

        editedProduct.setQuantity(optionValue);
        products.set(index, editedProduct);
        stateHolder.put("toBag", products);
    }
    
    public void editQuantitybyNumber(int index) {	
        List<WebElement> productsInBag = order__listing.findElements(By.className("item-row"));
        productsInBag = Lists.reverse(productsInBag);
        int itemIndex;
        
        itemIndex = productsInBag.size() - 1;
        
        WebElement product = productsInBag.get(itemIndex);
        WebElement select = product.findElement(By.className("item-qty"));

        Select selectQty = new Select(select);        
        List<WebElement> options = selectQty.getOptions();
        selectQty.selectByIndex(index-1);
        
        Util.waitLoadingBar(driver);
       //String optionValue = options.get(index).getText();
        String optionValue = Integer.toString(index);
       
		List<Product> products = stateHolder.getList("toBag");
		
        Product editedProduct = products.get(itemIndex);

        editedProduct.setQuantity(optionValue);
        products.set(itemIndex, editedProduct);
        stateHolder.put("toBag", products);
    }
    private String toString(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getItemTotal(int index) {
        List<WebElement> productsInBag = order__listing.findElements(By.className("item-row"));
        WebElement product = productsInBag.get(index);

        WebElement productTotal = product.findElement(By.className("item-total"));
        String total = productTotal.getText();
        total = total.replaceAll("[^0-9]", "");

        return total;
    }
    
    public List<WebElement> getItemEditElements(){
    	List<WebElement> editButtons = checkoutContainer.findElements(By.className("item-edit"));
    	return editButtons;
    }
    
    public String getItemCode(WebElement editButton){    	    	
    	String itemCode = editButton.findElement(By.xpath("../../li[1]")).getText().replace("Item ", "");
    	return itemCode;
    }
}