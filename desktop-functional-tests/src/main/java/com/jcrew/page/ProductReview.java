package com.jcrew.page;


import com.jcrew.utils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by 9msyed on 10/12/2016.
 */
public class ProductReview extends PageObject{
    @FindBy(id = "BVButtonPreviewID")
    private WebElement preview_review;
    @FindBy(id = "BVSubmissionContainer")
    private WebElement submissionContainer;

    public ProductReview(WebDriver driver){
        super(driver);
        Util.waitForPageFullyLoaded(driver);
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(preview_review));
    }
    public boolean isProductReview() {
        return submissionContainer.isDisplayed();
    }
}
