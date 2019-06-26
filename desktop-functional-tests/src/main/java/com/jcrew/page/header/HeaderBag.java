package com.jcrew.page.header;

import com.google.common.base.Function;
import com.jcrew.utils.Util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ngarcia on 3/22/17.
 */
public class HeaderBag extends HeaderWrap {

    @FindBy(xpath = "(//div[@class='nc-nav__bag-tab__icon'])[2]")
    private WebElement bag;
    @FindBy(xpath = "//div[@class='nc-nav__bag__panel is-open']")
    private WebElement minibag;
    @FindBy(xpath = "//span[text()='bag']")
    private WebElement bagFactory;
    @FindBy(xpath="(//div[@class='nc-nav__bag-tab__count'])[2]")
    private WebElement bagCount;
    @FindBy(className="js-cart-size")
    private WebElement bagCountFactory;

    public HeaderBag(WebDriver driver) {
        super(driver);
    }

	public void clickBag() {
		Util.scrollPage(driver, "UP");
		Util.wait(2000);
		if (driver.getCurrentUrl().contains("factory")) {
			bagFactory.click();
		} else {
			bag.click();
		}

	}

    public void hoverBag() {
        hoverAction.moveToElement(bag);
        hoverAction.perform();

        wait.until(ExpectedConditions.visibilityOf(minibag));
    }

    public boolean isMiniBagDisplayed() {
        String miniBagClass = minibag.getAttribute("class");
        return miniBagClass.contains("is-open");
    }

    public void waitUntilNoCheckOutDropdown() {

        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return !minibag.isDisplayed();
            }
        });

    }

    public void waitUntilCheckOutDropdown() {

        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return minibag.isDisplayed();
            }
        });

    }

    public int getItemsInBag() {
    	String cartSizeText = null;
    	
    	if(driver.getCurrentUrl().contains("factory")) {
    		cartSizeText = bagCountFactory.getText().trim();
    	}else {
    		wait.until(ExpectedConditions.visibilityOf(bag));
            cartSizeText = bagCount.getText().trim();
    	}
    	
        if (cartSizeText.isEmpty()) {
        	cartSizeText = "0";
        }
        cartSizeText = cartSizeText.replaceAll("[^0-9]", "");
        return Integer.parseInt(cartSizeText);
    }
}
