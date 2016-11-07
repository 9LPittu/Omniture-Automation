package com.jcrew.page;

import com.jcrew.utils.Util;

import edu.emory.mathcs.backport.java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ArraySearch extends Array{


    @FindBy(id = "page__search")
    private WebElement pageSearch;
    @FindBy(id = "c-search__results")
    private WebElement searchResults;
    @FindBy(className = "header__search")
    private WebElement headerSearch;
    @FindBy(xpath = "//div[@class='product__grid']")
    private WebElement productGrid;
    @FindBy(id = "c-search__filter")
    private WebElement searchFilter;

    public ArraySearch(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(pageSearch));

    }
    public void selectRandomProduct()
    {
        selectRandomProduct(searchResults);
    }

    public boolean isSearchPage() {
        Util.waitWithStaleRetry(driver, headerSearch);
        wait.until(ExpectedConditions.visibilityOf(searchResults));

        return headerSearch.isDisplayed() && searchResults.isDisplayed() && Util.countryContextURLCompliance(driver, country);
    }
    
    public boolean isSalePage() {
        Util.waitWithStaleRetry(driver, searchResults);

        return searchResults.isDisplayed() && Util.countryContextURLCompliance(driver, country);
    }


    public void click_first_product_in_grid() {
        WebElement random_product_tile = getFirstProduct();
        wait.until(ExpectedConditions.visibilityOf(random_product_tile));
        WebElement random_product_name = random_product_tile.findElement(By.className(NAME_CLASS));

        logger.info("Selected product: {}", random_product_name.getText());

        WebElement random_product_image = random_product_tile.findElement(By.tagName("img"));
        wait.until(ExpectedConditions.visibilityOf(random_product_image));
        random_product_image.click();

        Util.waitLoadingBar(driver);
    }

    private WebElement getFirstProduct() {
        return getProductTiles(searchResults).get(0);
    }

    public List<String> getPrices() {

        return getProductPrices(searchResults,PRICE_LIST_CLASS);
    }

    public List<String> getWasPrices() {

        return getProductPrices(searchResults,PRICE_WAS_CLASS);
    }

    public List<String> getSalePrices() {

        return getProductPrices(searchResults,PRICE_SALE_CLASS);
    }
    
    public String getHeaderTitle() {
    	WebElement headerTitle = pageSearch.findElement(By.tagName("h1"));
   
    	return headerTitle.getText();
    }
    
    public boolean isFiltersDisplayed() {
    	wait.until(ExpectedConditions.visibilityOf(searchFilter));
    	List<WebElement> searchFilters = searchFilter.findElements(By.xpath(".//div[contains(@class,'js-search__filter') or contains(@class,'js-filters__dropdown')]"));
    	int filterCount = searchFilters.size();
    	if (filterCount > 1) {
    		return true;
    	} else {
    		return false;
    	}  	
    } 
    
    public String getFilterValue(String filterName) {
    	wait.until(ExpectedConditions.visibilityOf(searchFilter));

    	
    	WebElement filterElement = searchFilter.findElement(By.xpath(".//h5[contains(@class,'search__refinement--name') and " + Util.xpathGetTextLower + "='" + filterName 
    					+ "']/following-sibling::h6"));

    	String filterValue = filterElement.getText();
    	
    	return filterValue;
    }
    
    
    public List<String> getGenderSelectors() {
    	List<String> genderList = new ArrayList<String>();
    	WebElement genderMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//menu[@class='menu__search menu__filters--gender']")));
    	List<WebElement> genderSelectors = genderMenu.findElements(By.xpath(".//div[contains(@class,'js-search__filter')]"));
    	
    	for (WebElement genderSelector:genderSelectors) {
    		String gender = genderSelector.getText().toLowerCase().trim();
    		genderList.add(gender);
    	}
    	
    	return genderList;
    }
}
