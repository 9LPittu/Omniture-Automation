package com.jcrew.page.product;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by ngarcia on 3/23/17.
 */
public class ProductDetailsQuantity extends ProductDetails {

    @FindBy(id = "c-product__quantity")
    private WebElement product_quantity;

    public ProductDetailsQuantity(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(product_quantity));
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

    public void selectSpecifiedQuantity(String quantity){
        wait.until(ExpectedConditions.visibilityOf(product_quantity));
        Select quantityElement = new Select(product_quantity.findElement(By.tagName("select")));
        quantityElement.selectByValue(quantity);
        logger.debug("Selected quantity: {}", quantity);
    }

    public String getQuantity() {
        Select qty = new Select(product_quantity.findElement(By.tagName("select")));
        return qty.getFirstSelectedOption().getText();
    }

    public boolean isDisplayed() {
        return product_quantity.isDisplayed();
    }
}
