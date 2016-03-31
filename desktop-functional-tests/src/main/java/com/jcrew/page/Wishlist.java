package com.jcrew.page;

import com.jcrew.pojo.User;
import com.jcrew.utils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nadiapaolagarcia on 3/31/16.
 */
public class Wishlist {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Wishlist.class);
    private final WebDriverWait wait;
    private final HeaderWrap header;

    @FindBy (id = "wishlistName")
    private WebElement wishlistName;

    public Wishlist(WebDriver driver){
        this.driver = driver;
        wait = Util.createWebDriverWait(driver);
        header = new HeaderWrap(driver);
        PageFactory.initElements(driver, this);

        wait.until(ExpectedConditions.visibilityOf(wishlistName));
    }

    public boolean isWishList(){
        return wishlistName.isDisplayed();
    }
}
