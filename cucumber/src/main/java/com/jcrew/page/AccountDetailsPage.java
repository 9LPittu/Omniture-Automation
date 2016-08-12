package com.jcrew.page;


import com.jcrew.util.StateHolder;
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

    @FindBy(id="account_navigation")
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
    public boolean isMyDetailPage(){
        try {
            Util.waitForPageFullyLoaded(driver);
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailForm));
            return myDetailForm.isDisplayed();
        }catch (Exception e){

            return false;
        }
    }
    public void selectDate(String dateType,String value){

        WebElement birthWrap=getformElement("Birth");
        WebElement list;
        if("Month".equalsIgnoreCase(dateType)){
            list=birthWrap.findElement(By.id(month));
        }else{
            list=birthWrap.findElement(By.id(day));
        }
        list.click();
        WebElement item = list.findElement(By.xpath("//li[contains(text(), '"+value+"')]"));
        item.click();
        Util.waitForPageFullyLoaded(driver);
    }


    public void selectFromList(String value){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(accountNavSection));
        WebElement navList = accountNavSection.findElement(By.className("account__selected-nav-item"));
        openMyDetailsList(navList);
        WebElement item = navList.findElement(By.xpath("//li[contains(text(), '"+value+"')]"));
        item.click();
    }
    public boolean isOptionAvailable(String value){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(accountNavSection));
        WebElement navList = accountNavSection.findElement(By.className("account__selected-nav-item"));
        try{
            WebElement item = navList.findElement(By.xpath("//li[contains(text(), '"+value+"')]"));
            return true;
        }catch (Exception e){
            return false;
        }

    }

   public void updateDetails(String fieldLabel){
       WebElement formElement;
       String value;
       Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailForm));
       formElement=getformElement(fieldLabel);
       value=formElement.getAttribute("value");
       formElement.clear();
       formElement.sendKeys("new"+value);
   }
   public void saveUpdates(){
       Util.waitForPageFullyLoaded(driver);
       getformElement("save button").click();
       Util.waitForPageFullyLoaded(driver);
   }
    public String getConfirmatonMsg(){
        WebElement formElement=myDetailSection.findElement(By.className("my-details-form__confirm-message"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(formElement));

        return formElement.getText();
    }


    public String getBirthErrorMessage(){
        return getformElement("Birth").findElement(By.xpath("following-sibling::span")).getText();
    }

    public String getErrorMessage(String fieldLabel){
        WebElement formElement;
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailForm));
        formElement=getformElement(fieldLabel);
        formElement.clear();
        formElement.sendKeys(Keys.TAB);
        formElement.click();
        WebElement span = formElement.findElement(By.xpath("following-sibling::span"));
        return span.getText();
    }

        public void clickChangePassword(){
            WebElement changePassword = myDetailForm.findElement(By.className("my-details-form__change-password-link"));
            changePassword.click();
        }
    public boolean isBirthFieldsDisabled(){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailForm));
        WebElement birthWrap = getformElement("Birth");
        String monthClass  = birthWrap.findElement(By.id(month)).getAttribute("class");
        String dateClass  = birthWrap.findElement(By.id(day)).getAttribute("class");

        return ( (monthClass.contains("is-disabled")) & (dateClass.contains("is-disabled")) );
    }


    private WebElement getformElement(String fieldLabel){
        WebElement formElement;
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailForm));
        switch (fieldLabel){
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
        return formElement;
    }

    private void openMyDetailsList(WebElement navList){
        int attempts = 0;
        boolean success = false;

        while (attempts <= 2 && !success) {
            try {
                navList.click();
                if(navList.getAttribute("style").contains("block")){
                    success = true;
                }
            } catch (Exception exception) {
                logger.debug("Exception while trying to open list box");
            }
            attempts++;
        }
    }

}
