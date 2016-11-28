package com.jcrew.page;

import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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

/**
 * Created by nadiapaolagarcia on 4/7/16.
 */
public class SaleLanding {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(SaleLanding.class);
    private final WebDriverWait wait;
    private final StateHolder stateHolder = StateHolder.getInstance();

    @FindBy(id = "page__sale")
    private WebElement page__sale;

    @FindBy(id = "c-promo-categories")
    private WebElement saleCategory;
    
    @FindBy(className = "c-sale__promo-frame")
    private WebElement promoFrame;
    
    @FindBy(id = "c-promo-alert")
    private WebElement secondPromoBox;
    

    public SaleLanding(WebDriver driver) {
        this.driver = driver;
        wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(page__sale));
    }

    public void selectRandomSaleCategory() {
        List<WebElement> categoryListItem = page__sale.findElements(By.className("c-category__list-item"));
        WebElement randomItem = Util.randomIndex(categoryListItem);
        WebElement randomItemLink = randomItem.findElement(By.tagName("a"));

        logger.info("Selected {} for sale category with link {}",
                randomItemLink.getAttribute("data-label"),
                randomItemLink.getAttribute("href"));

        randomItemLink.click();
        Util.waitLoadingBar(driver);
    }

    public void click_on_sale_subcategory(String subcategory) {
        Util.waitLoadingBar(driver);
        getSubcategoryFromSale(subcategory).click();
        stateHolder.put("sale category", subcategory);
        wait.until(ExpectedConditions.urlContains("search"));
        Util.waitLoadingBar(driver);
    }

    private WebElement getSubcategoryFromSale(String subcategory) {
        wait.until(ExpectedConditions.visibilityOf(saleCategory));
        WebElement sale = saleCategory.findElement(
                By.xpath(".//a[@class='js-sale__link' and translate(@data-label, 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz')='" + subcategory.toLowerCase() + "']"));

        logger.debug("Opening sale link {}", sale.getAttribute("href"));

        return sale;
    }
    
    public boolean isSalelanding() {
        wait.until(ExpectedConditions.visibilityOf(page__sale));
    	return page__sale.isDisplayed();
    }
    
    public List<String> getSaleCategory() {
    	List <String> categoryName = new ArrayList<String>();
    	WebElement saleCategories = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("c-promo-categories")));
    	List<WebElement> saleCategoryList = saleCategories.findElements(By.xpath(".//div[contains(@class,'c-category__list-item')]/a"));
    	for (WebElement saleCategory:saleCategoryList) {
    		String categoryText = saleCategory.getAttribute("data-label").toLowerCase();
    		categoryName.add(categoryText);	
    	}
    	
    	return categoryName;
    	
    }
    
    public boolean isSaleTitle() {
    	return page__sale.findElement(By.xpath("//div[@class='c-sale__title' and "+ Util.xpathGetTextLower +"='sale']")).isDisplayed();
    }
    
    public boolean isFirstPromo() {
    	wait.until(ExpectedConditions.visibilityOf(promoFrame));
    	WebElement Details = promoFrame.findElement(By.xpath(".//div/a[@class='js-sale-promo-link' and " + Util.xpathGetTextLower + "='details']"));
    	
    	return promoFrame.isDisplayed() && Details.isDisplayed();
    }
    
    public void clickDetailsLink() {
    	wait.until(ExpectedConditions.visibilityOf(promoFrame));
    	WebElement Details = promoFrame.findElement(By.xpath(".//div/a[@class='js-sale-promo-link' and " + Util.xpathGetTextLower + "='details']"));
    	wait.until(ExpectedConditions.visibilityOf(Details));
    	Details.click();
    	
    }
    
    public String getPromoPopUpState() {
    	WebElement promoPopOver = promoFrame.findElement(By.xpath(".//div[contains(@class,'js-sale-promo-popover')]"));
    	String style = promoPopOver.getAttribute("style").toLowerCase();
    	if (style.contains("block")) {
    		return "open";
    	} else {
    		return "closed";
    	}
    }
    
    public void closePromoDetails() {
    	WebElement closeIcon = promoFrame.findElement(By.xpath(".//span[contains(@class,'js-sale-promo-close')]"));
    	wait.until(ExpectedConditions.visibilityOf(closeIcon));
    	
    	closeIcon.click();
    }
    
    public boolean isSecondPromo() {
    	try {
	    	wait.until(ExpectedConditions.visibilityOf(secondPromoBox));
	    	
	    	WebElement secondPromo = secondPromoBox.findElement(By.xpath(".//div[@class='c-sale__promo-alert']"));
	    	
	    	return secondPromo.isDisplayed();
    	
    	} catch (TimeoutException te)	{
    		logger.info("second promo box is not displayed. Ignoring this as second promo is optional");
    		stateHolder.put("secondPromoVerification",true);
    		
    		return true;
    	}
    }
    
    public void clickSecondPromoLink(String name) {
    	name = name.toLowerCase().trim();
    	
    	wait.until(ExpectedConditions.visibilityOf(secondPromoBox));
    	
    	WebElement promoLink = secondPromoBox.findElement(By.xpath(".//div/a[" + Util.xpathGetTextLower + "='" + name + "']"));
    	wait.until(ExpectedConditions.elementToBeClickable(promoLink));
    	
    	promoLink.click();
    }

}
