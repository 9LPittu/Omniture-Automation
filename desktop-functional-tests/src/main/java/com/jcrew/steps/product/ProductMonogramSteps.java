package com.jcrew.steps.product;

import com.jcrew.page.product.ProductMonogram;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

/**
 * Created by ngarcia on 4/4/17.
 */
public class ProductMonogramSteps extends DriverFactory {
    private ProductMonogram monogram = new ProductMonogram(getDriver());

    @When("User fills monogram options")
    public void fill_options() {
        monogram.selectOptions();
    }
}
