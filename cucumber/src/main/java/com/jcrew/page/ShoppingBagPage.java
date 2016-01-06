package com.jcrew.page;

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
    
    @FindBy(className="breadcrumb__link")
    private WebElement breadcrumbLink;

    public ShoppingBagPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }


    public void click_checkout_button() {
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(checkoutLink));

        checkoutLink.click();
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
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(articleCheckout));
        return true;
    }

    public void click_edit_button() {
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(editAction));
        editAction.click();
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
        WebElement selectedElement = productRoot.findElement(By.xpath(".//span[text() = '" + element + "']"));
        return selectedElement.isDisplayed();
    }

    private WebElement getProductRoot(String productName) {
        return orderListing.findElement(By.xpath(".//a[contains(translate(text(), 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz')," +
                "translate(\"" + productName.replace(" (Pre-order)", "").replaceAll("&amp;", "&") + "\", 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz'))]/../../.."));
    }

    public String getPriceDisplayedForProduct(String productName) {
        WebElement productRoot = getProductRoot(productName);
        return productRoot.findElement(By.className("item-price")).getText().trim();
    }
    
    public boolean isPageTitleContains(String pageTitle){
    	return driver.getTitle().trim().toLowerCase().contains(pageTitle.toLowerCase());
    }
    
    public boolean isBagItemsCountMatches(int itemsCount){
    	String bagItemsCount = cartSize.getText().trim();
    	bagItemsCount = bagItemsCount.replace("(", "");
    	bagItemsCount = bagItemsCount.replace(")", "");
    	int actualItemsCount = Integer.parseInt(bagItemsCount);
    	
    	return actualItemsCount == itemsCount;
    }
    
    public boolean isBreadcrumbTextMatches(String breadcrumbText){
    	return breadcrumbLink.getText().trim().equalsIgnoreCase(breadcrumbText);
    }
}
