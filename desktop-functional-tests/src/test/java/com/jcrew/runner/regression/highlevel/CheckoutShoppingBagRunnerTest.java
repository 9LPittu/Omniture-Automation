package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/Checkout/ShoppingBag"},
        tags = {"@ShoppingBag","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-shoppingbag.json",
                "pretty",
                "html:target/cucumber/regression-shoppingbag"
        })
/**
 * Created by nadiapaolagarcia on 5/16/16.
 */
public class CheckoutShoppingBagRunnerTest {
}
