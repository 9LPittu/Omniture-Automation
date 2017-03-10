package com.jcrew.page.product;

import com.jcrew.page.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ngarcia on 10/6/16.
 */
public class ProductDetailSoldOut extends PageObject {

    @FindBy(id = "c-product__details")
    private WebElement product__details;
    @FindBy(id = "c-product__sold-out")
    private WebElement sold_out;

    public ProductDetailSoldOut(WebDriver driver) {
        super(driver);
    }

    public String getSoldOutMessage() {
        wait.until(ExpectedConditions.visibilityOf(sold_out));
        WebElement soldOutMessage = sold_out.findElement(By.className("product__sold-out"));

        return soldOutMessage.getText();
    }

    public boolean isSoldOut() {
        return sold_out.isDisplayed();
    }
}
