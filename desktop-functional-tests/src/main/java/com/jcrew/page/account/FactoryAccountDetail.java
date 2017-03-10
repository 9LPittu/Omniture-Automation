package com.jcrew.page.account;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.User;
import com.jcrew.utils.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by msayed3 on 8/20/2016.
 */
public class FactoryAccountDetail extends Account implements IAccountDetail {

    @FindBy(className = "mainContainer")
    private WebElement accountDetailForm;


    public FactoryAccountDetail(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(accountDetailForm));
    }

    public boolean isAccountDetailPage() {
        Util.waitLoadingBar(driver);
        wait.until(ExpectedConditions.visibilityOf(accountDetailForm));
        return accountDetailForm.isDisplayed();
    }

    public void updateDetails(String fieldLabel, String updateType) {
        throw new WebDriverException("Method not implemented for Factory");
    }

    public String getErrorMessage(String fieldLabel) {
        throw new WebDriverException("Method not implemented for Factory");
    }


    public void clickChangePassword() {
        throw new WebDriverException("Method not implemented for Factory");
    }

    public void fillChangePasswordFileds() {
        throw new WebDriverException("Method not implemented for Factory");
    }

    public boolean isBirthField(String btnStatus) {
        throw new WebDriverException("Method not implemented for Factory");

    }

    public void selectDate(String dateType, String value) {
        throw new WebDriverException("Method not implemented for Factory");
    }

    public void saveUpdates() {
        throw new WebDriverException("Method not implemented for Factory");
    }

    public String getConfirmatonMsg() {
        throw new WebDriverException("Method not implemented for Factory");
    }

    public String getBirthdayCopy() {
        throw new WebDriverException("Method not implemented for Factory");
    }

    public void clickLeftNavLinks(String linkText) {
        throw new WebDriverException("Method not implemented for Factory");
    }


    public void click_reward_link(String link) {
        throw new WebDriverException("Method not implemented for Factory");

    }

    public boolean isMenuLinkPresent(String link) {
        throw new WebDriverException("Method not implemented for Factory");
    }


    public User getUserDetails() {
        throw new WebDriverException("Method not implemented for Factory");
    }

    private WebElement getformElement(String fieldLabel) {
        throw new WebDriverException("Method not implemented for Factory");
    }


}
