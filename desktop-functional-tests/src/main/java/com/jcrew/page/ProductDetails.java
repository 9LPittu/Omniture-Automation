package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ProductDetails {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(ProductDetails.class);
    private final WebDriverWait wait;
    private final StateHolder holder = StateHolder.getInstance();
    private final HeaderWrap headerWrap;
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final String PRICE_SALE_CLASS = "product__price--sale";
    private final String PRICE_LIST_CLASS = "product__price--list";

    @FindBy(id = "c-product__price-colors")
    private WebElement price_colors;
    @FindBy(className = "c-product__colors")
    private WebElement c_product_colors;
    @FindBy(id = "c-product__sizes")
    private WebElement sizes;
    @FindBy(id = "c-product__price")
    private WebElement price;
    @FindBy(id = "c-product__variations")
    private WebElement variations;
    @FindBy(xpath = "//div[@id='c-product__vps']")
    private WebElement vpsMessage;
    @FindBy(id = "c-product__quantity")
    private WebElement product_quantity;
    @FindBy(id = "btn__add-to-bag")
    private WebElement addToBagButton;
    @FindBy(id = "c-product__overview")
    private WebElement productOverview;
    @FindBy(id = "c-product__reviews--ratings-summary")
    private WebElement reviewSummary;
    @FindBy(id = "c-product__sold-out")
    private WebElement soldoutMessage;
    @FindBy(xpath = "//div[@class='product__us-sizes']")
    private WebElement sizeMessage;
    @FindBy(xpath = "//div[@class='c-product_pdpMessage']/div")
    private WebElement pdpMessage;
    @FindBy(id = "page__p")
    private WebElement page__p;
    @FindBy(xpath = "//div[@id='c-product__no-intl-shipping']")
    private WebElement shippingRestrictionMessage;
    @FindBy(className = "product__name")
    private WebElement productName;

    @FindBy(id = "btn__add-to-bag")
    private WebElement addToBag;
    
    @FindBy(xpath="//a[contains(@class,'js-link__size-fit') and text()='Size & Fit Details']")
    private WebElement sizeAndFitDetailsLink;
    
    @FindBy(xpath="//div[@class='product__size-fit product__description']/div/div/span")
    private WebElement sizeAndFitDrawer;
    
    @FindBy(xpath="//div[@class='product__details product__description']/div/div/span")
    private WebElement productDetailsDrawer;

    public ProductDetails(WebDriver driver) {
        this.driver = driver;
        wait = Util.createWebDriverWait(driver);
        headerWrap = new HeaderWrap(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(reviewSummary));
    }

    public void selectRandomColor() {
        wait.until(ExpectedConditions.visibilityOf(price_colors));
        List<WebElement> availableColors =
                price_colors.findElements(By.xpath(".//li[@class='js-product__color colors-list__item']"));

        if (availableColors.size() > 0) {
            WebElement selectedColor = Util.randomIndex(availableColors);

            selectedColor.click();
        }
    }

    public void selectRandomSize() {
        wait.until(ExpectedConditions.visibilityOf(sizes));
        String availableSizesSelector = ".//li[contains(@class,'js-product__size sizes-list__item') " +
                "and not(contains(@class,'is-unavailable')) " +
                "and not(contains(@class,'is-selected'))]";
        List<WebElement> availableSizes = sizes.findElements(By.xpath(availableSizesSelector));

        if (availableSizes.size() > 0) {
            final WebElement selectedSize = Util.randomIndex(availableSizes);

            selectedSize.click();
        }
    }

    public void selectRandomQty() {
        wait.until(ExpectedConditions.visibilityOf(product_quantity));
        Select qty = new Select(product_quantity.findElement(By.tagName("select")));

        int availableQty = qty.getOptions().size();
        if (availableQty > 1) {
            int randomQty = Util.randomIndex(availableQty - 1) + 1; //

            qty.selectByValue(Integer.toString(randomQty));
        }
    }

    private String getSelectedColor() {
        WebElement selectedColor = price_colors.findElement(By.xpath(".//li[contains(@class,'is-selected')]"));
        return selectedColor.getAttribute("data-name");
    }

    private String getSelectedSize() {
        WebElement selectedSize = sizes.findElement(
                By.xpath(".//li[contains(@class,'js-product__size') and contains(@class,'is-selected')]"));
        return selectedSize.getAttribute("data-name");
    }

    private String getProductName() {
        WebElement name = productOverview.findElement(By.tagName("h1"));
        return name.getText();
    }

    private String getQuantity() {
        Select qty = new Select(product_quantity.findElement(By.tagName("select")));
        return qty.getFirstSelectedOption().getText();
    }

    private String getPrice() {
        List<WebElement> priceGroups = price_colors.findElements(By.xpath(".//div[@class='product__group']"));
        WebElement productPrice;

        if (priceGroups.size() > 1) {
            WebElement selectedColor = price_colors.findElement(By.xpath(".//li[contains(@class,'is-selected')]"));
            productPrice = selectedColor.findElement(By.xpath(".//ancestor::div[@class='product__group']/span"));
        } else {
            //if has variations, get price from variations
            List<WebElement> variationsPrice = variations.findElements(By.tagName("li"));
            if (variationsPrice.size() > 0) {
                WebElement selectedVariation = variations.findElement(By.className("is-selected"));

                //check if variation has sale price
                productPrice = selectedVariation.findElement(
                        By.xpath(".//span[contains(@class,'" + PRICE_SALE_CLASS + "')]"));
                if (!productPrice.isDisplayed()) {
                    //if no sale price get regular price
                    productPrice = selectedVariation.findElement(
                            By.xpath(".//span[contains(@class,'" + PRICE_LIST_CLASS + "')]"));
                }

            } else { //if no variations, get sale price
                wait.until(ExpectedConditions.visibilityOf(price));
                productPrice = price.findElement(By.className(PRICE_SALE_CLASS));
                if (!productPrice.isDisplayed()) {
                    //if no sale price get regular price
                    productPrice = price.findElement(By.className(PRICE_LIST_CLASS));
                }
            }
        }

        return productPrice.getText();
    }

    private boolean isSoldOut() {
        List<WebElement> message = soldoutMessage.findElements(By.className("product__sold-out"));

        return message.size() > 0;
    }

    public Product getProduct() {
        Product product = new Product();
        product.setName(getProductName());

        if (!isSoldOut()) {
            product.setColor(getSelectedColor());
            product.setSize(getSelectedSize());
            product.setQuantity(getQuantity());
            product.setPrice(getPrice());
        } else {
            product.setSoldOut(true);
        }

        return product;
    }

    public void addToBag() {
        Stack<Product> productsInBag = (Stack<Product>) holder.get("bag_items");

        if (productsInBag == null) {
            productsInBag = new Stack<>();
        }

        Product product = getProduct();

        productsInBag.add(product);

        logger.info("Adding to bag {}", product);
        holder.put("bag_items", productsInBag);

        addToBagButton.click();
        headerWrap.waitUntilCheckOutDropdown();
    }

    private boolean verifyCurrency(String currency) {
        List<WebElement> salePrices = page__p.findElements(By.className(PRICE_SALE_CLASS));
        List<WebElement> listPrices = page__p.findElements(By.className(PRICE_LIST_CLASS));

        List<WebElement> allPrices = new ArrayList<>();
        allPrices.addAll(salePrices);
        allPrices.addAll(listPrices);

        Iterator<WebElement> allPricesIterator = allPrices.iterator();

        boolean result = true;

        while (result & allPricesIterator.hasNext()) {
            WebElement priceElement = allPricesIterator.next();

            if (priceElement.isDisplayed()) {
                String price = priceElement.getText();

                result = CurrencyChecker.anyPriceType(currency, price);

                if (!result) {
                    logger.error("PDP: Not able to check price currency format {}", price);
                }
            }

        }


        return result;
    }

    public boolean verifyContext() {
        Country country = (Country) stateHolder.get("context");

        boolean result= Util.countryContextURLCompliance(driver, country);

        return result;
    }

    public boolean isProductDetailPage() {
        Country country = (Country) stateHolder.get("context");
        logger.info("country context is  : {}", country.getName());
        Util.waitForPageFullyLoaded(driver);
        wait.until(ExpectedConditions.visibilityOf(productName));
        boolean isURL = Util.countryContextURLCompliance(driver, country);
        logger.debug("is url?  {}", isURL);
        return productName.isDisplayed() && StringUtils.isNotBlank(productName.getText()) && isURL;
    }

    public void click_add_to_cart() {
        wait.until(ExpectedConditions.visibilityOf(addToBag));

        Product thisProduct = new Product();
        thisProduct.setProductName(getProductNameFromPDP());
        thisProduct.setSelectedColor(getSelectedColor());
        thisProduct.setSelectedSize(getSelectedSize());

        stateHolder.put("recentlyAdded", thisProduct);

        addToBag.click();
    }

    public String getProductNameFromPDP() {
        wait.until(ExpectedConditions.visibilityOf(productOverview));

        return productOverview.findElement(By.tagName("h1")).getText();
    }

    public void click_update_cart() {
        wait.until(ExpectedConditions.textToBePresentInElement(addToBag, "UPDATE BAG"));

        Product thisProduct = new Product();
        thisProduct.setProductName(getProductNameFromPDP());
        thisProduct.setSelectedColor(getSelectedColor());
        thisProduct.setSelectedSize(getSelectedSize());

        stateHolder.put("recentlyAdded", thisProduct);

        Util.clickWithStaleRetry(addToBag);
    }

    public boolean isSoldOutMessageDisplayed() {
        Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();

        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        String message = testDataReader.getData("pdp.soldout.item.message") + " " +
                testDataReader.getData(countryCode + ".phone");

        logger.info("Expected soldout message: {}", message);

        wait.until(ExpectedConditions.visibilityOf(soldoutMessage));
        String actualSoldOutMessage = soldoutMessage.getText().trim();
        logger.info("Actual soldout message: {}", actualSoldOutMessage);


        boolean result = actualSoldOutMessage.equalsIgnoreCase(message);

        if ("jp".equalsIgnoreCase(countryCode)) {
            message = testDataReader.getData("pdp.soldout.item.message") + " " +
                    testDataReader.getData(countryCode + ".email");

            result |= actualSoldOutMessage.equalsIgnoreCase(message);
        }

        return result;

    }

    public boolean isSizeMessageDisplayedOnPDP() {

        Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();

        String expectedSizeMessage = "";
        String actualSizeMessage = "";

        if (!countryCode.equalsIgnoreCase("us")) {
            TestDataReader testDataReader = TestDataReader.getTestDataReader();
            expectedSizeMessage = testDataReader.getData("pdp.size.message");
            logger.info("Expected Size Message on PDP: {}", expectedSizeMessage);

            wait.until(ExpectedConditions.visibilityOf(sizeMessage));
            actualSizeMessage = sizeMessage.getText().trim();
            logger.info("Actual Size Message on PDP: {}", actualSizeMessage);
        } else {
            logger.info("Size message on PDP will not be displayed for '" + countryCode + "' country");
        }

        return actualSizeMessage.equalsIgnoreCase(expectedSizeMessage);
    }

    public boolean isPriceMessageDisplayedOnPDP() {

        Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();

        String expectedPDPMessage = "";
        String actualPDPMessage = "";

        TestDataReader testDataReader = TestDataReader.getTestDataReader();

        if (!countryCode.equalsIgnoreCase("us")) {
            expectedPDPMessage = testDataReader.getData(countryCode + ".pdp.message");
            logger.info("Expected PDP Message: {}", expectedPDPMessage);

            wait.until(ExpectedConditions.visibilityOf(pdpMessage));
            actualPDPMessage = pdpMessage.getText().trim();
            logger.info("Actual PDP Message: {}", expectedPDPMessage);
        } else {
            logger.info("PDP message will not be displayed for '" + countryCode + "' country");
        }

        return actualPDPMessage.equalsIgnoreCase(expectedPDPMessage);
    }

    public void selectRandomVariantOnPDP() {
        List<WebElement> productVariations = variations.findElements(By.className("radio__label"));

        int randomIndex = Util.randomIndex(productVariations.size());
        WebElement selectedVariation = productVariations.get(randomIndex);
        WebElement selectedVariationName = selectedVariation.findElement(By.className("product__variation--name"));
        logger.debug("Selected variation {}", selectedVariationName.getText());
        selectedVariation.click();

        wait.until(ExpectedConditions.visibilityOf(price_colors));
    }

    public boolean isVPSMessageDisplayed() {

        Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();

        boolean result = false;

        if (countryCode.equalsIgnoreCase("us") || countryCode.equalsIgnoreCase("ca") || countryCode.equalsIgnoreCase("uk")) {
            TestDataReader testDataReader = TestDataReader.getTestDataReader();
            String expectedVPSMessage = testDataReader.getData(countryCode + ".pdp.vps.item.message");
            logger.info("Expected VPS message: {}", expectedVPSMessage);

            wait.until(ExpectedConditions.visibilityOf(vpsMessage));
            String actualVPSMessage = vpsMessage.getText().trim();
            logger.info("Actual VPS message: {}", actualVPSMessage);

            result = actualVPSMessage.equalsIgnoreCase(expectedVPSMessage);
        } else {
            logger.info("VPS message will not be displayed for " + countryCode + " country");
            result = true;
        }

        return result;
    }

    public boolean isShippingRestrictionMessageDisplayed() {

        Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();

        boolean result = false;

        if (!countryCode.equalsIgnoreCase("us")) {
            TestDataReader testDataReader = TestDataReader.getTestDataReader();
            String expectedShippingRestrictionMessage = testDataReader.getData("pdp.shipping.restriction.message");
            logger.info("Expected Shipping Restriction message: {}", expectedShippingRestrictionMessage);

            wait.until(ExpectedConditions.visibilityOf(shippingRestrictionMessage));
            String actualShippingRestrictionMessage = shippingRestrictionMessage.getText().trim();
            logger.info("Actual Shipping Restriction message: {}", actualShippingRestrictionMessage);

            result = actualShippingRestrictionMessage.equalsIgnoreCase(expectedShippingRestrictionMessage);
        } else {
            logger.info("Shipping restriction message will not be displayed for " + countryCode + " country");
            result = true;
        }

        return result;
    }

    public boolean isCorrectCurrencySymbolonPDP() {
        Country c = (Country) stateHolder.get("context");

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//section[@id='c-product__details']"))));
        List<WebElement> productpricess = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[contains(@class,'product__price--')]")));

        return CurrencyChecker.validatePrices(productpricess, c);

    }
    
    public boolean isSizeAndFitDetailsLinkDisplayedAboveAddToBag(){
    	
    	List<WebElement> sizeElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[contains(@class,'js-product__size sizes-list__item')]")));
    	if(sizeElements.size()==1){
    		return true;
    	}   	
    	
    	int sizeAndDetailsLink_Y_Value = getYCoordinate("size & fit details");    	
    	int addToBag_Y_Value = getYCoordinate("add to bag");
    	
    	return sizeAndDetailsLink_Y_Value < addToBag_Y_Value;
    }
    
    public int getYCoordinate(String elementName){
    	WebElement element = null;
    	
    	switch(elementName.toLowerCase()){
    		case "size & fit details":
    			element = sizeAndFitDetailsLink;
    			break;    		
    		case "add to bag":
    			element = addToBag;
    			break;
    		case "size & fit":
    			element = sizeAndFitDrawer;
    			break;
    		case "product details":
    			element = productDetailsDrawer;
    			break;
    	}
    	
    	element = wait.until(ExpectedConditions.visibilityOf(element));
    	Point point = element.getLocation();
    	int yCoordinate = point.getY();
    	return yCoordinate;
    }
    
    public void clickPdpDrawer(String drawerName){
    	WebElement drawerElement = null;
    	
    	switch(drawerName.toUpperCase()){
    		case "SIZE & FIT":
    			drawerElement = sizeAndFitDrawer;
    			break;
    		case "PRODUCT DETAILS":
    			drawerElement = productDetailsDrawer;
    			break;
    	}
    	
    	wait.until(ExpectedConditions.elementToBeClickable(drawerElement));
    	drawerElement.click();
    	Util.waitLoadingBar(driver);
    }
    
    public boolean isPdpDrawerInExpectedState(String drawerName, String expectedState){
    	WebElement drawerElement = null;
    	
    	switch(drawerName.toUpperCase()){
    		case "SIZE & FIT":
    			drawerElement = sizeAndFitDrawer;
    			break;
    		case "PRODUCT DETAILS":
    			drawerElement = productDetailsDrawer;
    			break;
    	}
    	
    	wait.until(ExpectedConditions.elementToBeClickable(drawerElement));
    	
    	WebElement drawerStateElement = null;
    	switch(expectedState.toLowerCase()){
    		case "expanded":
    			drawerStateElement = drawerElement.findElement(By.xpath(".//following-sibling::i[@class='js-icon icon-see-less']"));
    			break;
    		case "collapsed":
    			drawerStateElement = drawerElement.findElement(By.xpath(".//following-sibling::i[@class='js-icon icon-see-more']"));
    			break;
    	}
    	
    	return drawerStateElement.isDisplayed();
    			
    }
    
    public boolean isItemDetailsDisplayedInProductDetailsDrawer(){
    	wait.until(ExpectedConditions.visibilityOf(productDetailsDrawer));
    	String productDetailsDrawerText = productDetailsDrawer.getText();
    	return !StringUtils.isBlank(productDetailsDrawerText);
    }

}
