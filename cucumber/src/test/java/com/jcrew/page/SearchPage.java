package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {

    private final WebDriver driver;

    @FindBy(className = "header__search")
    private WebElement headerSearch;

    @FindBy(id = "global__footer")
    private WebElement footer;

    @FindBy(className = "product__grid")
    private WebElement productGrid;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }


    public boolean isSearchPage() {
        return headerSearch.isDisplayed() && footer.isDisplayed();
    }

    public boolean areResultsDisplayed() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(productGrid));
        return !productGrid.findElements(By.className("c-product-tile")).isEmpty();
    }
}
