package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/Checkout/ShoppingBag"},
        tags = {"@ShoppingBag"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-shoppingbag.json",
                "pretty",
                "html:target/cucumber/regression-shoppingbag"
        })

public class CheckoutShoppingBagRunnerTest {
}
