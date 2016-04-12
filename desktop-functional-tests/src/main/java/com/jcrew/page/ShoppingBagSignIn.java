package com.jcrew.page;

import com.jcrew.pojo.User;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class ShoppingBagSignIn {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(ShoppingBagSignIn.class);
    private final WebDriverWait wait;

    @FindBy(id = "frmGuestCheckOut")
    private WebElement guestCheckoutForm;
    @FindBy(id = "loginUser")
    private WebElement loginUser;
    @FindBy(id = "loginPassword")
    private WebElement loginPassword;
    @FindBy(id = "userSignIn")
    private WebElement userSignIn;

    public ShoppingBagSignIn(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(guestCheckoutForm));
    }

    public void startGuestCheckout() {
        WebElement guestCheckoutLink = guestCheckoutForm.findElement(By.tagName("a"));
        guestCheckoutLink.click();
    }

    public void startRegisteredCheckout() {
        User user = User.getUser();

        logger.debug(user.getEmail() + "/" + user.getPassword());

        loginUser.sendKeys(user.getEmail());
        loginPassword.sendKeys(user.getPassword());

        WebElement signInAndCheckOutLink = userSignIn.findElement(By.className("button-submit"));
        signInAndCheckOutLink.click();
    }

}
