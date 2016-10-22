package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ProductDetails extends PageObject {
    private final HeaderWrap headerWrap;

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
    @FindBy(id = "btn__wishlist")
    private WebElement wishListButton;
    @FindBy(id = "c-product__overview")
    private WebElement productOverview;
    @FindBy(id = "c-product__reviews--ratings-summary")
    private WebElement reviewSummary;

    @FindBy(id = "c-product__reviews--ratings")
    private WebElement reviewSection;

    @FindBy(id = "c-product__recommendations")
    private WebElement bayNoteSection;
    @FindBy(id="c-page__navigation")
    private WebElement endCapNav;

    @FindBy(id = "c-product__sold-out")
    private WebElement soldoutMessage;
    @FindBy(xpath = "//div[@class='product__us-sizes']")
    private WebElement sizeMessage;
    @FindBy(xpath = "//div[@class='c-product_pdpMessage']/div")
    private WebElement pdpMessage;
    @FindBy(xpath = "//div[@id='c-product__no-intl-shipping']")
    private WebElement shippingRestrictionMessage;
    @FindBy(className = "product__name")
    private WebElement productName;
    @FindBy(id = "btn__add-to-bag")
    private WebElement addToBag;
    @FindBy(xpath = "//a[contains(@class,'js-link__size-fit') and text()='Size & Fit Details']")
    private WebElement sizeAndFitDetailsLink;
    @FindBy(xpath = "//div[@class='product__size-fit product__description']/div/div/span")
    private WebElement sizeAndFitDrawer;
    @FindBy(xpath = "//div[@class='product__details product__description']/div/div/span")
    private WebElement productDetailsDrawer;
    @FindBy(id = "c-product__details")
    private WebElement product__details;
    @FindBy(className = "c-product__description")
    private WebElement productDetailsSection;

    @FindBy(xpath = "//button[@id='btn__add-to-bag' and text()='Update Bag']")
    private WebElement updateBagButton;

    @FindBy(id = "c-product__actions")
    private WebElement productActionsSection;

    public ProductDetails(WebDriver driver) {
        super(driver);
        headerWrap = new HeaderWrap(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(product__details));
    }

    public void selectRandomColor() {
        wait.until(ExpectedConditions.visibilityOf(price_colors));
        List<WebElement> availableColors =
                price_colors.findElements(By.xpath(".//li[@class='js-product__color colors-list__item'"
                        + " and not(contains(@class,'is-selected'))]"));

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
            int randomQty = Util.randomIndex(availableQty - 1) + 1;
            qty.selectByValue(Integer.toString(randomQty));
        }
    }

    public String getSelectedColor() {
        WebElement selectedColor = price_colors.findElement(By.xpath(".//li[contains(@class,'is-selected')]"));
        return selectedColor.getAttribute("data-name");
    }

    public String getSelectedSize() {
        WebElement selectedSize = sizes.findElement(
                By.xpath(".//li[contains(@class,'js-product__size') and contains(@class,'is-selected')]"));
        return selectedSize.getAttribute("data-name");
    }

    private String getProductName() {
        wait.until(ExpectedConditions.visibilityOf(productOverview));
        WebElement name = productOverview.findElement(By.tagName("h1"));
        return name.getText();
    }


    private String getQuantity() {
        Select qty = new Select(product_quantity.findElement(By.tagName("select")));
        return qty.getFirstSelectedOption().getText();
    }

    public  String getProductPrice(){
        WebElement productPrice;
        List<WebElement> variationsPrice = variations.findElements(By.tagName("li"));
        if (variationsPrice.size() > 0) {
            WebElement selectedVariation = variations.findElement(By.className("is-selected"));

            //check if variation has sale price
            productPrice = selectedVariation.findElement(
                    By.xpath(".//span[contains(@class,'" + PRICE_SALE_CLASS + "')]"));
            if (!productPrice.isDisplayed()) {
                //if no sale price get regular price from varations
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
        String price = productPrice.getText();
        price = price.trim().toLowerCase();
        price = price.replace("select colors", "").replace("now", "");
        price = price.replace("was", "");
        price = price.replace(" ", "");
        return price;
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

        String price = productPrice.getText();
        return price;
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
            product.setItemNumber(getProductCode());
            product.setQuantity(getQuantity());
        } else {
            product.setSoldOut(true);
        }

        return product;
    }

    public void addToBag() {
        Stack<Product> productsInBag = (Stack<Product>) stateHolder.get("bag_items");

        if (productsInBag == null) {
            productsInBag = new Stack<>();
        }

        Product product = getProduct();

        productsInBag.add(product);

        stateHolder.addToList("toBag", product);

        int itemsInBag = headerWrap.getItemsInBag();
        stateHolder.put("itemsInBag", itemsInBag);

        logger.info("Adding to bag {}", product);
        stateHolder.put("bag_items", productsInBag);

        addToBagButton.click();
        headerWrap.waitUntilCheckOutDropdown();
    }

    public boolean verifyContext() {
        Country country = (Country) stateHolder.get("context");

        boolean result = Util.countryContextURLCompliance(driver, country);

        return result;
    }
    public boolean isProductDetailPage() {
        Country country = (Country) stateHolder.get("context");
        logger.info("country context is  : {}", country.getName());
        Util.waitForPageFullyLoaded(driver);
        
        wait.until(ExpectedConditions.visibilityOf(productName));
        logger.debug("waited till product name gets visible on PDP");
        
        boolean isNameBlank = StringUtils.isNotBlank(productName.getText());
        logger.debug("is blank product name on PDP?  {}", isNameBlank);
        
        boolean isURL = Util.countryContextURLCompliance(driver, country);
        logger.debug("is url?  {}", isURL);
        
        return  isURL && isURL;
    }


    public void click_add_to_cart() {
        wait.until(ExpectedConditions.visibilityOf(addToBag));

        Product thisProduct = new Product();
        thisProduct.setProductName(getProductName());
        thisProduct.setSelectedColor(getSelectedColor());
        thisProduct.setSelectedSize(getSelectedSize());
        thisProduct.setIsBackOrder(getIsBackordered());
        thisProduct.setIsCrewCut(getIsCrewCut());

        stateHolder.put("recentlyAdded", thisProduct);

        addToBag.click();
    }


    public void click_write_review(){
        WebElement writeReviewButton;
        List<WebElement> noReviews = reviewSection.findElements(By.id("BVRRDisplayContentNoReviewsID"));

        if(noReviews.size() > 0) {
            logger.debug("Product has no reviews, this is the first review");
            writeReviewButton = noReviews.get(0).findElement(By.tagName("a"));
        } else {
            WebElement reviewContainer = reviewSection.findElement(By.id("BVRRContainer"));
            WebElement reviewId = reviewContainer.findElement(By.id("BVRRRatingSummaryLinkWriteID"));
            writeReviewButton = reviewId.findElement(By.tagName("a"));
        }

        wait.until(ExpectedConditions.visibilityOf(writeReviewButton));
        writeReviewButton.click();


    }
    public void click_update_cart() {
        wait.until(ExpectedConditions.textToBePresentInElement(addToBag, "UPDATE BAG"));

        Product thisProduct = new Product();
        thisProduct.setProductName(getProductName());
        thisProduct.setSelectedColor(getSelectedColor());
        thisProduct.setSelectedSize(getSelectedSize());
        thisProduct.setIsBackOrder(getIsBackordered());

        stateHolder.put("recentlyAdded", thisProduct);

        stateHolder.addToList("toBag", getProduct());
        stateHolder.addToList("editedItem", getProduct());

        int itemsInBag = headerWrap.getItemsInBag();
        stateHolder.put("itemsInBag", itemsInBag);

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

            //wait.until(ExpectedConditions.visibilityOf(reviewSummary));
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

    public List<String> getAllPrices() {
        List<WebElement> productpricess = product__details.findElements(By.xpath("//span[contains(@class,'product__price--')]"));
        List<String> prices = new ArrayList<>(productpricess.size());

        for (WebElement price : productpricess) {
            prices.add(price.getText());
        }

        return prices;

    }

    public int getYCoordinate(String elementName){
        WebElement element = getPDPElement(elementName);
    	element = wait.until(ExpectedConditions.visibilityOf(element));
    	Point point = element.getLocation();
    	int yCoordinate = point.getY();
    	return yCoordinate;
    }

    public void clickPdpDrawer(String drawerName) {
        WebElement drawerElement = getPDPElement(drawerName);

        wait.until(ExpectedConditions.elementToBeClickable(drawerElement));
        drawerElement.click();
        Util.waitLoadingBar(driver);
}

    public boolean isItemDetailsDisplayedInProductDetailsDrawer() {
        wait.until(ExpectedConditions.visibilityOf(productDetailsDrawer));
        String productDetailsDrawerText = productDetailsDrawer.getText();
        return !StringUtils.isBlank(productDetailsDrawerText);
    }
    public boolean isPdpDrawerInExpectedState(String drawerName, String expectedState) {
        boolean result = false;
        WebElement drawerElement = getPDPElement(drawerName);
        wait.until(ExpectedConditions.elementToBeClickable(drawerElement));

        WebElement drawerStateElement = null;
        switch (expectedState.toLowerCase()) {
            case "expanded":
                drawerStateElement = drawerElement.findElement(By.xpath(".//following-sibling::i[@class='js-icon icon-see-less']"));
                result=drawerStateElement.isDisplayed();
                break;
            case "collapsed":
                drawerStateElement = drawerElement.findElement(By.xpath(".//following-sibling::i[@class='js-icon icon-see-more']"));
                result=drawerStateElement.isDisplayed();
                break;
            case "disabled":
                drawerElement = drawerElement.findElement(By.xpath(".."));
                result = drawerElement.getAttribute("class").contains("is-disabled");

        }

        return result;

    }
    private WebElement getPDPElement(String element){
        boolean result = false;
        WebElement pdpElement = null;

        switch (element.toLowerCase()) {
            case "item code":
                pdpElement=  productOverview.findElement(By.className("c-product__code"));
                break;
            case "variations":
                pdpElement = variations;
                break;
            case "color swatchs":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(price_colors));
                break;
            case "size chips":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(sizes));
                break;
            case "size chart":
                pdpElement = sizes.findElement(By.linkText("size charts"));
                break;
            case "quantity":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(product_quantity));
                break;
            case "add to bag":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(addToBagButton));
                break;
            case "wishlist":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(wishListButton));
                break;
            case "social icons":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(product__details.findElement(By.className("product__social"))));
                break;
            case "reviews":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(reviewSection));
                break;
            case "baynotes":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(bayNoteSection));
                break;
            case "endcaps":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(endCapNav));
                break;
            case "update bag":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(updateBagButton));
                break;
            case "size & fit":
                pdpElement = sizeAndFitDrawer;
                break;
            case "size & fit details":
                pdpElement = sizeAndFitDetailsLink;
                break;
            case "product details":
                pdpElement = productDetailsDrawer;
                break;
            case "name":
                pdpElement = productName;
                break;
            case "price":
                pdpElement = price;
                break;
        }
        return pdpElement;
    }



    public boolean isDisplayedInPDP(String element){
        WebElement pdpElement = getPDPElement(element);
        return pdpElement.isDisplayed();
    }



    public String getProductCode() {

        WebElement productCodeElement = null;
        try {
            productCodeElement = Util.createWebDriverWait(driver).until(
                    ExpectedConditions.visibilityOf(productOverview.findElement(By.className("c-product__code"))));
        } catch (TimeoutException toe) {
            throw new WebDriverException("Product/item code is not found on the PDP!");
        }

        String productCodeText = (productCodeElement.getText().replace("Item ", "")).replace("item ", "");
        productCodeText = productCodeText.replace(".", "").toUpperCase();
        return productCodeText;
    }





    public void selectColor(String colorName) {
        wait.until(ExpectedConditions.visibilityOf(price_colors));

        List<WebElement> colors = price_colors.findElements(
                By.xpath(".//li[contains(@class, 'js-product__color') and @data-name='" + colorName.toUpperCase() + "']"));

        if (colors.size() > 0) {
            WebElement selectedColor = colors.get(0);
            WebElement selectedColorImage = selectedColor.findElement(By.tagName("img"));

            logger.info("Selecting specified color {}", colorName);

            wait.until(ExpectedConditions.visibilityOf(selectedColorImage));
            selectedColorImage.click();
        } else {
            logger.info("Color " + colorName + " not found");
        }
    }

    public void selectSize(String size) {
        List<WebElement> productSizes = sizes.findElements(
                By.xpath(".//li[contains(@class,'js-product__size') and @data-name='" + size.toUpperCase() + "']"));

        if (productSizes.size() > 0) {
            WebElement selectedSize = productSizes.get(0);
            WebElement selectedSizeLabel = selectedSize.findElement(By.className("btn__label"));
            selectedSizeLabel.click();

            logger.info("Selecting specified size {}", size);
        } else {
            logger.info("Size " + size + " not found");
        }
    }

    public boolean compare_PDP_price(Product product) {
        String arrayPrice = product.getPrice();
        String pdpPrice = getProductPrice().replace("select colors", "").replace("now", "");
        logger.info("array product price: '{}' - pdp price: '{}'", arrayPrice, pdpPrice);
        return arrayPrice.equals(pdpPrice);
    }

    public boolean compare_PDP_name(Product product) {
        String arrayName = product.getName();
        String pdpName = getProductName();
        logger.info("array product name: '{}' - pdp name: '{}'", arrayName, pdpName);
        return arrayName.equalsIgnoreCase(pdpName);
    }


    public boolean getIsBackordered() {
        String message = getButtonErrorMessage().toLowerCase();
        return message.contains("backordered");
    }

    public String getButtonErrorMessage() {
        String message = "";
        List<WebElement> messages = productActionsSection.findElements(By.className("product__message"));
        if (messages.size() > 0) {
            message = messages.get(0).getText();
        }
        return message;
    }

    public boolean getIsCrewCut() {
        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        String category = "";
        String subCategory = "";
        String saleCategory = "";
        String categoryFromPDPURL = "";

        if (stateHolder.hasKey("category")) {
            category = ((String) stateHolder.get("category")).toLowerCase();
            stateHolder.remove("category");
        }

        if (stateHolder.hasKey("subcategory")) {
            subCategory = ((String) stateHolder.get("subcategory")).toLowerCase();
            stateHolder.remove("subcategory");
        }

        if (stateHolder.hasKey("sale category")) {
            saleCategory = ((String) stateHolder.get("sale category")).toLowerCase();
            stateHolder.remove("sale category");
        }

        if (stateHolder.hasKey("categoryFromPDPURL")) {
            categoryFromPDPURL = ((String) stateHolder.get("categoryFromPDPURL")).toLowerCase();
            stateHolder.remove("categoryFromPDPURL");
        }

        String crewCutCategories[] = testDataReader.getDataArray("crewCutCategories");
        List<String> crewCuts = Arrays.asList(crewCutCategories);

        if (crewCuts.contains(category) || (category == "sale" && crewCuts.contains(saleCategory)) || crewCuts.contains(categoryFromPDPURL) || (category == "wedding" && subCategory == "flowergirl")) {
            return true;
        } else {
            return false;
        }
    }
}