package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/Checkout/Registered"},
        tags = {"@RegisteredCheckout"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-registered.json",
                "pretty",
                "html:target/cucumber/regression-registered"
        })
/**
 * Created by nadiapaolagarcia on 5/16/16.
 */
public class CheckoutRegisteredRunnerTest {
}
