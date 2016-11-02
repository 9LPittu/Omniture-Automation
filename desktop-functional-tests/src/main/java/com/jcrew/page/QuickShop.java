package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by 9msyed on 10/21/2016.
 */
public class QuickShop extends PageObject {

    private final String PRICE_SALE_CLASS = "product__price--sale";
    private final String PRICE_LIST_CLASS = "product__price--list";

    @FindBy(id = "c-quickshop")
    WebElement qsModal;
    @FindBy(id = "c-quickshop__body")
    WebElement qsBody;
    @FindBy(id = "c-product__overview")
    WebElement prodOverview;
    @FindBy(id =  "btn__add-to-bag")
    private WebElement addToBagButton;
    @FindBy(id =  "btn__wishlist")
    private WebElement wishListButton;

    @FindBy(id =  "c-product__variations")
    private WebElement variation;


    @FindBy(className = "c-product__price-colors")
    private WebElement colors;

    @FindBy(id = "c-product__price")
    private WebElement price;

    @FindBy(id = "c-product__sizes")
    private WebElement sizes;

    @FindBy(id = "c-product__quantity")
    private  WebElement quantity;

    @FindBy(className = "c-quickshop__product-details")
    private WebElement prod_detials;



    public QuickShop(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public boolean isQuickShop(){
        wait.until(ExpectedConditions.visibilityOf(qsModal));
        return qsModal.isDisplayed();
    }
    public String getProductName(){
        return getQSElement("name").getText();
    }
    public boolean isElementDisplayed(String element){
        WebElement webElement=getQSElement(element);
        return webElement.isDisplayed();
    }



    private WebElement getQSElement(String element){
        WebElement qsElement = null;

        switch (element.toLowerCase()) {
            case "name":
                WebElement productOverview = prodOverview.findElement(By.className("product__overview"));
                qsElement = productOverview.findElement(By.className("product__name"));
                break;
            case "color swatchs":
                qsElement = wait.until(ExpectedConditions.visibilityOf(colors));
                break;
            case "size chips":
                qsElement = wait.until(ExpectedConditions.visibilityOf(sizes));
                break;
            case "view full details link":
                qsElement = prod_detials.findElement(By.linkText("View full details"));
                break;
            case "close":
                break;
            case "item code":
                break;
            case "variations":
                qsElement = wait.until(ExpectedConditions.visibilityOf(variation.findElement(By.className("variations-list-wrap"))));
                break;
            case "size chart":
                qsElement = sizes.findElement(By.linkText("size charts"));
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
               // pdpElement = productDetailsDrawer;
                break;
        }
        return qsElement;
    }
}
