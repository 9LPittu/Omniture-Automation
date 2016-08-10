package com.madewell.page;

import com.madewell.pojo.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 5/17/16.
 */
public class CheckoutMergeCart extends Checkout {

    @FindBy(id = "registered")
    private WebElement registered;
    @FindBy(id = "mergedCartActionTop")
    private WebElement actionsTop;
    @FindBy(id = "userMergeCart")
    private WebElement form;

    public CheckoutMergeCart(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(registered));
    }

    @Override
    public boolean isDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(form));

        return form.isDisplayed();
    }

    public String getUserName() {
        String subtitle = getSubtitle().toLowerCase();
        subtitle = subtitle.replace("welcome back", "");
        subtitle = subtitle.replace("!", "");

        return subtitle.trim();
    }

    public void saveWishlist() {
        wait.until(ExpectedConditions.visibilityOf(actionsTop));
        WebElement wishList = actionsTop.findElement(By.xpath(".//a[1]"));
        wishList.click();
    }

    public void addItems() {
        wait.until(ExpectedConditions.visibilityOf(actionsTop));
        WebElement addItemsToBag = actionsTop.findElement(By.xpath(".//a[2]"));
        addItemsToBag.click();

		List<Product> recentlyAdded =stateHolder.getList("toBag");
		List<Product> previouslyAdded = stateHolder.getList("userBag");
        recentlyAdded.addAll(previouslyAdded);

        stateHolder.remove("userBag");
        stateHolder.put("toBag", recentlyAdded);
    }
    
    public WebElement getButtonElementInMergeCartPage(String mergeCartElementName){
    	WebElement element = null;
    	wait.until(ExpectedConditions.visibilityOf(actionsTop));
    	
    	switch(mergeCartElementName.toUpperCase()){
    		case "SAVE TO WISHLIST & CONTINUE":
    			element = actionsTop.findElement(By.xpath(".//a[1]"));
    			break;
    		case "ADD ITEMS TO BAG & REVIEW ORDER":
    			element = actionsTop.findElement(By.xpath(".//a[2]"));
    			break;
    	}
    	
    	return element;
    }
}
