package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MultiplePdpPage {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(MultiplePdpPage.class);
    private final StateHolder stateHolder = StateHolder.getInstance();

    @FindBy (id = "c-tray__pagination")
    private WebElement paginationSection;
    @FindBy (id = "c-tray__list")
    private WebElement multipleProductSection;
    @FindBy (id = "c-product__information")
    private  WebElement productInformationSection;
    @FindBy (id = "c-product__reviews--ratings")
    private  WebElement productReviewRatingsSection;
    @FindBy (id = "btn__add-to-bag")
    private WebElement addToBagButton;
    @FindBy (id = "btn__wishlist")
    private WebElement addToWishlistButton;
    @FindBy (id = "c-product__sizes")
    private WebElement divSizes;
    
    @FindBy(xpath="//section[@id='c-product__details--link']/div/a")
    private WebElement fullProductDetailsLink;
    
    private WebElement header;
    private List<WebElement> products = null;
    private List<WebElement> productsImages = null;
    private int numProducts;
    private WebElement next;
    private WebElement previous;
    private WebElement trayCount;
    private WebElement article;
    private WebElement productDetailsDrawer;
    private WebElement sizeAndFitDrawer;

    private WebDriverWait wait;
    
    public MultiplePdpPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;

        wait = Util.createWebDriverWait(driver);
        Util.waitLoadingBar(driver);
        loadNavigation();

        header = driver.findElement(By.className("header__tray"));
    }

    private void loadNavigation(){
        //product details
        article = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("c-product")));

        Util.waitWithStaleRetry(driver, paginationSection);
        previous = paginationSection.findElement(By.className("pagination__item--previous"));
        next = paginationSection.findElement(By.className("pagination__item--next"));

        Util.waitWithStaleRetry(driver, multipleProductSection);
        products = multipleProductSection.findElements(By.className("js-tray__item"));
        productsImages = multipleProductSection.findElements(By.className("tray__image--thumbnail"));
        trayCount =  multipleProductSection.findElement(By.className("tray--count"));

        numProducts = products.size();

        Util.waitWithStaleRetry(driver, productInformationSection);
        productDetailsDrawer = productInformationSection.findElement(By.id("c-product__description"));
        
        Util.waitWithStaleRetry(driver, productReviewRatingsSection);
        Util.waitWithStaleRetry(driver, addToBagButton);
        Util.waitWithStaleRetry(driver, addToWishlistButton);

    }

    public boolean headerText(String title){
        String pageHeader = header.getText();
        return title.equalsIgnoreCase(pageHeader);
    }

    public boolean containsNavigation(){
        return previous.isDisplayed() && next.isDisplayed();
    }

    public boolean isArrowDisabled(String arrow) {
        String state;
        WebElement link;
        By linkXpath = By.xpath(".//*[contains(@class,'pagination__link')]");

        if ("next".equalsIgnoreCase(arrow)) {
            link = next.findElement(linkXpath);
        } else if ("previous".equalsIgnoreCase(arrow)) {
            link = previous.findElement(linkXpath);
        } else {
            logger.debug("bad use of isArrowDisabled(String)");
            return false;
        }

        state = link.getAttribute("class");

        return state.contains("is-disabled");
    }

    public int getSelectedProductIndex(){
        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);
            String productClass = product.getAttribute("class");
            if(productClass.contains("is-selected")){
                return i;
            }
        }

        return -1;
    }

    public void setSelectProductIndex(int number){
        //I am not sure if this will work in case the multiple pdp page
        // has more products that are not visible in the screen
        WebElement selected;
        String productCode;

        if(number < 0){
            number = numProducts - 1;
        } else if (number >= numProducts){
            logger.debug("Bad use of setSelectProductIndex");
            return;
        }

        selected = productsImages.get(number);
        productCode = products.get(number).getAttribute("data-code");

        Util.waitForPageFullyLoaded(driver);
        stateHolder.put("shoppableTrayProduct", article);
        Util.scrollToElement(driver, selected);
        selected.click();
        wait.until(ExpectedConditions.urlContains("itemCode="+productCode));
        loadNavigation();
    }

    public void clickNext(){
        stateHolder.put("shoppableTrayProduct", article);
        WebElement nextLink = next.findElement(By.tagName("a"));
        String currUrl = driver.getCurrentUrl();
        Util.waitLoadingBar(driver);
        wait.until(ExpectedConditions.invisibilityOfElementLocated((By.xpath("//div[@class='header__cart--image']/img"))));
        Util.scrollToElement(driver, fullProductDetailsLink);
        wait.until(ExpectedConditions.elementToBeClickable(nextLink));
        Util.clickOnElement(driver, nextLink);
        Util.waitLoadingBar(driver);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currUrl)));
        loadNavigation();
    }

    public void clickPrevious() {
        stateHolder.put("shoppableTrayProduct", article);
        String url = driver.getCurrentUrl();
        PropertyReader reader = PropertyReader.getPropertyReader();
        String browser = reader.getProperty("browser");

        if("androidchrome".equals(browser)){
            previous.click();
        } else {
            WebElement previousLink = previous.findElement(By.tagName("a"));
            wait.until(ExpectedConditions.visibilityOf(previousLink));
            previousLink.click();
        }

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
        loadNavigation();
    }

    public boolean hasProductChanged(){
        WebElement lastSelectedProduct = (WebElement) stateHolder.get("shoppableTrayProduct");
        return !lastSelectedProduct.equals(article);
    }

    public boolean checkEveryItemDetails(){
        if(getSelectedProductIndex() != 0)
            setSelectProductIndex(0);

        boolean result = true;

        for (int i = 0; i < numProducts && result; i++) {
            result = checkDetails();
            navigateToNextProduct(i);
        }

        return result;
    }

    private boolean checkDetails() {
        boolean detailsCheck = true;
        WebElement detail;

        //contains PRODUCT NAME
        detail = article.findElement(By.className("product__name"));
        detailsCheck &= !detail.getText().isEmpty();
        logger.debug("Name: {}", detail.getText());

        //contains IMAGE
        detail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product__image")));
        detailsCheck &= detail.isDisplayed();

        //contains COLOR SWATCHES
        detail = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//ul[@class='product__colors colors-list']")));
        detailsCheck &= detail.isDisplayed();

        //contains SIZES OPTIONS
        detail = divSizes.findElement(By.className("sizes-list"));
        detailsCheck &= detail.isDisplayed();

        //contains price
        if(hasVariations()){
            //get variations number
            List<WebElement> variationsType = article.findElements(By.xpath(".//li[contains(@class,'js-product__variation')]"));


            for(int i = 1; i < variationsType.size(); i++) {
                //get price of current variation
                detail = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(".//div[contains(@class,'product__variation--wrap')]/" +
                                "span[contains(@class,'product__variation--price-list')]")));

                detailsCheck &= detail.isDisplayed();

                //click on next variation
                selectNextVariation();
            }
        } else {
            //product does not have variations
            detail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product__price--list")));
            detailsCheck &= detail.isDisplayed();
        }


        return detailsCheck;
    }

    private void selectNextVariation(){
    	
    	WebElement productVariations = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='product__variations']")));
    	Util.scrollToElement(driver, productVariations);
    	
        By currentVariationXpath = By.xpath(".//li[contains(@class,'js-product__variation') and contains(@class,'is-selected')]");
        WebElement currentVariation =  wait.until(ExpectedConditions.presenceOfElementLocated(currentVariationXpath));
        WebElement nextVariation = currentVariation.findElement(By.xpath("following-sibling::li/div[contains(@class,'radio__label')]"));

        String url = driver.getCurrentUrl();
        nextVariation.click();
        Util.waitForPageFullyLoaded(driver);
        Util.waitLoadingBar(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
        loadNavigation();

    }

    public boolean itemsNumberMatchesPicturesSize() {
        String itemsString = trayCount.getText();        
        itemsString = itemsString.replace(" items", "");
        itemsString = itemsString.replace(" item", "");
        int itemsNumber = Integer.parseInt(itemsString);

        return itemsNumber == numProducts;
    }

    public boolean checkEveryItemDrawers() {
        if(getSelectedProductIndex() != 0)
            setSelectProductIndex(0);

        boolean result = true;

        for (int i = 0; i < numProducts && result; i++) {
            result = openDrawer(productDetailsDrawer);
            result &= openDrawer(productReviewRatingsSection);
            result &= isDrawerOpen(productDetailsDrawer);
            
            try{
            	sizeAndFitDrawer = productInformationSection.findElement(By.id("c-product__size-fit"));
            	result &= isDrawerOpen(sizeAndFitDrawer);
            }
            catch(NoSuchElementException nsee){
            	logger.debug("Size and Fit drawer is not displayed!!!");
            }            
            
            result &= isDrawerOpen(productReviewRatingsSection);
            navigateToNextProduct(i);
        }

        return result;
    }

    private boolean isDrawerOpen(WebElement parentDrawer){
    	wait.until(ExpectedConditions.visibilityOf(parentDrawer));
        wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(parentDrawer.findElement(By.className("accordian__wrap")))));
        WebElement drawer = parentDrawer.findElement(By.className("accordian__wrap"));

        //drawer is closed
        return drawer.getAttribute("class").contains("is-expanded");
    }

    private boolean openDrawer(WebElement parentDrawer) {
        boolean result = true;
        WebElement drawer = parentDrawer.findElement(By.className("accordian__wrap"));

        //drawer is closed
        result &= !drawer.getAttribute("class").contains("is-expanded");

        int cntr = 0;
        do{
        	drawer = parentDrawer.findElement(By.className("accordian__wrap"));
        	Util.scrollToElement(driver, drawer);
        	WebElement drawerHeader = drawer.findElement(By.className("accordian__header"));
            wait.until(ExpectedConditions.elementToBeClickable(drawerHeader));
            Util.clickOnElement(driver, drawerHeader);
            cntr++;

            //drawer is open
            result = drawer.getAttribute("class").contains("is-expanded");         
            if(!result){
            	logger.error("Drawer is not in open state!!!");
            	JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("window.scrollBy(0,-100)", "");
            }
        }while(!result && cntr<3);

        return result;
    }

    public void addAllProductsTo(String bag){
        List<Product> itemsInTray = new ArrayList<>(numProducts);

        if(getSelectedProductIndex() != 0)
            setSelectProductIndex(0);

        if("cart".equals(bag)){

            for (int i = 0; i < numProducts; i++) {
                pickColor();
                pickAvailableSize();
                addToBagButton.click();
                waitForBag(Integer.toString(i+1));
                navigateToNextProduct(i);
            }

        } else if("wish list".equals(bag)){

            for (int i = 0; i < numProducts; i++) {
                pickColor();
                pickAvailableSize();
                Product item = getProduct();
                itemsInTray.add(item);
                addToWishlistButton.click();
                navigateToNextProduct(i);
            }
            stateHolder.put("itemsInTray", itemsInTray);

        } else {
            logger.debug("Not able to add somewhere...");
        }
       
    }

    public void addRandomProductsTo(String bag) {
        List<Product> itemsInTray = new ArrayList<>(numProducts);
        int index = Util.randomIndex(itemsInTray.size());

        if (getSelectedProductIndex() != index)
            setSelectProductIndex(index);

        pickColor();
        pickAvailableSize();

        if ("cart".equals(bag)) {
            addToBagButton.click();

        } else if ("wish list".equals(bag)) {

            Product item = getProduct();
            itemsInTray.add(item);
            addToWishlistButton.click();
            stateHolder.put("itemsInTray", itemsInTray);

        } else {
            logger.debug("Not able to add somewhere...");
        }

    }

    private void waitForBag(String items) {    	
    	wait.until(ExpectedConditions.invisibilityOfElementLocated((By.xpath("//div[@class='header__cart--image']/img"))));
        WebElement bagText = driver.findElement(By.className("js-cart-size"));
        wait.until(ExpectedConditions.textToBePresentInElement(bagText,items));        
        logger.debug("added: {}", bagText.getText());        
    }

    private Product getProduct(){
        Product item = new Product();
        //name
        WebElement data = driver.findElement(By.className("product__name"));
        item.setProductName(data.getText().toLowerCase());

        //color
        data = driver.findElement(By.xpath("//div[@class='product__price-colors']/dl/dd"));
        item.setSelectedColor(data.getText().toLowerCase());

        //size
        data = driver.findElement(By.xpath("//div[@class='product__sizes']/dl/dd"));
        item.setSelectedSize(data.getText().toLowerCase());

        logger.debug("{}/{}/{}",item.getProductName(), item.getSelectedColor(), item.getSelectedSize());
        return item;
    }

    public int getNumProducts(){
        return numProducts;
    }

    private String pickAvailableSize() {
        List<WebElement> availableSizes = article.findElements(By.xpath(".//ul[@class='sizes-list']" +
                "/li[contains(@class,'js-product__size sizes-list__item') and not(contains(@class,'is-unavailable'))]"));
        WebElement sizeElement;

        if(availableSizes.size() > 0) {
            int random = Util.randomIndex(availableSizes.size());
            sizeElement  = availableSizes.get(random);
        } else {
            sizeElement = availableSizes.get(0);
        }

        sizeElement.click();

        return sizeElement.getText();
    }

    private String pickColor() {
        List<WebElement> availableColors = article.findElements(By.xpath(".//ul[@class='product__colors colors-list']/li"));
        WebElement colorElement;

        if(availableColors.size() > 0) {
            int random = Util.randomIndex(availableColors.size());
            colorElement = availableColors.get(random);
        } else {
            colorElement = availableColors.get(0);
        }

        colorElement.click();

        return colorElement.getAttribute("data-code");
    }

    private void navigateToNextProduct(int currentIndex){
        if(currentIndex < numProducts - 1) {
            WebElement nextProduct = products.get(currentIndex + 1);
            String productCode = nextProduct.getAttribute("data-code");            
            clickNext();
            Util.waitForPageFullyLoaded(driver);
            Util.waitLoadingBar(driver);            
            wait.until(ExpectedConditions.urlContains("itemCode=" + productCode));
        }
    }

    private void getModifiedURL(String query){
        try{
            URL trayURL = new URL(driver.getCurrentUrl());
            String newURL = trayURL.getProtocol() + "://" +trayURL.getHost() + trayURL.getPath() + query;
            logger.debug("new tray URL: {}", newURL);
            driver.get(newURL);
            loadNavigation();
        } catch (MalformedURLException badURL){
            logger.error("Not able to create URL");
        }
    }

    public void visitTrayWithLowerCaseExternalProduct(){
        String items = "";
        for (int i = 0; i < products.size(); i++) {
            items += products.get(i).getAttribute("data-code");
            if (i + 1 < products.size()) {
                items += ",";
            }
        }
        stateHolder.put("originalURLProducts", items);
        getModifiedURL("?externalProductCodes=" + items.toLowerCase());
    }

    public boolean productsMatchesOriginalURL() {
        boolean result = true;
        String originalProducts = (String) stateHolder.get("originalURLProducts");
        String products[] = originalProducts.split(",");

        for(String product : products){
            WebElement productByCode = multipleProductSection.findElement(By.xpath(".//li[@data-code='"+product+"']"));
            result &= productByCode != null;
        }

        return result;
    }

    public void visitTrayWithSelectedColors() {
        if(getSelectedProductIndex() != 0)
            setSelectProductIndex(0);

        String items = "";
        String color;
        List<String> colors = new ArrayList<>(numProducts);

        for (int i = 0; i < numProducts; i++) {
            items += products.get(i).getAttribute("data-code");
            color = pickColor();
            colors.add(color);
            items += "-"+color;
            if(i + 1 < products.size()){
                items += ",";
            }
            navigateToNextProduct(i);
        }

        stateHolder.put("selectedColors", colors);
        getModifiedURL("?externalProductCodes=" + items);
    }

    public boolean selectedColorsByDefault() {
        boolean result = true;
        List<String> colors = (List<String>) stateHolder.get("selectedColors");

        if(getSelectedProductIndex() != 0)
            setSelectProductIndex(0);

        for (int i = 0; i < numProducts; i++) {
            String colorCode = colors.get(i);
            WebElement defaultColor = article.findElement(By.xpath(".//li[@class='js-product__color colors-list__item is-selected']"));
            result &= colorCode.equals(defaultColor.getAttribute("data-code"));
            navigateToNextProduct(i);
        }

        return result;
    }

    private boolean hasVariations(){
        try{
            WebElement variations = driver.findElement(By.xpath("//section[@id='c-product__details']/div[@id='c-product__variations']/div/div/div[@class='variations-list-wrap']/menu"));
            logger.debug("Variations: {}", variations.getText());
            return true;

        }catch (NoSuchElementException noVariations){
            logger.debug("Product does not contain variations");
            return false;
        }
    }

    public boolean checkURLCountryContext() {
        Country country = (Country) stateHolder.get("context");

        return Util.countryContextURLCompliance(driver,country);
    }
}
