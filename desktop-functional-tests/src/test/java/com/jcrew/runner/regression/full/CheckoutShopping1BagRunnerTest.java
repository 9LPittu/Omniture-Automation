package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/Checkout/ShoppingBag"},
        tags = {"@ShoppingBag1"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-shoppingbag1.json",
                "pretty",
                "html:target/cucumber/regression-shoppingbag1"
        })
/**
 * Created by nadiapaolagarcia on 5/16/16.
 */
public class CheckoutShopping1BagRunnerTest {
}
