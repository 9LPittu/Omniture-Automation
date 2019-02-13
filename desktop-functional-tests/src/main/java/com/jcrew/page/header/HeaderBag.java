package com.jcrew.page.header;

import com.google.common.base.Function;
import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ngarcia on 3/22/17.
 */
public class HeaderBag extends HeaderWrap {

    @FindBy(className = "nc-nav__bag-tab__icon")
    private WebElement bag;
    @FindBy(xpath = "//div[@class='nc-nav__bag__panel is-open']")
    private WebElement minibag;

    public HeaderBag(WebDriver driver) {
        super(driver);
    }

    public void clickBag() {
        Util.scrollPage(driver, "UP");
    	Util.wait(2000);
        bag.click();
        /*Util.wait(2000);
        driver.get("https://aka-int-www.jcrew.com/checkout2/shoppingbag.jsp?sidecar=true");
        Util.wait(2000);*/
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
    	wait.until(ExpectedConditions.visibilityOf(bag));
        WebElement cart_size = driver.findElement(By.className("nc-nav__bag-tab__count"));
        cartSizeText = cart_size.getText().trim();
        if (cartSizeText.isEmpty()) {
        	cartSizeText = "0";
        }
        cartSizeText = cartSizeText.replaceAll("[^0-9]", "");
        return Integer.parseInt(cartSizeText);
    }
}
