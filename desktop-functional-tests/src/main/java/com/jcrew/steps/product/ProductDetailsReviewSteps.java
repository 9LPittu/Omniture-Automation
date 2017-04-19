package com.jcrew.steps.product;

import com.jcrew.page.product.ProductDetailsReview;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 4/3/17.
 */
public class ProductDetailsReviewSteps extends DriverFactory {
    private ProductDetailsReview review = new ProductDetailsReview(getDriver());

    @When("User clicks on write a review button")
    public void write_review_button_pressed(){
        review.click_write_review();
    }

    @Then("Verify product review section is displayed")
    public void review_section_displayed() {
        assertTrue("PDP shows review section", review.isReviewSectionDisplayed());
    }

    @Then("Verify reviews summary is displayed")
    public void reviews_summary() {
        assertTrue("Product reivew summary is dislayed", review.isSummaryDisplayed());
    }
}
