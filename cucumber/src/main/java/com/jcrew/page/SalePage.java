package com.jcrew.page;

import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.util.Util;


/**
 * Created by vbonker on 11/04/2015.
 */
public class SalePage {

    private final Logger logger = LoggerFactory.getLogger(SearchPage.class);

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

    public SalePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    //public boolean isSalePage() {
        //new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(headerSearch));
        //new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(footer));
        //return headerSearch.isDisplayed() && footer.isDisplayed();
    //}
    
    public void selectSaleCategory(String saleCategory) throws Throwable{    	
    	for(int i=0;i<saleCategoryLinks.size();i++){
    		String categoryLinkName = saleCategoryLinks.get(i).getText().trim().toLowerCase();    		
    		if(categoryLinkName.equals(saleCategory.toLowerCase())){    			
    			saleCategoryLinks.get(i).click();
    			logger.info("click on sale category -> {}", saleCategory);
    			break;
    		}
    	}    	
    }
    
    public boolean isSalePageDisplayed(){
    	return saleHeader.isDisplayed();
    }
    
    public boolean isRefineButtonDisplayed(){
    	return refineButton.isDisplayed();
    }
    
    public boolean verifySelectedCategory(String category){
    	return genderInFilterName.getText().trim().equals(category);
    }
    
    public void clickRefineButton(){
    	refineButton.click();
    }
    
    public boolean verifyRefinementPageDisplayed(){    	
    	return refinementSection.isDisplayed();
    }
    
    public boolean verifyNewInSaleCheckboxSelectedByDefault(){
    	return newInSaleCheckBox.isSelected();
    }
    
    public boolean verifyFilterNameDisplayed(String filterName){
    	
    	boolean blnFlag = false;
    	
    	for(int i=0;i<filterLabel.size();i++){
    		if(filterLabel.get(i).getText().trim().toLowerCase().equals(filterName.trim().toLowerCase())){
    			blnFlag = true;
    			break;
    		}
    	}
    	
    	return blnFlag;
    }
    
    public boolean verifyAccordianDrawerOpenedWhenFilterClicked(String filter){
    	
    	boolean blnFlag = false;   	
    	
    	for(int i=0;i<filterLabel.size();i++){
    		if(filterLabel.get(i).getText().trim().toLowerCase().equals(filter.trim().toLowerCase())){
    			filterLabel.get(i).click();
    			blnFlag = true;
    			break;
    		}
    	}
    	
    	if(blnFlag){    	
	    	try{	    			
	    		WebElement accordianMenu = driver.findElement(By.xpath("//a[@data-group='" + filter.toLowerCase() + "' and text()='View All']"));	    		
	    		if(accordianMenu.isDisplayed()){
	    			blnFlag = true;
	    		}
	    	}
	    	catch(Exception e){
	    		blnFlag = false;
	    	}
    	}
    	
    	return blnFlag;
    }
    
    public boolean verifyAccordianNotDisplayed(String filter){
    	
    	boolean blnFlag = false;
    	
    	try{
    		WebElement accordianMenu = driver.findElement(By.xpath("//a[@data-group='" + filter.toLowerCase() + "' and text()='View All']"));	    		
    		if(!accordianMenu.isDisplayed()){
    			blnFlag = true;
    		}
    		else{
    			blnFlag = false;
    		}
    	}
    	catch(Exception e){
    		blnFlag = true;
    	}
    	
    	return blnFlag;
    }
    
    public boolean verifySortSectionFirstOption(String firstSortOption){
    	
    	boolean blnFlag = false;
    	
    	try{
    		if(sortSectionFirstOption.getText().trim().toLowerCase().equals(firstSortOption.toLowerCase())){
        		blnFlag = true;
        	}
        	else{
        		blnFlag = false;
        	}
    	}
    	catch(Exception e){
    		blnFlag = false;
    	}
    	    	
    	return blnFlag;
    }
    
    public boolean verifySortSectionFirstOptionIsSelectedByDefault(){
    	
    	boolean blnFlag = false;
    	
    	try{
    		if(newInSaleCheckBox.isSelected()){
        		blnFlag = true;
        	}
        	else{
        		blnFlag = false;
        	}
    	}
    	catch(Exception e){
    		blnFlag = false;
    	}
    	    	
    	return blnFlag;
    }
    
    public boolean verifySortOptionsDisplayed(String sortByFilterOption){
    	
    	boolean blnFlag = false;    	
    	
    	switch(sortByFilterOption.toLowerCase()){
	    	case "price: low to high":
	    		if(priceLowToHighText.isDisplayed()){
	    			blnFlag = true;
	    		}
	    		break;
	    	case "price: high to low":
	    		if(priceHighToLowText.isDisplayed()){
	    			blnFlag = true;
	    		}
	    		break;
    	}
    	
    	return blnFlag;    	
    }
    
    public boolean verifyOtherSortOptionsUnchecked(String sortByOption){    	
    	
    	boolean blnFlag = false;
    	
    	switch(sortByOption.toLowerCase()){
    		case "new in sale":
    			
    			newInSaleCheckBox.click();
    			
    			if(!(priceLowToHighCheckBox.isSelected() || priceHighToLowCheckBox.isSelected())){
    				blnFlag = true;
    			}
    			break;
    		case "price: low to high":
    			
    			priceLowToHighCheckBox.click();    			
    			
    			if(!(newInSaleCheckBox.isSelected() || priceHighToLowCheckBox.isSelected())){
    				blnFlag = true;
    			}
    			break;
    		case "price: high to low":
    			
    			priceHighToLowCheckBox.click();
    			
    			if(!(newInSaleCheckBox.isSelected() || priceLowToHighCheckBox.isSelected())){
    				blnFlag = true;
    			}
    			break;
    	}
    	
    	return blnFlag;
    }
    
    public void selectSortOptionCheckbox(String sortOption){
    	
    	//select the sort option
    	switch(sortOption.toLowerCase()){
    		case "new in sale":
    			newInSaleCheckBox.click();
    			break;
    		case "price: low to high":
    			priceLowToHighCheckBox.click();
    			break;
    		case "price: high to low":
    			priceHighToLowCheckBox.click();
    			break;
    	}
    }
    
    public void clickDoneButton(){
    	refinePageDoneButton.click();
    }
    
    public boolean verifySalePricesAreSorted(String sortOrder){
    	
    	boolean blnFlag = false;
    	
    	//return false if objects are not found
    	if(!(salePrice.size()>0)){
    		return blnFlag;
    	}
    	
    	//Initialize the String array 
    	String[] arrSalePrices = new String[salePrice.size()];    	
    	
    	//Capture all the prices into String array
    	for(int i=0;i<salePrice.size();i++){
    		String salePriceVal = salePrice.get(i).getText().toLowerCase();
    		salePriceVal = salePriceVal.replaceAll("now", "");
    		salePriceVal = salePriceVal.replaceAll("select colors", "");
    		salePriceVal = salePriceVal.replaceAll(new Util().getCurrencySymbol(), "");
    		arrSalePrices[i]= salePriceVal.trim();    		
    	}
    	
    	//Verify the prices are sorted correctly
    	if(sortOrder.toLowerCase().contains("low to high")){
    		for(int i=0;i<arrSalePrices.length;i++){
    			double currentSalePriceVal = Double.parseDouble(arrSalePrices[i]);
    			double nextSalePriceVal = Double.parseDouble(arrSalePrices[i+1]);
    			
    			if(currentSalePriceVal <=nextSalePriceVal){
    				blnFlag = true;
    			}
    			else{
    				blnFlag = false;
    				break;
    			}
    			
    		}
    	}
    	
    	return blnFlag;
    }
}