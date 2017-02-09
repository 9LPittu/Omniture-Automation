package com.jcrew.page;

import ch.qos.logback.classic.Logger;
import com.jcrew.pojo.Product;
import com.jcrew.utils.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by 9msyed on 10/21/2016.
 */
public class QuickShop extends PageObject {

    private final String PRICE_SALE_CLASS = "product__price--sale";
    private final String PRICE_LIST_CLASS = "product__price--list";

    @FindBy(id = "c-quickshop")
    WebElement qsModal;

    @FindBy(id = "js-header__cart")
    private WebElement bag;

    @FindBy(id = "c-product__overview")
    WebElement prodOverviewSection;

    @FindBy(id =  "btn__add-to-bag")
    private WebElement addToBagButton;
    @FindBy(id =  "btn__wishlist")
    private WebElement wishListButton;

    @FindBy(id = "c-product__variations")
    private WebElement variations;
    @FindBy(id = "c-product__price-colors")
    private WebElement price_colors;
    @FindBy(id = "c-product__price")
    private WebElement price;

    @FindBy(className = "c-product__description")
    private WebElement productDetails;

    @FindBy(id = "c-product__quantity")
    private  WebElement quantity;

    @FindBy(className = "c-quickshop__product-details")
    private WebElement prod_full_detials;




    public QuickShop(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
        wait.until(ExpectedConditions.visibilityOf(qsModal));
    }

    public boolean isQuickShop(){
        try{
            wait.until(ExpectedConditions.visibilityOf(qsModal));
            return qsModal.isDisplayed();
        }catch(WebDriverException e){
            return false;
        }
    }
    public String getProductName(){
        return getQSElement("name").getText();
    }
    public boolean isElementDisplayed(String element){
        WebElement webElement=getQSElement(element);
        return webElement.isDisplayed();
    }



    private Product getProduct() {
        Product product = new Product();
        product.setName(getProductName());
        product.setPrice(getPrice());
        product.setSelectedColor(getSelectedColor());
        product.setColor(getSelectedColor());
        product.setSize(getSelectedSize());
        product.setQuantity(getQuantity());
        product.setItemNumber(getItemNumber());
        return product;
    }

    private String getPrice() {
        List<WebElement> priceGroups = price_colors.findElements(By.xpath(".//div[@class='product__group']"));
        WebElement productPrice;
//        price_colors    c-product__price-colors
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

    private String getSelectedColor(){
        wait.until(ExpectedConditions.visibilityOf(price_colors));
        WebElement selectedColor =
                price_colors.findElement(By.xpath(".//li[contains(@class,'is-selected')]"));
        return selectedColor.getAttribute("data-name");

    }
    private String getSelectedSize(){
        WebElement size =  getQSElement("size chips");
        try{
            WebElement selectedSize =
                    size.findElement(By.xpath(".//li[contains(@class,'is-selected')]"));
            return selectedSize.getAttribute("data-name");
        }catch (NoSuchElementException e){
            return "";
        }
    }

    private String getQuantity() {
        Select qty = new Select(quantity.findElement(By.tagName("select")));
        return qty.getFirstSelectedOption().getText();
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


    public void selectRandomQty() {
        wait.until(ExpectedConditions.visibilityOf(quantity));
        Select qty = new Select(quantity.findElement(By.tagName("select")));

        int availableQty = qty.getOptions().size();
        if (availableQty > 1) {
            int randomQty = Util.randomIndex(availableQty - 1) + 1;
            qty.selectByValue(Integer.toString(randomQty));
        }
    }

    public void selectRandomSize() {
        WebElement size =  getQSElement("size chips");
        String availableSizesSelector = ".//li[contains(@class,'js-product__size sizes-list__item') " +
                "and not(contains(@class,'is-unavailable')) " +
                "and not(contains(@class,'is-selected'))]";

        List<WebElement> availableSizes = size.findElements(By.xpath(availableSizesSelector));

        if (availableSizes.size() > 0) {
            final WebElement selectedSize = Util.randomIndex(availableSizes);

            selectedSize.click();
        }
    }


    public void clickFullOnDetails(){
        stateHolder.put("fromQuickShop", getProduct());
        wait.until(ExpectedConditions.visibilityOf(getQSElement("view full details link"))).click();
        Util.waitForPageFullyLoaded(driver);
        new ProductDetails(driver);
    }
    public void clickBag(){
        stateHolder.addToList("toBag", getProduct());
        wait.until(ExpectedConditions.visibilityOf(getQSElement("add to bag"))).click();
        Util.createWebDriverWait(driver);
    }
    public void clickWishlist(){
        stateHolder.put("fromQuickShop", getProduct());
        wait.until(ExpectedConditions.visibilityOf(getQSElement("wishlist"))).click();
        Util.createWebDriverWait(driver);
        Util.scrollToElement(driver,bag);
    }
    public void clickClose(){
        wait.until(ExpectedConditions.visibilityOf(getQSElement("close"))).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("c-quickshop")));
    }
    public boolean isEnabled(String element){
        WebElement webElement = getQSElement(element);
        try{
            return webElement.isEnabled();
        }catch (WebDriverException e){
            return false;
        }
    }
    public boolean isVariationChanged(){
        String itemCode,newItemCode;
        itemCode = getSelectedVariationItemCode();
        selectRandomVarition();
        newItemCode = getSelectedVariationItemCode();
        logger.info("this is new item code "+newItemCode);
        return !(itemCode.equalsIgnoreCase(newItemCode));
    }

    private void selectRandomVarition(){
        WebElement variationList=getQSElement("variations");
        List <WebElement> variations=variationList.findElements(By.xpath(".//li[not(contains(@class,'is-selected'))]"));
        if(variations.size() > 0 ){
            variations.get(0).click();
        }
        Util.waitForPageReady(driver);

    }
    private String getSelectedVariationItemCode(){
        WebElement variationList=getQSElement("variations");
        WebElement selectedVariations=variationList.findElement(By.xpath(".//li[(contains(@class,'is-selected'))]"));
        return selectedVariations.getAttribute("data-code");

    }

    public String getMessage(){
       int attempts = 0;
       boolean retry = true;
       String errorMessage="";
       do{
            try{
                WebElement messageSection = wait.until(ExpectedConditions.visibilityOf(qsModal.findElement(By.className("c-product__message"))));
                WebElement errorMessageElement = messageSection.findElement(By.xpath(".//div[contains(@class,'message__other')]/div"));
                errorMessage = errorMessageElement.getText();
                retry=false;
            }catch (NoSuchElementException exception){
                Util.wait(2000);
                logger.error("Error message element on quick shop not found retried {} ",retry);
                attempts++;
            }
       }while (attempts < 5 & retry);
       return errorMessage;
    }
    private String getItemNumber(){
        WebElement prodDetailsDrawer = wait.until(ExpectedConditions.visibilityOf(getQSElement("product details")))
                .findElement(By.xpath(".//div[contains(@class,'accordian__wrap')]"));
        prodDetailsDrawer.click();
        WebElement prodCode = prodDetailsDrawer.findElement(By.xpath(".//li[@class='product__code']"));
        wait.until(ExpectedConditions.visibilityOf(prodCode));
        return prodCode.getText().replaceAll("Item","").replaceAll(" ","").replaceAll("\\.","").trim();

    }
    public boolean isDetailsDrawerClosed(){
        WebElement prodDetailsDrawer = wait.until(ExpectedConditions.visibilityOf(getQSElement("product details")))
                .findElement(By.xpath(".//div[contains(@class,'accordian__wrap')]"));
        return !(prodDetailsDrawer.getAttribute("class").contains("is-expanded"));
    }

    private WebElement getQSElement(String element){
        WebElement qsElement = null;

        switch (element.toLowerCase()) {
            case "name":
                WebElement productOverview = prodOverviewSection.findElement(By.className("product__overview"));
                qsElement = productOverview.findElement(By.className("product__name"));
                break;
            case "color swatches":
                qsElement = wait.until(ExpectedConditions.visibilityOf(price_colors));
                break;
            case "size chips":
            	wait.until(ExpectedConditions.visibilityOf(qsModal));
                qsElement = wait.until(ExpectedConditions.visibilityOf(qsModal.findElement(By.id("c-product__sizes"))));
                break;
            case "view full details link":
                qsElement = prod_full_detials.findElement(By.linkText("View full details"));
                break;
            case "close":
                qsElement = wait.until(ExpectedConditions.visibilityOf(qsModal.findElement(By.className("icon-close"))));
                break;
            case "variations":
                qsElement = variations.findElement(By.className("variations-list-wrap"));
                wait.until(ExpectedConditions.visibilityOf(qsElement));
                break;
            case "size chart":
                qsElement = getQSElement("size chips").findElement(By.linkText("size charts"));
                wait.until(ExpectedConditions.visibilityOf(qsElement));
                break;
            case "quantity":
                qsElement = wait.until(ExpectedConditions.visibilityOf(quantity));
                break;
            case "add to bag":
                qsElement = wait.until(ExpectedConditions.visibilityOf(addToBagButton));
                break;
            case "wishlist":
                qsElement = wait.until(ExpectedConditions.visibilityOf(wishListButton));
                break;
            case "product details":
                wait.until(ExpectedConditions.visibilityOf(productDetails));
                qsElement = productDetails.findElement(By.className("product__details"));
                break;
        }
        return qsElement;
    }
}
