package com.jcrew.page;

import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.PropertyReader;
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
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class LogIn extends DriverFactory{

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(HeaderWrap.class);
    private final WebDriverWait wait;
    private final HeaderWrap header;
    private final PropertyReader reader = PropertyReader.getPropertyReader();

    @FindBy (id = "sidecarUser")
    private WebElement sidecarUser;
    @FindBy (id = "sidecarPassword")
    private WebElement sidecarPassword;
    @FindBy (xpath = "//button[@class='btn--primary btn--signin js-button-submit']")
    private WebElement signInHereButton;

    public LogIn (WebDriver driver){
        this.driver = driver;
        header = new HeaderWrap(driver);
        wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver,this);
        wait.until(ExpectedConditions.elementToBeClickable(signInHereButton));
    }

    public void signIn(){
        User user = User.getUser();
        logger.info("{}/{}",user.getEmail(), user.getPassword());
        sidecarUser.sendKeys(user.getEmail());
        sidecarPassword.sendKeys(user.getPassword());
        signInHereButton.click();
        header.reload();
    }
}
