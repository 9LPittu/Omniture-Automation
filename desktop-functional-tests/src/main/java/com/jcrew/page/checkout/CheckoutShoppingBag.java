package com.jcrew.page.checkout;

import com.google.common.collect.Lists;
import com.jcrew.page.Footer;
import com.jcrew.pojo.Product;
import com.jcrew.utils.Util;
import com.thoughtworks.selenium.webdriven.commands.KeyEvent;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
@SuppressWarnings("unused")
public class CheckoutShoppingBag extends Checkout {

    @FindBy(id = "giftCardContainer")
    private WebElement giftCard;
    @FindBy(id = "help-header")
    private WebElement help;    
    @FindBy(className="checkout-container")
    private WebElement checkoutContainer;
    @FindBy(className="js-cart-size")
    private WebElement cartSize;
    
    @FindBy(xpath="//button[contains(text(),'REDEEM')]")
    private WebElement reedem;
  
    @FindBy(xpath="//span[contains(text(),'Rewards Redeemed')]")
    private WebElement rewardsRedeemed;
    
    @FindBy(className="item-gc")
    private WebElement giftCardElement;
    
   /* @FindBy(xpath=".//span[@class='summary-label' and contains(text(), 'SUBTOTAL')]/following-sibling::*")
    private WebElement subTotal;
    
    @FindBy(xpath=".//span[@class='summary-label' and contains(text(), 'Rewards Redeemed')]/following-sibling::*")
    private WebElement redeemedAmount;*/
    
    @FindBy(xpath=".//span[@class='summary-label' and contains(text(), 'Total')]/following-sibling::*")
    private WebElement total;

    public CheckoutShoppingBag(WebDriver driver) {
        super(driver);
        Util.waitForPageFullyLoaded(driver);
        Footer footer = new Footer(driver);
    }

    public boolean isDisplayed() {
        Util.waitForPageFullyLoaded(driver);
       /* String bodyId = getBodyAttribute("id");

        return bodyId.equals("shoppingBag");*/
        return driver.getCurrentUrl().contains("shoppingbag");
    }

    public void checkOutNow()  {
        checkoutNow.click();
        Util.waitLoadingBar(driver);
        Util.wait(1000);
        try {
			Robot r = new Robot();
			r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
			r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
    }

    public List<Product> getProducts() {
        List<WebElement> productsInBag = orderListing.findElements(By.className("item-row"));
        List<Product> products = new ArrayList<>();

        for(WebElement inBag : productsInBag) {
            Product product = new Product();

            WebElement element = inBag.findElement(By.className("item-name"));
            String value = element.getText().trim();
            product.setName(value.replaceAll("PRE-ORDER ", ""));

            product.setQuantity(getQuantity(inBag));

            element = inBag.findElement(By.className("item-price"));
            String price = element.getText().trim();
            product.setPrice(price.replaceAll("[^0-9.,]", ""));

            List<WebElement> descriptionElements = inBag.findElements(By.className("item-label"));

            element = descriptionElements.get(0).findElement(By.tagName("span"));
            product.setItemNumber(element.getText().trim());

            element = descriptionElements.get(1).findElement(By.tagName("span"));
            value = element.getText();
            if(!value.toUpperCase().contains("ONE SIZE")){
                value = element.getText().replace("SIZE", "").trim();
            }
            product.setSize(value);

            element = descriptionElements.get(2).findElement(By.tagName("span"));
            product.setColor(element.getText().trim());

            products.add(product);
        }

        return products;
    }

    public boolean itemsButtons() {
        boolean result = true;
        List<WebElement> productsInBag = orderListing.findElements(By.className("item-row"));

        for (WebElement product : productsInBag) {
            WebElement productName = product.findElement(By.className("item-name"));
            if (!productName.getText().toLowerCase().contains("promo card")) {
                List<WebElement> buttons = product.findElements(By.xpath(".//li[@class='item-actions']/a"));

                result &= buttons.size() == 2;
                result &= buttons.get(0).getText().equals("EDIT");
                result &= buttons.get(1).getText().equals("REMOVE");
            }
        }

        return result;
    }

    public boolean giftCard() {
        return giftCard.isDisplayed();
    }

    public boolean help() {
        return help.isDisplayed();
    }

    public String getQuestionsPhone() {
        WebElement phone = help.findElement(By.className("help-phone"));
        return phone.getText();
    }

    private void actionItem(int index, String action) {
		List<Product> products = stateHolder.getList("toBag");
        List<WebElement> productsInBag = orderListing.findElements(By.className("item-row"));
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
        List<WebElement> productsInBag = orderListing.findElements(By.className("item-row"));
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
        List<WebElement> productsInBag = orderListing.findElements(By.className("item-row"));
        WebElement product = productsInBag.get(index);

        WebElement productTotal = product.findElement(By.className("item-total"));
        String total = productTotal.getText();
        total = total.replaceAll("[^0-9]", "");

        return total;
    }
    
    public boolean isEditedItemDisplayedFirst(){
    	List<WebElement> productsInBag = wait.until(ExpectedConditions.visibilityOfAllElements(
                orderListing.findElements(By.className("item-row"))));
    	
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

    public List<Product> getUnavailableItems() {
        List<WebElement> productsInBag = wait.until(ExpectedConditions.visibilityOfAllElements(
                orderListing.findElements(By.className("item-row-unavailable"))));
        List<Product> products = new ArrayList<>();

        for (WebElement product : productsInBag) {
            WebElement nameElement = product.findElement(By.className("item-name"));
            Product p = new Product();
            p.setName(nameElement.getText().trim());
            products.add(p);
        }

        return products;
    }
    
    public boolean itemsSaveButtons() {
        boolean result = true;

        List<WebElement> productsInBag = orderListing.findElements(By.className("item-row"));
        try {

            for (WebElement product : productsInBag) {
                List<WebElement> promo = product.findElements(By.xpath(".//li[@class='item-promo']"));
                List<WebElement> buttons;

                if (promo.size() == 0) {
                    buttons = product.findElements(By.xpath(".//li[@class='item-actions']/a"));

                    result &= buttons.size() >= 3;
                    result &= buttons.get(2).getText().equals("SAVE");

                } else {
                    buttons = product.findElements(
                            By.xpath(".//li[@class='item-promo']/preceding-sibling::li[@class='item-actions']/a"));

                    if (buttons.size() > 0) {

                        result &= buttons.get(2).getText().equals("SAVE");
                    }
                }
            }
        } catch (Exception e) {
            result &= false;

        }
        return result;
    }
    
    public void reedemRewardPoint() {
    	try {
    		String totalBeforRedeem = total.getText();
    		totalBeforRedeem = totalBeforRedeem.replaceAll("[^0-9\\.]", "");
    		Util.scrollToElement(driver, reedem);
    		Assert.assertTrue(reedem.isDisplayed());
    		reedem.click();
    		Util.waitForPageFullyLoaded(driver);
    		Assert.assertTrue(rewardsRedeemed.isDisplayed());
    		String totalAfterRedeem = total.getText();
    		totalAfterRedeem=totalAfterRedeem.replaceAll("[^0-9\\.]", "");
    		Assert.assertNotEquals(totalBeforRedeem, totalAfterRedeem);
    	}catch (Exception e) {
			
		}
    }
}