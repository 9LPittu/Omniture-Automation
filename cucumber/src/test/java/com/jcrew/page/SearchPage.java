package com.jcrew.page;

import org.openqa.selenium.*;
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
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(headerSearch));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(footer));
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
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(refineButton));
        return refineButton.isDisplayed();
    }

    public void click_refine_button() {
        refineButton.click();
    }

    public boolean isRefinementDisplayed(String filterRefinement) {
        final WebElement filterRefinementElement = getRefinementElement(filterRefinement);
        return filterRefinementElement.isDisplayed();
    }

    private WebElement getRefinementElement(String filterRefinement) {
        WebElement element = null;
        try {
            element = searchFilterRefinementSection.
                    findElement(By.xpath(".//span[text()='" + filterRefinement + "' and @class='search__filter--label']"));
        } catch (StaleElementReferenceException sere) {

            element = searchFilterRefinementSection.
                    findElement(By.xpath(".//span[text()='" + filterRefinement + "' and @class='search__filter--label']"));
        }
        return element;
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

    public int getProductArrayCount() {
        return productGrid.findElements(By.className("c-product-tile")).size();
    }

    public List<String> getAllProductsDisplayedFor(String productId) {
        List<WebElement> productTiles = driver.findElements(By.className("product-tile"));
        List<String> products = new ArrayList<>();
        for (WebElement productTile : productTiles) {
            String dataProductAttribute = productTile.getAttribute("data-product");
            if (dataProductAttribute.contains(productId) && dataProductAttribute.contains("color")) {
                products.add(productTile.getText());
            }
        }
        return products;
    }

    public void click_refinement(String refinement) {
        final WebElement filterRefinementElement = getRefinementElement(refinement);
        filterRefinementElement.click();
    }

    public boolean isOptionSelectedForRefinementWithAccordionOpen(String option, String refinement) {
        final WebElement optionElementLink = getOptionElementFromRefinement(option, refinement);
        final WebElement optionCheckbox = optionElementLink.findElement(By.xpath("preceding-sibling::input"));
        return optionCheckbox.isSelected();
    }

    private WebElement getOptionElementFromRefinement(String option, String refinement) {
        final WebElement filterRefinementElement = getRefinementElement(refinement);
        final WebElement accordionMenuForRefinement =
                filterRefinementElement.findElement(By.xpath("../../div[@class='accordian__menu']"));

        return accordionMenuForRefinement.findElement(By.xpath(".//a[contains(text(), '" + option + "')]"));
    }

    public void select_option_from_refinement(String option, String refinement) {
        final WebElement optionElementLink = getOptionElementFromRefinement(option, refinement);
        optionElementLink.click();
    }

    public boolean isOptionSelectedForRefinementWithAccordionClosed(String optionSelected, String refinement) {

        final WebElement filterRefinementElement = getRefinementElement(refinement + ":");
        final WebElement selectedOption = filterRefinementElement.findElement(
                By.xpath("../span[@class='search__filter--selected' and text() = '" + optionSelected + "']"));

        return selectedOption.isDisplayed();

    }
}

