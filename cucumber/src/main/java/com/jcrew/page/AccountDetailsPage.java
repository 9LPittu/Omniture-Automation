package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by 9msyed on 8/8/2016.
 */
public class AccountDetailsPage {
    private final WebDriver driver;

    @FindBy(className = "account_content")
    private WebElement myDetailSection;



    public AccountDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }
    public boolean isMyDetailPage(){
        try {
            Util.waitForPageFullyLoaded(driver);
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailSection));
            myDetailSection.findElement(By.className("my-details-form"));
            return myDetailSection.isDisplayed();
        }catch (Exception e){
            return false;
        }
    }
    private boolean getAttrib(WebElement element,String attrib){
        boolean result = false;
        switch (attrib){
            case "isDisplayed":
                result = element.isDisplayed();
                break;
            case "isEnabled":
                result = element.isEnabled();
                break;
            default:
                result =false;
                break;
        }
        return result;
    }
    public boolean validateField(String fieldName,String testType){
        WebElement field;
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myDetailSection));
        WebElement myDetailForm=myDetailSection.findElement(By.className("my-details-form"));
        switch (fieldName){
            case "first_name":
                field =  myDetailForm.findElement(By.id("my-details-form__first-name"));
                break;
            case "last_name":
                field =  myDetailForm.findElement(By.id("my-details-form__last-name"));
                break;
            case "email":
                field =  myDetailForm.findElement(By.id("my-details-form__email"));
                break;
            case "save_button":
                field =  myDetailForm.findElement(By.className("my-details-form__btn-submit"));
                break;
            default:
                return false;
        }
        return getAttrib(field,testType);



    }
}
