package com.jcrew.page.product;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by ngarcia on 4/3/17.
 */
public class ProductDetailsReview extends ProductDetails {

    @FindBy(id = "c-product__reviews--ratings-summary")
    private WebElement reviewSummary;
    @FindBy(id = "c-product__reviews--ratings")
    private WebElement reviewSection;

    public ProductDetailsReview(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(reviewSection));
    }

    public boolean isReviewSectionDisplayed() {
        return reviewSection.isDisplayed();
    }

    public void click_write_review(){
        WebElement writeReviewButton;
        List<WebElement> noReviews = reviewSection.findElements(By.id("BVRRDisplayContentNoReviewsID"));

        if(noReviews.size() > 0) {
            logger.debug("Product has no reviews, this is the first review");
            writeReviewButton = noReviews.get(0).findElement(By.tagName("a"));
        } else {
            WebElement reviewContainer = wait.until(
                    ExpectedConditions.presenceOfNestedElementLocatedBy(reviewSection, By.id("BVRRContainer")));
            WebElement reviewId = reviewContainer.findElement(By.id("BVRRRatingSummaryLinkWriteID"));
            writeReviewButton = reviewId.findElement(By.tagName("a"));
        }

        wait.until(ExpectedConditions.visibilityOf(writeReviewButton));
        writeReviewButton.click();


    }
}
