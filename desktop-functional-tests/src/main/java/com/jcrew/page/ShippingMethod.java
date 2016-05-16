package com.jcrew.page;

import com.jcrew.pojo.Product;
import com.jcrew.utils.StateHolder;
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

import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class ShippingMethod {
	
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(ShippingMethod.class);
    private final StateHolder stateHolder = StateHolder.getInstance();
    private final WebDriverWait wait;

    @FindBy(id = "frmSelectShippingMethod")
    private WebElement selectShippingMethod;

    public ShippingMethod(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(selectShippingMethod));
    }

    public void selectRandomShippingMethod() {
        List<WebElement> methods = selectShippingMethod.findElements(By.className("form-shipmethod"));
        WebElement selectedMethod = Util.randomIndex(methods);

        selectedMethod.click();
    }

    public void clickContinue() {
        WebElement continueLink = selectShippingMethod.findElement(By.className("button-submit-bg"));
        continueLink.click();
    }
    

}
