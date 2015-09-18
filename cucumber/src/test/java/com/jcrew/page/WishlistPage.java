package com.jcrew.page;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WishlistPage {

    private final WebDriver driver;

    private Logger logger = LoggerFactory.getLogger(Logger.class);

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


    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public boolean isWishlistPage() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(wishListName));
        return wishListName.isDisplayed();
    }

    public String getSelectedItemProductColor() {
        return driver.findElement(By.id("colorValue")).getText();

    }

    public String getSelectedItemProductSize() {
        return driver.findElement(By.id("sizeValue")).getText();
    }

    public String getSelectedItemProductQuantity() {
        return driver.findElement(By.id("quantityValue")).getText();
    }

    public void click_product(String itemId) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("if(!Utils.isTouch()) {window.ontouchstart = function(){};}");
        WebElement wishedProduct = driver.findElement(By.id(itemId));
        wishedProduct.click();
    }

    public void click_home_icon() {
        homeIcon.click();
    }

    public void click_edit_product() {
        menuOptions.findElement(By.className("item-edit")).click();
    }
}
