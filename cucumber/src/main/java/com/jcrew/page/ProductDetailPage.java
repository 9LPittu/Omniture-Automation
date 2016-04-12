package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ProductDetailPage {

    private final WebDriver driver;
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final Logger logger = LoggerFactory.getLogger(ProductDetailPage.class);

    @FindBy(id = "btn__add-to-bag")
    private WebElement addToBag;

    @FindBy(id = "btn__wishlist")
    private WebElement wishList;

    @FindBy(id = "variants")
    private WebElement productDetailsVariantsSection;

    @FindBy(id = "c-product__variations")
    private WebElement productVariationSection;

    @FindBy(id = "c-product__sizes")
    private WebElement productSizesSection;

    @FindBy(className = "product__name")
    private WebElement productName;

    @FindBy(id = "c-product__quantity")
    private WebElement productQuantitySection;

    @FindBy(className = "primary-nav__item--bag")
    private WebElement bagContainer;

    @FindBy(id = "global__footer")
    private WebElement footer;

    @FindBy(className = "product__price--sale")
    private WebElement salePrice;

    @FindBy(id = "c-product__details")
    private WebElement productDetails;

    @FindBy(id = "c-product__overview")
    private WebElement productOverview;

    @FindBy(id = "pdpMainImg0")
    private WebElement productImage;

    @FindBy(id = "c-product__actions")
    private WebElement productActionsSection;

    @FindBy(className = "message--headline")
    private WebElement messageHeadline;

    @FindBy(className = "message--body")
    private WebElement messageBody;
    
    @FindBy(css=".btn--link.btn--checkout.btn--primary")
    private WebElement minicartCheckout;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isProductDetailPage() {
        Country country = (Country) stateHolder.get("context");
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productName));
        boolean isURL = Util.countryContextURLCompliance(driver, country);

        return productName.isDisplayed() && StringUtils.isNotBlank(productName.getText()) && isURL;
    }
    
    
	public boolean isProductNamePriceListMatchesWithArrayPage(){

        String pdpProductNameString = getProductNameFromPDP();
        String pdpProductPriceString = getProductPriceList();
    	
		@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");

        logger.debug("Looking for: {} - {}", pdpProductNameString, pdpProductPriceString);

        for(Product product:productList){
            String productName = product.getProductName();
            String productPrice = product.getPriceList();
            if(productName.equalsIgnoreCase(pdpProductNameString) && productPrice.equals(pdpProductPriceString)){
                return true;
            }
        }

		return false;
    }

    public void select_variation() {
        List<WebElement> variations = productVariationSection.findElements(By.tagName("input"));

        if (!variations.isEmpty()) {
            WebElement variation = variations.get(0);

            if (variation.isSelected()) {
                logger.debug("Variation is already selected");
            } else {
                Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(variation));
                variation.click();
            }
        }
    }

    public void select_quantity(String quantity) {
        Select quantitySelect = getQuantitySelector();
        quantitySelect.selectByValue(quantity);

    }

    private Select getQuantitySelector() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productQuantitySection));
        WebElement quantitySelectWebElement = productQuantitySection.findElement(By.className("dropdown--quantity"));
        return new Select(quantitySelectWebElement);
    }

    public boolean isWishlistButtonPresent() {
        boolean result = false;
        if (!productActionsSection.findElements(By.id("btn__add-to-bag")).isEmpty()) {
            result = wishList.isDisplayed();
        }
        return result;
    }

    public void click_add_to_cart() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(addToBag));
        addToBag.click();
    }

    public int getNumberOfItemsInBag() {
        WebElement bagSize = bagContainer.findElement(By.className("js-cart-size"));
        
        Util.waitWithStaleRetry(driver, bagSize);

        String bagSizeStr = bagSize.getAttribute("innerHTML");
        String stringSize = bagSizeStr.replace("(", "").replace(")", "").trim();
        return Integer.parseInt(stringSize);
    }

    public boolean showsMinicartMessage(String message) {
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.textToBePresentInElementLocated(By.className("header__cart--details"), message));
        return true;
    }

    public String getSalePrice() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(salePrice));
        return salePrice.getText();
    }

    public void select_size(String productSize) {
        WebElement productSizeElement = getProductSizeElement(productSize);
        productSizeElement.click();        
    }

    private WebElement getProductSizeElement(String productSize) {

        return Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(productSizesSection.findElement(
                        By.xpath(".//li[@data-name='" + productSize + "']"))));
    }

    public void select_color(String productColor) {
        WebElement productColorElement = getProductColorElement(productColor);
        productColorElement.click();
    }

    private WebElement getProductColorElement(String productColor) {
        WebElement productColors = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("c-product__price-colors")
                )
        );
        WebElement productColorElement = productColors.findElement(By.xpath(".//li[@data-name='" + productColor + "']"));

        return productColorElement;
    }

    public String getSelectedColor() {
        WebElement productColorContainer = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("c-product__price-colors")));
        WebElement productColorElement = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productColorContainer.findElement(By.className("is-selected"))));
        return productColorElement.getAttribute("data-name");
    }

    public String getSelectedSize() {
        WebElement productSizeElement = productSizesSection.findElement(By.className("is-selected"));
        return productSizeElement.getAttribute("data-name");
    }

    public String getBagButtonText() {
        return addToBag.getText();
    }

    public void click_update_cart() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElement(addToBag, "UPDATE BAG"));
        Util.clickWithStaleRetry(addToBag);
    }

    public String getSelectedVariationName() {
        WebElement selectedElement = productVariationSection.findElement(By.className("is-selected"));
        WebElement productVariationNameElement = selectedElement.findElement(By.className("product__variation--name"));
        return productVariationNameElement.getText();
    }

    public String getSelectedQuantity() {
        Select quantitySelect = getQuantitySelector();
        String text = quantitySelect.getFirstSelectedOption().getText();
        if ("".equals(text)) {
            text = "1";
        }
        return text;
    }

    public String getWishlistButtonMessage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(wishList));
        return wishList.getText();
    }

    public void click_wishlist() {
        wishList.click();
    }

    public void go_to_wishlist() {
        WebElement wishlistConfirmation = Util.createWebDriverWait(driver).until(
                ExpectedConditions.presenceOfElementLocated(By.className("wishlist-confirmation-text")));
        wishlistConfirmation.findElement(By.tagName("a")).click();
        Util.waitLoadingBar(driver);
    }

    public boolean isSizeSelectorSectionPresent() {
        return !productSizesSection.findElements(By.className("product__sizes")).isEmpty();
    }

    public boolean isColorSelectorSectionPresent() {
        return !driver.findElement(By.id("c-product__price-colors")).
                findElements(By.className("product__price-colors")).isEmpty();
    }

    public boolean isQuantitySelectorSectionPresent() {
        return !productQuantitySection.findElements(By.className("product__quantity")).isEmpty();
    }

    public boolean isAddToBagButtonPresent() {
        return !productActionsSection.findElements(By.id("btn__add-to-bag")).isEmpty();
    }

    public String getHeadlineMessage() {
        return messageHeadline.getText();
    }

    public String getBodyMessage() {
        return messageBody.getText();
    }

    public String getProductNameFromPDP() {
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(productOverview));
        return productOverview.findElement(By.tagName("h1")).getText();
    }

    public String getFirstProductNameFromMultiPDP() {
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(productOverview));
        return productOverview.findElements(By.tagName("h1")).get(0).getText();
    }
    public String getProductImageSourceFromPDP() {
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(productImage));
        return productImage.getAttribute("src");
    }

    public String getProductPriceList() {
        String productListPrice = "";
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productDetails));
        if (getVariationsNames().isEmpty()) {
            productListPrice = productDetails.findElement(By.className("product__price--list")).getText();
        } else {
            List<WebElement> prices = productDetails.findElements(By.className("product__price--list"));
            for (WebElement price:prices) {
                if(price.isDisplayed()) {
                    return price.getText();
                }
            }
        }
        return productListPrice;
    }


    public boolean isPreOrderButtonDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(addToBag));
        return addToBag.isDisplayed();
    }

    public List<String> getVariationsNames() {
        List<WebElement> variationsList = productVariationSection.findElements(By.className("product__variation--name"));
        List<String> variationsListString = new ArrayList<>();
        for (WebElement variation : variationsList) {
            variationsListString.add(variation.getAttribute("innerHTML"));
        }
        return variationsListString;
    }

    public int getNumberOfColors() {
        return driver.findElement(By.id("c-product__price-colors")).
                findElements(By.className("colors-list__item")).size();
    }

    public String getProductPriceSale() {
        return productDetails.findElement(By.className("product__price--sale")).getText();
    }

    public String getProductPriceWas() {
        return productDetails.findElement(By.className("product__price--list")).getText();
    }

    public void select_random_color() {
        try {
            List<WebElement> colorsList = Util.createWebDriverWait(driver).until(
                    ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='c-product__price-colors']")))).
                    findElements(By.xpath("//li[contains(@class,'colors-list__item')]"));

            if (!colorsList.isEmpty()) {
                int index = Util.randomIndex(colorsList.size());
                WebElement color = colorsList.get(index);
                Product product = Util.getCurrentProduct();
                String colorName = color.getAttribute("data-name");
                product.setSelectedColor(colorName);
                color.click();
                logger.info("Selected color name: {}", colorName);
                setSalePriceIfPresent(color, product);
            }

        } catch (StaleElementReferenceException sere) {
            select_random_color();
        }
    }

    private void setSalePriceIfPresent(WebElement color, Product product) {
        try {
            product.setPriceSale(salePrice.getText());

        } catch (StaleElementReferenceException sere) {

            setSalePriceIfPresent(color, product);

        } catch (NoSuchElementException nsee) {
            logger.debug("There was no sale price for color: " + color.getAttribute("data-name"));
        }
    }

    public void select_random_size() {
        List<WebElement> allSizes = productSizesSection.findElements(By.className("sizes-list__item"));
        List<WebElement> selectableSizes = new ArrayList<>();
        for (WebElement size : allSizes) {
            if (!size.getAttribute("class").contains("is-unavailable")) {
                selectableSizes.add(size);
            }
        }

        if (!selectableSizes.isEmpty()) {
            int index = Util.randomIndex(selectableSizes.size());
            WebElement size = selectableSizes.get(index);
            Product product = Util.getCurrentProduct();
            String sizeName = size.getAttribute("data-name");
            product.setSelectedSize(sizeName);
            size.click();
            logger.info("Selected size name: {}", sizeName);
        }
    }

    public String getButtonErrorMessage() {
        return productActionsSection.findElement(By.className("product__message")).getText();
    }

    public boolean isBagButtonText(String text) {
        return Util.createWebDriverWait(driver).until(
                ExpectedConditions.textToBePresentInElement(addToBag, text));
    }

    public boolean isWishlistConfirmationMessageDisplayed() {
        return productActionsSection.findElement(By.className("content-button-secondary-confirmation")).isDisplayed();
    }
    
    public void clickMinicartCheckout(){
        String currentURL = driver.getCurrentUrl();

        try {
            minicartCheckout.click();
        } catch (NoSuchElementException noMiniCart){
            logger.debug("Minicart not found, so going through bag button");
            bagContainer.click();
        }

        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
    }
    
    public boolean isPreviouslySelectedColorStillDisplayedAsSelected(){

    	String currentSelectedColor = getSelectedColor().toLowerCase();
    	logger.debug("Current selected color in application: {}", currentSelectedColor);

    	@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");
    	Product product = productList.get(0);
    	String expectedColorName = product.getSelectedColor();
    	logger.debug("Expected color to be in selection: {}", expectedColorName);

    	return expectedColorName.equalsIgnoreCase(currentSelectedColor);
    }

    public boolean isPreviouslySelectedSizeStillDisplayedAsSelected(){

    	String currentSelectedSize = getSelectedSize().toLowerCase();
    	logger.debug("Current selected size in application: {}", currentSelectedSize);

    	@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");
    	Product product = productList.get(0);
    	String expectedSizeName = product.getSelectedSize();
    	logger.debug("Expected size to be in selection: {}", expectedSizeName);

    	return expectedSizeName.equalsIgnoreCase(currentSelectedSize);
    }

	public void selectNewColor(){

    	List<WebElement> itemColors = driver.findElements(By.xpath("//li[contains(@class,'js-product__color colors-list__item') and not(contains(@class,'is-selected'))]"));
    	int randomIndex = Util.randomIndex(itemColors.size());
    	itemColors.get(randomIndex).findElement(By.tagName("img")).click();
		String newSelectedColor = driver.findElement(By.className("product__value")).getText().toLowerCase();

    	logger.debug("Selected new item color: {}", newSelectedColor);
    	@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");
    	Product product = productList.get(0);
    	product.setSelectedColor(newSelectedColor);

        productList.add(product);
        stateHolder.put("productList", productList);
    }

    public void selectNewSize(){

    	List<WebElement> itemSizes = driver.findElements(By.xpath("//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-selected'))]"));
    	int randomIndex = Util.randomIndex(itemSizes.size());
    	itemSizes.get(randomIndex).findElement(By.tagName("span")).click();
		String newSelectedSize = itemSizes.get(randomIndex).getAttribute("data-name").toLowerCase();
    	
    	logger.debug("Selected new item size: {}", newSelectedSize);
    	@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");
    	Product product = productList.get(0);
    	product.setSelectedSize(newSelectedSize);

        productList.add(product);
        stateHolder.put("productList", productList);
    }

    public boolean isproductVariantSectionPresent() {

    	try{
    		List<WebElement> variants = productDetailsVariantsSection.findElements(By.className("product-details-variants"));
    	    return true;
    	}
    	catch(Exception e){
    		return false;
    	}
    }

    public boolean isVariantRadioButtonisSelected(String variant) {
        boolean result = false;
        try {
            WebElement selectedRadioButton = productDetailsVariantsSection.findElement(By.xpath("//input[@checked]"));
            WebElement selectedVariant = selectedRadioButton.findElement(By.xpath(".//ancestor::div[contains(@class,'variant-wrapper')]/div[@class='product-pricing']/span"));
            String selectedVariantName = selectedVariant.getText();
            if (variant.equalsIgnoreCase(selectedVariantName)) {
                result = true;
                logger.debug("Regular Variation is selected");
            }
        }
        catch(Exception e) {
            result = false;
        }
        return result;
   }

    public void validate_extended_size_on_pdp_page_is_displayed(){
		if (isproductVariantSectionPresent()){
            String variant = "Regular";
			if(isVariantRadioButtonisSelected(variant)){
				logger.info("Variant section is present and default Regular variant is selected on PDP page ");
			}
			else{
				  logger.error("Variant section is present but Default Regular variant is not selected on PDP page");
			}
		}
		else{
		  	  logger.info("Variants are not present on PDP page");
		}

	}
    }
