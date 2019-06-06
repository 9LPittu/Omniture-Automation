package com.jcrew.page.header;

/*import com.jcrew.page.PageObject;
import com.jcrew.utils.TestDataReader;*/
import com.jcrew.utils.Util;
import gherkin.formatter.model.DataTableRow;

//import org.apache.maven.model.Build;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngarcia on 3/2/17.
 */
public class TopNav extends HeaderWrap {
	
	String currentUrl = driver.getCurrentUrl();
    /*@FindBy(className = "js-header__department-nav")
    protected WebElement top_nav;*/
	@FindBy(className = "nc-nav_nav-bar--inner")
	protected WebElement top_nav;
    @FindBy(xpath = "//div[@class='c-header__department-nav js-header__department-nav']")
    protected WebElement top_nav_factory;
    
    
    public TopNav(WebDriver driver) {
        super(driver);
        //wait.until(ExpectedConditions.visibilityOf(top_nav));
    }
    
    public boolean isDisplayed(){
        return top_nav.isDisplayed();
    }

    public void clickDeptLinkFromTopNav(String dept) {
        String url = driver.getCurrentUrl();
        WebElement topnavlink = top_nav.findElement(
                By.xpath(".//a[@class='department-nav__link']/span[" + Util.xpathGetTextLower + " = '" + dept.toLowerCase() + "']"));
        topnavlink.click();

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
    }

    public List<String> getTopNavOptions() {
        List<WebElement> options = top_nav.findElements(By.className("department-nav__item"));
        List<String> optionsString = new ArrayList<>(options.size());

        for (WebElement option : options) {
            optionsString.add(option.getText().toLowerCase());
        }

        return optionsString;
    }

    @SuppressWarnings("unused")
	public void hoverCategory(String dept) {
    	WebElement categoryLink;
    	if(currentUrl.contains("factory.jcrew.com")) {
    		categoryLink = top_nav_factory.findElement(By.xpath(
                    "//div[@class='c-header__department-nav js-header__department-nav']//ul//li//a//span[text()='"+dept+"']"));
    	}else {
        categoryLink = top_nav.findElement(By.xpath("//span[contains(@class,'nc-nav__dept-link-wrap')]/*[text()='"+dept+"']"));
    	}
        hoverAction.moveToElement(categoryLink);
        hoverAction.build().perform();

        logger.info("Hovering over category {} in top nav", dept);
        stateHolder.put("category", dept);
        SubCategory subCategory = new SubCategory(driver);
    }

    public void hoverCategory(List<String> categories) {
        int index = Util.randomIndex(categories.size());
        String randomCategory = categories.get(index);

        hoverCategory(randomCategory);
    }

    public void hoverCategory(DataTableRow categories) {
        String category = categories.getCells().get(0);
        String subcategory = categories.getCells().get(1);
        
        hoverCategory(category);

        SubCategory subCategoryWrap = new SubCategory(driver);
        subCategoryWrap.selectSubCategoryForCategory(category,subcategory);
    }

    public void hoverCategory(String category, String subcategory) {
        hoverCategory(category);

        SubCategory subCategoryWrap = new SubCategory(driver);
        subCategoryWrap.selectSubCategory(subcategory);
    }
    
    
    
    
    public void hoverCatAndSubCat() {
        List<WebElement> cats = top_nav.findElements(By.xpath("//span[contains(@class,'nc-nav__dept-link-wrap')]/*"));
        for(int i=0;i<cats.size();i++) {
        	String catName = cats.get(i).getText();
        	hoverAction.moveToElement(cats.get(i));
        	hoverAction.build().perform();
        	List<WebElement> subCat = driver.findElements(By.xpath("//*[text()='"+catName+"']/parent::span//following-sibling::div[2]//div/ul//li/a"));
        	for(int j=0;j<subCat.size();j++) {
        		System.out.println(subCat.get(j).getText());
        		subCat.get(j).click();
        		//WebElement viewAll = driver.findElement(By.xpath(""));
        	}
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
