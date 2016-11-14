package com.jcrew.page;

import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

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
    @FindBy(id = "c-search__header-pagination")
    private WebElement headerPagination;
    @FindBy(id = "c-search__pagination")
    private WebElement footerPagination;


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
    	boolean result;
    	try {
    		result = searchResults.isDisplayed();
    	} catch (StaleElementReferenceException staleException)	{
    		Util.waitWithStaleRetry(driver, searchResults);
    		result = searchResults.isDisplayed();
    	}
    	
    	result = result && Util.countryContextURLCompliance(driver, country);
        
    	return result;
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
    	return searchFilters.size() > 1;  	
    } 
    
    public String getFilterValue(String filterName) {
    	wait.until(ExpectedConditions.visibilityOf(searchFilter));

    	
    	WebElement filterElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h5[contains(@class,'search__refinement--name') and " + Util.xpathGetTextLower + "='" + filterName 
    					+ "']/following-sibling::h6")));

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
    
    public void clickGenderSelector(String gender) {
    	gender = gender.toLowerCase().trim();
    	WebElement genderMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//menu[@class='menu__search menu__filters--gender']")));
    	WebElement genderSelector = genderMenu.findElement(By.xpath(".//div[contains(@class,'js-search__filter') and " 
    								+ Util.xpathGetTextLower + "='" + gender +"']"));
    	wait.until(ExpectedConditions.elementToBeClickable(genderSelector));
    	genderSelector.click();
    	wait.until(ExpectedConditions.urlContains("/r/search"));
    }
    
    public int getSearchResultsNumber() {
    	wait.until(ExpectedConditions.visibilityOf(searchResults));
        WebElement searchResultsNumber = searchResults.findElement(By.className("search__results--count"));
        String resultsText = searchResultsNumber.getText();
        resultsText = resultsText.replaceAll("[^0-9]*", "");

        return Integer.parseInt(resultsText);
    }
    
    public boolean isPaginationDisplayed(String position){
        boolean result=false;
        int resultsNumber = getSearchResultsNumber();
        stateHolder.put("pagination", true);

        
        if(resultsNumber > 60){
        	WebElement pagination = getPaginationElement(position);
        	result = headerPagination.isDisplayed();
        } else{
        	try {
        		result = !headerPagination.isDisplayed();
        	} catch (NoSuchElementException noElement) {
        		result = true;	
        	}
        }
        return result;
    }
    
    public int getPageNumber(String position) {
    	WebElement pagination = getPaginationElement(position);
    	wait.until(ExpectedConditions.visibilityOf(pagination));
    	
    	WebElement pageNumber = pagination.findElement(By.xpath(".//select[contains(@class,'dropdown--quantity')]/option[@selected='selected']"));
    	String selectedPageNumber = pageNumber.getAttribute("value").trim();
    	
    	return Integer.parseInt(selectedPageNumber);
    }
    
    public boolean isPaginationArrowDisplayed(String name, String position) {
    	WebElement pagination = getPaginationElement(position);
    	WebElement paginationArrow = pagination.findElement(By.xpath(".//li[contains(@class,'pagination__item') and contains(@class,'" + name + "')]/descendant::span[@class='pagination__arrow']"));
    	return paginationArrow.isDisplayed();
    }
    
    public String getPaginationArrowState(String name, String state, String position) {
    	WebElement pagination = getPaginationElement(position);
    	WebElement paginationArrow = pagination.findElement(By.xpath(".//li[contains(@class,'pagination__item') and contains(@class,'" + name + "')]/descendant::span[contains(@class,'pagination__link')]"));
    	
    	boolean isDisabled = paginationArrow.getAttribute("class").contains("is-disabled");
    	
    	if (isDisabled) {
    		return "disabled";
    	} else {
    		return "active";
    	}
    }
    
    public void selectPaginationArrow(String name, String position) {
    	//Save the name of first item in current page
    	String firstItemName = getFirstItemName();
        stateHolder.put("firstItemNameInArray", firstItemName);
    	
        WebElement pagination = getPaginationElement(position);
    	WebElement paginationArrow = pagination.findElement(By.xpath(".//li[contains(@class,'pagination__item') and contains(@class,'" + name + "')]/descendant::span[@class='pagination__arrow']"));
    	wait.until(ExpectedConditions.elementToBeClickable(paginationArrow));
    	paginationArrow.click();
    	Util.waitSpinningImage(driver);
    }
    
    public void selectPaginationLink(String name,String position) {
    	//Save the name of first item in current page
    	String firstItemName = getFirstItemName();
        stateHolder.put("firstItemNameInArray", firstItemName);
    	
        WebElement pagination = getPaginationElement(position);
    	WebElement paginationLink = pagination.findElement(By.xpath(".//li[contains(@class,'pagination__item') and contains(@class,'" + name + "')]/descendant::a"));
    	wait.until(ExpectedConditions.elementToBeClickable(paginationLink));
    	
    	Util.scrollAndClick(driver, paginationLink);

    	Util.waitSpinningImage(driver);
    }
    
    public void selectRandomPageNumberFromPaginationDropdown(String position){
    	
    	//Save the name of first item in current page
    	String firstItemName = getFirstItemName();
        stateHolder.put("firstItemNameInArray", firstItemName);
    	
        WebElement pagination = getPaginationElement(position);

        Select list = new Select(pagination.findElement(By.xpath(".//select[contains(@class,'dropdown--quantity')]")));
        int randomNumber = Util.randomIndex(list.getOptions().size()-1);
        list.selectByIndex(randomNumber + 1);

        Util.waitSpinningImage(driver);
    }
    
    public String getFirstItemName() {
    	List<WebElement> items = driver.findElements(By.className("tile__detail--name"));
        return items.get(0).getText();
    }
    
    public WebElement getPaginationElement(String position) {
    	position = position.trim();
    	if (position.equalsIgnoreCase("header")) {
    		Util.waitWithStaleRetry(driver, headerPagination);
    		return headerPagination;
    	} else {
    		Util.waitWithStaleRetry(driver, footerPagination);
    		return footerPagination;
    	}
    }
}


