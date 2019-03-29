package com.jcrew.page.checkout;

import com.google.common.base.Function;
import com.jcrew.page.PageObject;
import com.jcrew.pojo.GiftCard;
import com.jcrew.pojo.Product;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 7/15/16.
 */
public abstract class Checkout extends PageObject {

    @FindBy(id = "button-checkout")
    protected WebElement checkoutNow;
    @FindBy(xpath = "//section[@class='checkout-container']")
    private  WebElement checkoutContainer;
    @FindBy(id = "order-listing")
    protected WebElement orderListing;
    @FindBy(id = "checkout")
    private WebElement checkout;
    @FindBy(id = "breadCrumbs")
    private WebElement breadCrumbs;
    public Checkout() {
        
    }
    public Checkout(WebDriver driver) {
        super(driver);
        Util.waitForPageFullyLoaded(driver);
    }

    public boolean hasErrors() {
        List<WebElement> errors = driver.findElements(By.id("errors"));

        return errors.size() > 0;
    }

    public List<String> getItemsPrice() {
        wait.until(ExpectedConditions.visibilityOf(orderListing));
        List<WebElement> productpricess = orderListing.findElements(By.xpath("//div[contains(@class,'item-price') " +
                "or contains(@class,'item-total')]"));
        List<String> prices = new ArrayList<>(productpricess.size());

        for(WebElement priceElement : productpricess) {
            prices.add(priceElement.getText());
        }

        return prices;

    }
    
    public String getTitle() {
        WebElement title = checkout.findElement(By.className("page-title"));

        return title.getText().trim();
    }

    public String getSubtitle() {
        WebElement title = checkout.findElement(By.className("page-subtitle"));

        return title.getText().trim();
    }

    public int getListingPromoTotal() {
        int subtotal = 0;
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElements(
                orderListing.findElements(By.xpath(".//div[@class='item-row clearfix']"))));

        for (WebElement product : products) {
            boolean addTotal = true;

            WebElement itemTotal = product.findElement(By.className("item-total"));
            List<WebElement> itemSale = product.findElements(By.className("item-sale"));

            if (itemSale.size() > 0) {
                WebElement sale = itemSale.get(0);
                addTotal = !sale.getText().equalsIgnoreCase("excluded from promo");
            }

            if (addTotal) {
                String itemTotalString = itemTotal.getText();
                itemTotalString = itemTotalString.replaceAll("[^0-9]", "");
                subtotal += Integer.parseInt(itemTotalString);
            }
        }

        return subtotal;
    }

    protected String getQuantity(final WebElement productElement) {
        WebElement quantityParentElement = productElement.findElement(By.className("item-quantity"));
        WebElement formAncestor = quantityParentElement.findElement(
                By.xpath(".//ancestor::section[contains(@class,'checkout-container')]//parent::form"));

        String ancestorId = formAncestor.getAttribute("id");
        String quantity = "";

        if ("frm_shopping_cart_continue".equals(ancestorId)) {        	
        	int cntr = 0;
        	do{
        		try{
        			quantity = Util.createWebDriverWait(driver, 20).until(new Function<WebDriver, String>(){
						@Override
						public String apply(WebDriver driver) {
							String qty=null;
							WebElement quantityElement = productElement.findElement(By.xpath(".//*[@class='item-qty' or @class='item-quantity-amount']"));
							
							if(quantityElement.getTagName().equalsIgnoreCase("select")){
								Select quantitySelect = new Select(quantityElement);
								qty =  quantitySelect.getFirstSelectedOption().getText();
							}else{
								qty = quantityElement.getText();
							}
							
		                    if(qty!=null){
		                    	return qty;
		                    }else{
		                    	return null;
		                    }
						}
        			});
        			
        		}
        		catch(NoSuchElementException nsee){
        			cntr++;
        			driver.navigate().refresh();
        		}
        		catch(StaleElementReferenceException sere){
        			cntr++;
        			driver.navigate().refresh();
        		}
        		catch(TimeoutException toe){
        			cntr++;
        			driver.navigate().refresh();
        		}
        		
        		if(!quantity.isEmpty()){
        			break;
        		}
        	}while (cntr<=5);
        } else if ("frmOrderReview".equals(ancestorId)
                || "userMergeCart".equals(ancestorId)) {
            WebElement quantityElement = productElement.findElement(By.className("item-quantity-amount"));
            quantity = quantityElement.getText().trim();
        }
        
        logger.info("Selected quantity is {} ",quantity);

        return quantity;
    }

    public boolean previouslyAdded() {
		List<Product> products = stateHolder.getList("userBag");
        logger.debug("Got {} items previously added", products.size());

        List<WebElement> productsInBag = wait.until(ExpectedConditions.visibilityOfAllElements(
        								 orderListing.findElements(By.xpath(".//div[@class='item-row clearfix']"))));
        
        logger.debug("Got {} items in checkout page excluding gift cards", productsInBag.size());

        boolean result = products.size() == productsInBag.size();

        for (int i = 0; i < products.size() && result; i++) {
            Product fromPDP = products.get(i);
            String productName = fromPDP.getName();
            productName = productName.replaceAll("PRE-ORDER ", "");

            logger.debug("Looking for product {}, item number {}, in size {} in color {} with price {}",
                    productName, fromPDP.getItemNumber(), fromPDP.getSize(), fromPDP.getColor(), fromPDP.getPrice());

            boolean found = false;

            for (int j = 0; j < productsInBag.size() && !found; j++) {
                WebElement productElement = productsInBag.get(j);
                WebElement nameElement = productElement.findElement(By.className("item-name"));
                String name = nameElement.getText().trim();
                name = name.replaceAll("PRE-ORDER ", "");

                String quantity = getQuantity(productElement);

                WebElement priceElement = productElement.findElement(By.className("item-price"));
                String price = priceElement.getText().trim();
                price = price.replaceAll("[^0-9.,]", "");

                List<WebElement> descriptionElements = productElement.findElements(By.className("item-label"));

                WebElement numberElement = descriptionElements.get(0).findElement(By.tagName("span"));
                String itemNumber = numberElement.getText().trim();

                WebElement sizeElement = descriptionElements.get(1).findElement(By.tagName("span"));
                String size = sizeElement.getText();                
                if(!size.toUpperCase().contains("ONE SIZE")){
                	size = sizeElement.getText().replace("SIZE", "").trim();
                }

                WebElement colorElement = descriptionElements.get(2).findElement(By.tagName("span"));
                String color = colorElement.getText().trim();

                logger.debug("Found {} product {}, item number {}, in size {} in color {} with price {} in bag",
                        quantity, name, itemNumber, size, color, price);

                found = name.equalsIgnoreCase(productName)
                        && price.equalsIgnoreCase(fromPDP.getPrice().replaceAll("[^0-9.,]", ""))
                        && size.equalsIgnoreCase(fromPDP.getSize())
                        && color.equalsIgnoreCase(fromPDP.getColor())
                        && itemNumber.equalsIgnoreCase(fromPDP.getItemNumber())
                        && quantity.equalsIgnoreCase(fromPDP.getQuantity());
            }

            result = found;
        }

        logger.debug("Products found in bag: {}", result);

        return result;
    }

    public String getBreadCrumb() {
        WebElement progress = breadCrumbs.findElement(By.className("crumbs-progress"));
        String progressText = progress.getText().trim();

        WebElement breadCrumb = progress.findElement(By.xpath(".//parent::li[contains(@class, 'crumbs-item')]"));
        String breadCrumbText = breadCrumb.getText().trim();

        return breadCrumbText.replace(progressText, "").trim();
    }

    public void nextStep(WebElement form) {
    	
    	WebElement continueButton = null;
    	
    	TestDataReader testDataReader = TestDataReader.getTestDataReader();
    	
    	if(testDataReader.getBoolean("dual.continue.buttons.toggle")){
    		continueButton = form.findElement(By.xpath(".//a[@id='main__button-continue']"));
    	}else{
    		continueButton = form.findElement(By.className("button-submit"));
    	}
    	
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
       // Util.scrollPage(driver, "down");
        continueButton.click();

        wait.until(ExpectedConditions.stalenessOf(continueButton));
    }
    
    public void continueButton(WebElement form) {
    	WebElement continueButton = form.findElement(By.xpath("//a[@id='order-summary__button-continue']"));
    	continueButton.click();
    }

    public abstract boolean isDisplayed();

    public void selectAddressFromList(WebElement form) {
        List<WebElement> addressEntry = form.findElements(By.xpath(".//li[contains(@class,'address-entry') " +
                "and not(@id='address-entry-new')]"));
        int random = Util.randomIndex(addressEntry.size());

        WebElement selectedAddress = addressEntry.get(random);
        WebElement selectedLabel = selectedAddress.findElement(By.tagName("label"));
        selectedLabel.click();
    }
    
    public void selectAddressFromListNoDefault(WebElement form) {
        List<WebElement> addressEntry = form.findElements(By.xpath(".//li[contains(@class,'address-entry') " +
                "and not(@id='address-entry-new') and not(@id='address-default')]"));
        int random = Util.randomIndex(addressEntry.size());

        WebElement selectedAddress = addressEntry.get(random);
        stateHolder.put("selectedshippingAddress", selectedAddress.getText());
        WebElement selectedLabel = selectedAddress.findElement(By.tagName("label"));
        selectedLabel.click();
    }

    public boolean giftCardsInBag(){
    	if(!stateHolder.hasKey("giftCardsToBag"))
    		return true;
    	
		List<GiftCard> giftCards = stateHolder.getList("giftCardsToBag");
        logger.debug("{} gift card(s) added to bag", giftCards.size());

        return matchGiftCardList(giftCards);
    }
    
    public boolean matchGiftCardList(List<GiftCard> expectedGiftCards) {
        List<WebElement> giftCardsInBag = wait.until(ExpectedConditions.visibilityOfAllElements(
        								 orderListing.findElements(By.xpath(".//div[contains(@class,'item-gc') or contains(@class,'item-egc')]"))));
        
        logger.debug("Got {} gift card(s) in checkout page", giftCardsInBag.size());

        boolean result = expectedGiftCards.size() == giftCardsInBag.size();

        for (int i = 0; i < expectedGiftCards.size() && result; i++) {
        	GiftCard fromGCPage = expectedGiftCards.get(i);
            String giftCardName = fromGCPage.getGiftCardName();

            logger.debug("Looking for gift card '{}', amount '{}', sender '{}', recipient '{}', recipient email address '{}'",
               		      giftCardName, fromGCPage.getGiftCardAmount(), fromGCPage.getSenderName(), fromGCPage.getRecipientName(), fromGCPage.getRecipientEmailAddress());

            boolean found = false;

            for (int j = 0; j < giftCardsInBag.size() && !found; j++) {
                WebElement giftCardElement = giftCardsInBag.get(j);
                WebElement nameElement = giftCardElement.findElement(By.className("item-name"));
                String name = nameElement.getText().trim();

                WebElement priceElement = giftCardElement.findElement(By.className("item-price"));
                String price = priceElement.getText().trim();
                price = price.replaceAll("[^0-9.,]", "");
                price = price.replace(".00", "");

                List<WebElement> descriptionElements = giftCardElement.findElements(By.className("item-label"));

                WebElement fromElement = descriptionElements.get(0).findElement(By.tagName("span"));
                String fromText = fromElement.getText().trim();

                WebElement toElement = descriptionElements.get(1).findElement(By.tagName("span"));
                String toText = toElement.getText().trim();
                
                WebElement recipientEmailElement = descriptionElements.get(2).findElement(By.tagName("span"));
                String recipientEmailText = recipientEmailElement.getText().trim();
                
                WebElement dateElement = null;
                WebElement giftMessageElement = null;                
                String expectedgiftMessage = null;
                
                switch(giftCardName.toUpperCase()){
                	case "J.CREW GIFT CARD":
                		giftMessageElement = descriptionElements.get(3).findElement(By.tagName("span"));
                		expectedgiftMessage = fromGCPage.getLine1() + "\n" +fromGCPage.getLine2(); 
                		break;
                	case "J.CREW E-GIFT CARD":
                		dateElement = descriptionElements.get(3).findElement(By.tagName("span"));
                		giftMessageElement = descriptionElements.get(4).findElement(By.tagName("span"));
                		expectedgiftMessage = fromGCPage.getGiftMessage();
                		break;
                }
                
                String giftMessage = giftMessageElement.getText().trim();
                found = expectedgiftMessage.equalsIgnoreCase(giftMessage);
                
                String dateToBeSent;
                if(dateElement!=null){
                	dateToBeSent = dateElement.getText().trim();
                	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");

                	Date date = null;
                	try {
                		  date = dateFormat.parse(dateToBeSent);
                	} catch (ParseException e) {
                		e.printStackTrace();
                		fail("Failed to parse date " + dateToBeSent);
                	}	
                	
                	found &= date.compareTo(fromGCPage.getDateToBeSent())==0;
                }

                logger.debug("Found gift card '{}', amount '{}', sender '{}', recipient '{}', recipient email address '{}' in bag",
                              name, price, fromText, toText, recipientEmailText);

                found &= name.equalsIgnoreCase(giftCardName)
                         && price.equalsIgnoreCase(fromGCPage.getGiftCardAmount().replaceAll("[^0-9.,]", ""))
                         && fromText.equalsIgnoreCase(fromGCPage.getSenderName())
                         && toText.equalsIgnoreCase(fromGCPage.getRecipientName())
                         && recipientEmailText.equalsIgnoreCase(fromGCPage.getRecipientEmailAddress());
            }

            result = found;
        }

        logger.debug("Gift card found in bag: {}", result);

        return result;
    }
    
    public void selectMultipleShippingAddressRadioButton(WebElement checkoutFormElement){

    	WebElement multipleAddressRadioBtn = checkoutFormElement.findElement(By.xpath("//div[@class='multiShippingAddresses_container']/label"));
    	multipleAddressRadioBtn.click();
    	Util.waitForPageFullyLoaded(driver);
    	Util.waitLoadingBar(driver);
    	/*checkoutFormElement.click();
    	Util.waitForPageFullyLoaded(driver);
    	Util.waitLoadingBar(driver);*/
    	
    }
    
    public void handleQAS(){
    	WebElement qasModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-qas")));
    	logger.debug("QAS is displayed for entered address...");
    	WebElement useAddressAsEntered = qasModal.findElement(By.xpath(".//a[@class='button-submit']"));
    	useAddressAsEntered.click();
    	logger.debug("QAS is handled by clicking on 'USE ADDRESS AS ENTERED' button...");
    }


}