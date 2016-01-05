package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    @FindBy(xpath = "//div[@data-group='gender']")
    private
    List<WebElement> genderSelectors;

    @FindBy(id = "c-search__results")
    private WebElement searchResult;

    @FindBy(className = "search__filter--gender")
    private WebElement genderTag;

    @FindBy(className = "c-search__filter--refinement")
    private WebElement searchFilterRefinementSection;

    @FindBy(className = "search__button--refine")
    private WebElement refineButton;

    @FindBy(className = "search__filter--sort")
    private WebElement searchFilterSortBySection;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }


    public boolean isSearchPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerSearch));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(searchResult));
        return headerSearch.isDisplayed() && searchResult.isDisplayed();
    }


    public boolean areResultsDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productGrid));
        return !productGrid.findElements(By.className("c-product-tile")).isEmpty();
    }

    public List<String> areGenderSelectorsDisplayed() {
        final List<String> genderSelectorAttributes = new ArrayList<>();
        for (WebElement gender_selector : genderSelectors) {

            genderSelectorAttributes.add(gender_selector.getAttribute("data-group"));
        }
        return genderSelectorAttributes;
    }

    public void click_on_gender_selector() {
        womenSelector.click();
    }

    public void click_on_gender_selector(String gender) {
        for (WebElement genderSelector : genderSelectors) {
            if (genderSelector.getText().equalsIgnoreCase(gender)) {
                genderSelector.click();
            }
        }

    }

    public boolean isRefinePage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(genderTag));
        return genderTag.isDisplayed();
    }

    public boolean isRefineButtonDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(refineButton));
        return refineButton.isDisplayed();
    }

    public void click_refine_button() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(refineButton));
        refineButton.click();
    }

    public boolean isRefinementDisplayed(String filterRefinement) {
        final WebElement filterRefinementElement = getRefinementElement(filterRefinement);
        return filterRefinementElement.isDisplayed();
    }

    private WebElement getRefinementElement(String filterRefinement) {
        WebElement element;
        try {
            element = searchFilterRefinementSection.
                    findElement(By.xpath(".//span[contains(text(), '" + filterRefinement + "') and @class='search__filter--label']"));
        } catch (StaleElementReferenceException sere) {
            element = searchFilterRefinementSection.
                    findElement(By.xpath(".//span[contains(text(), '" + filterRefinement + "') and @class='search__filter--label']"));
        }
        return element;
    }

    public boolean isSortByOptionDisplayed(String sortByOption) {
        final WebElement filterRefinementElement = searchFilterSortBySection.
                findElement(By.xpath(".//a[text()='" + sortByOption + "' and contains(@class, 'search__refinement--link')]"));
        return filterRefinementElement.isEnabled();
    }


    public boolean isSortByOptionSelected(String sortByOption) {
        final WebElement sortByOptionCheckbox = searchFilterSortBySection.findElement(
                By.xpath(".//a[text()='" + sortByOption + "' and contains(@class, 'search__refinement--link') and contains(@class, 'is-selected')]"));

        return sortByOptionCheckbox.isDisplayed();
    }

    public String click_on_no_sale_price_product() {

        List<WebElement> products_displayed = productGrid.findElements(By.className("c-product-tile"));

        List<WebElement> no_sale_products = new ArrayList<>();
        for (WebElement product_displayed : products_displayed) {
            if (!product_displayed.getText().contains("now")) {
                no_sale_products.add(product_displayed);
            }
        }
        int min = 0;
        int max = no_sale_products.size() - 1;
        int range = (max - min) + 1;
        int randomNumber = (int) (Math.random() * range) + min;
        WebElement selectedNoSalePriceProduct = no_sale_products.get(randomNumber);

        WebElement selectedProductNameElement = selectedNoSalePriceProduct.findElement(By.className("tile__detail--name"));

        String productName = selectedProductNameElement.getText();
        logger.debug("sale product selected now {}", productName);
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(
                selectedNoSalePriceProduct.findElement(By.className("product__image--small"))));
        selectedNoSalePriceProduct.findElement(By.className("product__image--small")).click();

        return productName;
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
        try {
            final WebElement optionElementLink = getOptionElementFromRefinement(option, refinement);
            final WebElement optionCheckbox = optionElementLink.findElement(By.xpath("preceding-sibling::input"));
            return optionCheckbox.isSelected();

        } catch (StaleElementReferenceException sere) {
            return isOptionSelectedForRefinementWithAccordionOpen(option, refinement);
        }
    }

    private WebElement getOptionElementFromRefinement(String option, String refinement) {
        try {
            final WebElement filterRefinementElement = getRefinementElement(refinement);
            final WebElement accordionMenuForRefinement =
                    filterRefinementElement.findElement(By.xpath("../../div[@class='accordian__menu']"));

            return accordionMenuForRefinement.findElement(By.xpath(".//a[contains(text(), '" + option + "')]"));

        } catch (StaleElementReferenceException sere) {
            return getOptionElementFromRefinement(option, refinement);
        }
    }

    public void select_option_from_refinement(String option, String refinement) {
        final WebElement optionElementLink = getOptionElementFromRefinement(option, refinement);
        optionElementLink.click();
    }

    public boolean isOptionSelectedForRefinementWithAccordionClosed(String optionSelected, String refinement) {

        boolean result;
        try {
            result = Util.createWebDriverWait(driver).until(
                    ExpectedConditions.visibilityOf(
                            getRefinementElement(refinement).findElement(
                                    By.xpath("../span[@class='search__filter--selected' and contains(text(),'" + optionSelected + "')]")
                            )
                    )
            ).isDisplayed();
        } catch (StaleElementReferenceException sere) {
            result = isOptionSelectedForRefinementWithAccordionClosed(optionSelected, refinement);
        }

        return result;
    }

    public void select_option_from_multiple_select_refinement(String option, String refinement) {
        try {
            final WebElement filterRefinementElement = getRefinementElement(refinement);
            final WebElement accordionMenuForRefinement =
                    filterRefinementElement.findElement(By.xpath("../../div[@class='accordian__menu']"));
            final WebElement optionElement = accordionMenuForRefinement.findElement(By.linkText(option));

            optionElement.click();

        } catch (StaleElementReferenceException sere) {
            select_option_from_multiple_select_refinement(option, refinement);
        }
    }

    public boolean isRefinementOpen(String refinement) {
        final WebElement filterRefinementElement = getRefinementElement(refinement);
        final WebElement drawerIcon = filterRefinementElement.findElement(By.xpath("following-sibling::i"));
        return drawerIcon.getAttribute("class").contains("icon-see-less");
    }

    public void click_refinement_close_drawer(String refinement) {
        try {
            final WebElement filterRefinementElement = getRefinementElement(refinement);
            final WebElement drawerIcon = filterRefinementElement.findElement(
                    By.xpath("following-sibling::i[contains(@class, 'icon-see-less')]"));

            drawerIcon.click();

        } catch (StaleElementReferenceException sere) {

            click_refinement_close_drawer(refinement);

        }
    }

    public void click_refinement_menu_done_button() {
        final WebElement doneButton = searchFilterRefinementSection.findElement(By.id("btn__search--done"));
        doneButton.click();
    }

    public int getCurrentNumberOfResults() {
        String text = searchResult.findElement(By.className("search__results--count")).getText();
        Integer numberOfResults = Integer.valueOf(text.replace(" results", ""));

        logger.debug("Number of results found are {}", numberOfResults);

        return numberOfResults;
    }

    public boolean isBreadcrumbDisplayedFor(String option) {
        WebElement breadcrumbElement = searchResult.findElement(
                By.xpath(".//button[contains(@class, 'search__results--crumb') and contains(text(), '" + option
                        + "')]"));

        return breadcrumbElement.isDisplayed();
    }
}

