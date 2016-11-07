package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/Checkout/Registered"},
        tags = {"@RegisteredCheckout", "@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-registered.json",
                "pretty",
                "html:target/cucumber/regression-registered"
        })

public class CheckoutRegisteredRunnerTest {
}
