package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/checkout/ShoppingBag"},
        tags = {"@ShoppingBag1"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-fa-regression-shoppingbag1.json",
                "pretty",
                "html:target/cucumber/fa-regression-shoppingbag1"
        })

public class CheckoutShopping1BagRunnerTest {
}