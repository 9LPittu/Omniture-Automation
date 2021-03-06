package com.jcrew.page;

import com.google.common.base.Function;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.util.CurrencyChecker;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
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
import java.util.Arrays;
import java.util.List;

public class ProductDetailPage {

    private final WebDriver driver;
    private final StateHolder stateHolder = StateHolder.getInstance();  
    private final Logger logger = LoggerFactory.getLogger(ProductDetailPage.class);
    
    private Header header;

    private final String PRICE_SALE_CLASS = "product__price--sale";
    private final String PRICE_LIST_CLASS = "product__price--list";

    @FindBy(id = "c-product__price-colors")
    private WebElement price_colors;
    
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

    @FindBy(xpath = ".//li[contains(@class,'primary-nav__item--bag')]")
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

    @FindBy(css = ".btn--link.btn--checkout.btn--primary")
    private WebElement minicartCheckout;

    @FindBy(xpath = "//div[@class='product__us-sizes']")
    private WebElement sizeMessage;

    @FindBy(xpath = "//div[@class='c-product_pdpMessage']/div")
    private WebElement pdpMessage;

    @FindBy(xpath = "//div[@class='c-product__sold-out']")
    private WebElement soldOutMessage;

    @FindBy(xpath = "//div[@id='c-product__vps']")
    private WebElement vpsMessage;

    @FindBy(xpath = "//div[@id='c-product__no-intl-shipping']")
    private WebElement shippingRestrictionMessage;

    @FindBy(className = "c-product__colors")
    private WebElement c_product_colors;

    @FindBy(className = "c-product__description")
    private WebElement productDetailsSection;
    
    @FindBy(xpath="//a[contains(@class,'js-link__size-fit') and text()='Size & Fit Details']")
    private WebElement sizeAndFitDetailsLink;
    
    @FindBy(xpath="//div[@class='product__size-fit product__description']/div/div/span")
    private WebElement sizeAndFitDrawer;
    
    @FindBy(xpath="//div[@class='product__details product__description']/div/div/span")
    private WebElement productDetailsDrawer;
    
    @FindBy(id = "c-product__price")
    private WebElement price;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;        
        PageFactory.initElements(driver, this);
        
        header = new Header(driver);
    }

    public boolean isProductDetailPage() {
    	try{
	        Country country = (Country) stateHolder.get("context");
	        logger.info("country context is  : {}", country.getCountryName());
	        
	        WebElement pdpProductName = null;
	        int cntr = 0;
	        do{
	        	try{
	        		Util.waitForPageFullyLoaded(driver);
	    	        Util.waitLoadingBar(driver);
	    	        Util.createWebDriverWait(driver, Util.getDefaultTimeOutValue()/3).until(ExpectedConditions.elementToBeClickable(productDetailsDrawer));
	        		pdpProductName = Util.createWebDriverWait(driver,Util.getDefaultTimeOutValue()/3).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='product__name']")));
	    	        Util.createWebDriverWait(driver,Util.getDefaultTimeOutValue()/3).until(ExpectedConditions.visibilityOf(pdpProductName));
	    	        Util.createWebDriverWait(driver,Util.getDefaultTimeOutValue()/3).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(pdpProductName)));
	    	        break;
	        	}
	        	catch(Exception e){
	        		cntr++;
	        	}
	        }while(cntr<3);
	        
	
	        boolean isURL = Util.countryContextURLCompliance(driver, country);
	        logger.debug("is url?  {}", isURL);
	        pdpProductName = driver.findElement(By.xpath("//h1[@class='product__name']"));
	        return pdpProductName.isDisplayed() && StringUtils.isNotBlank(pdpProductName.getText()) && isURL;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }


    public boolean isProductNamePriceListMatchesWithArrayPage() {

        String pdpProductNameString = getProductNameFromPDP();
        String pdpProductPriceString = getProductPriceList().replace("was ", "");
        String pdpNowPriceString = getProductNowPriceList();

        @SuppressWarnings("unchecked")
        List<Product> productList = (List<Product>) stateHolder.get("productList");

        logger.debug("Looking for item name - '{}'", pdpProductNameString);
        logger.debug("Looking for item list price - '{}'", pdpProductPriceString);
        if(!pdpNowPriceString.isEmpty()){
        	pdpNowPriceString = pdpNowPriceString.replace("now ", "");
            pdpNowPriceString = pdpNowPriceString.replace("select colors ", "");
        	logger.debug("Looking for item sale price - '{}'", pdpNowPriceString);
        }

        for (Product product : productList) {
            String productName = product.getProductName();
            productName = cleanProductName(productName);

            String productPrice = product.getPriceWas();
            String productNowPrice = product.getPriceSale();  
            
            //check if price ranges are given in the array page/PDP
            boolean salePriceValidation = pdpNowPriceString.equals(productNowPrice) || 
            							  pdpNowPriceString.contains(productNowPrice) ||
            							  productNowPrice.contains(pdpNowPriceString);
            
            if(!salePriceValidation){            	
            	Country c = (Country) stateHolder.get("context");
            	String currency = c.getCurrency();
            	
            	if(pdpNowPriceString.contains("-")){
            		Double lowerSalePrice =  Double.parseDouble(pdpNowPriceString.split("-")[0].replace(currency, "").trim());
            		Double upperSalePrice =  Double.parseDouble(pdpNowPriceString.split("-")[1].replace(currency, "").trim());
            		
            		Double expectedNowPrice = Double.parseDouble(productNowPrice.replace(currency, ""));
            		
            		if(Double.compare(lowerSalePrice, expectedNowPrice) < 0 && Double.compare(upperSalePrice, expectedNowPrice) > 0){
            			salePriceValidation = true;
            		}
            	}
            }
            
            if (productName.equalsIgnoreCase(pdpProductNameString) && productPrice.equals(pdpProductPriceString) && salePriceValidation) {
            	if(!productNowPrice.isEmpty()){
                	logger.debug("Found: {} - {} - {}", productName, productPrice, productNowPrice);
                }
                else{
                	logger.debug("Found: {} - {}", productName, productPrice);
                }
                return true;
            }
        }
        
        logger.error("Not found item details for '{}'", pdpProductNameString);
        return false;
    }

    private String cleanProductName(String productName) {
        productName = productName.toLowerCase();

        if (productName.startsWith("the ")) {
            productName = productName.replaceFirst("the ", "");
        } else if (productName.startsWith("a ")) {
            productName = productName.replaceFirst("a ", "");
        } else if (productName.startsWith("pre-order ")) {
            productName = productName.replaceFirst("pre-order ", "");
        }

        return productName;
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

        //Retrieve product category from PDP URL
        String url = driver.getCurrentUrl();
        String categoryFromPDPURL = url.substring(url.indexOf("/p/")+3,url.indexOf("/",url.indexOf("/p/")+3));
        categoryFromPDPURL = categoryFromPDPURL.replaceAll("_category","");
        stateHolder.put("categoryFromPDPURL", categoryFromPDPURL);
               
        //capture the price
        SubcategoryPage subcategoryPage = new SubcategoryPage(driver);
        String itemFinalPrice = subcategoryPage.getItemPriceFromPDP();
        
        Product thisProduct = new Product();
        thisProduct.setProductName(getProductNameFromPDP());
        thisProduct.setProductCode(getProductCodeFromPDP());
        thisProduct.setPriceList(itemFinalPrice);
        thisProduct.setSelectedColor(getSelectedColor());
        thisProduct.setSelectedSize(getSelectedSize());
        thisProduct.setQuantity("1");
        thisProduct.setIsBackOrder(getIsBackordered());
        thisProduct.setIsCrewCut(getIsCrewCut());
        
        ProductDetailAddMonogram monogram = new ProductDetailAddMonogram(driver);
        
        if(monogram.hasMonogram()) {
        	thisProduct.setMonogram(true);
        	thisProduct.setMonogramLetters((String) stateHolder.get("monogramLetters"));
        	thisProduct.setMonogramStyle((String) stateHolder.get("monogramStyle"));
        }
        
        stateHolder.put("recentlyAdded", thisProduct);
        
        List<Product> productList = stateHolder.getList("toBag");
        if (productList == null) {
            productList = new ArrayList<>();
        }

        productList.add(thisProduct);
        stateHolder.put("toBag", productList);

        int itemsInBag = (int)stateHolder.get("itemsInCart_BeforeAddToBag");
        boolean retry = true;
        int attempts = 0;
        
        Util.scrollAndClick(driver,addToBag);
        Util.waitLoadingBar(driver);
                
        do{
        	int itemCount = getNumberOfItemsInBag();
        	if (itemCount <= itemsInBag) {
        		Util.wait(3000);
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();", addToBag);
                attempts ++;
        	}
        	else{
        		retry=false;        		
        		break;
        	}
        }while(retry && attempts <=3);
        
        if (retry)
            throw new WebDriverException("Unable to add item to cart") ;
    }

    public int getNumberOfItemsInBag() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(bagContainer));
        WebElement bagSize = bagContainer.findElement(By.className("js-cart-size"));
        String bagSizeStr = bagSize.getAttribute("innerHTML");
        String stringSize = bagSizeStr.replace("(", "").replace(")", "").trim();

        if (stringSize.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(stringSize);
        }
    }

    public boolean showsMinicartMessage(String message) {
        try {
            Util.createWebDriverWait(driver).until(
                    ExpectedConditions.textToBePresentInElementLocated(By.className("header__cart--details"), message));
            return true;
        }  catch (TimeoutException timeout) {
            return false;
        }

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
                        By.xpath(".//li[@data-name='" + productSize.toUpperCase() + "']"))));
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
        WebElement productColorElement = productColors.findElement(By.xpath(".//li[@data-name='" + productColor.toUpperCase() + "']"));

        return productColorElement;
    }

    public String getSelectedColor() {
        WebElement productColorContainer = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='product__price-colors']")));
        WebElement productColorElement = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productColorContainer.findElement(By.className("is-selected"))));
        return productColorElement.getAttribute("data-name");
    }

    public String getSelectedSize() {
    	
    	String selectedSize = "";
    	
    	try{
    		WebElement productSizeElement = Util.createWebDriverWait(driver).until(
    					ExpectedConditions.visibilityOf(productSizesSection.findElement(
    								By.xpath("//li[contains(@class,'js-product__size sizes-list__item') and contains(@class,'is-selected')]"))));
    		
    		selectedSize = productSizeElement.getAttribute("data-name"); 
    	}
    	catch(Exception e){
    		logger.info("No size is selected!!!");
    	}    	
        
        return selectedSize;
    }
    
    public String getProductCodeFromPDP(){
    	
    	WebElement productCodeElement = null;
    	try{
    		
    		WebElement productDetailsAccordion = Util.createWebDriverWait(driver).until(
    				ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'product__details product__description')]/div/div")));    		
    		
    		Util.scrollToElement(driver, productDetailsAccordion);
    		
    		int cntr = 0;
    		do{
    			try{
    				if(productDetailsAccordion.findElement(By.tagName("i")).getAttribute("class").contains("more")){
    					productDetailsAccordion.click();
    					break;
    				}
    				else{
    					break;
    				}
        		}
        		catch(Exception e){
        			logger.error("Product Details Accordion is not in open state!!!");
                	JavascriptExecutor jse = (JavascriptExecutor) driver;
                    jse.executeScript("window.scrollBy(0,50)", "");
        		}
    		}while(cntr<3);
    		
    		
    		productCodeElement = Util.createWebDriverWait(driver,30).until(
                    ExpectedConditions.visibilityOf(productDetailsSection.findElement(By.xpath("//li[contains(text(),'Item')]"))));
    	}
    	catch(TimeoutException toe){
    		try{
     			productCodeElement = Util.createWebDriverWait(driver,30).until(
                        ExpectedConditions.visibilityOf(productDetailsSection.findElement(By.xpath("//li[contains(text(),'Item')]"))));
    		}
    		catch(Exception e){
    			throw new WebDriverException("Product/item code is not found on the PDP!");
    		}
    	}
    	
    	String productCodeText = (productCodeElement.getText().replace("Item ", "")).replace("item ", "");
    	productCodeText = productCodeText.replace(".", "").toUpperCase();
    	return productCodeText;
    }

    public String getBagButtonText() {
        return addToBag.getText();
    }

    public void click_update_cart() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElement(addToBag, "UPDATE BAG"));

        Product thisProduct = new Product();
        thisProduct.setProductName(getProductNameFromPDP());
        thisProduct.setProductCode(getProductCodeFromPDP());
        thisProduct.setSelectedColor(getSelectedColor());
        thisProduct.setSelectedSize(getSelectedSize());
        thisProduct.setIsBackOrder(getIsBackordered());

        stateHolder.put("recentlyAdded", thisProduct);
        
        stateHolder.addToList("toBag", getProduct());
        stateHolder.addToList("editedItem", getProduct());

        int itemsInBag = header.getItemsInBag();
        stateHolder.put("itemsInBag", itemsInBag);

        Util.clickWithStaleRetry(addToBag);
    }
    
    public Product getProduct() {
        Product product = new Product();
        product.setProductName(getProductNameFromPDP());

        if (!isSoldOut()) {
            product.setSelectedColor(getSelectedColor());
            product.setSelectedSize(getSelectedSize());
            product.setPriceList(getPrice());
            product.setProductCode(getProductCodeFromPDP());
            product.setQuantity("1");
        } else {
            product.setSoldOut(true);
        }

        return product;
    }
    
    private boolean isSoldOut() {
        List<WebElement> message = soldOutMessage.findElements(By.className("product__sold-out"));

        return message.size() > 0;
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
        Util.waitWithStaleRetry(driver, wishList);
        return wishList.getText();
    }

    public void click_wishlist() {
        Product thisProduct = new Product();
        thisProduct.setProductName(getProductNameFromPDP());
        thisProduct.setSelectedColor(getSelectedColor());
        thisProduct.setSelectedSize(getSelectedSize());
        thisProduct.setIsBackOrder(getIsBackordered());

        stateHolder.put("wishlist", thisProduct);
        wishList.click();
    }

    public void go_to_wishlist() {
    	
    	WebElement wishlistConfirmation = null;
    	int cntr = 0;
    	do{
    		try{
    			wishlistConfirmation = Util.createWebDriverWait(driver, Util.getDefaultTimeOutValue()/10).until(
    	                ExpectedConditions.presenceOfElementLocated(By.className("wishlist-confirmation-text")));
    			break;
    		}
    		catch(StaleElementReferenceException sere){
    			logger.debug("StaleElementReferenceException is thrown...");
    			cntr++;
    		}
    		catch(NoSuchElementException nsee){
    			logger.debug("NoSuchElementException is thrown...");
    			cntr++;
    		}
    	}while(cntr<=Util.getDefaultTimeOutValue()/10);
    	

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
            for (WebElement price : prices) {
                if (price.isDisplayed()) {
                    return price.getText();
                }
            }
        }
        return productListPrice;
    }
    
    public String getProductNowPriceList() {
        String productNowListPrice = "";
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productDetails));
        if (getVariationsNames().isEmpty()) {
        	productNowListPrice = productDetails.findElement(By.className("product__price--sale")).getText();
        } else {
            List<WebElement> prices = productDetails.findElements(By.className("product__price--sale"));
            for (WebElement price : prices) {
                if (price.isDisplayed()) {
                    return price.getText();
                }
            }
        }
        return productNowListPrice;
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
                
                Product product;
                try{
                	product = Util.getCurrentProduct();
                }
                catch(NullPointerException npe){
                	product = getProduct();
                	stateHolder.addToList("productList", product);
                }
                
                String colorName = color.getAttribute("data-name");
                product.setSelectedColor(colorName);
                Util.scrollAndClick(driver,color);
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
            product.setIsBackOrder(getIsBackordered());
            size.click();
            logger.info("Selected size name: {}", sizeName);
        }
    }

    public String getButtonErrorMessage() {
        String message = "";
        List<WebElement> messages = productActionsSection.findElements(By.className("product__message"));

        if(messages.size() > 0) {
            message = messages.get(0).getText();
        }

        return message;
    }

    public boolean isBagButtonText(String text) {
        return Util.createWebDriverWait(driver).until(
                ExpectedConditions.textToBePresentInElement(addToBag, text));
    }

    public boolean isWishlistConfirmationMessageDisplayed() {
        return productActionsSection.findElement(By.className("content-button-secondary-confirmation")).isDisplayed();
    }

    public void clickMinicartCheckout() {
        String currentURL = driver.getCurrentUrl();

        try {
            minicartCheckout.click();
        } catch (NoSuchElementException noMiniCart) {
            logger.debug("Minicart not found, so going through bag button");
            bagContainer.click();
        }

        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
    }

    public boolean isPreviouslySelectedColorStillDisplayedAsSelected() {

        String currentSelectedColor = getSelectedColor().toLowerCase();
        logger.debug("Current selected color in application: {}", currentSelectedColor);

        Product product = (Product) stateHolder.get("recentlyAdded");
        String expectedColorName;

        if (product == null) {
            product = (Product) stateHolder.get("wishlist");
        }

        expectedColorName = product.getSelectedColor();

        logger.debug("Expected color to be in selection: {}", expectedColorName);

        return expectedColorName.equalsIgnoreCase(currentSelectedColor);
    }

    public boolean isPreviouslySelectedSizeStillDisplayedAsSelected() {

        String currentSelectedSize = getSelectedSize().toLowerCase();
        logger.debug("Current selected size in application: {}", currentSelectedSize);

        Product product = (Product) stateHolder.get("recentlyAdded");
        String expectedSizeName;

        if (product == null) {
            product = (Product) stateHolder.get("wishlist");
        }

        expectedSizeName = product.getSelectedSize();

        logger.debug("Expected size to be in selection: {}", expectedSizeName);

        return expectedSizeName.equalsIgnoreCase(currentSelectedSize);
    }

    public void selectNewColor() {

        List<WebElement> itemColors = driver.findElements(By.xpath("//li[contains(@class,'js-product__color colors-list__item') and not(contains(@class,'is-selected'))]"));
        int randomIndex = Util.randomIndex(itemColors.size());
        WebElement colorImage = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(itemColors.get(randomIndex).findElement(By.tagName("img"))));
        colorImage.click();
        String newSelectedColor = driver.findElement(By.className("product__value")).getText().toLowerCase();

        logger.debug("Selected new item color: {}", newSelectedColor);
        @SuppressWarnings("unchecked")
        List<Product> productList = (List<Product>) stateHolder.get("productList");
        Product product = productList.get(0);
        product.setSelectedColor(newSelectedColor);
        product.setIsBackOrder(getIsBackordered());

        productList.add(product);
        stateHolder.put("productList", productList);
    }

    public void selectNewSize() {

        List<WebElement> itemSizes = driver.findElements(
                By.xpath("//li[contains(@class,'js-product__size sizes-list__item btn') and " +
                        "not(contains(@class,'is-selected')) and not(contains(@class,'is-unavailable'))]"));
        int randomIndex = Util.randomIndex(itemSizes.size());
        itemSizes.get(randomIndex).findElement(By.tagName("span")).click();
        String newSelectedSize = itemSizes.get(randomIndex).getAttribute("data-name").toLowerCase();

        logger.debug("Selected new item size: {}", newSelectedSize);
        @SuppressWarnings("unchecked")
        List<Product> productList = (List<Product>) stateHolder.get("productList");
        Product product = productList.get(0);
        product.setSelectedSize(newSelectedSize);
        product.setIsBackOrder(getIsBackordered());

        productList.add(product);
        stateHolder.put("productList", productList);
    }

    public boolean isproductVariantSectionPresent() {

        try {
            List<WebElement> variants = productDetailsVariantsSection.findElements(By.className("product-details-variants"));
            return true;
        } catch (Exception e) {
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
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public void validate_extended_size_on_pdp_page_is_displayed() {
        if (isproductVariantSectionPresent()) {
            String variant = "Regular";
            if (isVariantRadioButtonisSelected(variant)) {
                logger.info("Variant section is present and default Regular variant is selected on PDP page ");
            } else {
                logger.error("Variant section is present but Default Regular variant is not selected on PDP page");
            }
        } else {
            logger.info("Variants are not present on PDP page");
        }

    }

    public boolean isCorrectCurrencySymbolonPDP() {
    	boolean result;
        try{
	    	Country c = (Country) stateHolder.get("context");	
	        List<WebElement> productPriceElements = driver.findElements(By.xpath("//span[contains(@class,'product__price--')]"));
	
	        result = CurrencyChecker.validatePrices(productPriceElements, c);
	
	        if (result) {
	            logger.info("Currency symbol is displayed correctly on all on Product details page");
	
	        } else {
	            logger.debug("Currency symbol is not displayed correctly on all / any of the Item prices  on Product details page");
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        	result = false;
        }

        return result;
    }

    public boolean isSizeMessageDisplayedOnPDP() {
    	String expectedSizeMessage = "";
        String actualSizeMessage = "";
    	
        try{        	
	    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(c_product_colors));
	        Country c = (Country) stateHolder.get("context");
	        String countryCode = c.getCountry();
	
	        if (!countryCode.equalsIgnoreCase("us")) {
	            TestDataReader testDataReader = TestDataReader.getTestDataReader();
	            expectedSizeMessage = testDataReader.getData("pdp.size.message");
	            logger.info("Expected Size Message on PDP: {}", expectedSizeMessage);
	            
	            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(sizeMessage));
	            
	            actualSizeMessage = Util.createWebDriverWait(driver).until(new Function<WebDriver, String>(){
					@Override
					public String apply(WebDriver driver) {
						String sizeMessageText = null;					
						try{
							sizeMessageText = sizeMessage.getText().trim();
							return sizeMessageText;
						}
						catch(StaleElementReferenceException sere){
							logger.debug("StaleElementReferenceException is thrown...");
							return null;
						}					
					}            	
	            });
	            
	            logger.info("Actual Size Message on PDP: {}", actualSizeMessage);
	        } else {
	            logger.info("Size message on PDP will not be displayed for '" + countryCode + "' country");
	        }
	        
	        return actualSizeMessage.equalsIgnoreCase(expectedSizeMessage);
        }
        catch(TimeoutException te){        	
        	Util.logBrowserErrorMessages(driver);
        	te.printStackTrace();
        	return false;
        }
    }

    public boolean isMessageDisplayedOnPDP() {
    	String expectedPDPMessage = "";
        String actualPDPMessage = "";
    	
        try{
	        Country c = (Country) stateHolder.get("context");
	        String countryCode = c.getCountry();
	
	        TestDataReader testDataReader = TestDataReader.getTestDataReader();
	
	        if (!countryCode.equalsIgnoreCase("us")) {
	            expectedPDPMessage = testDataReader.getData(countryCode + ".pdp.message");
	            logger.info("Expected PDP Message: {}", expectedPDPMessage);
	            Util.waitWithStaleRetry(driver, pdpMessage);
	            
	            actualPDPMessage = Util.createWebDriverWait(driver).until(new Function<WebDriver, String>(){
					@Override
					public String apply(WebDriver driver) {
						String pdpMessageText = null;					
						try{
							pdpMessageText = pdpMessage.getText().trim();
							return pdpMessageText;
						}
						catch(StaleElementReferenceException sere){
							logger.debug("StaleElementReferenceException is thrown...");
							return null;
						}					
					}            	
	            });
	            logger.info("Actual PDP Message: {}", expectedPDPMessage);
	        } else {
	            logger.info("PDP message will not be displayed for '" + countryCode + "' country");
	        }
	
	        return actualPDPMessage.equalsIgnoreCase(expectedPDPMessage);
        }
        catch(TimeoutException te){        	
        	Util.logBrowserErrorMessages(driver);
        	te.printStackTrace();
        	return false;
        }
    }

    public boolean isSoldOutMessageDisplayed() {
    	boolean result;
    	
    	try{
	        Country c = (Country) stateHolder.get("context");
	        String countryCode = c.getCountry();
	
	        TestDataReader testDataReader = TestDataReader.getTestDataReader();
	        String message = testDataReader.getData("pdp.soldout.item.message") + " " +
	                testDataReader.getData(countryCode + ".phone");
	
	        logger.info("Expected soldout message: {}", message);
	
	        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(soldOutMessage));
	        String actualSoldOutMessage = soldOutMessage.getText().trim();
	        logger.info("Actual soldout message: {}", actualSoldOutMessage);
	
	        result = actualSoldOutMessage.equalsIgnoreCase(message);
	
	        if ("jp".equalsIgnoreCase(countryCode)) {
	            message = testDataReader.getData("pdp.soldout.item.message") + " " +
	                    testDataReader.getData(countryCode + ".email");
	
	            result |= actualSoldOutMessage.equalsIgnoreCase(message);
	        }
    	}
    	catch(TimeoutException te){        	
        	Util.logBrowserErrorMessages(driver);
        	te.printStackTrace();
        	return false;
        }
    	
        return result;
    }

    public void selectRandomVariantOnPDP() {
        List<WebElement> productVariations = productVariationSection.findElements(By.className("radio__label"));

        if (productVariations.size() == 0) {
            throw new WebDriverException("There are no variants to be selected!!!");
        } else {
            int randomIndex = Util.randomIndex(productVariations.size());
            WebElement selectedVariation = productVariations.get(randomIndex);
            WebElement selectedVariationNameElement = selectedVariation.findElement(By.className("product__variation--name"));
            String selectedVariationName = selectedVariationNameElement.getText();
            logger.debug("Selected variation {}", selectedVariationName);           
            selectedVariation.click();
            productName = driver.findElement(By.className("product__name"));           
            Util.waitLoadingBar(driver);
        }
    }

    public boolean isVPSMessageDisplayed() {
    	
    	boolean result;
    	try{
	        Country c = (Country) stateHolder.get("context");
	        String countryCode = c.getCountry();
	
	        if (countryCode.equalsIgnoreCase("us") || countryCode.equalsIgnoreCase("ca") || countryCode.equalsIgnoreCase("uk")) {
	            TestDataReader testDataReader = TestDataReader.getTestDataReader();
	            String expectedVPSMessage = testDataReader.getData(countryCode + ".pdp.vps.item.message");
	            logger.info("Expected VPS message: {}", expectedVPSMessage);
	
	            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(vpsMessage));
	            String actualVPSMessage = vpsMessage.getText().trim();
	            logger.info("Actual VPS message: {}", actualVPSMessage);
	
	            result = actualVPSMessage.equalsIgnoreCase(expectedVPSMessage);
	        } else {
	            logger.info("VPS message will not be displayed for " + countryCode + " country");
	            result = true;
	        }
    	}
    	catch(TimeoutException te){        	
        	Util.logBrowserErrorMessages(driver);
        	te.printStackTrace();
        	return false;
        }
    	
        return result;
    }

    public boolean isShippingRestrictionMessageDisplayed() {
    	
    	boolean result;
    	
    	try{
	        Country c = (Country) stateHolder.get("context");
	        String countryCode = c.getCountry();
	
	        if (!countryCode.equalsIgnoreCase("us")) {
	            TestDataReader testDataReader = TestDataReader.getTestDataReader();
	            String expectedShippingRestrictionMessage = testDataReader.getData("pdp.shipping.restriction.message");
	            logger.info("Expected Shipping Restriction message: {}", expectedShippingRestrictionMessage);
	
	            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(shippingRestrictionMessage));
	            String actualShippingRestrictionMessage = shippingRestrictionMessage.getText().trim();
	            logger.info("Actual Shipping Restriction message: {}", actualShippingRestrictionMessage);
	
	            result = actualShippingRestrictionMessage.equalsIgnoreCase(expectedShippingRestrictionMessage);
	        } else {
	            logger.info("Shipping restriction message will not be displayed for " + countryCode + " country");
	            result = true;
	        }
    	}
    	catch(TimeoutException te){        	
        	Util.logBrowserErrorMessages(driver);
        	te.printStackTrace();
        	return false;
        }
    	
        return result;
    }
    
    public boolean isSizeAndFitDetailsLinkDisplayedAboveAddToBag(){
    	
    	List<WebElement> sizeElements = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[contains(@class,'js-product__size sizes-list__item')]")));
    	if(sizeElements.size()==1){
    		return true;
    	}
    	
    	sizeAndFitDetailsLink = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(sizeAndFitDetailsLink));
    	Point sizeAndDetailsLinkPoint = sizeAndFitDetailsLink.getLocation();
    	int sizeAndDetailsLink_Y_Value = sizeAndDetailsLinkPoint.getY();
    	
    	int addToBag_Y_Value = getAddToBag_Y_Coordinate();
    	
    	return sizeAndDetailsLink_Y_Value < addToBag_Y_Value;
    }
    
    public int getAddToBag_Y_Coordinate(){
    	addToBag = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(addToBag));
    	Point addToBagPoint = addToBag.getLocation();
    	int addToBag_Y_Value = addToBagPoint.getY();
    	return addToBag_Y_Value;
    }
    
    public int getSizeAndFitDrawer_Y_Coordinate(){
    	sizeAndFitDrawer = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(sizeAndFitDrawer));
    	Point sizeAndFitDrawerPoint = sizeAndFitDrawer.getLocation();
    	int sizeAndDetailsDrawer_Y_Value = sizeAndFitDrawerPoint.getY();
    	return sizeAndDetailsDrawer_Y_Value;
    }
    
    public boolean isSizeAndFitDrawerDisplayedBelowAddToBag(){
            int sizeAndDetailsDrawer_Y_Value = getSizeAndFitDrawer_Y_Coordinate();
            int addToBag_Y_Value = getAddToBag_Y_Coordinate();
            return sizeAndDetailsDrawer_Y_Value > addToBag_Y_Value;
    }
    
    public boolean isProductDetailsDrawerDisplayedBelowSizeAndFitDrawer(){
        productDetailsDrawer = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productDetailsDrawer));
        Point productDetailsDrawerPoint = productDetailsDrawer.getLocation();
        int productDetailsDrawer_Y_Value = productDetailsDrawerPoint.getY();

        boolean isSizeAndFit = isSizeAndFitDrawerDisplayed();
        if (isSizeAndFit) {
            int sizeAndDetailsDrawer_Y_Value = getSizeAndFitDrawer_Y_Coordinate();
            return sizeAndDetailsDrawer_Y_Value < productDetailsDrawer_Y_Value;
        } else {
            return true;
        }
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
    	
    	Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(drawerElement));
        Util.scrollToElement(driver, drawerElement);
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
    	
    	Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(drawerElement));
    	
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
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productDetailsDrawer));
    	String productDetailsDrawerText = productDetailsDrawer.getText();
    	return !StringUtils.isBlank(productDetailsDrawerText);
    }

    public boolean getIsBackordered() {
        String message = getButtonErrorMessage().toLowerCase();

        return message.contains("backordered");
    }

    public boolean getIsCrewCut() {
        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        String category="";
        String subCategory="";
        String saleCategory="";
        String categoryFromPDPURL="";

        if (stateHolder.hasKey("category")) {
            category=((String) stateHolder.get("category")).toLowerCase();
            stateHolder.remove("category");
        }

        if (stateHolder.hasKey("subcategory")) {
            subCategory=((String) stateHolder.get("subcategory")).toLowerCase();
            stateHolder.remove("subcategory");
        }

        if (stateHolder.hasKey("sale category")) {
            saleCategory=((String) stateHolder.get("sale category")).toLowerCase();
            stateHolder.remove("sale category");
        }

        if (stateHolder.hasKey("categoryFromPDPURL")) {
            categoryFromPDPURL=((String) stateHolder.get("categoryFromPDPURL")).toLowerCase();
            stateHolder.remove("categoryFromPDPURL");
        }

        String crewCutCategories[] = testDataReader.getDataArray("crewCutCategories");
        List<String> crewCuts = Arrays.asList(crewCutCategories);

        if(crewCuts.contains(category) || (category=="sale" && crewCuts.contains(saleCategory)) || crewCuts.contains(categoryFromPDPURL) || (category=="wedding" && subCategory=="flowergirl")) {
            return true;
        } else {
            return false;
        }
    }

    private String getPrice() {
        List<WebElement> priceGroups = price_colors.findElements(By.xpath(".//div[@class='product__group']"));
        WebElement productPrice;
//        c-product__price-colors
        if (priceGroups.size() > 1) {

            WebElement selectedColor = price_colors.findElement(By.xpath(".//li[contains(@class,'is-selected')]"));
            productPrice = selectedColor.findElement(By.xpath(".//ancestor::div[@class='product__group']/span"));

        } else {
            //if has variations, get price from variations
            List<WebElement> variationsPrice = productVariationSection.findElements(By.tagName("li"));
            if (variationsPrice.size() > 0) {
                WebElement selectedVariation = productVariationSection.findElement(By.className("is-selected"));

                //check if variation has sale price
                productPrice = selectedVariation.findElement(
                        By.xpath(".//span[contains(@class,'" + PRICE_SALE_CLASS + "')]"));
                if (!productPrice.isDisplayed()) {
                    //if no sale price get regular price
                    productPrice = selectedVariation.findElement(
                            By.xpath(".//span[contains(@class,'" + PRICE_LIST_CLASS + "')]"));
                }

            } else { //if no variations, get sale price
            	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(price));
                productPrice = price.findElement(By.className(PRICE_SALE_CLASS));
                if (!productPrice.isDisplayed()) {
                    //if no sale price get regular price
                    productPrice = price.findElement(By.className(PRICE_LIST_CLASS));
                }
            }
        }

        String price = productPrice.getText();
        return price;
    }

    public boolean isSizeAndFitDrawerDisplayed() {
        try {
            driver.findElement(By.xpath("//div[@class='product__size-fit product__description']/div/div/span"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}