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


/**
 * Created by 9hvenaga on 10/29/2015.
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
    
    @FindBy(className="search__refinement--checkbox")
    private WebElement newInSaleCheckBox;
    
    @FindBys({@FindBy(className="search__filter--label")})
    private List<WebElement> filterLabel;

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
}
