package com.jcrew.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchPage {

    private final Logger logger = LoggerFactory.getLogger(SearchPage.class);
    
    private final WebDriver driver;

    @FindBy(className = "header__search")
    private WebElement headerSearch;

    @FindBy(id = "global__footer")
    private WebElement footer;

    @FindBy(className = "product__grid")
    private WebElement productGrid;
    
    @FindBy(className ="menu__search" )
    private WebElement gender_selector_element;
    
    @FindBy(xpath ="//div[@data-label='Women']" )
    private WebElement women_selector;
    
    @FindBy(id = "c-search__results")
    private WebElement search_result;
    
    @FindBy(xpath ="//button[@class='js-search__button--refine search__button--refine tab-menu--box']" )
    private WebElement refine_button;
    
  
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
      
        logger.info("Validating web element presence {}", gender_selector_element.getClass());
        List<String>  gender_selector_attributes= new ArrayList<String>();
        List<WebElement> gender_selectors = driver.findElements(By.xpath("//div[@data-group='gender']"));
        for(WebElement gender_selector:gender_selectors) {
            
                gender_selector_attributes.add(gender_selector.getAttribute("data-group"));
        }
        return gender_selector_attributes; 
     }
    
    public void click_on_gender_selector() {
        women_selector.click();
    }
    
    public boolean isRefinePage() {
       
        return search_result.isDisplayed();
    }
    
    public boolean isRefineButtonDisplayed() {
       
        return refine_button.isDisplayed();
    }
    
}
