package com.jcrew.page;

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
 * Created by nadiapaolagarcia on 3/29/16.
 */
public class MyAccount {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(MyAccount.class);
    private final WebDriverWait wait;
    private final HeaderWrap header;

    @FindBy (id = "main_cont")
    private WebElement main_content;

    public MyAccount(WebDriver driver){
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);
        this.header = new HeaderWrap(driver);
        PageFactory.initElements(driver, this);

        wait.until(ExpectedConditions.visibilityOf(main_content));
    }



}
