package com.jcrew.steps.product;

import com.jcrew.page.product.ProductDetailsActions;
import com.jcrew.page.product.ProductDetailsReview;
import com.jcrew.page.product.ProductDetailsSizeFit;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 4/10/17.
 */
public class ProductDetailsSizeFitSteps extends DriverFactory {
    private ProductDetailsSizeFit sizeFit = new ProductDetailsSizeFit(getDriver());

    @Then("^Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS$")
    public void verify_elements_layout_PDP(){

        if (sizeFit.hasProductDetails()) {
            int sizeFitY = sizeFit.getYCoordinate();
            int productDetailsY = sizeFit.getYCoordinate("PRODUCT DETAILS");

            ProductDetailsActions productDetailsActions = new ProductDetailsActions(getDriver());
            int addToBagY = productDetailsActions.getYCoordinate();

            assertTrue("Verify SIZE & FIT is displayed between ADD TO BAG and PRODUCT DETAILS",
                    addToBagY < sizeFitY & sizeFitY < productDetailsY);
        }
    }

    @Then("^Verify PRODUCT DETAILS is displayed between SIZE & FIT and reviews$")
    public void verify_product_details_place() {
        int productDetailsY = sizeFit.getYCoordinate("PRODUCT DETAILS");
        int sizeFitY = sizeFit.getYCoordinate();

        ProductDetailsReview review = new ProductDetailsReview(getDriver());
        int reviewsY = review.getYCoordinate();

        assertTrue("Verify PRODUCT DETAILS is displayed between SIZE & FIT and REVIEWS",
                sizeFitY < productDetailsY & productDetailsY < reviewsY);
    }

    @Then("Verify product size and fit shows overall fit slider")
    public void shows_slider() {
        assertTrue("Product shows overall fit slider", sizeFit.hasSlider());
    }
}
