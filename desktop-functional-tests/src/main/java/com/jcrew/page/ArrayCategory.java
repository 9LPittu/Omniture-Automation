package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ArrayCategory extends Array{

    @FindBy(id = "c-product__list")
    private WebElement productList;
    @FindBy(id = "c-category__filters")
    private WebElement categoryFilters;
    @FindBy(id = "c-category__item-count")
    private WebElement itemCount;

    public ArrayCategory(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(productList));
        footer = new Footer(driver);
        header = new HeaderWrap(driver);
    }

    private List<WebElement> getProductTiles() {
        return getProductTiles(productList);
    }

    public List<String> getPrices() {

        return getProductPrices(productList,PRICE_LIST_CLASS);
    }

    public List<String> getWasPrices() {
        return getProductPrices(productList,PRICE_WAS_CLASS);
    }

    public List<String> getSalePrices() {
        return getProductPrices(productList,PRICE_SALE_CLASS);
    }
    public void selectRandomProduct() {
        selectRandomProduct(productList);
    }

    public String getRefineText() {
        WebElement dropDown = categoryFilters.findElement(By.xpath(".//h3/span[contains(@class,'js-label')]"));
        return dropDown.getText().toLowerCase();
    }

    private void openRefineAccordion() {
        WebElement accordion = categoryFilters.findElement(By.className("js-accordian__wrap"));
        wait.until(ExpectedConditions.visibilityOf(accordion));
        String accordionClass = accordion.getAttribute("class");

        if(!accordionClass.contains("is-expanded")) {
            WebElement accordionHeader = accordion.findElement(By.className("js-accordian__header"));
            accordionHeader.click();
        } else {
            logger.info("Refine dropdown already open");
        }
    }

    public List<String> getRefineOptions() {
        WebElement accordion = categoryFilters.findElement(By.className("js-accordian__wrap"));
        openRefineAccordion();

        List<WebElement> options = accordion.findElements(By.className("accordian__menu__item"));

        List<String> optionsString = new ArrayList<>(options.size());

        for(WebElement option: options) {
            optionsString.add(option.getText().toLowerCase());
        }

        return optionsString;
    }

    public int getNumberOfLists() {
        List<WebElement> lists = productList.findElements(By.xpath(".//div[contains(@class,'product__list')]"));
        return lists.size();
    }

    public List<String> getAvailableLists() {
        List<WebElement> lists = productList.findElements(By.xpath(".//div[contains(@class,'product__list')]/header/h4"));
        List<String> optionsString = new ArrayList<>(lists.size());

        for(WebElement item: lists) {
            optionsString.add(item.getText().toLowerCase());
        }

        return optionsString;
    }

    public String getItemsText() {
        WebElement itemCountElement = itemCount.findElement(By.id("js-products-count"));
        logger.debug("Current category contains {} items", itemCountElement.getText());

        return itemCountElement.getText();
    }

    public void selectRefinement() {
        WebElement accordion = categoryFilters.findElement(By.className("js-accordian__wrap"));
        openRefineAccordion();
        List<WebElement> options = accordion.findElements(By.className("accordian__menu__item"));

        int random = Util.randomIndex(options.size() - 1) + 1;
        WebElement selectedOption = options.get(random).findElement(By.tagName("a"));

        logger.debug("Selected {} from refinement", selectedOption.getText());
        stateHolder.put("itemsBefore", getItemsText());
        stateHolder.put("selectedRefinement", selectedOption.getText().toLowerCase());

        selectedOption.click();
    }
    public boolean isCategoryArray(){
        return productList.isDisplayed();
    }
}
