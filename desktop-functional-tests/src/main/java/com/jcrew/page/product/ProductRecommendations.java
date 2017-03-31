package com.jcrew.page.product;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by ngarcia on 3/30/17.
 */
public class ProductRecommendations extends ProductDetails {

    @FindBy(id = "c-product__recommendations")
    private WebElement product__recommendations;

    public ProductRecommendations(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(product__recommendations));
    }

    public int getRecommendationsNumber() {
        List<WebElement> recommendations = product__recommendations.findElements(By.className("c-product-tile"));

        return recommendations.size();
    }

    public void clickFirstRecommendedProduct() {
        clickRecommendation(product__recommendations.findElement(By.className("c-product-tile")));
    }

    public void clickRandomRecommendedProduct() {
        List<WebElement> recommendations = product__recommendations.findElements(By.className("c-product-tile"));
        int randomIndex = recommendations.size() > 5 ? 5 : recommendations.size();

        clickRecommendation(recommendations.get(randomIndex));

    }

    private void clickRecommendation(WebElement tile) {
        WebElement details = tile.findElement(By.className("js-product-recommendations--link"));
        stateHolder.put("baynote", details.getAttribute("data-baynote-pid"));

        tile.click();

        Util.waitLoadingBar(driver);

    }
}
