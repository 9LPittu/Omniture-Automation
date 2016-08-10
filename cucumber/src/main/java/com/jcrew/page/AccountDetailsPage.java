package com.jcrew.page;

import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

    public void selectFromList(String value){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(accountNavSection));
        WebElement navList = accountNavSection.findElement(By.className("account__selected-nav-item"));
        openMyDetailsList(navList);
        WebElement item = navList.findElement(By.xpath("//li[contains(text(), '"+value+"')]"));
        item.click();
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


   private boolean getState(WebElement element,String testType){
       if(testType.equalsIgnoreCase("isDisplayed")){

           return element.isDisplayed();
       }else if(testType.equalsIgnoreCase("isEnabled")){
           return element.isEnabled();
       }else{
           return false;
       }
   }
    public boolean validateField(String fieldName,String testType){
        WebElement field;
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailForm));
        switch (fieldName){
            case "first name":
                field = myDetailForm.findElement(By.id("my-details-form__first-name"));
                break;
            case "last name":
                field = myDetailForm.findElement(By.id("my-details-form__last-name"));
                break;
            case "email":
                field = myDetailForm.findElement(By.id("my-details-form__email"));
                break;
            case "country":
                field = myDetailForm.findElement(By.className("my-details-form__country_select"));
                break;
            case "save button":
                field = myDetailForm.findElement(By.className("my-details-form__btn-submit"));
                break;
            case "Old password":
                field = myDetailForm.findElement(By.id("my-details-form__old-password"));
                break;
            case "New password":
                field = myDetailForm.findElement(By.id("my-details-form__old-password"));
                break;
            case "re-enter password":
                field = myDetailForm.findElement(By.id("my-details-form__confirm-password"));
                break;
            case "Birth":
                WebElement birthWrap = myDetailForm.findElement(By.className("my-details-form__birthday-wrapper"));
                WebElement monthList = birthWrap.findElement(By.id("my-details-form__birthday__month-list"));
                WebElement dateList  = birthWrap.findElement(By.id("my-details-form__birthday__day-list"));

                return (getState(monthList,testType) && getState(dateList,testType));

            default:
                return false;
        }
        return getState(field,testType);
     }
        public void clickChangePassword(){
            WebElement changePassword = myDetailForm.findElement(By.className("my-details-form__change-password-link"));
            changePassword.click();
        }
    public boolean isBirthFieldStatusValid() {

        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailForm));

        WebElement birthWrap = myDetailForm.findElement(By.className("my-details-form__birthday-wrapper"));

        WebElement monthSpan = birthWrap.findElement(By.className("my-details-form__birthday-selected-month"));
        WebElement daySpan = birthWrap.findElement(By.className("my-details-form__birthday-selected-day"));

        String monthClass  = birthWrap.findElement(By.id("my-details-form__birthday__month-list")).getAttribute("class");
        String dateClass  = birthWrap.findElement(By.id("my-details-form__birthday__day-list")).getAttribute("class");

        if((monthSpan.getText().equalsIgnoreCase("Month")) && daySpan.getText().equalsIgnoreCase("Day")){
            logger.info("Birth fields not set for the user {}, expected enabled",stateHolder.get("sidecarusername"));
             return ( !(monthClass.contains("is-disabled")) & !(dateClass.contains("is-disabled")) );

        } else{
            logger.info("Birth fields are set {}, {} for the user {}, expected disabled",monthSpan.getText(),daySpan.getText(),stateHolder.get("sidecarusername"));
            return ( (monthClass.contains("is-disabled")) & (dateClass.contains("is-disabled")) );
        }

    }

}
