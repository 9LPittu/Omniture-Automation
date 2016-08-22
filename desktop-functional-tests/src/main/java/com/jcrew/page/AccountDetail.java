package com.jcrew.page;


import com.jcrew.pojo.Country;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by msayed3 on 8/20/2016.
 */
public class AccountDetail extends PageObject {

    @FindBy(className = "my-details-form")
    WebElement accountDetailForm;

    @FindBy(id = "account_navigation")
    WebElement accountNavigationSection;



    public final String USER_DETAILS_FIRST_NAME = "my-details-form__first-name";
    public final String USER_DETAILS_LAST_NAME = "my-details-form__last-name";
    public final String USER_DETAILS_EMAIL = "my-details-form__email";
    public final String USER_DETAILS_COUNTRY = "my-details-form__country-list";
    public final String USER_DETAILS_DOB_DAY = "my-details-form__birthday__day-list";
    public final String USER_DETAILS_DOB_MONTH = "my-details-form__birthday__month-list";

    public AccountDetail(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
        wait.until(ExpectedConditions.visibilityOf(accountDetailForm));
    }

    public boolean isAccountDetailPage(){
        wait.until(ExpectedConditions.visibilityOf(accountDetailForm));
        return accountDetailForm.isDisplayed();
    }

    public WebElement getMenuLink(String link,String page){
        Util.waitForPageFullyLoaded(driver);
        Country country = (Country) stateHolder.get("context");
        logger.debug(country.getCountry());
        wait.until(ExpectedConditions.visibilityOf(accountNavigationSection));
        WebElement linksTray = accountNavigationSection.findElement(By.className("account__navigation__items"));
        WebElement linkElement = linksTray.findElement(By.xpath("//li[text() ='"+link+"']"));

        return linkElement;
    }


    public Map<String, String> getUserDetails() {
        HashMap<String, String> userDetails = new HashMap<>();



        wait.until(ExpectedConditions.visibilityOf(accountDetailForm));

        WebElement information = accountDetailForm.findElement(By.id(USER_DETAILS_FIRST_NAME));
        userDetails.put(USER_DETAILS_FIRST_NAME, information.getAttribute("value"));

        information = accountDetailForm.findElement(By.id(USER_DETAILS_LAST_NAME));
        userDetails.put(USER_DETAILS_LAST_NAME, information.getAttribute("value"));

        information = accountDetailForm.findElement(By.id(USER_DETAILS_EMAIL));
        userDetails.put(USER_DETAILS_EMAIL, information.getAttribute("value"));

        Select country = new Select(accountDetailForm.findElement(By.id(USER_DETAILS_COUNTRY)));
        logger.debug("User details country: {}", country.getFirstSelectedOption().getText());
        userDetails.put(USER_DETAILS_COUNTRY, country.getFirstSelectedOption().getText());

        return userDetails;
    }


/*
    public Map<String, String> getUserDetails() {
        HashMap<String, String> userDetails = new HashMap<>();

        wait
        containerBorderLeft
        wait.until(ExpectedConditions.visibilityOf(leftContainer));

        WebElement information = leftContainer.findElement(By.id(USER_DETAILS_FIRST_NAME));
        userDetails.put(USER_DETAILS_FIRST_NAME, information.getAttribute("value"));

        information = leftContainer.findElement(By.id(USER_DETAILS_LAST_NAME));
        userDetails.put(USER_DETAILS_LAST_NAME, information.getAttribute("value"));

        information = leftContainer.findElement(By.id(USER_DETAILS_EMAIL));
        userDetails.put(USER_DETAILS_EMAIL, information.getAttribute("value"));

        Select country = new Select(leftContainer.findElement(By.id(USER_DETAILS_COUNTRY)));
        logger.debug("User details country: {}", country.getFirstSelectedOption().getText());
        userDetails.put(USER_DETAILS_COUNTRY, country.getFirstSelectedOption().getText());

        return userDetails;
    }

*/
}
