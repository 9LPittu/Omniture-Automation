package com.jcrew.page;


import com.jcrew.pojo.Product;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.apache.commons.lang.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class WishlistPage {

    private final WebDriver driver;
    private final StateHolder stateHolder = StateHolder.getInstance();

    @FindBy(id = "wishlistName")
    private WebElement wishListName;

    @FindBy(id = "userWishlist")
    private WebElement userWishlist;

    @FindBy(className = "home-icon")
    private WebElement homeIcon;

    @FindBy(id = "menuOptions")
    private WebElement menuOptions;

    @FindBy(id = "itemInfo")
    private WebElement itemInfo;

    private Logger logger = LoggerFactory.getLogger(WishlistPage.class);


    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public boolean isWishlistPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(wishListName));
        return wishListName.isDisplayed();
    }

    public String getColorForProduct(String productName) {
        WebElement productData = getProductData(productName);
        return productData.getAttribute("data-color");
    }

    public String getSizeForProduct(String productName) {
        WebElement productData = getProductData(productName);
        return productData.getAttribute("data-size");
    }

    public String getQuantityForProduct(String productName) {
        WebElement productData = getProductData(productName);
        return productData.getAttribute("data-quantity");
    }

    public void click_product(String productName) {
        WebElement wishedProduct = getProductData(productName);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("" +
                "var currItemData    = $('#item" + wishedProduct.getAttribute("data-listid") + "').find('.item-data'),\n" +
                "                  currProductCode = currItemData.attr('data-product-code'),\n" +
                "                  currLineId      = currItemData.attr('data-listid'),\n" +
                "                  currSku         = currItemData.attr('data-data-skuid'),     \n" +
                "                  currColorName   = currItemData.attr('data-color'),\n" +
                "                  currSize        = currItemData.attr('data-size'),\n" +
                "                  currQuantity    = currItemData.attr('data-quantity'),\n" +
                "                  queryString     = \"edit_wishlist_flag=true&wishListLineId=\" + currLineId + \"&selectedColorName=\" + currColorName.replace(' ', '+') + \"&selectedSize=\" + currSize.replace(' ', '+') + \"&selectedQuantity=\" + currQuantity,\n" +
                "                  sidecarProductPageUrl = \"/r/bm-edit-product/\" + currProductCode + \"?\" + queryString;\n" +
                "              window.location = sidecarProductPageUrl;");
    }

    private WebElement getProductData(String productName) {
        WebElement data = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-itemtitle='" +
                productName + "']")));
        return data;
    }

    public void click_home_icon() {
        homeIcon.click();
    }

    public void click_edit_product() {
        menuOptions.findElement(By.className("item-edit")).click();
    }

    public String getUpdateWishlistMessage() {
        WebElement updateWishlistMessage = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.className("content-button-secondary-confirmation")));

        return updateWishlistMessage.getText();
    }

    public void delete_current_products() {
        Util.waitForPageFullyLoaded(driver);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript(
                "var params = $.parseJSON(globalObj.wishlist.itemsArrayJson).header;" +
                        "params.deleteItems = params.sortOrder;" +
                        "$.ajax({ url: \"/userwishlist/ajax/wishlist_ajax_update.jsp\",\n" +
                        "         dataType: \"json\"," +
                        "         data: params" +
                        "});"
        );
    }

    public boolean shopTheLookProducts() {
        boolean result = true;
        @SuppressWarnings("unchecked")
		List<Product> addedProducts = (List<Product>) stateHolder.get("itemsInTray");
        HashMap<String, Product> wishedProducts = getWishedProducts();

        for(Product product:addedProducts){
            Product wish = wishedProducts.get(product.getProductName());
            if(wish == null){
                logger.info("{} product is not in wish list", product.getProductName());
                result = false;
            } else {
                result &= product.equals(wish);
            }
        }

        return result;
    }

    private HashMap<String, Product> getWishedProducts(){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(userWishlist));
        List<WebElement> productsInWishlist = userWishlist.findElements(By.className("item-data"));
        HashMap<String, Product> wishedProducts = new HashMap<>(productsInWishlist.size());

        for(WebElement wishElement:productsInWishlist){
            Product wish = getProduct(wishElement);
            wishedProducts.put(wish.getProductName(), wish);
        }

        return wishedProducts;
    }

    private String cleanProductName(WebElement item_data){
        String name = item_data.getAttribute("data-itemtitle").toLowerCase();
        name = StringEscapeUtils.unescapeHtml(name);
        name = name.replace("pre-order ","");

        return name;
    }

    private Product getProduct(WebElement item_data){
        Product product = new Product();

        String color = item_data.getAttribute("data-color").toLowerCase();
        String size = item_data.getAttribute("data-size").toLowerCase();
        String name = cleanProductName(item_data);

        product.setProductName(name);
        product.setSelectedColor(color);
        product.setSelectedSize(size);

        return product;
    }
    
    public boolean isPDPPageColorDisplayedInWishlist(){
    	@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");
    	Product product = productList.get(0);
    	String productName = product.getProductName();
    	String expectedColorName = product.getSelectedColor();

    	WebElement productData = getProductData(productName);
    	String colorNameInWishlist = productData.getAttribute("data-color");

    	return colorNameInWishlist.equalsIgnoreCase(expectedColorName);
    }

    public boolean isPDPPageSizeDisplayedInWishlist(){
    	@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");
    	Product product = productList.get(0);
    	String productName = product.getProductName();
    	String expectedSizeName = product.getSelectedSize();

    	WebElement productData = getProductData(productName);
    	String sizeNameInWishlist = productData.getAttribute("data-size");
        sizeNameInWishlist = sizeNameInWishlist.replace("SIZE","");

    	return sizeNameInWishlist.equalsIgnoreCase(expectedSizeName);
    }

    public boolean isPDPPageQuantityDisplayedInWishlist(){
    	@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");
    	Product product = productList.get(0);
    	String productName = product.getProductName();
    	String expectedSizeName = product.getSelectedSize();

    	WebElement productData = getProductData(productName);
    	String sizeNameInWishlist = productData.getAttribute("data-size");

    	return sizeNameInWishlist.equalsIgnoreCase(expectedSizeName);
    }
    
    public boolean isItemQuantityMatchesInWishlist(int expectedItemQuantity){

    	@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");
    	Product product = productList.get(0);
    	String productName = product.getProductName();

    	WebElement productData = getProductData(productName);
        int actualItemQuantity = Integer.parseInt(productData.getAttribute("data-quantity"));

        return actualItemQuantity == expectedItemQuantity;
    }

    public void editItemFromWishlist(){
    	@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) stateHolder.get("productList");
    	Product product = productList.get(0);
    	String productName = product.getProductName();

    	click_product(productName);
    }
}
