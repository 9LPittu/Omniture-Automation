package com.jcrew.page;

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

    @FindBy(xpath = "//button[@class='js-search__button--refine search__button--refine tab-menu--box']")
    private WebElement refineButton;

    @FindBy(className = "search__filter--sort")
    private WebElement searchFilterSortBySection;


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
}
