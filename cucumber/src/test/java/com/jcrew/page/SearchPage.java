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

import java.util.ArrayList;
import java.util.List;

public class SearchPage {

    private final Logger logger = LoggerFactory.getLogger(SearchPage.class);

    private final WebDriver driver;

    @FindBy(className = "header__search")
    private WebElement headerSearch;

    @FindBy(id = "global__footer")
    private WebElement footer;

    @FindBy(className = "product__grid")
    private WebElement productGrid;

    @FindBy(className = "menu__search")
    private WebElement genderSelectorElement;

    @FindBy(xpath = "//div[@data-label='Women']")
    private WebElement womenSelector;

    @FindBy(id = "c-search__results")
    private WebElement searchResult;

    @FindBy(className = "search__filter--refinement")
    private WebElement searchFilterRefinementSection;

    @FindBy(className = "search__button--refine")
    private WebElement refineButton;

    @FindBy(className = "search__filter--sort")
    private WebElement searchFilterSortBySection;

    @FindBy(xpath = "//*[@id='c-search__results']/div/div[3]/div[1]/div/div[1]/a/img")
    List<WebElement> yellow_dresses;

    @FindBy(id = "c-product__overview")
    private WebElement product_details;

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

    public List<String> areGenderSelectorsDisplayed() {

        logger.info("Validating web element presence {}", genderSelectorElement.getClass());
        final List<String> gender_selector_attributes = new ArrayList<String>();
        final List<WebElement> gender_selectors = driver.findElements(By.xpath("//div[@data-group='gender']"));
        for (WebElement gender_selector : gender_selectors) {

            gender_selector_attributes.add(gender_selector.getAttribute("data-group"));
        }
        return gender_selector_attributes;
    }

    public void click_on_gender_selector() {
        womenSelector.click();
    }

    public boolean isRefinePage() {

        return searchResult.isDisplayed();
    }

    public boolean isRefineButtonDisplayed() {

        return refineButton.isDisplayed();
    }

    public void click_refine_button() {
        refineButton.click();
    }

    public boolean isRefinementDisplayed(String filterRefinement) {
        final WebElement filterRefinementElement = searchFilterRefinementSection.
                findElement(By.xpath(".//span[text()='" + filterRefinement + "' and @class='search__filter--label']"));
        return filterRefinementElement.isDisplayed();
    }

    public boolean isSortByOptionDisplayed(String sortByOption) {
        final WebElement filterRefinementElement = searchFilterSortBySection.
                findElement(By.xpath(".//a[text()='" + sortByOption + "' and contains(@class, 'search__refinement--link')]"));
        return filterRefinementElement.isDisplayed();
    }


    public boolean isSortByOptionSelected(String sortByOption) {
        final WebElement sortByOptionCheckbox = searchFilterSortBySection.findElement(
                By.xpath(".//a[text()='" + sortByOption + "' and contains(@class, 'search__refinement--link')]/../input"));

        return sortByOptionCheckbox.isSelected();
    }

    public boolean click_on_the_product() {
        List<WebElement> products_displayed = productGrid.findElements(By.className("c-product-tile"));
        List<WebElement> no_sale_products = new ArrayList<WebElement>();
        for (WebElement product_displayed : products_displayed) {
            if (product_displayed.getText().contains("now") == false) {
                logger.info("products with no sale price {}", product_displayed.getText());
                no_sale_products.add(product_displayed);
            }
        }

        // String product_name = no_sale_products.get(0).findElement(By.xpath("//*[@id='c-search__results']/div/div[3]/div[2]/div/div[2]/a/span")).getText();
        // String product_price = no_sale_products.get(0).findElement(By.xpath("//*[@id='c-search__results']/div/div[3]/div[2]/div/div[2]/span")).getText();
        no_sale_products.get(0).click();
        // logger.info(product_name);
        // logger.info(product_price);
        return true;

    }

    public String getProductName() {
        // WebElement product_details = driver.findElement(By.id("c-product__overview"));
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOf(product_details));
        String product_detail_name = product_details.findElement(By.tagName("h1")).getText();
        //String product_detail_price = product_details.findElement(By.cssSelector("#c-product__overview > header > section.product__price > span")).getText();
        logger.info(product_detail_name);
        // logger.info(product_detail_price);
        return product_detail_name;
    }

    public String getProductPrice() {
        String product_detail_price = product_details.findElement(By.className("product__price--list")).getText();
        logger.info(product_detail_price);
        return product_detail_price;
    }

    public String getProductSalePrice() {
        String product_detail_sale_price = product_details.findElement(By.className("product__price--sale")).getText();
        logger.info(product_detail_sale_price);
        return product_detail_sale_price;
    }

    public boolean scroll_down_the_page() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, 3000);");
        return true;
    }

    public boolean scroll_up_the_page() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, -500);");
        return true;
    }

    public String getPriceVariationInfo() {
        WebElement item_sale_price = driver.findElement(By.xpath("//*[@id='c-search__results']/div/div[3]/div[2]/div/div[2]/a/span[3]"));
        logger.info(item_sale_price.getText());
        return item_sale_price.getText();
    }


    public String isUpdatedSearchPage() {
        // List<WebElement> yellow_dresses = driver.findElements(By.tagName("img"));
        //logger.info(yellow_dresses.get(0).getClass().toString());

        for (WebElement yellow_dress : yellow_dresses) {
            waitForVisibility(yellow_dress);
            logger.info(yellow_dress.getText());
        }
        logger.info(yellow_dresses.get(0).getAttribute("src"));
        return yellow_dresses.get(0).getAttribute("src");

    }

    private void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
    }
}

