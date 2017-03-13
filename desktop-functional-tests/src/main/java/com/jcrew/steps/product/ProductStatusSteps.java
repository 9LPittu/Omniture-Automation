package com.jcrew.steps.product;

import com.jcrew.page.product.ProductDetailSoldOut;
import com.jcrew.steps.ProductDetailSteps;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

/**
 * Created by ngarcia on 10/6/16.
 */
public class ProductStatusSteps extends DriverFactory {

    @When("User adds to bag if product is not sold out")
    public void pdp_content() {
        ProductDetailSoldOut soldOut = new ProductDetailSoldOut(getDriver());

        if (!soldOut.isSoldOut()) {
            ProductDetailSteps pdpSteps = new ProductDetailSteps();
            pdpSteps.user_selects_random_color();
            pdpSteps.user_selects_random_size();
            pdpSteps.user_adds_product_to_bag();
        }
    }
}
