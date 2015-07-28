package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {

    @FindBy(className = "header__search")
    private WebElement headerSearch;

    @FindBy(id = "global__footer")
    private WebElement footer;

    @FindBy(className = "product__grid")
    private WebElement productGrid;

    public SearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public boolean isSearchPage() {
        return headerSearch.isDisplayed() && footer.isDisplayed();
    }

    public boolean areResultsDisplayed() {
        return !productGrid.findElements(By.className("c-product-tile")).isEmpty();
    }
}
