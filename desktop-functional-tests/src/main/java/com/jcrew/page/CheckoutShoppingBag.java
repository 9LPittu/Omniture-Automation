package com.jcrew.page;

import com.google.common.collect.Lists;
import com.jcrew.pojo.Product;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutShoppingBag extends Checkout {
	
    @FindBy(id = "button-checkout")
    private WebElement checkoutNow;
    @FindBy(id = "giftCardContainer")
    private WebElement giftCard;
    @FindBy(id = "help-header")
    private WebElement help;    
    @FindBy(className="checkout-container")
    private WebElement checkoutContainer;
    @FindBy(className="js-cart-size")
    private WebElement cartSize;
    @FindBy(css = ".summary-item > .summary-value")
    private WebElement subtotalValue;
    
    @FindBy(className="item-gc")
    private WebElement giftCardElement;
    
    private final Footer footer;

    public CheckoutShoppingBag(WebDriver driver) {
        super(driver);
 //       wait.until(ExpectedConditions.visibilityOf(checkoutNow));
        Util.waitForPageFullyLoaded(driver);
        this.footer = new Footer(driver);
    }

    public boolean isDisplayed() {
        Util.waitForPageFullyLoaded(driver);
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("shoppingBag");
    }

    public void checkOutNow() {
        checkoutNow.click();
        Util.waitLoadingBar(driver);
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
    
    public WebElement getPaypalElement(){
    	return orderSummary.findElement(By.className("button-paypal"));
    }

    public boolean payPalButton() {
        WebElement payPal = getPaypalElement();

        return payPal.isDisplayed();
    }
    
    public void clickPaypalElement(){
    	WebElement paypalElement = getPaypalElement();    	
    	paypalElement.click();
    	
    	Util.waitLoadingBar(driver);    	
    }

    public boolean help() {
        return help.isDisplayed();
    }

    public void estimateTax(String zipcode) {
        WebElement zipcodeField = orderSummary.findElement(By.id("zipcode"));
        zipcodeField.sendKeys(zipcode);

        WebElement zipcodeIndicator = wait.until(ExpectedConditions.visibilityOf(orderSummary.findElement(By.id("zipcode-transition-indicator"))));;
        wait.until(ExpectedConditions.stalenessOf(zipcodeIndicator));
        wait.until(ExpectedConditions.visibilityOf(checkoutNow));
    }
    
    public String getEstimatedShipping() {
        String countryCode = country.getCountry();
        if (countryCode.equalsIgnoreCase("us")) {
        	return getSummaryText("estimated shipping");
        } else {
        	return getSummaryText("shipping");
        }
    }

    public String getEstimatedTax() {
        return getSummaryText("estimated tax");
    }

    public String getEstimatedTotal() {
    	String countryCode = country.getCountry();
        if (countryCode.equalsIgnoreCase("us")) {
        	return getSummaryText("estimated total");
        } else {
        	return getSummaryText("total");
        }	
    }
    
    public String getSubtotalValue() {
        return subtotalValue.getText();
    }
    
    public String getTotalValue(){
    	return getSummaryText("total");
    }

    public String getQuestionsPhone() {
        WebElement phone = help.findElement(By.className("help-phone"));
        return phone.getText();
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
        	stateHolder.put("deleteditemprice", deletedProduct.getPrice());
        	stateHolder.put("deleteditemqty", deletedProduct.getQuantity());
        }

        products.remove(index);
        stateHolder.put("toBag", products);
        
        Util.scrollToElement(driver, productActionButton);
        productActionButton.click();
        Util.waitLoadingBar(driver);
    }

    public void removeItem(int index) {
        actionItem(index, "Remove");
    }

    public void editItem(int index) {
        actionItem(index, "Edit");

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

    public String getItemTotal(int index) {
        List<WebElement> productsInBag = order__listing.findElements(By.className("item-row"));
        WebElement product = productsInBag.get(index);

        WebElement productTotal = product.findElement(By.className("item-total"));
        String total = productTotal.getText();
        total = total.replaceAll("[^0-9]", "");

        return total;
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
        String expectedProductName = product.getName();
        String expectedProductPrice = product.getPrice().replaceAll("[^0-9.,]", "");
        String expectedProductNumber = product.getItemNumber();
        String expectedQuantity = product.getQuantity();
        
        boolean result = expectedProductName.equalsIgnoreCase(actualProductName) &&
        				 expectedProductPrice.equalsIgnoreCase(actualPrice) &&
        				 expectedProductNumber.equalsIgnoreCase(actualItemNumber) &&
        				 expectedQuantity.equalsIgnoreCase(actualQuantity);
        
        return result;
    }
    
    public List<WebElement> getItemEditElements(){
    	List<WebElement> editButtons = checkoutContainer.findElements(By.className("item-edit"));
    	return editButtons;
    }
    
    public String getItemCode(WebElement editButton){    	    	
    	String itemCode = editButton.findElement(By.xpath("../../li[1]")).getText().replace("Item ", "");
    	return itemCode;
    }

    public boolean verifyContext() {
        String countryFooter = footer.getCountry();
        boolean result = countryFooter.equalsIgnoreCase(country.getName());
        return result;
    }
    
    public String getEgiftCardSenderName(){
    	WebElement senderNameElement = giftCardElement.findElement(By.xpath(".//ul[@class='item-description']/li[1]/span"));
    	String senderName = senderNameElement.getText().trim();
    	return senderName;
    }
    
    public String getEgiftCardRecipientName(){
    	WebElement recipientNameElement = giftCardElement.findElement(By.xpath(".//ul[@class='item-description']/li[2]/span"));
    	String recipientName = recipientNameElement.getText().trim();
    	return recipientName;
    }
    
    public String getEgiftCardRecipientEmailAddress(){
    	WebElement recipientEmailElement = giftCardElement.findElement(By.xpath(".//ul[@class='item-description']/li[3]/span"));
    	String recipientEmail = recipientEmailElement.getText().trim();
    	return recipientEmail;
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
}