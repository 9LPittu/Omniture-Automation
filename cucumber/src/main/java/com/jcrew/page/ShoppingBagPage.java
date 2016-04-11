package com.jcrew.page;

import java.util.List;


import com.google.common.base.Predicate;
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
        return orderListing.findElement(By.xpath(".//a[contains(" + Util.xpathGetTextLower + "," +
                "translate(\"" + productName.replace(" (Pre-order)", "").replaceAll("&amp;", "&") +
                "\", 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz'))]/../../.."));
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
}