package com.jcrew.page;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WishlistPage {

    @FindBy(id = "wishlistName")
    private WebElement wishListName;

    public WishlistPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public boolean isWishlistPage() {
        return wishListName.isDisplayed();
    }
}
