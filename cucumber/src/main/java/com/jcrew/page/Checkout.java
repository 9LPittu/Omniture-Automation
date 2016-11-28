package com.jcrew.page;

import com.jcrew.pojo.Product;
import com.jcrew.util.CurrencyChecker;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public abstract class Checkout extends PageObject{

    @FindBy(xpath = "//section[@class='checkout-container']")
    private  WebElement checkoutContainer;
    @FindBy(id = "order-summary")
    private WebElement order_summary;
    @FindBy(id = "order-listing")
    protected WebElement orderListing;
    
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

    protected String getQuantity(WebElement productElement) {
        WebElement quantityParentElement = productElement.findElement(By.className("item-quantity"));
        WebElement formAncestor = quantityParentElement.findElement(
                By.xpath(".//ancestor::section[contains(@class,'checkout-container')]//parent::form"));

        String ancestorId = formAncestor.getAttribute("id");
        String quantity = "";

        if ("frm_shopping_cart_continue".equals(ancestorId)) {
            WebElement quantityElement = productElement.findElement(By.className("item-qty"));
            Select quantitySelect = new Select(quantityElement);
            quantity = quantitySelect.getFirstSelectedOption().getText();
        } else if ("frmOrderReview".equals(ancestorId)
                || "userMergeCart".equals(ancestorId)) {
            WebElement quantityElement = productElement.findElement(By.className("item-quantity-amount"));
            quantity = quantityElement.getText().trim();
        }

        return quantity;
    }

    public boolean previouslyAdded() {
		List<Product> products = stateHolder.getList("userBag");
        logger.debug("Got {} items previously added", products.size());

        return matchList(products);
    }

    public boolean itemsInBag() {
		List<Product> products = stateHolder.getList("toBag");
        logger.debug("Got {} items in bag", products.size());

        return matchList(products);
    }

    public boolean matchList(List<Product> products) {
        List<WebElement> productsInBag = wait.until(ExpectedConditions.visibilityOfAllElements(
        								 order__listing.findElements(By.className("item-row"))));
        
        logger.debug("Got {} items in checkout page", productsInBag.size());

        boolean result = products.size() == productsInBag.size();

        for (int i = 0; i < products.size() && result; i++) {
            Product fromPDP = (Product) products.get(i);
            String productName = fromPDP.getProductName();
            productName.replaceAll("PRE-ORDER ", "");

            logger.debug("Looking for product {}, item number {}, in size {} in color {} with price {}",
                    productName, fromPDP.getProductCode(), fromPDP.getSelectedSize(), fromPDP.getSelectedColor(), fromPDP.getPriceList());

            boolean found = false;

            for (int j = 0; j < productsInBag.size() && !found; j++) {
                WebElement productElement = productsInBag.get(j);
                WebElement nameElement = productElement.findElement(By.className("item-name"));
                String name = nameElement.getText().trim();
                name = name.replaceAll("PRE-ORDER ", "");
                name = name.replaceAll("Pre-order ", "");

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
                
                String pdpPrice = fromPDP.getPriceList();
                pdpPrice = pdpPrice.replaceAll("[^0-9.,]", "");

                found = name.equalsIgnoreCase(productName)
                        && price.equalsIgnoreCase(pdpPrice)
                        && size.equalsIgnoreCase(fromPDP.getSelectedSize())
                        && color.equalsIgnoreCase(fromPDP.getSelectedColor())
                        && itemNumber.equalsIgnoreCase(fromPDP.getProductCode());
            }

            result = found;
        }

        logger.debug("Products found in bag: {}", result);

        return result;
    }

    public String getBreadCrumb() {
        WebElement progress = breadCrumbs.findElement(By.className("crumbs-progress"));
        String progressText = progress.getText().trim();

        WebElement breadCrumb = progress.findElement(By.xpath(".//parent::li[@class='crumbs-item']"));
        String breadCrumbText = breadCrumb.getText().trim();

        return breadCrumbText.replace(progressText, "").trim();
    }

    public void nextStep(WebElement form) {
        WebElement continueButton = form.findElement(By.className("button-submit"));
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

        WebElement apply = promoCode.findElement(By.id("promoApply"));
        apply.click();

        wait.until(ExpectedConditions.stalenessOf(promoCodeField));
        wait.until(ExpectedConditions.visibilityOf(promoCode));
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
}
