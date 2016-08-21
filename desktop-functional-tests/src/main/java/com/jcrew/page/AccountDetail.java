package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by msayed3 on 8/20/2016.
 */
public class AccountDetail extends PageObject {

    @FindBy(className = "my-details-form")
    WebElement accountDetailForm;

    public final String USER_DETAILS_FIRST_NAME = "firstName";
    public final String USER_DETAILS_LAST_NAME = "lastName";
    public final String USER_DETAILS_EMAIL = "emailAdd";
    public final String USER_DETAILS_COUNTRY = "country";

    public AccountDetail(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
        wait.until(ExpectedConditions.visibilityOf(accountDetailForm));
    }

    public boolean isAccountDetailPage(){
        wait.until(ExpectedConditions.visibilityOf(accountDetailForm));
        return accountDetailForm.isDisplayed();
    }
}
