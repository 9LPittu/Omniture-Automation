package com.jcrew.page;

        import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

        import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;




/**
 * Created by 9hvenaga on 11/24/2015.
 */
public class SalePage {


        private final Logger logger = LoggerFactory.getLogger(SalePage.class);
        private final StateHolder stateHolder = StateHolder.getInstance();
        private final PropertyReader reader = PropertyReader.getPropertyReader();

        private final WebDriver driver;

        @FindBys({@FindBy(className = "menu__link--has-href")})
        private List<WebElement> saleCategoryLinks;

        @FindBy(className="header__sale")
        private WebElement saleHeader;

        @FindBy(className="js-search__button--refine")
        private WebElement refineButton;

        @FindBy(className="search__filter--gender")
        private WebElement genderInFilterName;

        @FindBy(className="search__filter--actions")
        private WebElement refinementSection;

        @FindBys({@FindBy(className="search__filter--label")})
        private List<WebElement> filterLabel;
        
        @FindBy(className="search__filter--sort")
        private WebElement sortBySection;

        @FindBy(xpath=".//section[contains(@class,'search__filter--sort')]/descendant::div[1]/a[text()='New in Sale']")
        private WebElement sortSectionFirstOption;

        @FindBy(xpath=".//section[contains(@class,'search__filter--sort')]/descendant::div[1]/a[text()='New in Sale']")
        private WebElement newInSaleText;

        @FindBy(xpath=".//section[contains(@class,'search__filter--sort')]/descendant::div[2]/a[text()='Price: Low to High']")
        private WebElement priceLowToHighText;

        @FindBy(xpath=".//section[contains(@class,'search__filter--sort')]/descendant::div[3]/a[text()='Price: High to Low']")
        private WebElement priceHighToLowText;

        @FindBy(xpath=".//section[contains(@class,'search__filter--sort')]/descendant::div[1]/input[@data-label='New in Sale']")
        private WebElement newInSaleCheckBox;

        @FindBy(xpath=".//section[contains(@class,'search__filter--sort')]/descendant::div[2]/input[@data-label='Low-High']")
        private WebElement priceLowToHighCheckBox;

        @FindBy(xpath=".//section[contains(@class,'search__filter--sort')]/descendant::div[3]/input[@data-label='High-Low']")
        private WebElement priceHighToLowCheckBox;

        @FindBy(id="btn__search--done")
        private WebElement refinePageDoneButton;

        @FindBys({@FindBy(className="tile__detail--price--sale")})
        private List<WebElement> salePrice;
        
        @FindBy(className="pagination__item--page-select")
        private WebElement pagination;
        
        @FindBy(xpath="//li[contains(@class,'pagination__item--previous')]/descendant::span[1]")
        private WebElement leftPagination;
        
        @FindBy(xpath="//li[contains(@class,'pagination__item--previous')]/descendant::span[contains(@class,'is-disabled')]")
        private WebElement leftPaginationDisabled;
        
        @FindBy(xpath="//li[contains(@class,'pagination__item--next')]/descendant::a[1]")
        private WebElement rightPagination;
        
        @FindBy(className="js-product__quantity")
        private WebElement paginationDropdown;

        public SalePage(WebDriver driver) {
            this.driver = driver;
            PageFactory.initElements(this.driver, this);
        }

        public boolean isSalePageDisplayed(){
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(saleHeader));
            return saleHeader.isDisplayed();
        }

        public boolean isSaleLandingPage() {
            System.out.println(driver.getCurrentUrl());
            String saleLandingUrl =reader.getProperty("environment")+"/r/sale";
            return driver.getCurrentUrl().equals(saleLandingUrl);

        }

        public boolean isRefineButtonDisplayed(){
            return refineButton.isDisplayed();
        }

        public boolean isSelectedCategoryDisplayed(String category){
            return genderInFilterName.getText().trim().equalsIgnoreCase(category);
        }

        public void clickRefineButton(){
            refineButton.click();
        }

        public boolean isRefinementPageDisplayed(){
            return refinementSection.isDisplayed();
        }

        public boolean isNewInSaleCheckboxSelectedByDefault(){
            return newInSaleCheckBox.isSelected();
        }

        public boolean isSortSectionFirstOptionDisplayed(String firstSortOption){
           return sortSectionFirstOption.getText().trim().toLowerCase().equals(firstSortOption.toLowerCase());
        }

        public boolean isSortOptionsDisplayed(String sortByFilterOption){

        	boolean blnFlag = false;
            
            List<WebElement> sortOptions = sortBySection.findElements(By.className("js-search__sort"));
            for(int i=0;i<sortOptions.size();i++){
            	String sortOptionName = sortOptions.get(i).getText().trim();
            	if(sortOptionName.equalsIgnoreCase(sortByFilterOption)){
            		blnFlag = true;
            		break;
            	}
            }
            
            return blnFlag;
        }
        
        public boolean isOtherSortOptionsUnchecked(String sortByOption1, String sortByOption2){    	
        	
        	boolean blnFlag = false;
        	
        	sortByOption1 = sortByOption1.toLowerCase();
        	sortByOption2 = sortByOption2.toLowerCase();
        	
        	if((sortByOption1.equals("new in sale") && sortByOption2.equals("price: high to low")) || 
        	   (sortByOption1.equals("price: high to low") && sortByOption2.equals("new in sale"))){
        		
        		if(!(newInSaleCheckBox.isSelected() || priceHighToLowCheckBox.isSelected())){
    				blnFlag = true;
    			}
        	}
        	else if((sortByOption1.equals("price: low to high") && sortByOption2.equals("price: high to low")) || 
             	   (sortByOption1.equals("price: high to low") && sortByOption2.equals("price: low to high"))){
        		
        		if(!(priceLowToHighCheckBox.isSelected() || priceHighToLowCheckBox.isSelected())){
    				blnFlag = true;
    			}
        	}
        	else if((sortByOption1.equals("new in sale") && sortByOption2.equals("price: low to high")) || 
              	   (sortByOption1.equals("price: low to high") && sortByOption2.equals("new in sale"))){
        		
        		if(!(newInSaleCheckBox.isSelected() || priceLowToHighCheckBox.isSelected())){
    				blnFlag = true;
    			}
        	}
        	
        	return blnFlag;     	
        }
        
        public void selectSortOptionCheckbox(String sortOption){        	
        	
        	 List<WebElement> sortOptions = sortBySection.findElements(By.className("search__refinement--link"));
             for(int index=0;index<sortOptions.size();index++){
             	String sortOptionName = sortOptions.get(index).getText().trim();
             	if(sortOptionName.toLowerCase().equals(sortOption.toLowerCase())){
             		WebElement sortCheckBox = driver.findElement(By.xpath("//section[contains(@class,'search__filter--sort')]/descendant::div[" + (index + 1) + "]/input"));
             		sortCheckBox.click();
             		Util.createWebDriverWait(driver).until(ExpectedConditions.elementSelectionStateToBe(sortCheckBox, true));
             		break;
             	}
             }
        }
        
        public void clickDoneButton() throws InterruptedException{

        	Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(refinePageDoneButton));
        	refinePageDoneButton.click();
        }
        
        public boolean isSalePricesAreSorted(String sortOrder){
        	
        	boolean blnFlag = false;
        	
        	//return false if objects are not found
        	if(!(salePrice.size()>0)){
        		blnFlag = false;
        	}
        	else{        	
	        	List<Double> lstSalePrices = new ArrayList<Double>();
	        			
	        	//Capture all the prices into double list
	        	for(int i=0;i<salePrice.size();i++){
	        		String salePriceVal = salePrice.get(i).getText().toLowerCase();    		    		    		
	        		salePriceVal = salePriceVal.replaceAll("[^0-9\\.]", "");        		
	        		lstSalePrices.add(Double.parseDouble(salePriceVal.trim()));
	        	}       	
	            
	            List<Double> ascending = new ArrayList<Double>(lstSalePrices);
	            Collections.sort(ascending);

	            List<Double> descending = new ArrayList<Double>(ascending);
	            Collections.reverse(descending);

	            if(sortOrder.toLowerCase().contains("low to high")){
	            	blnFlag = lstSalePrices.equals(ascending);
	            }
	            else if(sortOrder.toLowerCase().contains("high to low")){
	            	blnFlag = lstSalePrices.equals(descending);
	            }
        	}
            return blnFlag;        	
       }

        public boolean isLast4WeeksDisplayed() {

            return driver.findElement(By.xpath("//*[@id='c-search__results']/div/div[2]/button")).isDisplayed();
        }
        
        public boolean isPaginationDisplayed(){
        	
        	boolean blnFlag = false;
        	
        	WebElement searchResultsCount = driver.findElement(By.className("search__results--count"));
        	String resultsCount = searchResultsCount.getText().toLowerCase().replace("results", "").trim();
        	Integer resultsNumber = Integer.parseInt(resultsCount);
        	
        	//If the search results is > 60
        	if(resultsNumber > 60 && pagination.isDisplayed()){ 
       			blnFlag = true;
        	}	
        	else{
       			blnFlag = false;
       		}        			
        	
        	//If the search results is <= 60
        	if(resultsNumber <= 60 && pagination.isDisplayed()){        		
        		blnFlag = false;
        	}
        	else{
        		blnFlag = true;
        	}        			
        	
        	return blnFlag;
        }
        
        public boolean isPageUrlContains(String url){        	
        	return driver.getCurrentUrl().toLowerCase().contains(url.toLowerCase());
        }
        
        public boolean isLeftNavigationTextDisplayedAsPrev(String leftPaginationText){
        	return leftPagination.getText().trim().equalsIgnoreCase(leftPaginationText);
        }
        
        public boolean isLeftNavigationTextPrevIsDisabled(){        	        	
        	return leftPaginationDisabled.isDisplayed();
        }
        
        public boolean isRightNavigationTextDisplayedAsNext(String rightPaginationText){
        	return rightPagination.getText().trim().equalsIgnoreCase(rightPaginationText);
        }
        
        public boolean isRightNavigationTextNextIsActive(){
        	return rightPagination.isEnabled();
        }
        
        public boolean isPaginationDropdownDisplaysCurrentPageNumber(){
        	String pageString = driver.findElement(By.xpath("//span[contains(text(),'Page')]")).getText().toUpperCase();
        	Select list = new Select(paginationDropdown);
        	String currentPageNumber = list.getFirstSelectedOption().getText();
        	String totalPages = driver.findElement(By.xpath("//select[contains(@class,'js-product__quantity')]/following-sibling::span[1]")).getText().toUpperCase();
        	
        	String paginationText = pageString + currentPageNumber + totalPages;
        	
        	return paginationText.matches("^PAGE \\d+ OF \\d+$");
        }
        
        public void selectRandomPageNumberFromPaginationDropdown(){
        	
        	List<WebElement> arrayPageItemNames = driver.findElements(By.className("tile__detail--name"));      	
        	stateHolder.put("firstPageItemName",arrayPageItemNames.get(0).getText());

        	Select list = new Select(paginationDropdown);
        	int randomNumber = Util.randomIndex(list.getOptions().size());        	
        	list.selectByIndex(randomNumber);
        	
        	//wait till page is changed
        	Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains("Npge=" + String.valueOf(randomNumber+1)));
        }
        
        public boolean isCorrectPageDisplayedWhenPageNumberChanged(){
        	List<WebElement> arrayPageItemNames = driver.findElements(By.className("tile__detail--name"));
        	
        	String currentPageFirstItemName = arrayPageItemNames.get(0).getText();        	
        	String firstPageFirstItemName = (String) stateHolder.get("firstPageItemName");
        	
        	System.out.println("Current Page Item:" + currentPageFirstItemName);
        	System.out.println("First Page Item:" + firstPageFirstItemName);
        	
        	return !currentPageFirstItemName.equalsIgnoreCase(firstPageFirstItemName);
        }

        public void clickSaleLinkFromTopNav() {
            driver.findElement(By.xpath("//span[contains(@class, 'department-nav__text') and text() = 'sale']")).click();
        }

        public void clickOnSaleDept(String dept) {
            driver.findElement(By.xpath("//a[@class='js-sale__link' and @data-label='"+dept+"']")).click();
        }
}