package com.jcrew.page;

import com.jcrew.pojo.Product;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        sizeAndFitDrawer = productInformationSection.findElement(By.id("c-product__size-fit"));

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
        selected.click();
        wait.until(ExpectedConditions.urlContains("itemCode="+productCode));
        loadNavigation();
    }

    public void clickNext(){
        stateHolder.put("shoppableTrayProduct", article);
        WebElement nextLink = next.findElement(By.tagName("a"));
        String currUrl = driver.getCurrentUrl();
        nextLink.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currUrl)));
        loadNavigation();
    }

    public void clickPrevious() {
        stateHolder.put("shoppableTrayProduct", article);
        WebElement previousLink = previous.findElement(By.tagName("a"));
        previousLink.click();
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
        detail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//ul[@class='product__colors colors-list']")));
        detailsCheck &= detail.isDisplayed();

        //contains SIZES OPTIONS
        detail = article.findElement(By.className("sizes-list"));
        detailsCheck &= detail.isDisplayed();

        //contains SIZES & FIT DETAILS (available only when containing more than one size
        List<WebElement> sizes = detail.findElements(By.tagName("li"));
        if(sizes.size() > 1) {
            detail = article.findElement(By.className("js-link__size-fit"));
            detailsCheck &= detail.isDisplayed();
        }

        //contains price
        //get variations number
        List<WebElement> variationsType = article.findElements(By.xpath(".//li[contains(@class,'js-product__variation')]"));

        if(variationsType.size() > 0){
            for(int i = 1; i < variationsType.size(); i++) {
                //get price of current variation
                detail = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(".//div[@class='product__variation--wrap is-mobile']/" +
                                "span[contains(@class,'product__variation--price-list')]")));

                detailsCheck &= detail.isDisplayed();

                //click on next variation
                //selectNextVariation();
            }
        } else {
            //product does not have variations
            detail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product__price--list")));
            detailsCheck &= detail.isDisplayed();
        }


        return detailsCheck;
    }

    private void selectNextVariation(){
        By currentVariationXpath = By.xpath(".//li[contains(@class,'js-product__variation') and contains(@class,'is-selected')]");
        WebElement currentVariation =  wait.until(ExpectedConditions.presenceOfElementLocated(currentVariationXpath));
        WebElement nextVariation = currentVariation.findElement(By.xpath("following-sibling::li"));

        String url = driver.getCurrentUrl();
        nextVariation.click();
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
        loadNavigation();

    }

    public boolean itemsNumberMatchesPicturesSize() {
        String itemsString = trayCount.getText();
        itemsString = itemsString.replace(" items", "");
        int itemsNumber = Integer.parseInt(itemsString);

        return itemsNumber == numProducts;
    }

    public boolean checkEveryItemDrawers() {
        if(getSelectedProductIndex() != 0)
            setSelectProductIndex(0);

        boolean result = true;

        for (int i = 0; i < numProducts && result; i++) {
            result = openDrawer(productDetailsDrawer);
            result &= openDrawer(sizeAndFitDrawer);
            result &= openDrawer(productReviewRatingsSection);
            result &= isDrawerOpen(productDetailsDrawer);
            result &= isDrawerOpen(sizeAndFitDrawer);
            result &= isDrawerOpen(productReviewRatingsSection);
            navigateToNextProduct(i);
        }

        return result;
    }

    private boolean isDrawerOpen(WebElement parentDrawer){
        WebElement drawer = parentDrawer.findElement(By.className("accordian__wrap"));

        //drawer is closed
        return drawer.getAttribute("class").contains("is-expanded");
    }

    private boolean openDrawer(WebElement parentDrawer) {
        boolean result = true;
        WebElement drawer = parentDrawer.findElement(By.className("accordian__wrap"));

        //drawer is closed
        result &= !drawer.getAttribute("class").contains("is-expanded");

        WebElement drawerHeader =   drawer.findElement(By.className("accordian__header"));
        drawerHeader.click();

        //drawer is open
        result &= drawer.getAttribute("class").contains("is-expanded");

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

    private void waitForBag(String items){
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

        return colorElement.getAttribute("data-name");
    }

    private void navigateToNextProduct(int currentIndex){
        if(currentIndex < numProducts - 1) {
            WebElement nextProduct = products.get(currentIndex + 1);
            String productCode = nextProduct.getAttribute("data-code");
            clickNext();
            Util.waitForPageFullyLoaded(driver);
            wait.until(ExpectedConditions.urlContains("itemCode=" + productCode));
        }
    }
}
