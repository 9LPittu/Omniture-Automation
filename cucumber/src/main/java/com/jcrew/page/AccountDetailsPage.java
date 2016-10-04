package com.jcrew.page;


import com.jcrew.pojo.Country;
import com.jcrew.util.StateHolder;
import com.jcrew.util.UsersHub;
import com.jcrew.util.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 9msyed on 8/8/2016.
 */
public class AccountDetailsPage {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(AccountDetailsPage.class);
    private final StateHolder stateHolder = StateHolder.getInstance();

    @FindBy(id = "account_navigation")
    private WebElement accountNavSection;

    @FindBy(id = "account_content")
    private WebElement myDetailSection;

    @FindBy(className = "my-details-form")
    private WebElement myDetailForm;

    private String day = "my-details-form__birthday__day-list";
    private String month = "my-details-form__birthday__month-list";

    public AccountDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public boolean isMyDetailPage() {
        try {
            Util.waitForPageFullyLoaded(driver);
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailForm));
            Util.waitWithStaleRetry(driver,myDetailForm);
            return myDetailForm.isDisplayed();
        } catch (Exception e) {

            return false;
        }
    }


    public boolean toMyDetailPage() {
        try {
            return myDetailForm.isDisplayed();
        } catch (Exception e) {
            MyAccountPage myAccountPage = new MyAccountPage(driver);
            if (myAccountPage.isInAccountPage()) {
                myAccountPage.click_menu_link("MY DETAILS");
            }
            return isMyDetailPage();
        }
    }

    public void selectDate(String dateType, String value) {

        WebElement birthWrap = getformElement("Birth");
        WebElement list;
        if ("Month".equalsIgnoreCase(dateType)) {
            list = birthWrap.findElement(By.id(month));
        } else {
            list = birthWrap.findElement(By.id(day));
        }
        list.click();
        WebElement item = list.findElement(By.xpath("//li[contains(text(), '" + value + "')]"));
        item.click();
        Util.waitForPageFullyLoaded(driver);
    }

    public boolean verifyRewardLink(String link, String userCategory) {
        boolean expected = false;
        Country c = (Country) stateHolder.get("context");
        if (userCategory.equalsIgnoreCase(UsersHub.CAT_LOYALTY) && ("us".equalsIgnoreCase(c.getCountry())))
            expected = true;

        return expected == isMenuLinkPresent(link);

    }

    private boolean isMenuLinkPresent(String link) {
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(accountNavSection));
        try {
            WebElement navList = accountNavSection.findElement(By.className("account__selected-nav-item"));
            navList.findElement(By.xpath("//li[contains(text(), '" + link + "')]"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectFromList(String link) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(accountNavSection));
        Country c = (Country) stateHolder.get("context");

        String userCategory = (String) stateHolder.get("sidecaruserCategory");

        boolean ifReward = link.equalsIgnoreCase("J.Crew Card Rewards Status");
        boolean testRewardVisible = true;
        if (ifReward) {
            testRewardVisible = ((userCategory.equalsIgnoreCase(UsersHub.CAT_LOYALTY)) && "us".equalsIgnoreCase(c.getCountry()) && ifReward);
        }

        boolean ifOtherCountries = !(link.equalsIgnoreCase("GIFT CARD BALANCE") || link.equalsIgnoreCase("CATALOG PREFERENCES"));
        if ((("ca".equals(c.getCountry()) && !(link.equalsIgnoreCase("GIFT CARD BALANCE"))) || "us".equals(c.getCountry()) || ifOtherCountries) && testRewardVisible) {
            WebElement navList = accountNavSection.findElement(By.className("account__selected-nav-item"));
            WebElement item = navList.findElement(By.xpath("//li[contains(text(), '" + link + "')]"));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", item);
        }
    }

    public void saveUpdates() {
        Util.waitForPageFullyLoaded(driver);
        getformElement("save button").click();
        Util.waitForPageFullyLoaded(driver);
    }

    public String getConfirmatonMsg() {
        WebElement confimation = myDetailSection.findElement(By.className("my-details-form__confirm-message"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(confimation));
        return confimation.getText();
    }

    public String getBirthdayCopy() {
        WebElement birthCopy;
        WebElement birthCopyWrap = myDetailForm.findElement(By.className("my-details-form__label"));
        birthCopy = birthCopyWrap.findElement(By.xpath("//span[contains(@class,'is-birth-date-empty')]"));

        if (!(birthCopy.getAttribute("class").contains("is-hidden"))) {
            return birthCopy.getText();
        } else {
            birthCopy = birthCopyWrap.findElement(By.xpath("//span[contains(@class,'is-birth-date-populated')]"));
            return birthCopy.getText();
        }
    }


    public void updateDetails(String fieldLabel, String updateType) {
        WebElement formElement;
        Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains("/r/account/details"));
        Util.waitWithStaleRetry(driver,myDetailForm);        
        formElement = getformElement(fieldLabel);
        if ("invalid".equalsIgnoreCase(updateType)) {
            formElement.clear();
            formElement.sendKeys(Keys.TAB);
            formElement.click();
        } else {
            String oldValue = formElement.getAttribute("value");
            formElement.clear();
            formElement.sendKeys("new" + oldValue);
        }
    }

    public String getErrorMessage(String fieldLabel) {
        return getformElement(fieldLabel).findElement(By.xpath("following-sibling::span")).getText();
    }

    public void clickChangePassword() {
        getformElement("change password").click();
    }

    public void fillChangePasswordFileds() {
        getformElement("Old password").sendKeys((String) stateHolder.get("fakenewuserPassword"));
        getformElement("New password").sendKeys("TestNewPassword");
        getformElement("re-enter password").sendKeys("TestNewPassword");
    }

    public boolean isBirthField(String btnStatus) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailForm));
        WebElement birthWrap = getformElement("Birth");
        String monthClass = birthWrap.findElement(By.id(month)).getAttribute("class");
        String dateClass = birthWrap.findElement(By.id(day)).getAttribute("class");

        if ("disabled".equalsIgnoreCase(btnStatus)) {
            return ((monthClass.contains("is-disabled")) & (dateClass.contains("is-disabled")));
        } else {
            return !((monthClass.contains("is-disabled")) & (dateClass.contains("is-disabled")));
        }

    }

    private WebElement getformElement(String fieldLabel) {
        WebElement formElement;
        Util.waitLoadingBar(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailForm));
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(myDetailForm)));
        switch (fieldLabel) {
            case "first name":
                formElement = myDetailForm.findElement(By.id("my-details-form__first-name"));
                break;
            case "last name":
                formElement = myDetailForm.findElement(By.id("my-details-form__last-name"));
                break;
            case "email":
                formElement = myDetailForm.findElement(By.id("my-details-form__email"));
                break;
            case "country":
                formElement = myDetailForm.findElement(By.className("my-details-form__country_select"));
                break;
            case "save button":
                formElement = myDetailForm.findElement(By.className("my-details-form__btn-submit"));
                break;
            case "change password":
                formElement = myDetailForm.findElement(By.className("my-details-form__change-password-link"));
                break;
            case "Old password":
                formElement = myDetailForm.findElement(By.id("my-details-form__old-password"));
                break;
            case "New password":
                formElement = myDetailForm.findElement(By.id("my-details-form__new-password"));
                break;
            case "re-enter password":
                formElement = myDetailForm.findElement(By.id("my-details-form__confirm-password"));
                break;
            case "Birth":
                formElement = myDetailForm.findElement(By.className("my-details-form__birthday-wrapper"));
                break;
            default:
                return null;
        }
        
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(formElement)));
        return formElement;
    }

}