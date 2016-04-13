package com.jcrew.steps;

import com.jcrew.page.ProductDetails;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ProductDetailSteps extends DriverFactory {
    ProductDetails productDetails = new ProductDetails(getDriver());

    @When("User selects random color")
    public void user_selects_random_color() {
        productDetails.selectRandomColor();
    }

    @When("User selects random size")
    public void user_selects_random_size() {
        productDetails.selectRandomSize();
    }

    @When("User selects random quantity")
    public void user_selects_random_quantity() {
        productDetails.selectRandomQty();
    }

    @When("User adds product to bag")
    public void user_adds_product_to_bag() {
        productDetails.addToBag();
    }


}
