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

    @FindBy(id = "js-header__cart")
    private WebElement bag;
    @FindBy(id = "c-header__minibag")
    private WebElement minibag;

    public HeaderBag(WebDriver driver) {
        super(driver);
    }

    public void clickBag() {
        Util.scrollPage(driver, "UP");
    	Util.wait(2000);
        bag.click();
    }

    public void hoverBag() {
        hoverAction.moveToElement(bag);
        hoverAction.perform();

        wait.until(ExpectedConditions.visibilityOf(minibag));
    }

    public boolean isMiniBagDisplayed() {
        String miniBagClass = minibag.getAttribute("class");
        return miniBagClass.contains("is-active");
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
        wait.until(ExpectedConditions.visibilityOf(bag));
        WebElement cart_size = bag.findElement(By.className("js-cart-size"));
        String cartSizeText = cart_size.getText().trim();

        if (cartSizeText.isEmpty())
            cartSizeText = "0";

        cartSizeText = cartSizeText.replaceAll("[^0-9]", "");

        return Integer.parseInt(cartSizeText);
    }
}
