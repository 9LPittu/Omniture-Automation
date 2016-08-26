package com.jcrew.page;


import com.github.javafaker.Faker;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.User;
import com.jcrew.utils.Util;
import org.apache.commons.lang.StringEscapeUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by msayed3 on 8/20/2016.
 */
public class AccountDetail extends PageObject {

    @FindBy(className = "my-details-form")
    WebElement accountDetailForm;

    @FindBy(id = "account_navigation")
    WebElement accountNavigationSection;

    @FindBy(id = "account_content")
    WebElement accountContentSection;


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
        Util.waitLoadingBar(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(accountDetailForm));
        return accountDetailForm.isDisplayed();
    }
    public void updateDetails(String fieldLabel, String updateType) {
        WebElement formElement;
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(accountDetailForm));
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

    public boolean isAccountInfoMatched(){
        User loggedInUser = (User) stateHolder.get("signedUser");

        Map<String, String> userDetails = getUserDetails();
        boolean equalsFirstName =  StringEscapeUtils.unescapeHtml(userDetails.get(USER_DETAILS_FIRST_NAME)).equalsIgnoreCase(loggedInUser.getFirstName());
        boolean equalsLastName =  StringEscapeUtils.unescapeHtml(userDetails.get(USER_DETAILS_LAST_NAME)).equalsIgnoreCase(loggedInUser.getLastName());
        boolean equalsEmail =  userDetails.get(USER_DETAILS_EMAIL).equalsIgnoreCase(loggedInUser.getEmail());
        boolean equalsCountry = userDetails.get(USER_DETAILS_COUNTRY).equalsIgnoreCase(loggedInUser.getCountry());
        logger.debug("Compare account info in account detail page first {},lastname {},email {},country {}",equalsFirstName,
                equalsLastName,equalsEmail,equalsCountry);
        return (equalsFirstName && equalsLastName && equalsEmail && equalsCountry);
    }
    public void clickChangePassword() {
        getformElement("change password").click();
    }

    public void fillChangePasswordFileds() {
        String newPassword = new Faker().lorem().fixedString(6).replaceAll(" ","_");
        getformElement("Old password").sendKeys((String) stateHolder.get("fakenewuserPassword"));
        getformElement("New password").sendKeys(newPassword);
        getformElement("re-enter password").sendKeys(newPassword);
    }
    public boolean isBirthField(String btnStatus) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(accountDetailForm));
        WebElement birthWrap = accountDetailForm.findElement(By.className("my-details-form__birthday-wrapper"));
        String monthClass = birthWrap.findElement(By.id(USER_DETAILS_DOB_MONTH)).getAttribute("class");
        String dateClass = birthWrap.findElement(By.id(USER_DETAILS_DOB_DAY)).getAttribute("class");

        if ("disabled".equalsIgnoreCase(btnStatus)) {
            return ((monthClass.contains("is-disabled")) & (dateClass.contains("is-disabled")));
        } else {
            return !((monthClass.contains("is-disabled")) & (dateClass.contains("is-disabled")));
        }

    }
    public void selectDate(String dateType, String value) {

        WebElement birthWrap = getformElement("Birth");
        WebElement list;
        if ("Month".equalsIgnoreCase(dateType)) {
            list = birthWrap.findElement(By.id(USER_DETAILS_DOB_MONTH));
        } else {
            list = birthWrap.findElement(By.id(USER_DETAILS_DOB_DAY));
        }
        list.click();
        WebElement item = list.findElement(By.xpath("//li[contains(text(), '" + value + "')]"));
        item.click();
        Util.waitForPageFullyLoaded(driver);
    }
    public void saveUpdates() {
        Util.waitForPageFullyLoaded(driver);
        getformElement("save button").click();
        Util.waitForPageFullyLoaded(driver);
    }
    public String getConfirmatonMsg() {
        WebElement confimation = accountContentSection.findElement(By.className("my-details-form__confirm-message"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(confimation));
        return confimation.getText();
    }
    public String getBirthdayCopy() {
        WebElement birthCopy;
        WebElement birthCopyWrap = accountDetailForm.findElement(By.className("my-details-form__label"));
        birthCopy = birthCopyWrap.findElement(By.xpath("//span[contains(@class,'is-birth-date-empty')]"));

        if (!(birthCopy.getAttribute("class").contains("is-hidden"))) {
            return birthCopy.getText();
        } else {
            birthCopy = birthCopyWrap.findElement(By.xpath("//span[contains(@class,'is-birth-date-populated')]"));
            return birthCopy.getText();
        }
    }
    public void clickLeftNavLinks(String linkText){
        Util.waitForPageFullyLoaded(driver);
        Country country = (Country) stateHolder.get("context");
        logger.debug(country.getCountry());
        wait.until(ExpectedConditions.visibilityOf(accountNavigationSection));
        WebElement linksTray = accountNavigationSection.findElement(By.className("account__navigation__items"));
        WebElement linkElement = linksTray.findElement(By.xpath("//li[text() ='"+linkText+"']"));
        linkElement.click();
        Util.waitForPageFullyLoaded(driver);
    }
    public boolean verifyRewardLink(String link, String userCategory) {
        boolean expected = false;
        Country c = (Country) stateHolder.get("context");

        if (userCategory.equalsIgnoreCase(User.CAT_LOYALTY) && ("us".equalsIgnoreCase(c.getCountry())))
            expected = true;

        return expected == isMenuLinkPresent(link);
    }
    public void click_reward_link(String link){
        User signedInUser = (User ) stateHolder.get("signedUser");
        Country c = (Country) stateHolder.get("context");
        boolean rewardLinkShouldExists = ((signedInUser.getUserCategory().equalsIgnoreCase(User.CAT_LOYALTY)) && "us".equalsIgnoreCase(c.getCountry()));
        if (rewardLinkShouldExists){
            clickLeftNavLinks(link);
        }

    }

    public boolean isMenuLinkPresent(String link) {
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(accountNavigationSection));
        try {
            wait.until(ExpectedConditions.visibilityOf(accountNavigationSection));
            WebElement linksTray = accountNavigationSection.findElement(By.className("account__navigation__items"));
            WebElement linkElement = linksTray.findElement(By.xpath("//li[text() ='"+link+"']"));
            return (linkElement.isDisplayed());
        } catch (NoSuchElementException e) {
            return false;
        }
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


        information = accountDetailForm.findElement(By.xpath("//span[@class='my-details-form__selected-country']"));
        logger.debug("User details country: {}", information.getText());
        userDetails.put(USER_DETAILS_COUNTRY, information.getText());

        return userDetails;
    }

    private WebElement getformElement(String fieldLabel) {
        WebElement formElement;
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(accountDetailForm));
        switch (fieldLabel) {
            case "first name":
                formElement = accountDetailForm.findElement(By.id("my-details-form__first-name"));
                break;
            case "last name":
                formElement = accountDetailForm.findElement(By.id("my-details-form__last-name"));
                break;
            case "email":
                formElement = accountDetailForm.findElement(By.id("my-details-form__email"));
                break;
            case "country":
                formElement = accountDetailForm.findElement(By.className("my-details-form__country_select"));
                break;
            case "save button":
                formElement = accountDetailForm.findElement(By.className("my-details-form__btn-submit"));
                break;
            case "change password":
                formElement = accountDetailForm.findElement(By.className("my-details-form__change-password-link"));
                break;
            case "Old password":
                formElement = accountDetailForm.findElement(By.id("my-details-form__old-password"));
                break;
            case "New password":
                formElement = accountDetailForm.findElement(By.id("my-details-form__new-password"));
                break;
            case "re-enter password":
                formElement = accountDetailForm.findElement(By.id("my-details-form__confirm-password"));
                break;
            case "Birth":
                formElement = accountDetailForm.findElement(By.className("my-details-form__birthday-wrapper"));
                break;
            default:
                return null;
        }
        return formElement;
    }


}
