package com.jcrew.steps;

import com.jcrew.page.ProductReview;
import cucumber.api.java.en.Then;
import com.jcrew.utils.DriverFactory;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9msyed on 10/12/2016.
 */
public class ProductReviewSteps  extends DriverFactory {
    private ProductReview review = new ProductReview(getDriver());

    @Then("Verify product review page is displayed")
    public void review_page_is_displayed() {
        assertTrue("Product review page is not displayed", review.isProductReview());
    }
}
