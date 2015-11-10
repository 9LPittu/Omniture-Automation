package com.jcrew.page;


import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WishlistPage {

    private final WebDriver driver;

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
        return driver.findElement(By.xpath("//div[@data-itemtitle='" +
                productName + "']"));
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
}
