package com.jcrew.page;

import com.google.common.base.Function;
import com.jcrew.pojo.GiftCard;
import com.jcrew.pojo.Product;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
public abstract class Checkout extends PageObject{

    @FindBy(xpath = "//section[@class='checkout-container']")
    private  WebElement checkoutContainer;
    @FindBy(id = "order-summary")
    private WebElement order_summary;
    @FindBy(id = "order-listing")
    protected WebElement orderListing;
    @FindBy(id="zipcode")
    private WebElement zipCode;
    @FindBy(id = "checkout")
    private WebElement checkout;
    @FindBy(id = "order-listing")
    protected WebElement order__listing;
    @FindBy(id = "breadCrumbs")
    private WebElement breadCrumbs;
    @FindBy(id = "promoCodeContainer")
    private WebElement promoCode;
    @FindBy(id = "orderSummaryContainer")
    protected WebElement orderSummary;

    public Checkout(WebDriver driver) {
        super(driver);
        Util.waitForPageFullyLoaded(driver);
        PageFactory.initElements(driver, this);
    }

    public float getOrderTotal() {
        String total = getEstimatedTotal();

        float fTotal = CurrencyChecker.getPrice(total, country);

        logger.info("Order total is {}", fTotal);
        return fTotal;

    }

    public String getEstimatedTotal() {
        wait.until(ExpectedConditions.visibilityOf(order_summary));
        WebElement estimatedTotal = order_summary.findElement(By.className("summary-total"));
        WebElement estimatedTotalvalue = estimatedTotal.findElement(By.className("summary-value"));

        return estimatedTotalvalue.getText();
    }

    public String getSubtotal() {
        wait.until(ExpectedConditions.visibilityOf(order_summary));
        WebElement subTotal = order_summary.findElement(By.className("summary-subtotal"));
        WebElement subTotalvalue = subTotal.findElement(By.className("summary-value"));

        return subTotalvalue.getText();
    }

    public String getEstimatedShipping() {
        wait.until(ExpectedConditions.visibilityOf(order_summary));
        WebElement estimatedShipping = order_summary.findElement(By.className("summary-shipping"));
        WebElement estimatedShippingvalue = estimatedShipping.findElement(By.className("summary-value"));

        return estimatedShippingvalue.getText();
    }

    public boolean hasErrors() {
        List<WebElement> errors = driver.findElements(By.id("errors"));

        return errors.size() > 0;
    }

    public boolean orderNumberIsVisible() {
        boolean result;
        WebElement confirmation = driver.findElement(By.id("confirmation-number"));

        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String env = propertyReader.getProperty("environment");

        if (!"production".equals(env)) {
            result = confirmation.isDisplayed();
        } else {
            logger.info("Trying to place an order in production, ignoring");
            result = true;
        }

        return result;
    }

    public boolean isOrderConfirmationPage() {
        try {
            WebElement confirmation = driver.findElement(By.id("confirmation-number"));
            return confirmation.isDisplayed();
        } catch (NoSuchElementException noConfirmationNumber) {
            return false;
        }
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

        return matchList(products);
    }

    public boolean itemsInBag() {    	
    	if(!stateHolder.hasKey("toBag"))
    		return true;
    	
		List<Product> products = stateHolder.getList("toBag");
        logger.debug("Got {} items in bag", products.size());

        return matchList(products);
    }

    public boolean matchList(List<Product> products) {
        List<WebElement> productsInBag = wait.until(ExpectedConditions.visibilityOfAllElements(
        								 order__listing.findElements(By.xpath(".//div[@class='item-row clearfix']"))));
        
        logger.debug("Got {} items in checkout page excluding gift cards", productsInBag.size());

        boolean result = products.size() == productsInBag.size();

        for (int i = 0; i < products.size() && result; i++) {
            Product fromPDP = (Product) products.get(i);
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

        WebElement breadCrumb = progress.findElement(By.xpath(".//parent::li[@class='crumbs-item' or @class='crumbs-item ']"));
        String breadCrumbText = breadCrumb.getText().trim();

        return breadCrumbText.replace(progressText, "").trim();
    }

    public void nextStep(WebElement form) {
        WebElement continueButton = form.findElement(By.className("button-submit"));
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        Util.scrollPage(driver, "down");
        continueButton.click();

        wait.until(ExpectedConditions.stalenessOf(continueButton));
    }

    public abstract boolean isDisplayed();

    public boolean promoSection() {
        return promoCode.isDisplayed();
    }

    public void addPromoCode(String code) {
        WebElement promoHeader = promoCode.findElement(By.id("summary-promo-header"));
        Util.scrollToElement(driver, promoHeader);
        promoHeader.click();

        WebElement promoCodeField = promoCode.findElement(By.id("promotionCode1"));
        promoCodeField.clear();
        promoCodeField.sendKeys(code);
        stateHolder.put("promocode", code);

        WebElement apply = promoCode.findElement(By.id("promoApply"));
        Util.scrollPage(driver, "down");
        apply.click();

        wait.until(ExpectedConditions.stalenessOf(promoCodeField));
        wait.until(ExpectedConditions.visibilityOf(promoCode));
    }
    
    public void addPromoCode(String code, String page) {
    	
    	String pageTitle = driver.getTitle().toLowerCase();
    	
    	if(pageTitle.contains(page.toLowerCase())){    	
	        WebElement promoHeader = promoCode.findElement(By.id("summary-promo-header"));
	        Util.scrollToElement(driver, promoHeader);
	        promoHeader.click();
	
	        WebElement promoCodeField = promoCode.findElement(By.id("promotionCode1"));
	        promoCodeField.clear();
	        promoCodeField.sendKeys(code);
	        stateHolder.put("promocode", code);
	
	        WebElement apply = promoCode.findElement(By.id("promoApply"));
	        Util.scrollPage(driver, "down");
	        apply.click();
	
	        wait.until(ExpectedConditions.stalenessOf(promoCodeField));
	        wait.until(ExpectedConditions.visibilityOf(promoCode));
    	}
    }

    

    public String getPromoCodeMessage() {
        wait.until(ExpectedConditions.visibilityOf(promoCode));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("promoCodeMessage")));

        return message.getText();
    }

    public String getPromoName() {
        WebElement message = promoCode.findElement(By.className("module-name"));

        return message.getText();
    }

    public String getPromoDetails() {
        WebElement message = wait.until(ExpectedConditions.visibilityOf(promoCode.findElement(By.className("module-details-last"))));
        return message.getText();
    }

    protected String getSummaryText(String field) {
        By xpath;
        switch (field) {
            case "subtotal":
                xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'SUBTOTAL')]/following-sibling::*");
                break;
            case "estimated total":
                xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'Estimated Total')]/following-sibling::*");
                break;
            case "estimated tax":
                xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'Estimated Tax')]/following-sibling::*");
                break;
            case "estimated shipping":
            	xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'Estimated Shipping')]/following-sibling::*");
            	break;
            case "promo":
                xpath = By.xpath(".//li[contains(@class,'summary-promo')]/span[contains(@class,'summary-value')]");
                break;
            case "shipping":
            	xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'Shipping')]/following-sibling::*");
                break;
            case "total":
            	xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'Total')]/following-sibling::*");
                break;    
            default:
                throw new WebDriverException(field + " field on the checkout page is not recognized!!!");
        }
        
    	WebElement span = orderSummary.findElement(xpath);
    	String text = span.getText();
        logger.debug("Returning summary text {}", text);

        return text;
    }

    public String getSubTotal() {
        return getSummaryText("subtotal");
    }
    
    public String getPromoDiscount() {
        return getSummaryText("promo");
    }

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
    
    public WebElement getPromoRemoveElement(){
    	WebElement removeElement = promoCode.findElement(By.className("item-remove"));
    	return removeElement;
    }
    
    public WebElement getPromoMessageElementFromOrderSummary(){
    	WebElement promoMessageElement = orderSummary.findElement(By.xpath("//span[@class='summary-label' and text()='" + stateHolder.get("promoMessage") + "']"));
    	return promoMessageElement;
    }
    
    public boolean isPromoCodeApplied(String promoCodeText){
    	try{
    		 WebElement appliedPromoCodeElement = promoCode.findElement(By.xpath(".//span[@class='module-name' and contains(text(), '" + promoCodeText.toUpperCase() + "')]"));
    		 return appliedPromoCodeElement.isDisplayed();
    	}
    	catch(NoSuchElementException nsee){
    		return false;
    	}
    }
    
    public boolean isPromoCodeApplied(String promoCodeText, String state){
    	try{
    		WebElement appliedPromoCodeElement = null;
        	 if(state.equalsIgnoreCase("active")){
        		 appliedPromoCodeElement = promoCode.findElement(By.xpath(".//span[@class='module-name' and contains(text(), '" + promoCodeText.toUpperCase() + "')]"));
        	 }else{
        		 appliedPromoCodeElement = promoCode.findElement(By.xpath(".//span[@class='module-name-inactive' and contains(text(), '" + promoCodeText.toUpperCase() + "')]"));
        	 }
        	 
    		 return appliedPromoCodeElement.isDisplayed();
    	}
    	catch(NoSuchElementException nsee){
    		return false;
    	}
    }
    
    public int getAppliedPromoCodesCount(){    	
    	try{
    		 List<WebElement> appliedPromoCodeElements = promoCode.findElements(By.xpath(".//span[@class='module-name']"));
    		 return appliedPromoCodeElements.size();
    	}
    	catch(NoSuchElementException nsee){
    		return -1;
    	}
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
        								 order__listing.findElements(By.xpath(".//div[contains(@class,'item-gc') or contains(@class,'item-egc')]"))));
        
        logger.debug("Got {} gift card(s) in checkout page", giftCardsInBag.size());

        boolean result = expectedGiftCards.size() == giftCardsInBag.size();

        for (int i = 0; i < expectedGiftCards.size() && result; i++) {
        	GiftCard fromGCPage = (GiftCard) expectedGiftCards.get(i);
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
    	WebElement multipleAddressRadioBtn = checkoutFormElement.findElement(By.id("multiShippingAddresses"));
    	multipleAddressRadioBtn.click();
    	
    	Util.waitForPageFullyLoaded(driver);
    	Util.waitLoadingBar(driver);
    }
    
    public void handleQAS(){
    	WebElement qasModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-qas")));
    	logger.debug("QAS is displayed for entered address...");
    	WebElement useAddressAsEntered = qasModal.findElement(By.xpath(".//a[@class='button-submit']"));
    	useAddressAsEntered.click();
    	logger.debug("QAS is handled by clicking on 'USE ADDRESS AS ENTERED' button...");
    }
    
    public boolean isPromoTextBoxDisplayed(){
    	try{
    		return promoCode.findElement(By.id("promotionCode1")).isDisplayed();
    	}
    	
    	catch(Exception e){
    		return false;
    	}
    	
    	 
    }
    public void addZipCode(String code) {
        wait.until(ExpectedConditions.visibilityOf(zipCode));
        zipCode.sendKeys(code);
    }
    
    
    public String getZipCodeMessage() {
        wait.until(ExpectedConditions.visibilityOf(checkoutContainer));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("summary-zipcode-message")));
        return message.getText();
    }	
   
}
