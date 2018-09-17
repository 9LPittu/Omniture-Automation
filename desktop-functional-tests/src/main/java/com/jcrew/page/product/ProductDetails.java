package com.jcrew.page.product;

import com.google.common.base.Function;
import com.jcrew.page.PageObject;
import com.jcrew.page.header.HeaderLogo;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
@SuppressWarnings("unused")
public class ProductDetails extends PageObject {

    @FindBy(xpath = "//div[@id='c-product__vps']")
    private WebElement vpsMessage;

    @FindBy(id = "c-product__overview")
    private WebElement productOverview;

    @FindBy(id="c-page__navigation")
    private WebElement endCapNav;

    @FindBy(xpath = "//div[@class='product__us-sizes']")
    private WebElement sizeMessage;
    @FindBy(xpath = "//div[@class='c-product_pdpMessage']/div")
    private WebElement pdpMessage;
    @FindBy(xpath = "//div[@id='c-product__no-intl-shipping']")
    private WebElement shippingRestrictionMessage;
    @FindBy(className = "product__name")
    private WebElement productName;
    @FindBy(xpath = "//div[@class='product__details product__description']/div/div/span")
    private WebElement productDetailsDrawer;
    @FindBy(id = "c-product__details")
    private WebElement product__details;
    @FindBy(className = "c-product__description")
    private WebElement productDetailsSection;

    public ProductDetails(WebDriver driver) {
        super(driver);

        /*HeaderLogo logo = new HeaderLogo(driver);
        logo.hoverLogo();*/

        wait.until(ExpectedConditions.visibilityOf(product__details));
    }

    public String getProductName() {
        wait.until(ExpectedConditions.visibilityOf(productOverview));
        WebElement name = productOverview.findElement(By.tagName("h1"));
        return name.getText();
    }

    public Product getProduct() {
        ProductDetailColors colors = new ProductDetailColors(driver);
        ProductDetailsSizes sizes = new ProductDetailsSizes(driver);
        ProductDetailSoldOut soldOut = new ProductDetailSoldOut(driver);
        ProductDetailsQuantity quantity = new ProductDetailsQuantity(driver);
        ProductDetailsActions actions = new ProductDetailsActions(driver);
        ProductDetailsVariations variations = new ProductDetailsVariations(driver);
        IPersonalization personalization = PersonalizationFactory.getProductDetailsPersonalization(driver);

        Product product = new Product();
        product.setName(getProductName());

        if (!soldOut.isSoldOut()) {
            product.setColor(colors.getSelectedColor());
            product.setSize(sizes.getSelectedSize());
            product.setQuantity(quantity.getQuantity());
            product.setPrice(variations.getPrice());
            product.setItemNumber(getProductCode());
            product.setIsBackOrder(actions.getIsBackordered());
            product.setIsCrewCut(getIsCrewCut());
//            product.setHasMonogram(personalization.hasMonogram());
        } else {
            product.setSoldOut(true);
        }

        return product;
    }


    public boolean verifyContext() {
        return Util.countryContextURLCompliance(driver);
    }

    public boolean isProductDetailPage() {
        HeaderLogo logo = new HeaderLogo(driver);
        logo.hoverLogo();

        Country country = stateHolder.get("context");
        logger.info("country context is  : {}", country.getName());
        Util.waitForPageFullyLoaded(driver);
        
        wait.until(ExpectedConditions.visibilityOf(productName));
        logger.debug("waited till product name gets visible on PDP");
        
        boolean isNameBlank = StringUtils.isNotBlank(productName.getText());
        logger.debug("is blank product name on PDP?  {}", isNameBlank);
        
        boolean isURL = Util.countryContextURLCompliance(driver);
        logger.debug("is url?  {}", isURL);
        return  isURL & isNameBlank;
    }

    public boolean isPriceMessageDisplayedOnPDP() {

        Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();

        String expectedPDPMessage = "";
        String actualPDPMessage = "";

        TestDataReader testDataReader = TestDataReader.getTestDataReader();

        if (!countryCode.equalsIgnoreCase("us")) {
            expectedPDPMessage = testDataReader.getData("pdp.price.message");
            logger.info("Expected PDP Message: {}", expectedPDPMessage);

            Util.waitWithStaleRetry(driver, pdpMessage);
            
            actualPDPMessage = wait.until(new Function<WebDriver, String>(){
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
            
            logger.info("Actual PDP Message: {}", actualPDPMessage);
        } else {
            logger.info("PDP message will not be displayed for '" + countryCode + "' country");
        }

        return actualPDPMessage.equalsIgnoreCase(expectedPDPMessage);
    }

    public boolean isVPSMessageDisplayed() {

        Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();

        boolean result = false;

        if (countryCode.equalsIgnoreCase("us") || countryCode.equalsIgnoreCase("ca") || countryCode.equalsIgnoreCase("uk")) {
            TestDataReader testDataReader = TestDataReader.getTestDataReader();
            String expectedVPSMessage = testDataReader.getData("pdp.vps.item.message");
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
        List<WebElement> productpricess = product__details.findElements(By.xpath(".//span[contains(@class,'product__price--')]"));
        List<String> prices = new ArrayList<>(productpricess.size());

        for (WebElement price : productpricess) {
            String priceText = price.getText().trim();
            priceText = priceText.replaceAll("your price ", "");
            priceText = priceText.replaceAll("valued at ", "");
            prices.add(price.getText());
        }

        return prices;

    }

    public int getYCoordinate(String elementName){
        WebElement element = getPDPElement(elementName);

        if(element.isDisplayed()) {
    	    Point point = element.getLocation();
    	    return point.getY();
        } else {
            return 0;
        }
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

    public WebElement getPDPElement(String element){
        WebElement pdpElement = null;

        switch (element.toLowerCase()) {
            case "item code":
                pdpElement=  productOverview.findElement(By.className("c-product__code"));
                break;
            case "social icons":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//ul[@class='footer__social__menu']"))));
                break;
            case "endcaps":
                pdpElement = wait.until(ExpectedConditions.visibilityOf(endCapNav));
                break;
            case "product details":
                pdpElement = productDetailsDrawer;
                break;
            case "name":
                pdpElement = productName;
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

        if (stateHolder.hasKey("saleCategory")) {
            saleCategory = ((String) stateHolder.get("saleCategory")).toLowerCase();
            stateHolder.remove("saleCategory");
        }

        if (stateHolder.hasKey("categoryFromPDPURL")) {
            categoryFromPDPURL = ((String) stateHolder.get("categoryFromPDPURL")).toLowerCase();
            stateHolder.remove("categoryFromPDPURL");
        }

        String crewCutCategories[] = testDataReader.getDataArray("crewCutCategories");
        List<String> crewCuts = Arrays.asList(crewCutCategories);

        if (crewCuts.contains(category) ||  crewCuts.contains(saleCategory) || crewCuts.contains(categoryFromPDPURL) || subCategory.equalsIgnoreCase("flowergirl")) {
            return true;
        } else {
            return false;
        }
    }
    
    public void addMonogram(){
    	WebElement addMonogramElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='p-monogram--add']/span[1]")));
    	addMonogramElement.click();
    }
}